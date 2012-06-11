package tld.www.morsecodeime;

import java.lang.String;
import java.lang.StringBuilder;
import java.lang.CharSequence;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.os.Vibrator;
import android.widget.Toast;
import android.view.View;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView.OnKeyboardActionListener;
import android.inputmethodservice.InputMethodService;

public class MorseCodeIME extends InputMethodService implements OnKeyboardActionListener {
  private final byte VIBRATE_LENGTH = 40; // milliseconds
  private final MorseCodeTranslator translator = new MorseCodeTranslator();
  // these three constants are manually mirrored in the
  // MorseCodeKeyboardView class
  private final int SHIFT_OFF = 0;
  private final int SHIFT_ON = 1;
  private final int SHIFT_CAPS = 2;

  private StringBuilder text = new StringBuilder(0);
  private boolean vibrationEnabled = true;
  private boolean autoPunctuate = true;
  private boolean autoCapitalize = true;
  private Vibrator vib = null;
  private boolean shifted = false;
  private boolean capsLocked = false;
  private boolean showReference = false;
  private boolean swipeClose = false;
  private MorseCodeKeyboard morseCodeKeyboard = null;
  private MorseCodeKeyboardView morseCodeKeyboardView = null;

  @Override public void onCreate() {
    super.onCreate();
    vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
  }
  @Override public void onInitializeInterface() {
    morseCodeKeyboard = new MorseCodeKeyboard(this, R.layout.morse);
  }
  @Override public View onCreateInputView() {
    morseCodeKeyboardView = (MorseCodeKeyboardView) getLayoutInflater().inflate(R.layout.input, null);
    morseCodeKeyboardView.setOnKeyboardActionListener(this);
    morseCodeKeyboardView.setKeyboard(morseCodeKeyboard);
    return morseCodeKeyboardView;
  }
  @Override public void onStartInput(EditorInfo attribute, boolean restarting) {
    // get the user's desired preferences
    SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(this);
    vibrationEnabled = prefs.getBoolean("allow_vibrate", false);
    autoPunctuate = prefs.getBoolean("auto_punct", false);
    autoCapitalize = prefs.getBoolean("auto_caps", false);
    showReference = prefs.getBoolean("show_help", false);
    swipeClose = prefs.getBoolean("swipe_close", false);
    // but modify the desired preferences based on the type of field
    // the user is entering into
    final int inputTypeClass = attribute.inputType & EditorInfo.TYPE_MASK_CLASS;
    final int inputTypeVariation = attribute.inputType & EditorInfo.TYPE_MASK_VARIATION;
    if (inputTypeClass == EditorInfo.TYPE_CLASS_TEXT) {
      // check for variations in the text type because urls, emails, and web addresses,
      // and passwords should not assume a leading capital letter
      switch (inputTypeVariation) {
        case EditorInfo.TYPE_TEXT_VARIATION_EMAIL_ADDRESS:
        case EditorInfo.TYPE_TEXT_VARIATION_URI:
        case EditorInfo.TYPE_TEXT_VARIATION_PASSWORD:
        case EditorInfo.TYPE_TEXT_VARIATION_FILTER:
        case EditorInfo.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD:
          autoCapitalize = false;
          break;
      }
    }
    else if (inputTypeClass == EditorInfo.TYPE_CLASS_PHONE || inputTypeClass == EditorInfo.TYPE_CLASS_NUMBER) {
      // disable auto caps
      autoCapitalize = false;
    }
    // reset the input
    text.setLength(0);
    initShift(autoCapitalize);
  }
  @Override public void onKey(int primaryCode, int[] keyCodes) {
    switch (primaryCode) {
      case 1:
        text.append(".");
        break;
      case 2:
        text.append("-");
        break;
      case Keyboard.KEYCODE_SHIFT:
        toggleShift();
        break;
      case 32:
        handleSpace();
        break;
      case Keyboard.KEYCODE_DELETE:
        handleBackspace();
        break;
      default: break;
    }
  }
  @Override public void onRelease(int primaryCode) {
  }
  @Override public void onPress(int primaryCode) {
    // vibrate on press NOT on key to avoid continuous
    // vibration when repeating keys -- DEL and space
    if (vibrationEnabled) vib.vibrate(VIBRATE_LENGTH);
  }
  @Override public void onText(CharSequence text) {
  }
  @Override public void swipeDown() {
    if (swipeClose) handleClose();
  }
  @Override public void swipeUp() {
    if (showReference) {
      handleClose();
      Intent i = new Intent(this, Reference.class);
      i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
      startActivity(i);
    }
  }
  /*
    swipeLeft and swipeRight are two gestures not
    really required, but can be used maybe in the future
  */
  @Override public void swipeLeft() {
  }
  @Override public void swipeRight() {
  }
  private void commitText(boolean uppercase) {
    if (text.length() <= 0) return;
    InputConnection ic = getCurrentInputConnection();
    ic.commitText(translator.fromMorse(text.toString(), uppercase), /* new cursor pos */ 1);
    text.setLength(0);
  }
  private void commitText(String s) {
    InputConnection ic = getCurrentInputConnection();
    ic.commitText(s, 0);
    text.setLength(0);
  }
  private void handleClose() {
    requestHideSelf(0);
    morseCodeKeyboardView.closing();
  }
  private void handleBackspace() {
    if (text.length() == 0) {
      getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
    }
    text.setLength(0);
    if (autoCapitalize) {
      // if there is no text before (i.e the field is empty) reset the shift
      initShift(getCurrentInputConnection().getTextBeforeCursor(1, /* flags */ 0).length() != 1);
    }
    else initShift(false);
  }
  private void handleSpace() {
    if (text.length() == 0) {
      final InputConnection ic = getCurrentInputConnection();
      final String previousCharacter = ic.getTextBeforeCursor(1, 0).toString();
      if (autoPunctuate && previousCharacter.equalsIgnoreCase(" ") && ic.deleteSurroundingText(1, 0)) {
        commitText(". ");
        if (autoCapitalize && !capsLocked) {
          shifted = true;
          capsLocked = false;
          morseCodeKeyboardView.setShifted(SHIFT_ON);
          return;
        }
      }
      else commitText(" ");
    }
    else commitText(shifted);
    if (shifted && !capsLocked) {
      shifted = false;
      capsLocked = false;
      morseCodeKeyboardView.setShifted(SHIFT_OFF);
    }
  }
  private void toggleShift() {
    if (capsLocked) {
      shifted = false;
      capsLocked = false;
      morseCodeKeyboardView.setShifted(SHIFT_OFF);
      return;
    }
    if (shifted) {
      shifted = true;
      capsLocked = true;
      morseCodeKeyboardView.setShifted(SHIFT_CAPS);
      return;
    }
    shifted = true;
    morseCodeKeyboardView.setShifted(SHIFT_ON);
  }
  private void initShift(boolean shifted) {
    this.shifted = shifted;
    capsLocked = false;
    if (null != morseCodeKeyboardView) morseCodeKeyboardView.setShifted((shifted) ? SHIFT_ON : SHIFT_OFF);
  }
  // toast a message - used for simple debugging
  private void toastMsg(String msg) {
    Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
  }
}

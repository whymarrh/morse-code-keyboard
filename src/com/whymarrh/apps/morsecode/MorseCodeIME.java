package com.whymarrh.apps.morsecode;

import java.lang.Integer;
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

	private final MorseCodeTranslator translator = new MorseCodeTranslator();
	// these three constants are manually mirrored in the
	// MorseCodeKeyboardView class
	private final int SHIFT_OFF = 0;
	private final int SHIFT_ON = 1;
	private final int SHIFT_CAPS = 2;

	private StringBuilder text = new StringBuilder(0);
	private boolean vibrationEnabled = true;
	private byte vibrateLength = 40; // milliseconds
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
		vibrateLength = (byte) Integer.parseInt(prefs.getString("vibrate_length", "40"));
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
				default:
					// do nothing
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
			default:
				break;
		}
	}
	@Override public void onRelease(int primaryCode) {
		// do nothing
	}
	@Override public void onPress(int primaryCode) {
		// vibrate in onPress, NOT in onKey to avoid continuous
		// vibration when repeating keys - i.e. DEL and space
		if (vibrationEnabled) {
			vib.vibrate(vibrateLength);
		}
	}
	@Override public void onText(CharSequence text) {
		// do nothing
	}
	@Override public void swipeLeft() {
		// do nothing
	}
	@Override public void swipeRight() {
		// do nothing
	}
	@Override public void swipeDown() {
		if (swipeClose) {
			handleClose();
		}
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
	 * Commits the composed text (i.e. the dots and dashes
	 * that make up a letter or prosign) to the current input
	 * connection.
	 * @param uppercase whether the character represented should be commited as uppercase or lowercase.
	 */
	private void commitText(boolean uppercase) {
		if (text.length() <= 0) return;
		InputConnection ic = getCurrentInputConnection();
		// check for prosigns first and foremost
		if (translator.fromMorse(text.toString(), false) == "Newline") {
			ic.sendKeyEvent( new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_ENTER) );
		}
		else if (translator.fromMorse(text.toString(), false) == "EOT") {
			// perform the editor action as determined by the editor itself
			ic.performEditorAction(EditorInfo.IME_MASK_ACTION | EditorInfo.IME_FLAG_NO_ENTER_ACTION);
		}
		else {
			// if the composed dots and dashed do not represent a procedural signal
			ic.commitText(translator.fromMorse(text.toString(), uppercase), /* new cursor pos */ 1);
		}
		text.setLength(0);
	}
	/*
	 * Commits the passed text to the text box and reset the
	 * cursor position. Used to commit a predetermined string of
	 * text instead of something the user is composing.
	 * @param s the text to commit
	 */
	private void commitText(String s) {
		InputConnection ic = getCurrentInputConnection();
		ic.commitText(s, /* new cursor pos */1);
		text.setLength(0);
	}
	/*
	 * Handle the closing of this IME.
	 */
	private void handleClose() {
		requestHideSelf(0);
		morseCodeKeyboardView.closing();
	}
	/*
	 * Handle the backspace action.
	 */
	private void handleBackspace() {
		if (text.length() == 0) {
			// delete the letter to the left of the cursor position
			getCurrentInputConnection().sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, KeyEvent.KEYCODE_DEL));
		}
		text.setLength(0);
		if (!capsLocked && autoCapitalize) {
			initShift(getCurrentInputConnection().getTextBeforeCursor(1, /* flags */ 0).length() != 1);
		}
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
			else {
				commitText(" ");
			}
		}
		else {
			commitText(shifted);
		}
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
		if (null != morseCodeKeyboardView) {
			morseCodeKeyboardView.setShifted((shifted) ? SHIFT_ON : SHIFT_OFF);
		}
	}
	/*
	 * Show a simple toast message for debugging
	 * @param msg the debug message to show
	 */
	private void toastMsg(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}

}

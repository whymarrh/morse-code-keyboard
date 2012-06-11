package tld.www.morsecodeime;

import java.lang.CharSequence;

import android.util.AttributeSet;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.inputmethodservice.Keyboard;
import android.inputmethodservice.Keyboard.Key;
import android.inputmethodservice.KeyboardView;

public class MorseCodeKeyboardView extends KeyboardView {
  // these three constants are manually mirrored in the
  // MorseCodeIME class
  private final int SHIFT_OFF = 0;
  private final int SHIFT_ON = 1;
  private final int SHIFT_CAPS = 2;
  private Resources res = null;
  private Drawable capsIcon = null;
  private Drawable shiftIcon = null;
  private Key shiftKey = null;
  public MorseCodeKeyboardView(Context context, AttributeSet attrs) {
    super(context, attrs);
    setPreviewEnabled(false);
    res = context.getResources();
  }
  public MorseCodeKeyboardView(Context context, AttributeSet attrs, int defStyle) {
    super(context, attrs, defStyle);
    setPreviewEnabled(false);
    res = context.getResources();
  }
  public void setShifted(int state) {
    if (state == SHIFT_OFF) {
      shiftKey.icon = shiftIcon;
      super.setShifted(false);
    }
    else if (state == SHIFT_ON) {
      shiftKey.icon = shiftIcon;
      super.setShifted(true);
    }
    else {
      shiftKey.icon = capsIcon;
      super.setShifted(true);
    }
  }
  @Override public void setKeyboard(Keyboard keyboard) {
    super.setKeyboard(keyboard);
    capsIcon = res.getDrawable(R.drawable.sym_keyboard_shift_locked);
    shiftKey = ((MorseCodeKeyboard) getKeyboard()).getKeys().get(((MorseCodeKeyboard) getKeyboard()).getShiftKeyIndex());
    shiftIcon = shiftKey.icon;
  }
  @Override protected boolean onLongPress(Key key) {
    return super.onLongPress(key);
  }
}

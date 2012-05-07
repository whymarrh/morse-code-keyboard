package tld.www.morsecodeime;

import java.lang.CharSequence;
import android.content.Context;
import android.inputmethodservice.Keyboard;

public class MorseCodeKeyboard extends Keyboard {
  public MorseCodeKeyboard(Context context, int xmlLayoutResId) {
    super(context, xmlLayoutResId);
  }
  public MorseCodeKeyboard(Context context, int layoutTemplateResId, CharSequence characters, int columns, int horizontalPadding) {
    super(context, layoutTemplateResId, characters, columns, horizontalPadding);
  }
}
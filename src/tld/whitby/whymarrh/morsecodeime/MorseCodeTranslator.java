package tld.whitby.whymarrh.morsecodeime;

import java.lang.String;
import java.lang.Object;
import java.util.Map;
import java.util.LinkedHashMap;

class MorseCodeTranslator extends Object {
  private final String separator = "/";
  public final LinkedHashMap<String, String> codes = new LinkedHashMap<String, String>();
  public MorseCodeTranslator() {
    codes.put(".-", "a");
    codes.put("-...", "b");
    codes.put("-.-.", "c");
    codes.put("-..", "d");
    codes.put(".", "e");
    codes.put("..-.", "f");
    codes.put("--.", "g");
    codes.put("....", "h");
    codes.put("..", "i");
    codes.put(".---", "j");
    codes.put("-.-", "k");
    codes.put(".-..", "l");
    codes.put("--", "m");
    codes.put("-.", "n");
    codes.put("---", "o");
    codes.put(".--.", "p");
    codes.put("--.-", "q");
    codes.put(".-.", "r");
    codes.put("...", "s");
    codes.put("-", "t");
    codes.put("..-", "u");
    codes.put("...-", "v");
    codes.put(".--", "w");
    codes.put("-..-", "x");
    codes.put("-.--", "y");
    codes.put("--..", "z");

    // the digits
    codes.put(".----", "1");
    codes.put("..---", "2");
    codes.put("...--", "3");
    codes.put("....-", "4");
    codes.put(".....", "5");
    codes.put("-....", "6");
    codes.put("--...", "7");
    codes.put("---..", "8");
    codes.put("----.", "9");
    codes.put("-----", "0");
    
    // there are extra codes that
    // can be mapped to common punctuation
    codes.put(".-.-.-", ".");
    codes.put("--..--", ",");
    codes.put("..--..", "?");
    codes.put(".----.", "'");
    codes.put("-.-.--", "!");
    codes.put("-..-.", "/");
    codes.put("-.--.", "(");
    codes.put("-.--.-", ")");
    codes.put(".-...", "&");
    codes.put("---...", ":");
    codes.put("-.-.-.", ";");
    codes.put("-...-", "=");
    codes.put(".-.-.", "+");
    codes.put("-....-", "-");
    codes.put("..--.-", "_");
    codes.put(".-..-.", "\"");
    codes.put("...-..-", "$");
    codes.put(".--.-.", "@");
  }
  public String fromMorse(String s, boolean uppercase) {
    for (Map.Entry<String, String> entry : codes.entrySet()) {
      if (s.equalsIgnoreCase(entry.getKey()))
        return (uppercase) ? entry.getValue().toUpperCase() : entry.getValue();
    }
    return "";
  }
}
package tld.www.morsecodeime;

import java.util.Map;

import android.content.Context;
import android.content.res.Resources;
import android.app.Activity;
import android.os.Bundle;
import android.widget.TableLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TableRow.LayoutParams;
import android.widget.TextView;
import android.util.TypedValue;
import android.graphics.Typeface;

public class Reference extends Activity {
  private class Row extends TableRow {
    public Row(Context context, String a, String b, int index) {
      super(context);
      final int gravity = 0x11;
      final Resources res = context.getResources();
      final int bw = (int) res.getDimension(R.dimen.border_width);
      final int cp = (int) res.getDimension(R.dimen.cell_padding);
      final float lw = 0.4f;
      final float rw = 0.6f;
      final float ts = 16f;
      final boolean first = (index == 0);
      final boolean isAlt = (index % 2 == 0);
      setLayoutParams(new LayoutParams(LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT));
      if (!first) {
        setPadding(0, bw, 0, 0);
      }
      // add the two text views
      // braces used for scope retention
      {
        TextView t = new TextView(context);
        final int column = 1;
        LayoutParams lps = new LayoutParams(column);
        lps.setMargins(0, 0, bw, 0);
        lps.weight = lw;
        t.setPadding(cp, cp, cp, cp);
        t.setLayoutParams(lps);
        t.setText(a);
        t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, ts);
        t.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD));
        t.setTextColor(res.getColor(R.color.left_cell_text));
        t.setBackgroundResource(R.color.left_cell_bg);
        t.setGravity(gravity);
        addView(t);
      }{
        TextView t = new TextView(context);
        final int column = 1;
        LayoutParams lps = new LayoutParams(column);
        lps.setMargins(0, 0, bw, 0);
        lps.weight = rw;
        t.setPadding(cp, cp, cp, cp);
        t.setLayoutParams(lps);
        {
          String s = "";
          for (int i = 0; i < b.length(); i++) s += (b.charAt(i) == '.') ? "\u00B7" : "\u2212";
          t.setText(s);
        }
        t.setTextSize(TypedValue.COMPLEX_UNIT_DIP, ts);
        t.setTypeface(Typeface.create(Typeface.MONOSPACE, Typeface.BOLD));
        t.setTextColor(res.getColor(R.color.right_cell_text));
        t.setBackgroundResource((isAlt) ? R.color.right_cell_bg_alt : R.color.right_cell_bg);
        t.setGravity(gravity);
        addView(t);
      }
      return;
    }
  }
  private final MorseCodeTranslator translator = new MorseCodeTranslator();
  private TableLayout tbl = null;
  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.reference);
    tbl = (TableLayout) findViewById(R.id.tbl);
    int count = 0;
    for (Map.Entry<String, String> e : translator.codes.entrySet())
      tbl.addView(new Row(this, e.getValue().toUpperCase(), e.getKey(), count++));
  }
}
package cuit.travelweather.util;

import android.app.Activity;
import android.graphics.Typeface;
import android.widget.TextView;

public class MyFont {

	public static void setTypeface(Activity act, int[] textViewIDs) {
		Typeface tf = Typeface.createFromAsset(act.getAssets(), "font/fz.ttf");
		for (int i : textViewIDs) {
			((TextView) act.findViewById(i)).setTypeface(tf);
		}
	}
}

package cuit.travelweather.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.widget.TextView;

public class MyTextView extends TextView {

	private Typeface tf;

	public MyTextView(Context context) {
		super(context);
		tf = Typeface.createFromAsset(context.getAssets(), "font/fz.ttf");
		// TODO Auto-generated constructor stub
	}

	public MyTextView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		tf = Typeface.createFromAsset(context.getAssets(), "font/fz.ttf");
		// TODO Auto-generated constructor stub
	}

	public MyTextView(Context context, AttributeSet attrs) {
		super(context, attrs);
		tf = Typeface.createFromAsset(context.getAssets(), "font/fz.ttf");
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		this.setTypeface(tf);
		super.onDraw(canvas);
	}

}

package cuit.travelweather.view;

import java.util.ArrayList;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import cuit.travelweather.R;
import cuit.travelweather.util.Constant;

public class TrendView extends View {
	private ArrayList<String> listItem = new ArrayList<String>();
	private Paint mPointPaint = new Paint();
	private Paint mTextPaint = new Paint();
	private Paint mLinePaint = new Paint();
	private Bitmap topPic = BitmapFactory.decodeResource(this.getResources(),
			R.drawable.w1);;
	private Bitmap lowPic = BitmapFactory.decodeResource(this.getResources(),
			R.drawable.w0);;

	public TrendView(Context context) {
		super(context);
	}

	public TrendView(Context context, AttributeSet attrs) {

		super(context, attrs);
	}

	public TrendView(Context context, AttributeSet attrs, int defStyle) {

		super(context, attrs, defStyle);
	}
	
	private static int[] topT = new int[6];
	private static int[] lowT = new int[6];
	
	
	@SuppressLint("DrawAllocation")
	@Override
	protected void onDraw(Canvas canvas) {
		
		// ��������
		 int[] topTem = new int[] {};
		int[] lowTem = new int[] {};
		
		int Tn0 = (int) Double.parseDouble(Constant.temperature_night0);
		int Td0 = (int) Double.parseDouble(Constant.temperature_day0);
		int Tn1 = (int) Double.parseDouble(Constant.temperature_night1);
		int Td1 = (int) Double.parseDouble(Constant.temperature_day1);
		int Tn2 = (int) Double.parseDouble(Constant.temperature_night2);
		int Td2 = (int) Double.parseDouble(Constant.temperature_day2);
		topTem = topT;
		lowTem = getLowT();
		topTem = new int[] {Td0, Td1, Td2, Td0, Td1, Td2   };
		lowTem = new int[] { Tn0, Tn1, Tn2, Tn0, Tn1, Tn2  };
		int radius = 5;// Բ��뾶
		int xOffset = 10;// �ı�xƫ��
		int yOffset = 15;// �ı�yƫ��
		float txtSize = 20f;
		// Paint

		mPointPaint.setColor(Color.WHITE);

		mTextPaint.setColor(Color.WHITE);
		mTextPaint.setTextSize(txtSize);

		mLinePaint.setColor(Color.YELLOW);
		mLinePaint.setAntiAlias(true);
		mLinePaint.setStrokeWidth(3);
		mLinePaint.setStyle(Style.FILL);
		int max = getMax(topTem);
		int min = getMin(lowTem);
		float wOffset = ((float) this.getWidth() / (2 * topTem.length - 1)) * 2;
		int hOffset = 10;
		float tem0Y = this.getHeight() / 2 + (max + min) * hOffset / 2;
		float zoom = wOffset / 2 / topPic.getWidth();
		int width = (int) (topPic.getWidth() * zoom);
		int height = (int) (topPic.getHeight() * zoom);
		for (int i = 0; i < topTem.length - 1; i++) {
			float startX = wOffset / 4 + wOffset * i;
			float startY = tem0Y - topTem[i] * hOffset;
			float stopX = startX + wOffset;
			float stopY = startY + (topTem[i] - topTem[i + 1]) * hOffset;
			canvas.drawLine(startX, startY, stopX, stopY, mLinePaint);
			canvas.drawText(topTem[i] + "��", startX - xOffset,
					startY - yOffset, mTextPaint);
			canvas.drawCircle(startX, startY, radius, mPointPaint);
			canvas.drawBitmap(zoomBitmap(topPic, width, height), startX - width
					/ 2, startY - yOffset * 2 - height, null);
			if (i == lowTem.length - 2) {
				canvas.drawText(topTem[i + 1] + "��", stopX - xOffset, stopY
						- yOffset, mTextPaint);
				canvas.drawCircle(stopX, stopY, radius, mPointPaint);
				canvas.drawBitmap(zoomBitmap(topPic, width, height), stopX
						- width / 2, stopY - yOffset * 2 - height, null);
			}
		}
		mLinePaint.setColor(Color.GREEN);
		for (int i = 0; i < lowTem.length - 1; i++) {
			float startX = wOffset / 4 + wOffset * i;
			float startY = tem0Y - lowTem[i] * hOffset;
			float stopX = startX + wOffset;
			float stopY = startY + (lowTem[i] - lowTem[i + 1]) * hOffset;
			canvas.drawLine(startX, startY, stopX, stopY, mLinePaint);
			canvas.drawText(lowTem[i] + "��", startX - xOffset, startY + yOffset
					+ txtSize, mTextPaint);
			canvas.drawCircle(startX, startY, radius, mPointPaint);
			canvas.drawBitmap(zoomBitmap(lowPic, width, height), startX - width
					/ 2, startY + height, null);
			if (i == lowTem.length - 2) {
				canvas.drawText(lowTem[i + 1] + "��", stopX - xOffset, stopY
						+ yOffset + txtSize, mTextPaint);
				canvas.drawCircle(stopX, stopY, radius, mPointPaint);
				canvas.drawBitmap(zoomBitmap(lowPic, width, height), stopX
						- width / 2, stopY + height, null);
			}
		}

	}

	private int getMin(int[] nums) {
		int res = nums[0];
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] < res) {
				res = nums[i];
			}
		}
		return res;
	}

	private int getMax(int[] nums) {
		int res = nums[0];
		for (int i = 1; i < nums.length; i++) {
			if (nums[i] > res) {
				res = nums[i];
			}
		}
		return res;
	}

	private Bitmap zoomBitmap(Bitmap bitmap, int width, int height) {
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		Matrix matrix = new Matrix();
		float scaleWidth = ((float) width / w);
		float scaleHeight = ((float) height / h);
		matrix.postScale(scaleWidth, scaleHeight);
		Bitmap newbmp = Bitmap.createBitmap(bitmap, 0, 0, w, h, matrix, true);
		return newbmp;
	}

	public  int[] getLowT() {
		return lowT;
	}

	public  void setLowT(int[] lowT) {
		this.lowT = lowT;
	}

	public  int[] getTopT() {
		return topT;
	}

	public void setTopT(int[] topT) {
		this.topT = topT;
	}
	


}

package com.example.PCenter.Record;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.model.XYSeries;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

import android.graphics.Color;
import android.graphics.Paint.Align;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.example.PCenter.BaseActivity;
import com.exam.ThreeSystem.R;

public class Record_char_pesonActivity extends BaseActivity implements OnClickListener{
		private Button back;
		private String [] score;
		private int [] score2;
		private String [] date;
		private TextView head;
		private String number;
		
		private RelativeLayout layout;
		private XYSeries line;
		private XYMultipleSeriesDataset mDataset;
		private XYSeriesRenderer renderer;
		private XYMultipleSeriesRenderer mXYMultipleSeriesRenderer;
		private GraphicalView chart;
		@Override
		protected void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);
			Bundle b=this.getIntent().getExtras();
			score = b.getStringArray("score");
			number = b.getString("number");
			date = b.getStringArray("date");
			setContentView(R.layout.activity_record_char_person);
			init();
			initChart();
		}
		private void initChart() {
			line = new XYSeries("折线");
			renderer = new XYSeriesRenderer();
			//num
//			renderer.setDisplayChartValues(true);
//			renderer.setDisplayChartValuesDistance(30);
//			renderer.setChartValuesTextSize(30);			
			
			mDataset = new XYMultipleSeriesDataset();
			mXYMultipleSeriesRenderer = new XYMultipleSeriesRenderer();
			
			mXYMultipleSeriesRenderer.setApplyBackgroundColor(true);
			mXYMultipleSeriesRenderer.setBackgroundColor(Color.parseColor("#E6E6E6"));
			mXYMultipleSeriesRenderer.setMarginsColor(Color.parseColor("#E6E6E6"));

			initLine(line);
			initRenderer(renderer, Color.RED, PointStyle.CIRCLE, true);
			
			mDataset.addSeries(line);
			mXYMultipleSeriesRenderer.addSeriesRenderer(renderer);
			
			setChartSettings(mXYMultipleSeriesRenderer, "X", "Y", -1, 3, -5, 70, Color.RED);
			chart = ChartFactory.getLineChartView(this, mDataset, mXYMultipleSeriesRenderer);
			layout.addView(chart, new LayoutParams(LayoutParams.MATCH_PARENT,
	                LayoutParams.MATCH_PARENT));
		}
		protected void setChartSettings(XYMultipleSeriesRenderer mXYMultipleSeriesRenderer,
	            String xTitle, String yTitle, double xMin, double xMax,
	            double yMin, double yMax, int axesColor) {
	        mXYMultipleSeriesRenderer.setXAxisMin(xMin);
	        mXYMultipleSeriesRenderer.setLabelsTextSize(15);
	        mXYMultipleSeriesRenderer.setXAxisMax(xMax);
	        mXYMultipleSeriesRenderer.setYAxisMin(yMin);
	        mXYMultipleSeriesRenderer.setYAxisMax(yMax);
	        mXYMultipleSeriesRenderer.setAxesColor(axesColor);
	        mXYMultipleSeriesRenderer.setXLabelsColor(Color.BLACK);
	        mXYMultipleSeriesRenderer.setYLabelsColor(0, Color.BLACK);
	        mXYMultipleSeriesRenderer.setShowGrid(true);
	        mXYMultipleSeriesRenderer.setGridColor(Color.BLUE);//xian
	        mXYMultipleSeriesRenderer.setYLabelsAlign(Align.RIGHT);
	        mXYMultipleSeriesRenderer.setPointSize((float) 5);
	        mXYMultipleSeriesRenderer.setShowLegend(true);
	        mXYMultipleSeriesRenderer.setLegendTextSize(20);
	        if(date.length > 4){
	        	mXYMultipleSeriesRenderer.setPanLimits(new double[] { -1, date.length, -50, 50 });//范围
	        }else{
	        	mXYMultipleSeriesRenderer.setPanLimits(new double[] { -1, 3, -50, 50 });
	        }
	        mXYMultipleSeriesRenderer.setXLabels(0);//无数
	        mXYMultipleSeriesRenderer.setYLabels(0);
	        mXYMultipleSeriesRenderer.setZoomEnabled(false, false);
	    }
		private XYSeriesRenderer initRenderer(XYSeriesRenderer renderer, int color,
	            PointStyle style, boolean fill) {
	        renderer.setColor(color);
	        renderer.setPointStyle(style);
	        renderer.setFillPoints(fill);
	        renderer.setLineWidth(2);
	        return renderer;
	    }
		private void initLine(XYSeries line) {
			score2 = new int[score.length];
			 for(int i = 0; i < score.length; i++){
				if(score[i].equals("A")){
					score2[i] = 32;
				}else if(score[i].equals("B")){
					score2[i] = 24;
				}else if(score[i].equals("C")){
					score2[i] = 16;
				}else if(score[i].equals("D")){
					score2[i] = 8;
				}else if(score[i].equals("E")){
					score2[i] = 0;
				}
			 }
			 for (int i = 0; i < score.length; i++) {
				 line.add(i, score2[i]);
				 mXYMultipleSeriesRenderer.addXTextLabel(i, date[i]);
			 }
			 intx();
		}
		private void intx() {
			for(int i = 0; i <= 32; i=i+4)
			switch(i){
			 case 0:mXYMultipleSeriesRenderer.addYTextLabel(i, "E");break;
			 case 8:mXYMultipleSeriesRenderer.addYTextLabel(i, "D");break;
			 case 16:mXYMultipleSeriesRenderer.addYTextLabel(i, "C");break;
			 case 24:mXYMultipleSeriesRenderer.addYTextLabel(i, "B");break;
			 case 32:mXYMultipleSeriesRenderer.addYTextLabel(i, "A");break;
			 }
		}
		private void init() {
			getObjView();
			setOnclick();
		}
		private void setOnclick() {
			back.setOnClickListener(this);
		}
		private void getObjView() {
			back = (Button)findViewById(R.id.Record_char_person_back);
			layout = (RelativeLayout) findViewById(R.id.record_Person_charLine);
			head = (TextView)findViewById(R.id.record_char_Person_student);
			if(number.equals("0")||number.equals("1")||number.equals("2")){
			switch (Integer.parseInt(number)) {
			case 0:head.setText("当前学生考勤情况折线图");break;
			case 2:head.setText("当前学生作业情况折线图");break;
			}
			}else{
				head.setText("当前学生其他情况折线图");
			}
		}
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.Record_char_person_back:
				finish();
				break;
			}
		}
}

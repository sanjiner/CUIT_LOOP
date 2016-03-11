package com.example.process;

import com.exam.ThreeSystem.R;
import com.example.PCenter.BaseActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;

public class ProcessNewsDetailActivity extends BaseActivity implements OnClickListener{

	private ProgressDialog dialog;
	private Button backBtn;
	private String NewsTitle, NewsContent;
	private ScrollView scrollview;
	private TextView titleTextview, contentTextview;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_process_newsdetail);
		Intent intent = getIntent();
		NewsTitle = intent.getStringExtra("NewsTitle");
		NewsContent = intent.getStringExtra("NewsContent");
		
		init();		
	}
	
	private void getViewObj()
	{
		backBtn = (Button) findViewById(R.id.process_newsdetail_back);
		titleTextview = (TextView) findViewById(R.id.process_show_newstitle);
		scrollview = (ScrollView) findViewById(R.id.process_scrollview);
		contentTextview = (TextView) findViewById(R.id.process_show_content); 
		titleTextview.setText(NewsTitle);
		contentTextview.setText(NewsContent);
	}
	
	private void setListener()
	{
		backBtn.setOnClickListener(this);
	}

	private void init()
	{
		getViewObj();
		setListener();
		Dialog();
		//getDetailGrade();
	}
	
	private void Dialog()
	{
		dialog = new ProgressDialog(mActivity);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条  
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消  
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条  
        dialog.setIcon(R.drawable.ic_launcher);//  
        dialog.setMessage("正在加载，请等待。。。。");  
        dialog.setTitle("提示");
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.process_newsdetail_back:
			finish();
			break;
		}
		
	}
	
}

package com.example.thesis;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.exam.ThreeSystem.R;
import com.example.PCenter.BaseActivity;

public class ProjectManagement_Thesis_Defense extends BaseActivity implements OnClickListener{
//论文答辩
	public Button projectmanagement_defense_back;
	public RelativeLayout projectmanagement_defense_checkstugrade;
	public RelativeLayout projectmanagement_defense_Task_book;
	public RelativeLayout projectmanagement_defense_Opening_report;
	public RelativeLayout projectmanagement_defense_guidebook;
	public String studentName;
	public String studentId;
	public Activity mActivity;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		Intent intent=getIntent();
		studentName=intent.getStringExtra("studentName");
		studentId=intent.getStringExtra("studentId");
		setContentView(R.layout.projectmanagement_thesis_defense);
		mActivity=this;
		init();
	}
	
	public void getViewObj(){
		projectmanagement_defense_back=(Button) findViewById(R.id.projectmanagement_defense_back);
		projectmanagement_defense_checkstugrade=(RelativeLayout) findViewById(R.id.projectmanagement_defense_checkstugrade);
		projectmanagement_defense_Task_book=(RelativeLayout) findViewById(R.id.projectmanagement_defense_Task_book);
		projectmanagement_defense_Opening_report=(RelativeLayout) findViewById(R.id.projectmanagement_defense_Opening_report);
		projectmanagement_defense_guidebook=(RelativeLayout) findViewById(R.id.projectmanagement_defense_guidebook);
		
	}
	public void setlistener(){
		projectmanagement_defense_back.setOnClickListener(this);
		projectmanagement_defense_checkstugrade.setOnClickListener(this);
		projectmanagement_defense_Task_book.setOnClickListener(this);
		projectmanagement_defense_Opening_report.setOnClickListener(this);
		projectmanagement_defense_guidebook.setOnClickListener(this);
	}
	public void init(){
		getViewObj();
		setlistener();
		
	}

	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		
		Intent intent;
		switch (view.getId()) {
		case R.id.projectmanagement_defense_back:
			finish();
			break;
		case R.id.projectmanagement_defense_checkstugrade:
			intent=new Intent(mActivity,Project_showStuGrage_detail.class);
			intent.putExtra("studentName", studentName);
			intent.putExtra("studentId", studentId);
			startActivity(intent);
			break;
		case R.id.projectmanagement_defense_Task_book:
			intent=new Intent(mActivity,Project_TaskBook_detail.class);
			intent.putExtra("studentName", studentName);
			intent.putExtra("studentId", studentId);
			startActivity(intent);
			break;
		case R.id.projectmanagement_defense_Opening_report:
			intent=new Intent(mActivity,Project_OpenReport_detail.class);
			intent.putExtra("studentName", studentName);
			intent.putExtra("studentId", studentId);
			startActivity(intent);
			break;
		case R.id.projectmanagement_defense_guidebook:
			intent=new Intent(mActivity,Project_GuideReport_detail.class);
			intent.putExtra("studentName", studentName);
			intent.putExtra("studentId", studentId);
			startActivity(intent);
			break;
		default:
			break;
		}
		
	}

}

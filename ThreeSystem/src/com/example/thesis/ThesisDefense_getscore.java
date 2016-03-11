package com.example.thesis;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.exam.ThreeSystem.R;
import com.example.PCenter.BaseActivity;

public class ThesisDefense_getscore extends BaseActivity implements OnClickListener{
	
	public Button  personal_information_back;
	public TextView teacher_rate_name;
	public TextView teacher_rate_grade;
	public EditText teacher_rate_Quality;
	public EditText teacher_rate_process;
	public EditText  teacher_rate_judge;
	public Button guide_teacher_rate_detail_submit;
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.guide_teacher_rate_detail);
	}
	public void getViewObj()
	{
		personal_information_back=(Button) findViewById(R.id.personal_information_back);
		guide_teacher_rate_detail_submit=(Button) findViewById(R.id.guide_teacher_rate_detail_submit);
		teacher_rate_name=(TextView) findViewById(R.id.teacher_rate_name);
		teacher_rate_grade=(TextView) findViewById(R.id.teacher_rate_grade);
		teacher_rate_Quality=(EditText) findViewById(R.id.teacher_rate_Quality);
		teacher_rate_process=(EditText) findViewById(R.id.teacher_rate_process);
		teacher_rate_judge=(EditText) findViewById(R.id.teacher_rate_judge);
		
	}
	public void setlistener()
	{
		personal_information_back.setOnClickListener(this);
		guide_teacher_rate_detail_submit.setOnClickListener(this);
	}
	public void init()
	{
		getViewObj();
		setlistener();
	}
	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		switch (view.getId()) {
		case R.id.personal_information_back:
			finish();
			break;
		case R.id.guide_teacher_rate_detail_submit:
			
			break;
		default:
			break;
		}
		
	}
}

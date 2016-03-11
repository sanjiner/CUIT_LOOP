package com.example.process;

import com.exam.ThreeSystem.R;
import com.example.PCenter.BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class ProcessAppointExamActivity extends BaseActivity implements OnClickListener{

	
private Button backButton;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_process_appoint_exam);
		Init();
	}
	
	
	private void Init()
	{
		getViewObj();
	}
	
	private void getViewObj()
	{
		backButton = (Button) findViewById(R.id.process_appoint_exam_back);
		Listener();
	}
	
	private void Listener()
	{
		backButton.setOnClickListener(this);
	}


	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.process_appoint_exam_back:
			finish();
			break;

		default:
			break;
		}
		
	}
}

package com.example.PCenter.More;

import com.example.PCenter.BaseActivity;
import com.exam.ThreeSystem.R;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

public class MoreHelpActivity extends BaseActivity implements OnClickListener{
	private Button mBack;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_help);
		init();
	}
	
	private void init(){
		getViewObj();
		setListener();
	}
	
	private void getViewObj(){
		mBack = (Button) findViewById(R.id.b_more_help_back);
	}
	private void setListener(){
		mBack.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.b_more_help_back:
			finish();
			break;
		}
		
	}
}

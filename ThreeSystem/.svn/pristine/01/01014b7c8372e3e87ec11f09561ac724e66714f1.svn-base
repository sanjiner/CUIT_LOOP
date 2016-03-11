package com.example.PCenter;

import com.exam.ThreeSystem.R;
import com.example.PCenter.adapter.ClassListViewAdapter_homework_list;
import com.example.PCenter.fragments.HomeWorkFragment;
import android.content.Intent;
import android.os.Bundle;
import android.view.View.OnClickListener;
import android.view.View;
import android.widget.Button;

//已做列表   未做列表   返回
public class homework_state_activity extends BaseActivity implements OnClickListener{
	private Button homeworkState_finished=null;
	private Button homeworkState_unfinished=null;
	private Button homework_state_back=null;
	public String TeachingClassID;
	
	protected void onCreate(Bundle savedInstanceState)
	{
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homework_state);
		Intent intent = getIntent();
		TeachingClassID = intent.getStringExtra("TeachingClassID");
//		TeachingClassID=HomeWorkFragment.Stu_TeachingClassID;
		System.out.println("這個ID是  ：："+TeachingClassID);
		init();
		
		
	}
	private void init() {
		getViewObj();
		setListener();
	}
	private void getViewObj()
	{
		homeworkState_finished=(Button)findViewById(R.id.homeworkState_finished);
		homeworkState_unfinished=(Button)findViewById(R.id.homeworkState_unfinished);
		homework_state_back=(Button)findViewById(R.id.homework_state_back);
	}
	private void setListener()
	{
	homeworkState_unfinished.setOnClickListener(this);
	homeworkState_finished.setOnClickListener(this);
	homework_state_back.setOnClickListener(this);
	
	}
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.homeworkState_finished:
			{
				Intent intent=new Intent(homework_state_activity.this,homework_state_activity_stu_finnished.class);
				intent.putExtra("TeachingClassID", TeachingClassID);
				System.out.println(TeachingClassID);
				startActivity(intent);
			}
			break;
		case R.id.homeworkState_unfinished:
			{
				Intent intent=new Intent(homework_state_activity.this,homework_state_activity_stu_finnishing.class);
				intent.putExtra("TeachingClassID", TeachingClassID);
				System.out.println(TeachingClassID);
				startActivity(intent);
			}
			break;
		case R.id.homework_state_back:
			{
				 finish();

			}
			break;
		default:
			{
				
			}
			break;
		}
	}
	
	

}

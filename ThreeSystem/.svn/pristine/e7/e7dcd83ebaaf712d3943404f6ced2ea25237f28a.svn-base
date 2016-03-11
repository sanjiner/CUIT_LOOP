package com.example.theses.fragments.teacher;

import com.example.PCenter.More.MoreAboutUsActivity;
import com.example.PCenter.fragments.BaseFragment;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.exam.ThreeSystem.R;
import com.example.PCenter.Constant;
import com.example.PCenter.Login;
import com.example.thesis.Modify_password_teacher;
import com.example.thesis.MoreAboutUs;
import com.example.thesis.MyThises_Personal_information;

public class MyThisesFragment extends BaseFragment implements OnClickListener{

	public RelativeLayout activity_thesis_my_relativelayout_mydetailmessage;
	public RelativeLayout activity_thesis_my_relativelayout_Modifypassword;
	public RelativeLayout activity_thesis_my_relativelayout_aboutus;
	public Button b_more_logout_thesis;
	public Activity mActivity;
	private SharedPreferences sharedPref;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_thesis_my_teacher, container, false);
		mActivity = getActivity();
		sharedPref=mActivity.getSharedPreferences(Constant.USERINFO_SP, Context.MODE_PRIVATE);
		init(view);
		return view;
	}

	public void getViewObj(View view )
	{
		activity_thesis_my_relativelayout_mydetailmessage=(RelativeLayout) view.findViewById(R.id.activity_thesis_my_relativelayout_mydetailmessage);
		activity_thesis_my_relativelayout_Modifypassword=(RelativeLayout) view.findViewById(R.id.activity_thesis_my_relativelayout_Modifypassword);
		activity_thesis_my_relativelayout_aboutus=(RelativeLayout) view.findViewById(R.id.activity_thesis_my_relativelayout_aboutus);
		b_more_logout_thesis=(Button) view.findViewById(R.id.b_more_logout_thesis);
	}
	
	public void setlistener(){
		activity_thesis_my_relativelayout_mydetailmessage.setOnClickListener(this);
		activity_thesis_my_relativelayout_Modifypassword.setOnClickListener(this);
		activity_thesis_my_relativelayout_aboutus.setOnClickListener(this);
		b_more_logout_thesis.setOnClickListener(this);
		
	}
	
	public void init(View view ){
		getViewObj(view);
		setlistener();
	}
	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		Intent intent=null;
		switch (view.getId()) 
		{
		
		case R.id.activity_thesis_my_relativelayout_mydetailmessage:
			intent =new Intent(mActivity,MyThises_Personal_information.class);
			startActivity(intent);
			break;
		case R.id.activity_thesis_my_relativelayout_Modifypassword:
			intent =new Intent(mActivity,Modify_password_teacher.class);
			startActivity(intent);
			break;
		case R.id.activity_thesis_my_relativelayout_aboutus:
			intent=new Intent(mActivity,MoreAboutUsActivity.class);
			startActivity(intent);
			break;
		case R.id.b_more_logout_thesis:
			logout();
			dialog();
			break;
		default:
			break;
		}
		
	}
	
	private void dialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		builder.setTitle("注销");
		builder.setMessage("确认注销么?");
		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {
				Intent logoutIntent = new Intent(getActivity(), Login.class);
			//	logoutIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
				startActivity(logoutIntent);
				mActivity.finish();
			}
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
	}
	
	/**
	 * 退出已保存的用户信息
	 */
	private void logout(){
		Editor editor = sharedPref.edit();
		editor.remove(Constant.SPFIELD.NAME);
		editor.remove(Constant.SPFIELD.PASSWORD);
		editor.remove(Constant.SPFIELD.TEACHERID);
		editor.commit();
	}
}

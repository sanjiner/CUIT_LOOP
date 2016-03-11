package com.example.theses.fragments;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.common.tools.L;
import com.exam.ThreeSystem.R;
import com.example.PCenter.Login;
import com.example.PCenter.More.MoreAboutUsActivity;
import com.example.PCenter.fragments.BaseFragment;
import com.example.thesis.student.activity.Thesis_StudentMy_ModifyPwdActivity;
import com.example.thesis.student.activity.Thesis_StudentMy_PersonInfoActivity;

public class MyFragmentThises extends BaseFragment implements OnClickListener, android.content.DialogInterface.OnClickListener {
	private RelativeLayout mInfo, mModifyPwd, mAboutUs;
	private Button logout;
	

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_thesis_my, container, false);
		initView(view);
		return view;
	}

	private void initView(View v) {
		mInfo = (RelativeLayout) v.findViewById(R.id.thesis_stu_info);
		mModifyPwd = (RelativeLayout) v.findViewById(R.id.thesis_stu_modifypwd);
		mAboutUs = (RelativeLayout) v.findViewById(R.id.thesis_stu_aboutus);
		logout = (Button) v.findViewById(R.id.thesis_stu_logout);
		setEvent();
	}

	private void setEvent() {
		mInfo.setOnClickListener(this);
		mModifyPwd.setOnClickListener(this);
		mAboutUs.setOnClickListener(this);
		logout.setOnClickListener(this);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.thesis_stu_info:
			startActivity(new Intent(getActivity(),Thesis_StudentMy_PersonInfoActivity.class));
			break;
		case R.id.thesis_stu_modifypwd:
			startActivity(new Intent(getActivity(),Thesis_StudentMy_ModifyPwdActivity.class));
			break;
		case R.id.thesis_stu_aboutus://使用教辅的
			startActivity(new Intent(getActivity(),MoreAboutUsActivity.class));
			break;
		case R.id.thesis_stu_logout:
			showDialog();
			break;
		default:
			break;
		}
	}
	private void showDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		builder.setTitle("退出");
		builder.setMessage("确认退出？");
		builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				startActivity(new Intent(getActivity(),Login.class)
						.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK));
			}
			
		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		AlertDialog dialog = builder.create();
		dialog.show();
		L.d(TAG, "show dialog");
	}

	@Override
	public void onClick(DialogInterface dialog, int which) {
		
	}
}

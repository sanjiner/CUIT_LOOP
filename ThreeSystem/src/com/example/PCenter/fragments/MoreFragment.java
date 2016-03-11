package com.example.PCenter.fragments;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.common.tools.T;
import com.example.PCenter.Constant;
import com.example.PCenter.Login;
import com.exam.ThreeSystem.R;
import com.example.PCenter.WhatsnewPagesA;
import com.example.PCenter.More.MoreAboutUsActivity;
import com.example.PCenter.More.MoreModifyPwdActivity;
import com.example.PCenter.More.MoreModuleClassListActivity;
import com.example.PCenter.More.MoreTermActivity;
import com.example.PCenter.More.MoreUserInfoActivity;
import com.example.PCenter.More.MoreVersionActivity;
import com.trinea.connect.AdManager;
import com.trinea.connect.br.AdSize;
import com.trinea.connect.br.AdView;
import com.trinea.connect.br.AdViewListener;

public class MoreFragment extends BaseFragment implements OnClickListener {
	private RelativeLayout mVersion, mHelp, mTerm, mAboutus, mModifypwd, mInfo,  mModule;
	private Button mLogout, mother;
	private Activity mActivity;
	private String FILE = Constant.USERINFO_SP;//用于保存SharedPreferences的文件
	private SharedPreferences sp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.activity_more, container, false);
		
		mActivity = getActivity();
		sp = mActivity.getSharedPreferences(FILE, Context.MODE_PRIVATE);
		//youmi
		AdManager.getInstance(mActivity).init("4927c476dbf90a2a", "4927c476dbf90a2a", true);
		getViewObj(view);
		
		return view;
	}

	private void getViewObj(View view) {
//		mVersion = (RelativeLayout) view.findViewById(R.id.rl_more_version);
		mHelp = (RelativeLayout) view.findViewById(R.id.rl_more_help);
		mAboutus = (RelativeLayout) view.findViewById(R.id.rl_more_aboutus);
		mTerm = (RelativeLayout) view.findViewById(R.id.rl_more_term);
		mModifypwd = (RelativeLayout) view.findViewById(R.id.rl_more_modifypwd);
		mInfo = (RelativeLayout) view.findViewById(R.id.rl_more_userinfo);
		mModule = (RelativeLayout) view.findViewById(R.id.rl_more_module);
		//mother = (Button) view.findViewById(R.id.more_set);

		mLogout = (Button) view.findViewById(R.id.b_more_logout);

		setListener();
		Youmi(view);
	}

	private void Youmi(View view)
	{
		// 实例化广告条
		AdView adView = new AdView(mActivity, AdSize.FIT_SCREEN);
		// 获取要嵌入广告条的布局
		LinearLayout adLayout=(LinearLayout)view.findViewById(R.id.rl_more_adLayout);
		// 将广告条加入到布局中
		adLayout.addView(adView);
		
		adView.setAdListener(new AdViewListener() {
		    @Override
		    public void onSwitchedAd(AdView adView) {
		        //Toast.makeText(mActivity, "切换广告并展示", Toast.LENGTH_SHORT).show();
		    }
		    @Override
		    public void onReceivedAd(AdView adView) {
		        // 请求广告成功
		    	//Toast.makeText(mActivity, "请求广告成功", Toast.LENGTH_SHORT).show();
		    }
		    @Override
		    public void onFailedToReceivedAd(AdView adView) {
		        // 请求广告失败
		    	//Toast.makeText(mActivity, "请求广告失败", Toast.LENGTH_SHORT).show();
		    }
		});
	}

	private void setListener() {
//		mVersion.setOnClickListener(this);
		mHelp.setOnClickListener(this);
		mAboutus.setOnClickListener(this);
		mTerm.setOnClickListener(this);
		mModifypwd.setOnClickListener(this);
		mLogout.setOnClickListener(this);
		mInfo.setOnClickListener(this);
		mModule.setOnClickListener(this);
		//mother.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.rl_more_module:
			String identity = sp.getString(Constant.SPFIELD.USERIDENTITY,"");
			if(identity.equals("学生")){
				T.showShort(mActivity, "没有相关权限");
				return;
			}else{
				String term = sp.getString(Constant.SPFIELD.CURRENTTERM, "");
				if(TextUtils.isEmpty(term)){
					Intent termIntent = new Intent(getActivity(),
							MoreTermActivity.class);
					startActivity(termIntent);
				}
				else{
					Intent moduleIntent = new Intent(getActivity(),
							MoreModuleClassListActivity.class);
					startActivity(moduleIntent);
				}
			}
			break;
		case R.id.rl_more_term:
			Intent termIntent = new Intent(getActivity(),
					MoreTermActivity.class);
			startActivity(termIntent);
			break;
		case R.id.rl_more_userinfo:
			Intent infoIntent = new Intent(getActivity(),
					MoreUserInfoActivity.class);
			startActivity(infoIntent);
			break;
		case R.id.rl_more_aboutus:
			Intent aboutusIntent = new Intent(getActivity(),
					MoreAboutUsActivity.class);
			startActivity(aboutusIntent);
			break;
//		case R.id.rl_more_version:
//			Intent versionIntent = new Intent(getActivity(),
//					MoreVersionActivity.class);
//			startActivity(versionIntent);
//			break;
		case R.id.rl_more_help:

			Intent helpIntent = new Intent(getActivity(),
					WhatsnewPagesA.class);
			//在WhatsnewPagesA中的startbutton判断其跳转的页面
			Editor edit = sp.edit();
			edit.putString(Constant.SPFIELD.ISTOMORE, "yes");
			edit.commit();
			startActivity(helpIntent);
			break;
		case R.id.rl_more_modifypwd:
			Intent modifyIntent = new Intent(getActivity(),
					MoreModifyPwdActivity.class);
			startActivity(modifyIntent);
			break;
		case R.id.b_more_logout:
			dialog();
			break;
//		case R.id.more_set:
//			T.showShort(mActivity, "点击了");
//			break;
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
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}

}

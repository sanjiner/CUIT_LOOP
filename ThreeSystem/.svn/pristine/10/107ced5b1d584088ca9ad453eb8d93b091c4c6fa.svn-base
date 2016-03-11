package com.example.PCenter.More;

import com.example.PCenter.BaseActivity;
import com.example.PCenter.Constant;
import com.exam.ThreeSystem.R;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class MoreUserInfoActivity extends BaseActivity implements OnClickListener {
	private TextView userId, userName, userIdentity, userSex, userClass, userDepartmentName, MajorName;
	private TextView userSex2, userClass2, userDepartmentName2, MajorName2;
	private Button more_info_back;
	private SharedPreferences sp;
	private String FILE = Constant.USERINFO_SP;//用于保存SharedPreferences的文件
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_person_info);
		sp = mActivity.getSharedPreferences(FILE, MODE_PRIVATE);
		init();
		info();
	}

	private void init() {
		getViewObj();
		setListener();
	}
	private void getViewObj() {
		userId = (TextView)findViewById(R.id.more_info_userId);
		userName = (TextView)findViewById(R.id.more_info_userName);
		userIdentity = (TextView)findViewById(R.id.more_info_userIdentity);
		userSex = (TextView)findViewById(R.id.more_info_userSex);
		userClass = (TextView)findViewById(R.id.more_info_userClass);
		userDepartmentName = (TextView)findViewById(R.id.more_info_userDepartmentName);
		MajorName = (TextView)findViewById(R.id.more_info_MajorName);
		userSex2 = (TextView)findViewById(R.id.more_info_userSex2);
		userClass2 = (TextView)findViewById(R.id.more_info_userClass2);
		userDepartmentName2 = (TextView)findViewById(R.id.more_info_userDepartmentName2);
		MajorName2 = (TextView)findViewById(R.id.more_info_MajorName2);
		more_info_back = (Button)findViewById(R.id.more_personInfo_back);
	}
	private void setListener() {
		more_info_back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.more_personInfo_back:
			finish();
			break;
		}
	}

	private void info() {
		String userIden = sp.getString(Constant.SPFIELD.USERIDENTITY, "");
		if(userIden.equalsIgnoreCase("老师"))
		{
			userId.setText(sp.getString(Constant.SPFIELD.NAME, ""));
			userName.setText(sp.getString(Constant.SPFIELD.REALNAME, ""));
			userIdentity.setText(userIden);
			userSex2.setText("");
			userClass2.setText("");
			userDepartmentName2.setText("");
			MajorName2.setText("");
		}
		else
		{
			userId.setText(sp.getString(Constant.SPFIELD.NAME, ""));
			userName.setText(sp.getString(Constant.SPFIELD.REALNAME, ""));
			userIdentity.setText(userIden);
			userSex.setText(sp.getString(Constant.SPFIELD.SEX, ""));
			userClass.setText(sp.getString(Constant.SPFIELD.CLASSNAME, ""));
			MajorName.setText(sp.getString(Constant.SPFIELD.MAJORNAME, ""));
			userDepartmentName.setText(sp.getString(Constant.SPFIELD.DEPARTMENTNAME, ""));
		}
	}

	
}

package com.example.PCenter.More;

import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.example.PCenter.BaseActivity;
import com.example.PCenter.Constant;
import com.example.PCenter.MD5ENCODE;
import com.exam.ThreeSystem.R;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MoreModifyPwdActivity extends BaseActivity implements OnClickListener {
	private TextView more_modifyPwd_userId, more_modifyPwd_userName;
	private EditText more_modifyPwd_oldPwd, more_modifyPwd_newPwd, more_modifyPwd_comfirmNewPwd;
	private Button more_modifyPwd_submit, more_mdofyPwd_back;
	private ProgressBar progressbar;
	private SharedPreferences sp;
	private String FILE = Constant.USERINFO_SP;//用于保存SharedPreferences的文件
	private String realName;
	private Thread mthread;
	private String BASE_URL = Constant.INTERFACE_SITE;
	private String NOW_URL = "";
	
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_modifypwd);
		sp = mActivity.getSharedPreferences(FILE, MODE_PRIVATE);
		init();
		more_modifyPwd_userId.setText(sp.getString(Constant.SPFIELD.NAME, ""));
		realName = sp.getString(Constant.SPFIELD.REALNAME, "");
		more_modifyPwd_userName.setText(realName);
	}
	private void init() {
		getViewObj();
		setListener();
	}
	private void getViewObj() {
		more_modifyPwd_userId = (TextView)findViewById(R.id.more_modifyPwd_currentUserId);
		more_modifyPwd_userName = (TextView)findViewById(R.id.more_modifyPwd_currentUserName);
		
		more_modifyPwd_oldPwd = (EditText)findViewById(R.id.more_modifyPwd_currentUser_oldPwd);
		more_modifyPwd_newPwd = (EditText)findViewById(R.id.more_modifyPwd_currentUser_newPwd);
		more_modifyPwd_comfirmNewPwd = (EditText)findViewById(R.id.more_modifyPwd_currentUser_confireNewPwd);
		progressbar = (ProgressBar)findViewById(R.id.more_ModifyPwd_progressbar);
		more_modifyPwd_submit = (Button)findViewById(R.id.more_modifyPwd_submit);
		more_mdofyPwd_back = (Button)findViewById(R.id.more_modifypwd_back);
	}
	private void setListener() {
		more_modifyPwd_submit.setOnClickListener(this);
		more_mdofyPwd_back.setOnClickListener(this);
	}
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.more_modifyPwd_submit:
				submit();
			break;
		case R.id.more_modifypwd_back:
			finish();
			break;
		}
	}
	private void submit() {
		if(more_modifyPwd_oldPwd.getText().toString().equals("") ||
				more_modifyPwd_newPwd.getText().toString().equals("") || 
				more_modifyPwd_comfirmNewPwd.getText().toString().equals("")){
			Toast.makeText(mActivity, "请输入所有内容",
					Toast.LENGTH_SHORT).show();
		}
		else{
			if(more_modifyPwd_oldPwd.getText().toString().equals(sp.getString(Constant.SPFIELD.PASSWORD, ""))){ 
				if(more_modifyPwd_newPwd.getText().toString().equals(more_modifyPwd_comfirmNewPwd.getText().toString())){
					progressbar.setVisibility(View.VISIBLE);
					mthread = new Thread(runnable);
					mthread.start();
				}else{
					Toast.makeText(mActivity, "两次新密码不一致",
							Toast.LENGTH_SHORT).show();
				}
			}else{
				Toast.makeText(mActivity, "原密码错误",
						Toast.LENGTH_SHORT).show();
			}
		}
	}
	Runnable runnable = new Runnable(){
		@Override
		public void run(){
			try {
				String userIdentity = sp.getString(Constant.SPFIELD.USERIDENTITY, "");
				if(userIdentity.equalsIgnoreCase("老师")){
					TeacherUrl();	
					Modify();
				}
				else{
					StudentUrl();
					Modify();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};
	private void TeacherUrl() {
		NOW_URL = BASE_URL + "TeacherResetPassWord?TeacherName=" + more_modifyPwd_userId.getText().toString()
				+ "&oldPassWord=" + MD5ENCODE.MD5Encode(more_modifyPwd_oldPwd.getText().toString()).toString()
				+ "&newPassWord=" + MD5ENCODE.MD5Encode(more_modifyPwd_newPwd.getText().toString()).toString() + "";
	}
	private void StudentUrl() {
		NOW_URL = BASE_URL + "StudentResetPassWord?StuNumber=" + more_modifyPwd_userId.getText().toString()
				+ "&oldPassWord=" + MD5ENCODE.MD5Encode(more_modifyPwd_oldPwd.getText().toString()).toString()
				+ "&newPassWord=" + MD5ENCODE.MD5Encode(more_modifyPwd_newPwd.getText().toString()).toString() + "";
	}
	protected void Modify() {
		StringRequest stringRequest = new StringRequest(NOW_URL, getSuccessListenter(),errorListener());
		executeRequest(stringRequest);
	}
	private Response.Listener<String> getSuccessListenter() {
		return new Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					JSONObject json = new JSONObject(response);
					String isSuccess = json.getString("Success");
					if(isSuccess.equalsIgnoreCase("true")){
						Toast.makeText(mActivity, "修改成功",
								Toast.LENGTH_SHORT).show();
						Editor edit = sp.edit();
						edit.putString(Constant.SPFIELD.PASSWORD, more_modifyPwd_newPwd.getText().toString());
						edit.putString(Constant.SPFIELD.ISMEMORY, "no");
						edit.commit();
						finish();
					}
					else{
						Toast.makeText(mActivity, "修改失败",
								Toast.LENGTH_SHORT).show();
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		};
	}
}

	

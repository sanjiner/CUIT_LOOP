package com.example.thesis;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.plus.URLTools;
import com.android.volley.toolbox.StringRequest;
import com.baidu.mapapi.map.Text;
import com.common.tools.L;
import com.common.tools.T;
import com.exam.ThreeSystem.R;
import com.example.PCenter.BaseActivity;
import com.example.PCenter.Constant;

public class Project_showStuGrage_detail extends BaseActivity implements OnClickListener{
	/*个人成绩查看*/
	public Activity mActivity;
	public Button project_showstugrage_back;
	public TextView project_showstugrade_guidegrade;
	public TextView project_showstugrage_checkgrade;
	public TextView project_showstugrade_juegegrade;
	public TextView project_showstugrade_firstgrade;
	public TextView project_showstugrade_ispass;
	public String studentName;
	public String studentId;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.project_showstugrage_detail);
		Intent intent=getIntent();
		studentName=intent.getStringExtra("studentName");
		studentId=intent.getStringExtra("studentId");
		mActivity=this;
		init();
	}

	public void getViewObj(){
		project_showstugrage_back=(Button) findViewById(R.id.project_showstugrage_back);
		project_showstugrade_guidegrade=(TextView) findViewById(R.id.project_showstugrade_guidegrade);
		project_showstugrage_checkgrade=(TextView) findViewById(R.id.project_showstugrage_checkgrade);
		project_showstugrade_juegegrade=(TextView) findViewById(R.id.project_showstugrade_juegegrade);
		project_showstugrade_firstgrade=(TextView) findViewById(R.id.project_showstugrade_firstgrade);
		project_showstugrade_ispass=(TextView) findViewById(R.id.project_showstugrade_ispass);
		
	}
	public void setlistener()
	{
		project_showstugrage_back.setOnClickListener(this);
	}
	public void init(){
		getViewObj();
		setlistener();
		getGradeInfo();
	}

	/**
	 * 通过网络获取成绩信息
	 */
	private void getGradeInfo(){
		String url = URLTools.buildURL(Constant.THESIS_BASEURL + "CheckGrade", new String[]{"studentID"}, new String[]{studentId});
		StringRequest stringRequest = new StringRequest(url, onResponse(), errorListener());
		executeRequest(stringRequest);
	}
	/**
	 * 网络相应成功是处理返回值
	 * @param v
	 */
	private Response.Listener<String> onResponse(){
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				L.d(TAG, response);
				if(!TextUtils.isEmpty(response)){
					try {
						JSONObject jsonObj = new JSONObject(response);
						String isSuccess = jsonObj.getString("success");
						System.out.println(jsonObj.toString());
						if(isSuccess.equals("true")){
							project_showstugrade_guidegrade.setText(jsonObj.getString("GuidanceTeaMark"));
							project_showstugrage_checkgrade.setText(jsonObj.getString("ReviewTeaMark"));
							project_showstugrade_juegegrade.setText(jsonObj.getString("GuidanceTeaMark"));
							project_showstugrade_firstgrade.setText(jsonObj.getString("UltimateMark"));
							//rank.setText(jsonObj.getString("GuidanceTeaMark"));
							project_showstugrade_ispass.setText(jsonObj.getString("IsPass"));
							
						}else{
							T.showShort(mActivity, jsonObj.getString("ErrMsg"));
						}
						
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						L.e(TAG, e.toString());
					}
				}else{
					T.showShort(mActivity, "获取失败");
				}
				
				
			}
		};
				
	}
	
	
	@Override
	protected void onResume() {
		// TODO 自动生成的方法存根
		super.onResume();
		init();
	}

	@Override
	public void onClick(View view ) {
		// TODO 自动生成的方法存根
		switch (view.getId()) {
		case R.id.project_showstugrage_back:
			finish();
			break;

		default:
			break;
		}
	}
}

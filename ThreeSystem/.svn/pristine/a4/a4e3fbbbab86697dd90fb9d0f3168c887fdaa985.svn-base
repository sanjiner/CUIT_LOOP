package com.example.thesis;

import java.util.LinkedHashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.plus.URLTools;
import com.android.volley.plus.VolleyErrorHelper;
import com.android.volley.toolbox.StringRequest;
import com.common.tools.L;
import com.common.tools.T;
import com.exam.ThreeSystem.R;
import com.example.PCenter.BaseActivity;
import com.example.PCenter.Constant;

public class TeacherProjectTaskBookEditActivity extends BaseActivity implements OnClickListener{
	/**
	 * 任务书填写
	 * 综合系统！！！
	 * 
	 */
	
	public EditText edit_project_taskbook_comefrom;
	public EditText edit_project_taskbook_type;
	public EditText edit_project_taskbook_grade;
	public EditText edit_project_ataskbook_Requirement; 
	public Button edit_project_taskbook_back;
	public Button edit_project_submit;
	public String studentId;
	public String TaskSource;
	public String TaskType;
	public String BackGroundAndAim;
	public String TaskAndRequire;
	 public String source;
	 public String type;
	public ProgressDialog dialog;
	public RadioGroup myRadioGroup_project_taskbook_type;
	public RadioGroup myRadioGroup_project_taskbook_comefrom;
	public RadioButton myRadioButton1_project_taskbook_comefrom, myRadioButton2_project_taskbook_comefrom
	, myRadioButton3_project_taskbook_comefrom, myRadioButton4_project_taskbook_comefrom,
	myRadioButton1_project_taskbook_type, myRadioButton2_project_taskbook_type,
	myRadioButton3_project_taskbook_type, myRadioButton4_project_taskbook_type;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_teacher_project_taskbook_edit);
		Intent intent = getIntent();
		studentId = intent.getStringExtra("studentId");
		TaskSource=intent.getStringExtra("TaskSource");
		TaskType=intent.getStringExtra("TaskType");
		
		BackGroundAndAim=intent.getStringExtra("BackGroundAndAim");
		TaskAndRequire=intent.getStringExtra("TaskAndRequire");
		init();
		initButton(TaskSource,TaskType);
	}
	
	public void initButton(String TaskSource,String TaskType)
	{
		if(TaskSource.equals("科研项目"))
		{
			myRadioButton1_project_taskbook_comefrom.setChecked(true);
		}
		else if(TaskSource.equals("生产实践"))
		{
			myRadioButton2_project_taskbook_comefrom.setChecked(true);
		}
		else if(TaskSource.equals("自选题目"))
		{
			myRadioButton3_project_taskbook_comefrom.setChecked(true);
		}
		else 
		{
			myRadioButton4_project_taskbook_comefrom.setChecked(true);
		}
		if(TaskType.equals("理论研究"))
		{
			myRadioButton1_project_taskbook_type.setChecked(true);
		}
		else if(TaskType.equals("科学实验"))
		{
			myRadioButton2_project_taskbook_type.setChecked(true);
		}
		else if(TaskType.equals("设计开发"))
		{
			myRadioButton3_project_taskbook_type.setChecked(true);
		}
		else 
		{
			myRadioButton4_project_taskbook_type.setChecked(true);
		}
	}
	public void  getViewObj() {
		//edit_project_taskbook_comefrom=(EditText) findViewById(R.id.eidt_project_taskbook_comefrom);
		//edit_project_taskbook_type=(EditText) findViewById(R.id.edit_project_taskbook_type);
		myRadioGroup_project_taskbook_type=(RadioGroup) findViewById(R.id.myRadioGroup_project_taskbook_type);
		myRadioGroup_project_taskbook_comefrom=(RadioGroup) findViewById(R.id.myRadioGroup_project_taskbook_comefrom);
		myRadioButton1_project_taskbook_comefrom=(RadioButton) findViewById(R.id.myRadioButton1_project_taskbook_comefrom);
		myRadioButton2_project_taskbook_comefrom=(RadioButton) findViewById(R.id.myRadioButton2_project_taskbook_comefrom);
		myRadioButton3_project_taskbook_comefrom=(RadioButton) findViewById(R.id.myRadioButton3_project_taskbook_comefrom);
		myRadioButton4_project_taskbook_comefrom=(RadioButton) findViewById(R.id.myRadioButton4_project_taskbook_comefrom);
		
		myRadioButton1_project_taskbook_type=(RadioButton) findViewById(R.id.myRadioButton1_project_taskbook_type);
		myRadioButton2_project_taskbook_type=(RadioButton) findViewById(R.id.myRadioButton2_project_taskbook_type);
		myRadioButton3_project_taskbook_type=(RadioButton) findViewById(R.id.myRadioButton3_project_taskbook_type);
		myRadioButton4_project_taskbook_type=(RadioButton) findViewById(R.id.myRadioButton4_project_taskbook_type);
		
		
		edit_project_taskbook_grade=(EditText) findViewById(R.id.edit_project_taskbook_grade);
		edit_project_ataskbook_Requirement=(EditText) findViewById(R.id.edit_project_ataskbook_Requirement);
		edit_project_taskbook_back=(Button) findViewById(R.id.edit_project_taskbook_back);
		edit_project_submit=(Button) findViewById(R.id.edit_project_submit);
		
	}
	public void setlistener(){
		edit_project_taskbook_back.setOnClickListener(this);
		edit_project_submit.setOnClickListener(this);
		//edit_project_taskbook_comefrom.setText(TaskSource);
		//edit_project_taskbook_type.setText(TaskType);
		edit_project_taskbook_grade.setText(BackGroundAndAim);
		edit_project_ataskbook_Requirement.setText(TaskAndRequire);
		
		RadioGroup.OnCheckedChangeListener mChangeRadio = new 
		           RadioGroup.OnCheckedChangeListener()
		{

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO 自动生成的方法存根
				if(checkedId==myRadioButton1_project_taskbook_type.getId())
				{
					type=myRadioButton1_project_taskbook_type.getText().toString();
				}
				else if(checkedId==myRadioButton2_project_taskbook_type.getId())
				{
					type=myRadioButton2_project_taskbook_type.getText().toString();
				}
				else if(checkedId==myRadioButton3_project_taskbook_type.getId())
				{
					type=myRadioButton3_project_taskbook_type.getText().toString();
				}
				else if(checkedId==myRadioButton4_project_taskbook_type.getId())
				{
					type=myRadioButton4_project_taskbook_type.getText().toString();
				}
			}
			
		};
		
		RadioGroup.OnCheckedChangeListener mChangeRadio2 = new 
		           RadioGroup.OnCheckedChangeListener()
		{

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// TODO 自动生成的方法存根
				if(checkedId==myRadioButton1_project_taskbook_comefrom.getId())
				{
					source=myRadioButton1_project_taskbook_comefrom.getText().toString();
				}
				else if(checkedId==myRadioButton2_project_taskbook_comefrom.getId())
				{
					source=myRadioButton2_project_taskbook_comefrom.getText().toString();
				}
				else if(checkedId==myRadioButton3_project_taskbook_comefrom.getId())
				{
					source=myRadioButton3_project_taskbook_comefrom.getText().toString();
				}
				else if(checkedId==myRadioButton4_project_taskbook_comefrom.getId())
				{
					source=myRadioButton4_project_taskbook_comefrom.getText().toString();
				}
			}
			
		};
		
		myRadioGroup_project_taskbook_type.setOnCheckedChangeListener(mChangeRadio);
		myRadioGroup_project_taskbook_comefrom.setOnCheckedChangeListener(mChangeRadio2);
	}
	public void init(){
		getViewObj();
		setlistener();
		
	}
	
	
	
	private void submitInfo(){
		String bg = edit_project_taskbook_grade.getText().toString();
//		String source = edit_project_taskbook_comefrom.getText().toString();
//		String type = edit_project_taskbook_type.getText().toString();
		String require = edit_project_ataskbook_Requirement.getText().toString();
		if(TextUtils.isEmpty(bg) || TextUtils.isEmpty(source) || TextUtils.isEmpty(type) || TextUtils.isEmpty(require)){
			dialog.dismiss();
			Toast.makeText(mActivity, "不能为空", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Map<String, String> params = new LinkedHashMap<String, String>();
		
		params.put("studentID", studentId);
		params.put("taskSource", source);
		params.put("taskType", type);
		params.put("backGround", bg);
		params.put("require", require);
		
		String url = URLTools.buildURL(Constant.THESIS_BASEURL+"EditTask", params);
		StringRequest stringRequest = new StringRequest(url, onResponse(), errorListener());
		executeRequest(stringRequest);
	}
	
	
	private Response.Listener<String> onResponse(){
		
		return new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				try {
					JSONObject json = new JSONObject(response);
					if(json.getString("success").equals("true")){
						dialog.dismiss();
						Toast.makeText(mActivity, "成功", Toast.LENGTH_SHORT).show();
						finish();
					}
					else{
						dialog.dismiss();
						T.showShort(mActivity, json.getString("ErrMsg"));
					}
				} catch (JSONException e) {
					L.e(TAG, e.toString());
					dialog.dismiss();
					T.showShort(mActivity, e.toString());
				}
				
			}
		};
	}
	
	/**
	 * 网络请求失败
	 */
	@Override
	protected Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				dialog.dismiss();
				L.d(TAG, error.getMessage());
				Toast.makeText(mActivity, VolleyErrorHelper.getErrorType(error), Toast.LENGTH_LONG).show();
			}
		};
	}
	
	private void Dialog()
	{
		dialog = new ProgressDialog(this);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条  
        dialog.setCancelable(false);// 设置是否可以通过点击Back键取消  
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条  
        dialog.setIcon(R.drawable.ic_launcher);//  
        dialog.setMessage("正在提交，请等待。。。。");  
        dialog.setTitle("提示");
        dialog.show();
	}
	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		switch (view.getId()) {
		case R.id.edit_project_taskbook_back:
			finish();
			break;
		case R.id.edit_project_submit:
			Dialog();
			submitInfo();
			break;
		default:
			break;
		}
	}
}

package com.example.thesis;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
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

public class Topic_Management_Detail extends BaseActivity implements OnClickListener{
//选题管理
	public Button topic_management_detail_back;
	public Button topicmanagechoose_submit;
	public RelativeLayout topicmanagechoose_difficulty;
	public RelativeLayout topicmanagechoose_assignment;
	public RelativeLayout et_topicmanagechoose_majorsatisfy;
	public TextView topicmanagechoose_username;
	public EditText et_topicmanagechoose_taskname;
	//public EditText et_topicmanagechoose_majorsatisfy;
	public TextView topicmanagechoose_difficulty_value;
	public TextView topicmanagechoose_assignment_value;
	public TextView et_topicmanagechoose_majorsatisfy_value;
	private String[] difficultyValues = {"偏易","适中","偏难"};
	private String[] assignmentValues = {"偏小","适中","偏大"};
	public  String[] ProfessionalFitness={"符合","一般符合","不符合"};
	/*学生信息*/
	private String studentName, studentId;
	/*默认选择*/
	private int difficultyValuesIndex=0, assignmentValuesIndex=0,ProfessionalFitnessIndex=0;
	
	/*选中后的值*/
	private int difficultyValuesSelectedIndex=0, assignmentValuesSelectedIndex=0,ProfessionalFitnessSelectedIndex=0;
	private Activity mActivity;
	public ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.topic_management_detail);
		getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
		Intent intent = getIntent();
		studentId = intent.getStringExtra("studentId");
		studentName = intent.getStringExtra("studentName");
		mActivity=this;
		init();
	}

	public void getViewObj(){
		topic_management_detail_back=(Button) findViewById(R.id.topic_management_detail_back);
		topicmanagechoose_submit=(Button) findViewById(R.id.topicmanagechoose_submit);
		topicmanagechoose_difficulty=(RelativeLayout) findViewById(R.id.topicmanagechoose_difficulty);
		topicmanagechoose_assignment=(RelativeLayout) findViewById(R.id.topicmanagechoose_assignment);
		et_topicmanagechoose_majorsatisfy=(RelativeLayout) findViewById(R.id.et_topicmanagechoose_majorsatisfy);
		
		topicmanagechoose_username=(TextView) findViewById(R.id.topicmanagechoose_username);
		et_topicmanagechoose_taskname=(EditText) findViewById(R.id.et_topicmanagechoose_taskname);
		//et_topicmanagechoose_majorsatisfy=(EditText) findViewById(R.id.et_topicmanagechoose_majorsatisfy);
		topicmanagechoose_difficulty_value=(TextView) findViewById(R.id.topicmanagechoose_difficulty_value);
		topicmanagechoose_assignment_value=(TextView) findViewById(R.id.topicmanagechoose_assignment_value);
		et_topicmanagechoose_majorsatisfy_value=(TextView) findViewById(R.id.et_topicmanagechoose_majorsatisfy_value);
		
	}
	public void setlistener(){
		topic_management_detail_back.setOnClickListener(this);
		topicmanagechoose_submit.setOnClickListener(this);
		topicmanagechoose_difficulty.setOnClickListener(this);
		topicmanagechoose_assignment.setOnClickListener(this);
		et_topicmanagechoose_majorsatisfy.setOnClickListener(this);
	}
	public void init(){
		getViewObj();
		setlistener();
		topicmanagechoose_username.setText(studentName);
	}

	
	/**
	 * 选题提示框
	 */
	private void chooseDifficulty(){
		
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		builder.setTitle("选题难度");
		builder.setSingleChoiceItems(difficultyValues, 0,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						difficultyValuesIndex = which;
						
					}
				});

		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				difficultyValuesSelectedIndex = difficultyValuesIndex;
				topicmanagechoose_difficulty_value.setText(difficultyValues[difficultyValuesIndex]);
			}

		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.create().show();
	}
	
	/**
	 * 工作量
	 */
	private void chooseAssagment(){
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		builder.setTitle("工作量");
		builder.setSingleChoiceItems(assignmentValues, 0,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						assignmentValuesIndex = which;
					}
				});

		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				assignmentValuesSelectedIndex=assignmentValuesIndex;
				topicmanagechoose_assignment_value.setText(assignmentValues[assignmentValuesIndex]);
			}

		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.create().show();
	}
	
	/**
	 * 专业符合度
	 */
	private void majorsatisfy(){
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		builder.setTitle("专业符合度");
		builder.setSingleChoiceItems(ProfessionalFitness, 0,
				new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						ProfessionalFitnessIndex = which;
					}
				});

		builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {
				ProfessionalFitnessSelectedIndex=ProfessionalFitnessIndex;
				et_topicmanagechoose_majorsatisfy_value.setText(ProfessionalFitness[ProfessionalFitnessIndex]);
			}

		});
		builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

			@Override
			public void onClick(DialogInterface dialog, int which) {

			}
		});
		builder.create().show();
	}
	
	
	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		switch (view.getId()) {
		case R.id.topic_management_detail_back:
			finish();
			break;
		case R.id.topicmanagechoose_submit:
			Dialog();
			submit();
			
			break;
		/* 选择难度 */
		case R.id.topicmanagechoose_difficulty:
			chooseDifficulty();
			break;
		/* 选择工作量 */
		case R.id.topicmanagechoose_assignment:
			chooseAssagment();
			break;
		case R.id.et_topicmanagechoose_majorsatisfy:
			majorsatisfy();
			break;
		default:
			break;
		}
	}
	
	/**
	 * 提交信息
	 */
	public void submit() {
		/*获取输入值*/
		String taskName = et_topicmanagechoose_taskname.getText().toString();
		/* 检查是否为空 */
		if (TextUtils.isEmpty(taskName) ) {
			dialog.dismiss();
			T.showShort(mActivity, "不能为空");

		} else {
			Map<String, String> params = new HashMap<String, String>();
			params.put("studentID", studentId);
			params.put("majorSatisfy", ProfessionalFitness[ProfessionalFitnessIndex]);
			params.put("taskName", taskName);
			params.put("workWeight", assignmentValues[assignmentValuesSelectedIndex]);
			params.put("difficultyDegree", difficultyValues[difficultyValuesSelectedIndex]);
			
			String url = URLTools.buildURL(Constant.THESIS_BASEURL + "ChooseTask", params);
			System.out.println(params.toString());
			StringRequest stringRequest = new StringRequest(url, onResponse(),
					errorListener());
			executeRequest(stringRequest);
		}
	}

	/**
	 * 处理提交后返回结果
	 * 
	 * @return
	 */
	public Response.Listener<String> onResponse() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				try {
					JSONObject json = new JSONObject(response);
					String success = json.getString("success");
					if(success.equals("true")){
						dialog.dismiss();
						Toast.makeText(mActivity, "成功", Toast.LENGTH_SHORT).show();
						finish();
					}else{
						dialog.dismiss();
						Toast.makeText(mActivity, "失败", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					dialog.dismiss();
					e.printStackTrace();
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
				L.d(TAG, error.getMessage());
				dialog.dismiss();
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
}

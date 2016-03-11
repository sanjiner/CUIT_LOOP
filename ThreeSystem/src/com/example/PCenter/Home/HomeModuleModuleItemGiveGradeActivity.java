package com.example.PCenter.Home;

import java.util.HashMap;
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
import android.widget.SeekBar;
import android.widget.Toast;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TextView;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.plus.URLTools;
import com.android.volley.plus.VolleyErrorHelper;
import com.android.volley.toolbox.StringRequest;
import com.common.tools.L;
import com.example.PCenter.BaseActivity;
import com.example.PCenter.Constant;
import com.exam.ThreeSystem.R;

public class HomeModuleModuleItemGiveGradeActivity extends BaseActivity implements OnClickListener,OnSeekBarChangeListener{
	
	private String StuNumber, StuName;
	private String ScoreGrade, ModuleItemID, ModuleScoreRecordID;
	private TextView StudentName;
	private Button back, giveScore;
	private SeekBar seekbar;
	private EditText score;	
	private ProgressDialog dialog;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home_module_givescore);
		
		Intent intent = getIntent();
		StuNumber = intent.getStringExtra("StuNumber");
		StuName = intent.getStringExtra("StuName");
		
		ScoreGrade = intent.getStringExtra("ScoreGrade");
		ModuleItemID = intent.getStringExtra("ModuleItemID");
		ModuleScoreRecordID = intent.getStringExtra("ModuleScoreRecordID");		
		
		InitView();
	}
	
	private void InitView()
	{
		back = (Button) findViewById(R.id.home_module_givescore_back);
		StudentName = (TextView) findViewById(R.id.home_module_givescore_studentName);
		seekbar = (SeekBar) findViewById(R.id.home_module_givescore_seekbar);
		score = (EditText) findViewById(R.id.home_module_givescore_score);
		giveScore = (Button) findViewById(R.id.home_module_givescore_submit);
		
		
		if(!TextUtils.isEmpty(ScoreGrade)){
			score.setText(ScoreGrade);
			giveScore.setText("修改");
		}
		
		StudentName.setText(StuName);
		Dialog();
		Listener();
		SetProgressbar();
	}

	private void Dialog()
	{
		dialog = new ProgressDialog(mActivity);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条  
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消  
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条  
        dialog.setIcon(R.drawable.ic_launcher);//  
        dialog.setMessage("正在操作，请等待。。。。");  
        dialog.setTitle("提示");
	}
	
	private void Listener()
	{
		back.setOnClickListener(this);
		giveScore.setOnClickListener(this);
		seekbar.setOnSeekBarChangeListener(this);
	}
	
	@Override
	public void onClick(View v) {
		switch(v.getId()){
		case R.id.home_module_givescore_back:
			finish();
			break;
		case R.id.home_module_givescore_submit:
			giveScore(v);
			//finish();
			break;
		}		
	}

	//设置progressbar与Textview同步
	private void SetProgressbar()
	{
		String s = score.getText().toString();
		if(s.equals("A")){
			seekbar.setProgress(0);
		}if(s.equals("B")){
			seekbar.setProgress(1);
		}if(s.equals("C")){
			seekbar.setProgress(2);
		}if(s.equals("D")){
			seekbar.setProgress(3);
		}if(s.equals("E")){
			seekbar.setProgress(4);
		}
	}
	
	
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		int s = seekbar.getProgress();
		if(s==0){
			score.setText("A");
		}if(s==1){
			score.setText("B");
		}if(s==2){
			score.setText("C");
		}if(s==3){
			score.setText("D");
		}if(s==4){
			score.setText("E");
		}		
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub		
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub		
	}	
	
	private void giveScore(View v) {
		v.setEnabled(false);
		dialog.show();
		Map<String, String> params = new HashMap<String, String>();
		String action = "";
		String a = score.getText().toString();
		if(TextUtils.isEmpty(ScoreGrade)){
			params.put("StuNumber", StuNumber);
			params.put("ModuleItemID", ModuleItemID);
			params.put("ScoreGrade", a);
			action = "New_AddModuleItemStuScore";			
		}else{
			params.put("ModuleScoreRecordID", ModuleScoreRecordID);
			params.put("ScoreGrade", a);
			action = "New_ModifyModuleItemStuScore";
		}
		String url = URLTools
				.buildURL(Constant.INTERFACE_SITE + action, params);
		L.d(TAG, url);
		StringRequest stringRequest = new StringRequest(url, successListener(v),
				new Response.ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(
						mActivity,
						VolleyErrorHelper
								.getErrorType(error),
						Toast.LENGTH_LONG).show();
			}
		});
		executeRequest(stringRequest);
	}
	
	private Response.Listener<String> successListener(final View v) {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				if (response.length() != 0) {
					try {
						JSONObject jsonObj = new JSONObject(response);
						String success = jsonObj.getString("Success");
						if (success.equals("true")) {
							Toast.makeText(mActivity, "打分成功", Toast.LENGTH_SHORT).show();
							dialog.dismiss();
							finish();
						} else {
							L.d(TAG, "Success is false");
						}
						dialog.dismiss();
						v.setEnabled(true);
					} catch (JSONException e) {
						L.e(TAG, e.toString());
					}
				}
			}
		};
	}
}
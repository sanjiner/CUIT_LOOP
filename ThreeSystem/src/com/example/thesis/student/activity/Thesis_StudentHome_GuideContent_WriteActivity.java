package com.example.thesis.student.activity;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

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

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Thesis_StudentHome_GuideContent_WriteActivity extends BaseActivity implements OnClickListener{
	private Button back, submit;
	private EditText content;
	private TextView err;
	private SharedPreferences sp;
	private String FILE;
	private ProgressDialog progressdialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FILE = Constant.USERINFO_SP;
		sp = getSharedPreferences(FILE, MODE_PRIVATE);
		setContentView(R.layout.thesis_activity_studenthome_guidecontent_write);
		initView();
	}

	private void initView() {
		back =  (Button) findViewById(R.id.thesis_StudentHome_GuideContent_Write_back);
		submit =  (Button) findViewById(R.id.thesis_StudentHome_GuideContent_Write_submit);
		content = (EditText) findViewById(R.id.thesis_StudentHome_GuideContent_Write_Content);
		err = (TextView) findViewById(R.id.thesis_StudentHome_GuideContent_Write_err);

		setEvent();
	}

	private void showDialog() {
		progressdialog = new ProgressDialog(mActivity);
		progressdialog.setCanceledOnTouchOutside(false);
		progressdialog.setCancelable(true);
		progressdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		progressdialog.setTitle("提示");
		progressdialog.setIcon(R.drawable.ic_launcher);
		progressdialog.setMessage("正在加载。。。");
		L.d(TAG, "show dialog");
		progressdialog.show();
	}

	private String getCurrentTime(){
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");       
		Date curDate = new Date(System.currentTimeMillis());//获取当前时间       
		return formatter.format(curDate); 
	}
	private void submit() {
//		String s = content.getText().toString();
		if((!content.getText().toString().equals(""))){
			Map<String, String> params = new HashMap<String, String>();
			params.put("studentID", sp.getString(Constant.SPFIELD.NAME, ""));
			params.put("Content", content.getText().toString());
			params.put("GuideTime", getCurrentTime());
			String url = URLTools.buildURL(Constant.THESIS_BASEURL + "AddRecord", params);
			StringRequest request = new StringRequest(url, success(),
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
			executeRequest(request);
		}else{
			progressdialog.dismiss();
			T.showShort(mActivity, "内容不能为空。。");
		}
	}

	private Response.Listener<String> success() {
		return new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				L.d(TAG, response);
				if(!response.isEmpty()){
					try {
						JSONObject jsonobject = new JSONObject(response);
						if(jsonobject.getString("success").equals("true")){
							progressdialog.dismiss();
							T.showShort(mActivity, "提交成功");

							finish();
						}else{
							progressdialog.dismiss();
							//T.showShort(mActivity, jsonobject.getString("ErrMsg"));
							err.setText(jsonobject.getString("ErrMsg"));
							err.setVisibility(View.VISIBLE);

							finish();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			}
		};
	}

	private void setEvent() {
		submit.setOnClickListener(this);
		back.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.thesis_StudentHome_GuideContent_Write_back:
			finish();
			break;
		case R.id.thesis_StudentHome_GuideContent_Write_submit:
			showDialog();
			submit();
			//			finish();
			break;
		default:

			break;
		}
	}
}

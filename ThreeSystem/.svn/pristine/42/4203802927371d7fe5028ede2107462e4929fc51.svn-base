package com.example.thesis.student.activity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
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
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Thesis_StudentHome_GuideContentActivity extends BaseActivity implements OnClickListener, OnItemClickListener{
	private ListView lv;
	private Button back, write;
	private ProgressDialog progressdialog;
	private TextView err;
	private SharedPreferences sp;
	private String FILE;
	private List<Map<String, String>> data;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		FILE = Constant.USERINFO_SP;
		sp = getSharedPreferences(FILE, MODE_PRIVATE);
		setContentView(R.layout.thesis_activity_studenthome_guidecontent);
		initView();
	}
	private void initView() {
		lv = (ListView) findViewById(R.id.thesis_StudentHome_GuideContent_ListView);
		back = (Button)findViewById(R.id.thesis_StudentHome_GuideContent_back);
		err = (TextView)findViewById(R.id.thesis_StudentHome_GuideContent_err);
//		ok = (Button)findViewById(R.id.thesis_StudentHome_GuideContent_ok);
//		ok.setVisibility(View.INVISIBLE);
		write = (Button)findViewById(R.id.thesis_StudentHome_GuideContent_write);
		setEvent();
		showDialog();
		getData();
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

	private void getData() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("studentID", sp.getString(Constant.SPFIELD.NAME, ""));
		String url = URLTools.buildURL(Constant.THESIS_BASEURL + "CheckRecord", params);
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
	}

	private Response.Listener<String> success() {
		return new Response.Listener<String>() {

			@Override
			public void onResponse(String response) {
				L.d(TAG, response);
				if(!response.isEmpty()){
					try {
						JSONObject object = new JSONObject(response);
						if(object.getString("success").equals("true")){
							data = new ArrayList<Map<String, String>>();
							JSONArray jsonarray = object.getJSONArray("RecordInfo");
							for(int i=0;i<jsonarray.length();i++){
								JSONObject jsonobject = jsonarray.getJSONObject(i);
								Map<String, String> map = new HashMap<String, String>();
//								data.add(jsonobject.getString("GuideContent"+i));
								map.put("GuidanceRecordID", jsonobject.getString("GuidanceRecordID"));
								map.put("GuideContent", jsonobject.getString("GuideContent"));
								map.put("RecordTime", jsonobject.getString("RecordTime"));
								map.put("GuideTime", jsonobject.getString("GuideTime"));
								map.put("TeacherSign", jsonobject.getString("TeacherSign"));
								
								data.add(map);
							}
							lv.setAdapter(new Thesis_StudentHome_GuideContentAdapter(mActivity, data));
//							ok.setVisibility(View.VISIBLE);
							progressdialog.dismiss();
						}else{
							T.showShort(mActivity, object.getString("ErrMsg"));
							err.setText(object.getString("ErrMsg"));
							err.setVisibility(View.VISIBLE);
//							ok.setVisibility(View.VISIBLE);
							progressdialog.dismiss();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}

				}
			}
		};
	}

	private void setEvent() {
//		ok.setOnClickListener(this);
		back.setOnClickListener(this);
		write.setOnClickListener(this);
		lv.setOnItemClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
//		case R.id.thesis_StudentHome_GuideContent_ok:
//			finish();
//			break;
		case R.id.thesis_StudentHome_GuideContent_back:
			finish();
			break;
		case R.id.thesis_StudentHome_GuideContent_write:
			startActivity(new Intent(this,Thesis_StudentHome_GuideContent_WriteActivity.class));

			break;
		default:
			break;
		}
	}
	@Override
	protected void onResume() {
		super.onResume();
		getData();
	}
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
		
		Intent i = new Intent(this, Thesis_StudentHome_GuideContent_EditActivity.class);
		i.putExtra("GuideContent", data.get(position).get("GuideContent"));
		i.putExtra("GuidanceRecordID", data.get(position).get("GuidanceRecordID"));
		i.putExtra("GuideTime", data.get(position).get("GuideTime"));
		startActivity(i);
	}
	
}

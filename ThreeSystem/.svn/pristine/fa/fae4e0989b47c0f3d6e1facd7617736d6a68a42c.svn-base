package com.example.PCenter.More;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.plus.URLTools;
import com.android.volley.toolbox.StringRequest;
import com.common.tools.L;
import com.example.PCenter.BaseActivity;
import com.example.PCenter.Constant;
import com.example.PCenter.MainActivity;
import com.exam.ThreeSystem.R;
import com.example.PCenter.adapter.TermListViewAdapter;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MoreTermActivity extends BaseActivity implements OnClickListener,
		OnItemClickListener {
	private Button mBack, mConfirm;
	private List<String> termsList;
	private SharedPreferences sp;
	private ListView termListView;
	private TextView mCurrentTerm;
	private String currentTerm;
	private ProgressBar mProgressBar;
	private ProgressDialog dialog;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_more_term);
		init();
	}

	private void init() {
		getViewObj();
		setListener();
		Dialog();

		sp = getSharedPreferences(Constant.USERINFO_SP, MODE_PRIVATE);
		mCurrentTerm.setText(sp.getString(Constant.SPFIELD.CURRENTTERM, "未设置"));
		getTerms();
	}

	private void getViewObj() {
		mCurrentTerm = (TextView) findViewById(R.id.more_term_selectedterm);
		mBack = (Button) findViewById(R.id.b_more_term_back);
		mConfirm = (Button) findViewById(R.id.b_more_term_confirm);
		termListView = (ListView) findViewById(R.id.lv_more_term_list);
		mProgressBar = (ProgressBar) findViewById(R.id.pb_more_term);
	}

	private void Dialog()
	{
		dialog = new ProgressDialog(mActivity);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条  
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消  
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条  
        dialog.setIcon(R.drawable.ic_launcher);//  
        dialog.setMessage("正在加载，请等待。。。。");  
        dialog.setTitle("提示");
	}
	
	private void setListener() {
		mBack.setOnClickListener(this);
		termListView.setOnItemClickListener(this);
		mConfirm.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.b_more_term_back:
			finish();
			break;

		case R.id.b_more_term_confirm:
			String s = sp.getString(Constant.SPFIELD.CURRENTTERM, "");
			if(s.equals("")){
				saveTerm();
				Intent intent = new Intent(MoreTermActivity.this, MainActivity.class);
				intent.putExtra("againtomore", "yes");
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent); 
				MoreTermActivity.this.finish();
			}else{
				saveTerm();
				L.d(TAG, "选择的学期是：" + currentTerm);
				finish();
				break;
			}
		}

	}

	private void saveTerm() {
		if (!TextUtils.isEmpty(currentTerm)) {
			Editor editor = sp.edit();
			editor.putString(Constant.SPFIELD.CURRENTTERM, currentTerm);
			editor.commit();
			Toast.makeText(this, "选择学期是：" + currentTerm, Toast.LENGTH_SHORT)
					.show();
		}

	}

	private void getTerms() {
		dialog.show();
		Map<String, String> params = new HashMap<String, String>();
		String action = "";
		String userIdentity = sp.getString(Constant.SPFIELD.USERIDENTITY, "");
		String userName = sp.getString(Constant.SPFIELD.NAME, "");
		if (userIdentity.equals(Constant.USERIDENTITY[1])) {
			params.put("TeacherName", userName);
			action = "TeacherGetAllTermByUserName";
		} else if (userIdentity.equals(Constant.USERIDENTITY[0])) {
			params.put("StuNumber", userName);
			action = "StudentGetAllTermByUserName";
		} else {
			L.d(TAG, "没有获取到用户身份");
			return;
		}
		String url = URLTools
				.buildURL(Constant.INTERFACE_SITE + action, params);
		L.d(TAG, url);
		StringRequest stringRequest = new StringRequest(url, successListener(),
				errorListener());
		executeRequest(stringRequest);
	}

	private Response.Listener<String> successListener() {
		return new Response.Listener<String>() {
			@Override
			public void onResponse(String response) {
				L.d(TAG, response);
				if (response.length() != 0) {
					try {
						JSONObject jsonObj = new JSONObject(response);
						String success = jsonObj.getString("Success");
						if (success.equals("true")) {
							termsList = new ArrayList<String>();
							JSONArray jsonArray = jsonObj
									.getJSONArray("TermList");
							for (int i = 0; i < jsonArray.length(); i++) {
								JSONObject json = jsonArray.getJSONObject(i);
								termsList.add(json.getString("Term"));
							}
							termListView.setAdapter(new TermListViewAdapter(
									mActivity, termsList));
							mProgressBar.setVisibility(View.GONE);
							dialog.dismiss();
						} else {
							L.d(TAG, "Success is false");
						}
					} catch (JSONException e) {
						L.e(TAG, e.toString());
					}
				}
			}
		};
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		currentTerm = termsList.get(position);
	}
}

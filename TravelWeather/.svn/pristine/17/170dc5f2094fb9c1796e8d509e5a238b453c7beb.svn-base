package cuit.travelweather.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint.Join;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Toast;
import cuit.travelweather.R;
import cuit.travelweather.activity.PictureUploadActivity.SpinnerSelectedListener;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.MyHttpClient;

public class PictureCommentActivity extends BaseAct {
	private Context context = PictureCommentActivity.this;
	private ImageButton ib_comment_back;
	private EditText et_comment_content;
	private Button btn_comment_addBtn;
	private EditText et_tag_content;
	private Spinner spinnerPicType;
	private String picType = "FOOD";
	
	private HashMap<String, String> map;
	private SharedPreferences sp;
	
	private String temp=" ";
	
	private static final int SHOW_PROCESS = 1;
	private static final int DISMISS_PROCESS = 0;
	private static ProgressDialog pdialog;
	private static Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_PROCESS:
				pdialog.show();
				break;
			case DISMISS_PROCESS:
				pdialog.dismiss();
				break;
			}
		}

	};
	JSONObject jsonObject ;
	
	
	@SuppressWarnings("unchecked")
	private void initViews() {
		//map = new HashMap<String, String>();
		sp = context.getSharedPreferences("User_SP", MODE_PRIVATE);
		map = (HashMap<String, String>) getIntent().getSerializableExtra("map");
		ib_comment_back = (ImageButton) this.findViewById(R.id.ib_comment_back);
		et_comment_content = (EditText) this.findViewById(R.id.et_comment_content);
		btn_comment_addBtn = (Button) this.findViewById(R.id.btn_comment_addBtn);
		et_tag_content = (EditText) this.findViewById(R.id.et_tag_content);
		// 设置菜单
		spinnerPicType = (Spinner) this.findViewById(R.id.spinnerPicType);
		String[] m = {
				"吃",
				"住",
				"行",
				"购"
		};

		ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, m);
		adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinnerPicType.setAdapter(adapter);
		spinnerPicType.setOnItemSelectedListener(new SpinnerSelectedListener());
		spinnerPicType.setVisibility(View.VISIBLE);
	}

	public class SpinnerSelectedListener implements OnItemSelectedListener {
		
		String[] menuEnglish = {"FOOD","HOTEL","routeline","SHOP"};

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
			picType = menuEnglish[arg2];
		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			picType = menuEnglish[0];
		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_comment);
		initViews();

		ib_comment_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				finish();
			}
		});

		btn_comment_addBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				setComment();
			}
		});
	}
	
	// 上传评论
	private void setComment() {
		
		if (et_comment_content.getText().toString().equals("")&&et_comment_content.getTextSize()>0) {
			Toast.makeText(context, "请填写评论内容", Toast.LENGTH_SHORT).show();
			return;
		}
		if (!et_tag_content.getText().toString().equals("")) {
			temp = et_tag_content.getText().toString();
		}
		pdialog = new ProgressDialog(PictureCommentActivity.this);
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("正在添加……");
		
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... param) {
				// TODO Auto-generated method stub
				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				try {
					params.add(new BasicNameValuePair("commentTitle", URLEncoder
							.encode("图片评论", "UTF-8")));
					params.add(new BasicNameValuePair("commentDetails", URLEncoder
							.encode(et_comment_content.getText().toString(), "UTF-8")));
					params.add(new BasicNameValuePair("commnetItem", URLEncoder
							.encode("ITEM", "UTF-8")));
					params.add(new BasicNameValuePair("userId", URLEncoder
							.encode(sp.getString("userId", " "), "UTF-8")));
					params.add(new BasicNameValuePair("pictureID", URLEncoder
							.encode(map.get("pictureId"), "UTF-8")));
					params.add(new BasicNameValuePair("commentTypeId", URLEncoder
							.encode(map.get("pictureTypeId"), "UTF-8")));
					params.add(new BasicNameValuePair("commentType", URLEncoder
							.encode(picType, "UTF-8")));
					params.add(new BasicNameValuePair("commentPicture", URLEncoder
							.encode("", "UTF-8")));
					params.add(new BasicNameValuePair("typeDecribe", URLEncoder
							.encode(temp, "UTF-8")));
					params.add(new BasicNameValuePair("name", URLEncoder
							.encode(sp.getString("userId", " "), "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				jsonObject = MyHttpClient.getJson(PictureCommentActivity.this,
						Constant.addComment, params);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));
				int status;
				try {
					status = jsonObject.getInt("status");
					if (status == 1) {
						Toast.makeText(context, "评论成功", Toast.LENGTH_SHORT).show();
						finish();
					} else{
						Toast.makeText(context, "评论失败", Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				super.onPostExecute(result);
			}

		}.execute();
	}
	@Override
	public void onBackPressed() {
		finish();
		super.onBackPressed();
	}

}

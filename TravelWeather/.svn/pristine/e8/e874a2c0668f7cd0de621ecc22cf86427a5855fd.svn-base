package cuit.travelweather.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cuit.travelweather.R;
import cuit.travelweather.util.MyHttpClient;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.Activity;
import android.app.ProgressDialog;
import android.view.Menu;
import android.widget.ListView;
import android.widget.SimpleAdapter;

public class CommentDetailActivity extends BaseAct {
	
	private ListView list;

	private JSONObject jsonObject;
	private SimpleAdapter adapter;

	private static ProgressDialog pdialog;
	private static final int SHOW_PROCESS = 1;
	private static final int DISMISS_PROCESS = 0;
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_comment_detail);
		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		list = (ListView) findViewById(R.id.lv_commentdetail);
		getData();
	}
	
	private void getData() {
		// TODO Auto-generated method stub
		pdialog = new ProgressDialog(CommentDetailActivity.this);
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("正在加载数据中");
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... param) {
				// TODO Auto-generated method stub
				ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
				try {
					params.add(new BasicNameValuePair("pageNo", URLEncoder
							.encode("1", "UTF-8")));
					params.add(new BasicNameValuePair("pageSize", URLEncoder
							.encode("10", "UTF-8")));
					params.add(new BasicNameValuePair("commentType",
							URLEncoder.encode("weatherComment", "UTF-8")));
					params.add(new BasicNameValuePair("commentTypeId",
							URLEncoder.encode("100001", "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				adapter = getAdapter(data, "/comment/GetCommentList", params);

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));
				list.setAdapter(adapter);
				super.onPostExecute(result);
			}
		}.execute();
	}

	private SimpleAdapter getAdapter(ArrayList<HashMap<String, Object>> data,
			String method, List<NameValuePair> params) {

		jsonObject = MyHttpClient.getJson(this, method, params);
		try {
			JSONArray jsonArray = jsonObject.getJSONArray("commentList");
			HashMap<String, Object> map = null;
			for (int i = 0; i < jsonArray.length(); i++) {
				map = new HashMap<String, Object>();
				JSONObject json = jsonArray.getJSONObject(i);
				String commentDetails = json.getString("commentDetails");
				map.put("commentDetails", commentDetails);
				data.add(i, map);
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		adapter = new SimpleAdapter(CommentDetailActivity.this, data,
				R.layout.place_list_itme, new String[] { "commentDetails" },
				new int[] { R.id.place_list_tv });

		return adapter;

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.comment_detail, menu);
		return true;
	}

}

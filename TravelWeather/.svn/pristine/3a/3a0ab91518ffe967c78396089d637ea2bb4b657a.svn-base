package cuit.travelweather.activity;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;
import cuit.travelweather.R;
import cuit.travelweather.util.Split;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.webkit.WebView;

public class CurrentActivity extends BaseAct {

	private String city;
	private SharedPreferences sp;
	private WebView mWeb;
	
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
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_current);
		Intent it = getIntent();
		Bundle bundle = it.getExtras();
		city = Split.cutCity(bundle.getString(JPushInterface.EXTRA_EXTRA));
		init();
		
		
	}

	private void init() {
		// TODO Auto-generated method stub
		mWeb = (WebView) findViewById(R.id.current_webview);
		mWeb.getSettings().setUseWideViewPort(true);
		mWeb.setInitialScale(70);
		try {
			JSONObject jo = new JSONObject(city);
			System.out.println(jo.getString("url"));
			mWeb.loadUrl(jo.getString("url"));
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}
}

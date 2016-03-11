package cuit.travelweather.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cuit.travelweather.R;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.MyHttpClient;

public class DialogCurrentActivity extends BaseAct implements OnClickListener{  
  
	private LinearLayout layout01,layout02,layout03; 
    private Context mContext = DialogCurrentActivity.this;
    
	boolean bIsSelection = false;


	
	private TextView no_present;
	private static String text = "您今天已经猜过了!";
	private static ProgressDialog pdialog;     //进度条
	private static final int SHOW_PROCESS = 1;
	private static final int DISMISS_PROCESS = 0;
	
	private JSONObject jsonobject;
	
	List<NameValuePair> param = new ArrayList<NameValuePair>();

	private SharedPreferences sp;

	String[] menu_toolbar_name_array;
	private String max = "1";
	private String min = "0";
	private int in = 0;
	private String str2 = null;
	

           
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);
        //getData(); 
        setContentView(R.layout.activity_dialog);  
        TextView tv1 = (TextView)findViewById(R.id.dia1);
        TextView tv2 = (TextView)findViewById(R.id.dia2);
        TextView tv3 = (TextView)findViewById(R.id.dia3);
        sp= getSharedPreferences("test", Activity.MODE_PRIVATE); 
		 String size = sp.getString("affairinfo_fontsize", "2");
		if(size.equals("1")){
			tv1.setTextSize(10);
			tv2.setTextSize(10);
			tv3.setTextSize(10);
		}else if(size.equals("2")){
		}else{
			tv1.setTextSize(30);
			tv2.setTextSize(30);
			tv3.setTextSize(30);
		}
    	getData();   	
        initView();  
    }  
       
    /** 
     * 初始化组件 
     */  
    private void initView(){  
        //得到布局组件对象并设置监听事件  
    
    	//getData();
        layout01 = (LinearLayout)findViewById(R.id.llayout01);  
        layout02 = (LinearLayout)findViewById(R.id.llayout02);  //评论
        layout03 = (LinearLayout)findViewById(R.id.llayout03);  //查看排名
        
       
        layout01.setOnClickListener(this);  
        layout02.setOnClickListener(this);  
        layout03.setOnClickListener(this);
        layout02.setOnClickListener(new OnClickListener() {  
            @Override
            public void onClick(View v) {  
                startActivity(new Intent(DialogCurrentActivity.this,Comment4weatherActivity.class));  
            }  
        });  
    
    
        layout03.setOnClickListener(new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			simple(layout03);
		}
	});
        

     
    }
    
    
    public void simple(View source) {
		SharedPreferences sp;
		sp = mContext.getSharedPreferences("User_SP", MODE_PRIVATE);
		String sum = sp.getString("Sum", "100");
		String rating = sp.getString("YourNum", "1");
		AlertDialog.Builder builder = new AlertDialog.Builder(this).setTitle(
				"查看排名")
				.setMessage("您当前排名第" + rating + "位\n总共有" + sum + "人参加排名").setPositiveButton("确定", new DialogInterface.OnClickListener() {
					public void onClick(DialogInterface dialog,int which){
						Toast tt = Toast
								.makeText(mContext, "亲，你真棒！", Toast.LENGTH_LONG);
						tt.show();

						
					}
				}).setNegativeButton("取消",new DialogInterface.OnClickListener() {
					
					
					@Override
					public void onClick(DialogInterface arg0, int arg1) {
						// TODO Auto-generated method stub
						Toast tt = Toast
								.makeText(mContext, "亲，加油哦！", Toast.LENGTH_LONG);
						tt.show();

						
					}
			
				});
		builder.create().show();
}


    @Override  
    public boolean onTouchEvent(MotionEvent event){  
        finish();  
        return true;  
    }  
           
    @Override  
    public void onClick(View v) {  
    	
               
    }  
    
    void getData() {
		pdialog = new ProgressDialog(this);
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("正在加载内容……");

		sp = this.getSharedPreferences("User_SP", this.MODE_PRIVATE);
		new AsyncTask<Void, Void, List<NameValuePair>>() {

			@Override
			protected List<NameValuePair> doInBackground(Void... params) {
				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
				String userID = sp.getString("userName", "");
				try {
					param.add(new BasicNameValuePair("userID", URLEncoder
							.encode(userID, "UTF-8")));
					param.add(new BasicNameValuePair("highestTemperature",
							URLEncoder.encode(max, "UTF-8")));
					param.add(new BasicNameValuePair("lowestTemperature",
							URLEncoder.encode(min, "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					System.out.print(e.getMessage());
				}
				jsonobject = MyHttpClient.getJson(mContext,
						Constant.guessWeather, param);
				return null;
			}

		
				@Override
				protected void onPostExecute(List<NameValuePair> result) {

					handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));
					try {
						in = jsonobject.getInt("status");
						str2 = jsonobject.getString("message");
						System.out.println(in);
						System.out.println(str2);

					} catch (JSONException e) {
						e.printStackTrace();
					}
					if (in == -1) {
						layout01.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								showDialogFragment("fragment dialog");
							}
						});
					} else {
						layout01.setOnClickListener(new View.OnClickListener() {
							public void onClick(View v) {
								showDialogFragment("fragment dialog");
							}
						});
					}
					super.onPostExecute(result);
				}

			}.execute();
		

	}

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
	
	
	
	void showDialogFragment(String str) {
		DialogFragment newFragment = MyDialogFragment.newInstance(str);
		newFragment.show(getFragmentManager(), str);
	}
	
	
	@SuppressLint("NewApi")
	AlertDialog.OnKeyListener onKeyListener = new AlertDialog.OnKeyListener() {
		@Override
		public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				DialogCurrentActivity.this.finish();
			}
			return false;
		}
	};

}

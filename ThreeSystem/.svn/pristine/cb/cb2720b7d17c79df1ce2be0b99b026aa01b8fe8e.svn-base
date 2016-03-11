package com.example.PCenter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cn.trinea.android.common.entity.FailedReason;
import cn.trinea.android.common.service.impl.ImageCache;
import cn.trinea.android.common.service.impl.RemoveTypeLastUsedTimeFirst;
import cn.trinea.android.common.service.impl.ImageMemoryCache.OnImageCallbackListener;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.plus.URLTools;
import com.android.volley.plus.VolleyErrorHelper;
import com.android.volley.toolbox.StringRequest;
import com.common.tools.L;
import com.exam.ThreeSystem.R;
import com.example.PCenter.adapter.ClassListViewAdapter_Homework_tea_class_list_activity;
import com.example.PCenter.adapter.Homework_everystu_Adapter;
import com.example.PCenter.adapter.Homework_tea_class_activity;
import com.example.PCenter.fragments.HomeWorkFragment;

import android.widget.SeekBar;
import android.widget.ImageView.ScaleType;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;
//评阅详情（未批阅 ）
public class Homework_tea_class_activity_detail_MarkedList extends BaseActivity implements OnClickListener,
OnSeekBarChangeListener {
	private SharedPreferences sp;
	private String FILE = Constant.USERINFO_SP;
	private Button back_tea=null;
	private Button tea_tijiao=null;
	
	String action="";
	private List<Map<String, String>> classesList=null;
	public JSONArray jsonArray;
	private JSONObject json;
	
	protected Activity mActivity;
	private String TeacherMarkMemo;
	private String WorkName;
	private String WorkScore;
	private String WorkContent;
	private String WorkMemo;//学生备注
	private String isTimeOut;
	private String uploadTime;
	private  Bitmap bit;
	private String StudentWorkID;
	private String TeachingClassID;
	private String HomeworkID;
	private String ScoreGrade;
	private String Memo;//教师评语
	private String TeacherName;
	private EditText tea_remark;
	private EditText tea_edit;
	private Thread mThread;
	//private String URL="http://222.18.158.198:8016";
	private String URL;
	private String realurl;
	private String WorkURL;
	private String AnswerURL;
	 public    Bitmap bitmap;
	 private   ImageView imageView1;
	TextView stu_WorkScore;
	TextView stu_outoftime;
	TextView stu_name;
	private TextView tea_markmemo;
	TextView stu_number;
	TextView stu_title;
	TextView stu_memo;
	TextView stu_StuScore;
	TextView stu_updatetime;
	TextView tv_more_class_id;
	TextView stu_daan;
	private String  StuName;
	private String StuNumber;
	private SeekBar seekbar;
	private static final int MSG_SUCCESS = 0;// 获取图片成功的标识
	private static final int MSG_FAILURE = 1;// 获取图片失败的标识
	private LinearLayout tea_LinearLayout;
	private LinearLayout stu_getscorelayout;
	private LinearLayout workscore_LinearLayout;
	private LinearLayout workgrade_LinearLayout;
	private LinearLayout ImageView_LinearLayout;
	private ProgressDialog dialog;
	private ProgressDialog dialog2;
	public LayoutParams params;
	public static final ImageCache IMAGE_CACHE = new ImageCache(128, 512);
	public static final String TAG_CACHE = "image_cache";
	public void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homework_review_details);
		mActivity = this;
		Intent intent=getIntent();
		TeachingClassID=intent.getStringExtra("TeachingClassID");
		TAG = mActivity.getClass().getSimpleName();
		sp = mActivity.getSharedPreferences(FILE, Context.MODE_PRIVATE);
		TeacherName=sp.getString(Constant.SPFIELD.NAME, "");
		URL=Constant.URL;
		
		getViewObj();
		
		loadseekbar();
		setListener();
		gettext();
		settext();
//		getimage();
		initBiCache();
		if(!AnswerURL.equals(""))
		{
		Dialog2(); 
		imageView1.setScaleType(ScaleType.CENTER_CROP);
		imageView1.setBackgroundResource(R.drawable.image_border);
		LinearLayout.LayoutParams layoutParams = (LinearLayout
				.LayoutParams) imageView1
				.getLayoutParams();
		Display display = getWindowManager().getDefaultDisplay();
		layoutParams.width = display.getWidth();
		layoutParams.height = (int) (display.getHeight() * 0.85);
		imageView1.setLayoutParams(layoutParams);
		String pictureLink = realurl;
		IMAGE_CACHE.get(pictureLink, imageView1);
		}
		
	}
	
	private void Dialog2()
	{
		dialog2 = new ProgressDialog(mActivity);
		dialog2.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条  
        dialog2.setCancelable(false);// 设置是否可以通过点击Back键取消  
        dialog2.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条  
        dialog2.setIcon(R.drawable.ic_launcher);//  
        dialog2.setMessage("正在加载图片");  
        dialog2.setTitle("提示");
        dialog2.show();
	}
	private void getViewObj() {
		// TODO 自动生成的方法存根
		tea_tijiao=(Button) findViewById(R.id.tea_tijiao);
		back_tea=(Button) findViewById(R.id.back_tea);
		stu_name=(TextView) findViewById(R.id.homeworkDetail_title);
		stu_number=(TextView) findViewById(R.id.homeworkDetail_number);
		stu_WorkScore=(TextView) findViewById(R.id.stu_WorkScore);
		stu_outoftime=(TextView) findViewById(R.id.stu_outoftime);
		stu_title=(TextView) findViewById(R.id.stu_title);
		stu_memo=(TextView) findViewById(R.id.stu_memo);
		stu_updatetime=(TextView) findViewById(R.id.stu_updatetime);
		tea_LinearLayout=(LinearLayout) findViewById(R.id.tea_LinearLayout);
		tea_markmemo=(TextView) findViewById(R.id.TeacherMarkMemo);
		tea_markmemo.setVisibility(View.GONE);
		stu_daan=(TextView) findViewById(R.id.stu_daan);
		tea_edit=(EditText) findViewById(R.id.tea_edit);
		tea_remark=(EditText) findViewById(R.id.tea_remark);
		seekbar=(SeekBar) findViewById(R.id.homework_givescore_seekbar);
		workscore_LinearLayout=(LinearLayout) findViewById(R.id.workscore_LinearLayout);
		stu_getscorelayout=(LinearLayout) findViewById(R.id.stu_getscorelayout);
		workgrade_LinearLayout=(LinearLayout) findViewById(R.id.workgrade_LinearLayout);
		ImageView_LinearLayout=(LinearLayout) findViewById(R.id.ImageView_LinearLayout);
		workgrade_LinearLayout.setVisibility(View.GONE);
		stu_getscorelayout.setVisibility(View.GONE);
		workscore_LinearLayout.setVisibility(View.GONE);
		imageView1=(ImageView) this.findViewById(R.id.imageView1);
		params = imageView1.getLayoutParams();
//		tea_edit.setVisibility(View.INVISIBLE);
//		stu_daan.setVisibility(View.INVISIBLE);
//		tea_LinearLayout.setVisibility(View.INVISIBLE);
	}
	private void setListener() {
		// TODO 自动生成的方法存根
		tea_tijiao.setOnClickListener(this);
		back_tea.setOnClickListener(this);
		seekbar.setOnSeekBarChangeListener(this);
	
	}
	//点击显示大图
	public void show_click(View v){
	     startActivity(new Intent(this,ImageShower.class));
	     
	    }
	
	private void gettext()
	{
		WorkName=homework_tea_class_activity.WorkName;
		WorkScore=homework_tea_class_activity.WorkScore;
		WorkContent=homework_tea_class_activity.WorkContent;
		WorkMemo=homework_tea_class_activity.WorkMemo;
		isTimeOut=homework_tea_class_activity.isTimeOut;
		uploadTime=homework_tea_class_activity.uploadTime;
//		TeacherMarkMemo=homework_tea_class_activity.TeacherMarkMemo;
		StuName=homework_tea_class_activity.StuName;
		StuNumber=homework_tea_class_activity.StuNumber;
		HomeworkID=Homework_tea_class_list_activity.HomeworkID;
		StudentWorkID=homework_tea_class_activity.StuWorkID;
		AnswerURL=homework_tea_class_activity.AnswerURL;
		if(AnswerURL.equals(""))
		{
			ImageView_LinearLayout.setVisibility(View.GONE);
		}
		
		realurl=URL+AnswerURL;
//		TeachingClassID=HomeWorkFragment.Tea_TeachingClassID;
		ScoreGrade=tea_remark.getText().toString();
		Memo=tea_edit.getText().toString();
		
	}
	private void settext()
	{
		stu_name.setText(StuName);
		stu_daan.setText(WorkContent);
		stu_number.setText(StuNumber);
		stu_WorkScore.setText(WorkScore);
		stu_daan.setText(WorkContent);
		stu_title.setText(WorkName);
		stu_memo.setText(WorkMemo);
		stu_updatetime.setText(uploadTime);

		if(isTimeOut.equals("NO"))
		{
		stu_outoftime.setText("未过期");
		}
		else if(isTimeOut.equals("YES"))
		{
			stu_outoftime.setText("已过期");
		}

	}
	
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.tea_tijiao:
		{
			Dialog();
			if(tea_remark.getText().toString().equals(""))
			{
				Toast.makeText(mActivity, "请选择作业等级!", 2000).show();
			}
			else {
				if(tea_edit.getText().toString().equals(""))
				{
					tea_edit.setText(" ");
				}
			gettext();
			putterm();
			
			System.out.println("点击了提交");
			}
			
		}
		break;
		case R.id.back_tea:
		{
			if(IMAGE_CACHE!=null)
			{
			IMAGE_CACHE.clear();
			}
			if(bit!=null)
			{
			bit.recycle();
			}
			finish();
		}
		break;
		}
	}
	
	private void Dialog()
	{
		dialog = new ProgressDialog(mActivity);
		dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条  
        dialog.setCancelable(true);// 设置是否可以通过点击Back键取消  
        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条  
        dialog.setIcon(R.drawable.ic_launcher);//  
        dialog.setMessage("请等待。。。。");  
        dialog.setTitle("提示");
        dialog.show();
	}
	private void putterm()
	{
		Map<String, String> params = new HashMap<String, String>();
		params.put("StudentWorkID",StudentWorkID );
		params.put("HomeworkID",HomeworkID);
		params.put("TeachingClassID",TeachingClassID);
		params.put("ScoreGrade",ScoreGrade);
		params.put("Memo",Memo);
		params.put("TeacherName",TeacherName);
		action="New_CheckFinishedHomework";
		String url = URLTools.buildURL(Constant.INTERFACE_SITE + action,params);
		L.d(TAG, url);
		L.d(TAG, Memo);
		L.d(TAG, ScoreGrade);
		StringRequest stringRequest = new StringRequest(url,successListener(),new Response.ErrorListener() {
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
	private Response.Listener<String> successListener()
	{
		return new Response.Listener<String>()
		{
			public void onResponse(String response)
			{
				try
				{
				JSONObject jsonObj = new JSONObject(response);
				String success = jsonObj.getString("Success");
				if(success.equals("true"))
				{
				System.out.println("成功");
				L.d(TAG, response);
				System.out.println("请求是："+response);
				Toast.makeText(mActivity, "提交成功！", 2000).show();
				dialog.dismiss();
				finish();
				
				}
				else
				{
					Toast.makeText(mActivity, "提交失败！", 2000).show();
					dialog.dismiss();
				}
				}
				catch(JSONException e)
				{
					L.e(TAG, e.toString());
				}
			}
		};
	}

	public void loadseekbar()
	{
		seekbar.setProgress(0);
		tea_remark.setText("A");
	}
	
	@Override
	public void onProgressChanged(SeekBar arg0, int arg1, boolean arg2) {
		// TODO 自动生成的方法存根


		int s = seekbar.getProgress();
		if(s==0){
			tea_remark.setText("A");
		}if(s==1){
			tea_remark.setText("B");
		}if(s==2){
			tea_remark.setText("C");
		}if(s==3){
			tea_remark.setText("D");
		}if(s==4){
			tea_remark.setText("E");
		}
		
	}

	@Override
	public void onStartTrackingTouch(SeekBar arg0) {
		// TODO 自动生成的方法存根
		
	}

	@Override
	public void onStopTrackingTouch(SeekBar arg0) {
		// TODO 自动生成的方法存根
		
	}
	
	private void getimage()
	{
		mThread = new Thread(runnable);
		mThread.start();
		System.out.println(realurl);
		 

	}
	
	Runnable runnable = new Runnable()
	{

		@Override
		public void run() {
			// TODO 自动生成的方法存根
			HttpClient httpClient = new DefaultHttpClient();
			HttpGet httpGet = new HttpGet(realurl);
//			final Bitmap bitmap;
			try
			{
				HttpResponse httpResponse = httpClient.execute(httpGet);
				bitmap = BitmapFactory.decodeStream(httpResponse.getEntity().getContent());
			}
			catch(Exception e)
			{
				mHandler.obtainMessage(MSG_FAILURE).sendToTarget();
				return;
			}
			mHandler.obtainMessage(MSG_SUCCESS, bitmap).sendToTarget();
		}
		
	};
	/*private static  Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case MSG_SUCCESS:
				
				imageView1.setImageBitmap((Bitmap) msg.obj);
				break;
			case MSG_FAILURE:
				break;
			}
		}
	};*/
	private Handler mHandler = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case MSG_SUCCESS:
				params.height=bitmap.getHeight();
				params.width =bitmap.getWidth();
				imageView1.setLayoutParams(params);
				imageView1.setImageBitmap((Bitmap) msg.obj);
				break;
			case MSG_FAILURE:
				break;
			}
		}
	};
	public void initBiCache() {
		/** init icon cache **/
		OnImageCallbackListener imageCallBack = new OnImageCallbackListener() {
			@Override
			public void onGetSuccess(String imageUrl, Bitmap loadedImage, View view, boolean isInCache) {
				if (view != null && loadedImage != null) {
					bit=BitmapHelper.comp(loadedImage);
					loadedImage.recycle();
					ImageView imageView = (ImageView) view;
					imageView.setImageBitmap(bit);
					dialog2.dismiss();
					if (!isInCache) {
						imageView.startAnimation(getInAlphaAnimation(2000));
					}

				}
			}

			/**
			 * callback function before get image, run on ui thread
			 * 
			 * @param imageUrl
			 *            imageUrl
			 * @param view
			 *            view need the image
			 */
			@Override
			public void onPreGet(String imageUrl, View view) {
				// Log.e(TAG_CACHE, "pre get image");
			}

			/**
			 * callback function after get image failed, run on ui thread
			 * 
			 * @param imageUrl
			 *            imageUrl
			 * @param loadedImage
			 *            bitmap
			 * @param view
			 *            view need the image
			 * @param failedReason
			 *            failed reason for get image
			 */
			@Override
			public void onGetFailed(String imageUrl, Bitmap loadedImage, View view, FailedReason failedReason) {
				Log.e(TAG_CACHE,
						new StringBuilder(128).append("get image ").append(imageUrl)
								.append(" error, failed type is: ").append(failedReason.getFailedType())
								.append(", failed reason is: ").append(failedReason.getCause().getMessage())
								.toString());
			}

			@Override
			public void onGetNotInCache(String imageUrl, View view) {
				if (view != null && view instanceof ImageView) {
					((ImageView) view).setImageResource(R.drawable.not_loaded);// 等待载入时的图片
				}
			}
		};
		IMAGE_CACHE.setOnImageCallbackListener(imageCallBack);
		IMAGE_CACHE.setCacheFullRemoveType(new RemoveTypeLastUsedTimeFirst<Bitmap>());

		IMAGE_CACHE.setHttpReadTimeOut(10000);
		IMAGE_CACHE.setOpenWaitingQueue(true);
		IMAGE_CACHE.setValidTime(-1);
		/**
		 * close connection, default is connect keep-alive to reuse connection.
		 * if image is from different server, you can set this
		 */
		// IMAGE_CACHE.setRequestProperty("Connection", "false");
	}
	public static AlphaAnimation getInAlphaAnimation(long durationMillis) {
		AlphaAnimation inAlphaAnimation = new AlphaAnimation(0, 1);
		inAlphaAnimation.setDuration(durationMillis);
		return inAlphaAnimation;
	}
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO 自动生成的方法存根
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			if(IMAGE_CACHE!=null)
			{
			IMAGE_CACHE.clear();
			}
			if(bit!=null)
			{
			bit.recycle();
			}
        	finish();
             return false; 
        }else { 
            return super.onKeyDown(keyCode, event); 
        } 
	}
}


package com.example.PCenter;

import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;

import cn.trinea.android.common.entity.FailedReason;
import cn.trinea.android.common.service.impl.ImageCache;
import cn.trinea.android.common.service.impl.RemoveTypeLastUsedTimeFirst;
import cn.trinea.android.common.service.impl.ImageMemoryCache.OnImageCallbackListener;

import com.common.tools.T;
import com.exam.ThreeSystem.R;
import com.example.PCenter.fragments.HomeWorkFragment;

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
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
//添加數據、、、、、
//学生作业详情
public class Homework_stu_detail_activity extends BaseActivity implements OnClickListener{
	private Button homework_homeworkDetail_back;
	private Button homework_homeworkDetail_submit;
	private Button homeworkDetail_content;
	private TextView homeworkDetail_text_content;
	private TextView homeworkDetail_guoqi;
	private TextView homeworkDetail_teacher;
	private TextView homeworkDetail_title;
	private TextView homeworkDetail_note;
	private TextView homeworkDetail_manfen;
	private TextView homeworkDetail_stu_daan;
	private TextView homeworkDetail_datetime;
	private EditText homeworkDetail_edit;
	private String StuNumber;
	private String FILE = Constant.USERINFO_SP;
	private String HomeworkName;
	private String Memo;
	private String WorkScore;
	private String isTimeOut;
	private String DeadTime;
	private String StuAnswer;
	private String WorkDesc;
	private String TeacherName;
	private String TeachingClassID;
	private  Bitmap bit;
	private LinearLayout stu_ImageView_LinearLayout;
	private String isMarked;
	private Thread mThread;
	private String realurl;
	private String WorkURL;
	private   ProgressDialog dialog;
	//private String URL="http://222.18.158.198:8016";
	private String URL;
	
	public    Bitmap bitmap;
	private static  ImageView stu_homework_image_view;
	private LinearLayout allscore_LinearLayout;
	private SharedPreferences sp;
	private static final int MSG_SUCCESS = 0;// 获取图片成功的标识
	private static final int MSG_FAILURE = 1;// 获取图片失败的标识
	public static final ImageCache IMAGE_CACHE = new ImageCache(128, 512);
	public static final String TAG_CACHE = "image_cache";
	private   static boolean hasgetbitmap=false;
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_homework_homeworkdetail);
		mActivity = this;
		TAG = mActivity.getClass().getSimpleName();
		sp = mActivity.getSharedPreferences(FILE, Context.MODE_PRIVATE);
		URL=Constant.URL;
		hasgetbitmap=false;
		getViewObj();
		setListener();
		getdata();
		setText();
		initBiCache();
//		getimage();
		//有图片时加载
		if(!WorkURL.equals(""))
		{
		Dialog(); 
		stu_homework_image_view.setScaleType(ScaleType.CENTER_CROP);
		stu_homework_image_view.setBackgroundResource(R.drawable.image_border);
		LinearLayout.LayoutParams layoutParams = (LinearLayout
				.LayoutParams) stu_homework_image_view
				.getLayoutParams();
		Display display = getWindowManager().getDefaultDisplay();
		layoutParams.width = display.getWidth();
		layoutParams.height = (int) (display.getHeight() * 0.85);
		stu_homework_image_view.setLayoutParams(layoutParams);
		String pictureLink = realurl;
		IMAGE_CACHE.get(pictureLink, stu_homework_image_view);
		}
	}
	private void getViewObj()
	{
		stu_homework_image_view=(ImageView) findViewById(R.id.stu_homework_image_view);
		homework_homeworkDetail_back=(Button) findViewById(R.id.homework_homeworkDetail_back);
		homework_homeworkDetail_submit=(Button) findViewById(R.id.homework_homeworkDetail_submit);
		homeworkDetail_content=(Button) findViewById(R.id.homeworkDetail_content);
		homeworkDetail_title=(TextView) findViewById(R.id.homeworkDetail_title);
		homeworkDetail_note=(TextView) findViewById(R.id.homeworkDetail_note);
		homeworkDetail_text_content=(TextView) findViewById(R.id.homeworkDetail_text_content);
		homeworkDetail_manfen=(TextView) findViewById(R.id.homeworkDetail_manfen);
		homeworkDetail_guoqi=(TextView) findViewById(R.id.homeworkDetail_guoqi);
		//顯示評閱老師
		homeworkDetail_teacher=(TextView) findViewById(R.id.homeworkDetail_teacher);
		homeworkDetail_edit=(EditText) findViewById(R.id.homeworkDetail_edit);
		homeworkDetail_datetime=(TextView) findViewById(R.id.homeworkDetail_datetime);
		allscore_LinearLayout=(LinearLayout) findViewById(R.id.allscore_LinearLayout);
		allscore_LinearLayout.setVisibility(View.GONE);
		homeworkDetail_edit.setVisibility(View.INVISIBLE);
		homeworkDetail_stu_daan=(TextView) findViewById(R.id.homeworkDetail_stu_daan);
		stu_ImageView_LinearLayout=(LinearLayout) findViewById(R.id.stu_ImageView_LinearLayout);
		
	}
	private void setListener()
	{
		homework_homeworkDetail_back.setOnClickListener(this);
		homework_homeworkDetail_submit.setOnClickListener(this);
		homeworkDetail_content.setOnClickListener(this);
		homeworkDetail_content.setVisibility(View.INVISIBLE);
		homework_homeworkDetail_submit.setVisibility(View.INVISIBLE);
		
	}
	public void onClick(View v)
	{
		switch(v.getId())
		{
		case R.id.homework_homeworkDetail_back:
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
		case R.id.homeworkDetail_content:
		{
			
		}
		break;
		}
	}
	private void getdata()
	{
		Intent intent = getIntent();
		isMarked=intent.getStringExtra("isMarked");
		TeachingClassID = HomeWorkFragment.Stu_TeachingClassID;
//		HomeworkName= intent.getStringExtra("HomeworkName");
		HomeworkName=homework_state_activity_stu_finnished.WorkName;
		System.out.println("這個那麼是    。。。。"+HomeworkName);
//		WorkScore=homework_state_activity_stu_finnished.WorkScore;
		isTimeOut=homework_state_activity_stu_finnished.isTimeOut;
		WorkDesc=homework_state_activity_stu_finnished.WorkDesc;
		Memo=homework_state_activity_stu_finnished.Memo;
		StuAnswer=homework_state_activity_stu_finnished.StuAnswer;
		TeacherName=homework_state_activity_stu_finnished.TeacherName;
		DeadTime=homework_state_activity_stu_finnished.DeadTime;
		WorkURL=homework_state_activity_stu_finnished.WorkURL;
		if(WorkURL.equals(""))
		{
			stu_ImageView_LinearLayout.setVisibility(View.GONE);
		}
		realurl=URL+WorkURL;
	}
	private void setText()
	{
		homeworkDetail_title.setText(HomeworkName);
		homeworkDetail_note.setText(Memo);
		homeworkDetail_text_content.setText(WorkDesc);
		homeworkDetail_manfen.setText(WorkScore);
		homeworkDetail_datetime.setText(DeadTime);
		if(isTimeOut.equals("NO"))
		{
			homeworkDetail_guoqi.setText("未过期");
		}
		else if(isTimeOut.equals("YES"))
		{
			homeworkDetail_guoqi.setText("已过期");
		}
		if(isMarked.equals("NO"))
		{
			homeworkDetail_teacher.setText("暂未批阅");
		}
		else if(isMarked.equals("YES"))
		{
		homeworkDetail_teacher.setText(TeacherName);
		}
		homeworkDetail_stu_daan.setText(StuAnswer);
	}
	// 显示网络加载的图片 
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
				mHandler2.obtainMessage(MSG_FAILURE).sendToTarget();
				return;
			}
			mHandler2.obtainMessage(MSG_SUCCESS, bitmap).sendToTarget();
		}
		
	};
	private static  Handler mHandler2 = new Handler()
	{
		public void handleMessage(Message msg)
		{
			switch (msg.what)
			{
			case MSG_SUCCESS:
				stu_homework_image_view.setImageBitmap((Bitmap) msg.obj);
				break;
			case MSG_FAILURE:
				break;
			}
		}
	};

	//点击显示大图
		public void show_click(View v){
//		     startActivity(new Intent(this,Stu_finnished_Imageshower.class));
		    }
		public void initBiCache() {
			/** init icon cache **/
			OnImageCallbackListener imageCallBack = new OnImageCallbackListener() {
				@Override
				public void onGetSuccess(String imageUrl, Bitmap loadedImage, View view, boolean isInCache) {
					if (view != null && loadedImage != null) {
//						ImageView imageView = (ImageView) view;
//						imageView.setImageBitmap(loadedImage);
						bit=BitmapHelper.comp(loadedImage);
						loadedImage.recycle();
						ImageView imageView = (ImageView) view;
						imageView.setImageBitmap(bit);
						dialog.dismiss();
						hasgetbitmap=true;
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
			if (keyCode == KeyEvent.KEYCODE_BACK ) {
				if(hasgetbitmap==false)
				{
					T.showShort(mActivity, "正在加载数据");
				}
				else if(hasgetbitmap==true)
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
	             return true; 
	        }else { 
	            return super.onKeyDown(keyCode, event); 
	        } 
		}
		
		private void Dialog()
		{
			dialog = new ProgressDialog(mActivity);
			dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);// 设置进度条的形式为圆形转动的进度条  
	        dialog.setCancelable(false);// 设置是否可以通过点击Back键取消  
	        dialog.setCanceledOnTouchOutside(false);// 设置在点击Dialog外是否取消Dialog进度条  
	        dialog.setIcon(R.drawable.ic_launcher);//  
	        dialog.setMessage("正在加载图片");  
	        dialog.setTitle("提示");
	        dialog.show();
		}
}
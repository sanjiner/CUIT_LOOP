package com.example.PCenter;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.security.PublicKey;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.entity.FileEntity;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import com.exam.ThreeSystem.R;
import com.example.PCenter.adapter.AddhomeworkAdapter;
import com.example.PCenter.fragments.HomeWorkFragment;
import com.example.PCenter.ImageThumbnail;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.android.volley.Response;
import com.common.tools.BitmapToBase64;
import com.common.tools.DateTimePickerDialog;
import com.common.tools.L;
import com.common.tools.DateTimePickerDialog.OnDateTimeSetListener;
import com.common.tools.T;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.IntentService;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.view.ViewGroup.LayoutParams;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Toast;

public class homework_tea_addhomework extends BaseActivity implements
		OnClickListener, OnItemClickListener, OnItemSelectedListener,
		OnCheckedChangeListener {
	public List<Map<String, String>> termsList;
	private ListView termListView;
	private Button addhomework_back;
	private Button addhomework_comp;
	private Button addhomework_addImg;
	private Button addhomework_studentVisible;// 学生是否可见按钮
	private Button addhomework_last_date;// 截止日期按钮
	private ImageView imageView;// 照片显示
	private EditText addhomework_homeworkTitle;
	private EditText addhomework_homeworkContent;
	private EditText addhomework_hour;
	private EditText addhomework_minute;
	private EditText addhomework_note;
	private ListView addhomework_listview;
	private SharedPreferences sp;
	private String FILE = Constant.USERINFO_SP;
	private int oode1;
	private static final int CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE = 100;
	private static final int PICK_IMAGE_ACTIVITY_REQUEST_CODE = 200;
//	private static String picFileFullName=null;
	private String action = "";
	private String actionwithimage = "";
	// 新添作业需要的参数
	private String TeacherName;
	private String TeachingClassIDListString ="";
	private String TeachingClassID="";
	private String WorkScore;
	private String WorkName="";
	private String WorkDesc;
	private String DeadTime="";
	private String DeadTime2="";
	private String IsStuSee;
	private String Memo;
	private String urlPath;
	private String urlPathwithimage;
	private String TeachingClassName = "";
	private String realPath = null;
	private String TeachingClassIDdata[];
	private String judgehomework="false";
	private String  getalldata="true";
	private Bitmap bmp;
	private Bitmap resizedBitmap;
	private Uri uri;
	private LinearLayout addgrade_LinearLayout;
	private final static int MSG_POST_HOME_WORKE = 0x0001;
	private  List<checkbox_damobean> demoDatas;
	private Map<Integer, Boolean> checkStatusMap = new HashMap<Integer, Boolean>();
	public CheckBox cb;
	AddhomeworkAdapter listItemAdapter;
	private ProgressDialog  dialog;
	private static HttpClient client = null;
	// private Stream ImgStream;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_addhomework);
		mActivity = this;
		TAG = mActivity.getClass().getSimpleName();
		sp = mActivity.getSharedPreferences(FILE, Context.MODE_PRIVATE);
		demoDatas=HomeWorkFragment.demoDatas;
		termsList = HomeWorkFragment.termsList;
		action = "New_AddCommonHomeworkNoImagePost";
		actionwithimage = "New_AddCommonHomeworkAndImagePost";
		urlPath = Constant.INTERFACE_SITE + action;
		
		getViewObj();
		setListener();
		listItemAdapter=new AddhomeworkAdapter(mActivity, termsList);
		termListView.setAdapter(listItemAdapter);

		
		
		
	}

	
	private void getViewObj() {
		addhomework_back = (Button) findViewById(R.id.addhomework_back);
		addhomework_comp = (Button) findViewById(R.id.addhomework_comp);
		addhomework_addImg = (Button) findViewById(R.id.addhomework_addImg);
		addhomework_studentVisible = (Button) findViewById(R.id.addhomework_studentVisible);
		addhomework_last_date = (Button) findViewById(R.id.addhomework_the_last_date);
		addhomework_homeworkTitle = (EditText) findViewById(R.id.addhomework_homeworkTitle);
		addhomework_homeworkContent = (EditText) findViewById(R.id.addhomework_homeworkContent);
		addhomework_hour = (EditText) findViewById(R.id.addhomework_hour);
		addhomework_minute = (EditText) findViewById(R.id.addhomework_minute);
		addhomework_note = (EditText) findViewById(R.id.addhomework_note);
		imageView = (ImageView) findViewById(R.id.addhomework_image_view);
		addgrade_LinearLayout = (LinearLayout) findViewById(R.id.addgrade_LinearLayout);
		addgrade_LinearLayout.setVisibility(View.GONE);
		termListView = (ListView) findViewById(R.id.addhomework_listview);
		
		

	}

	private void setListener() {
		termListView.setOnItemClickListener(this);
		addhomework_back.setOnClickListener(this);
		addhomework_comp.setOnClickListener(this);
		addhomework_addImg.setOnClickListener(this);
		addhomework_studentVisible.setOnClickListener(this);
		addhomework_last_date.setOnClickListener(this);
		
		
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

	private Handler mHandler = new Handler() {
		@Override
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case MSG_POST_HOME_WORKE:
				T.showShort(mActivity, "添加作业成功");
				break;

			default:
				break;
			}
		};
	};

	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.addhomework_back: {
			finish();
			break;
		}
		case R.id.addhomework_comp: {
			
		
			Dialog();
			gethomeworkdata();
			
			if(getalldata.equals("true"))
		{
				
			if(realPath==null)
			{
				
				new Thread(new Runnable() {
					
					
					@Override
					public void run() {
						HttpPostData();
					}
				}).start();
			}
			else if(realPath!=null)
			{
//				postimage();
//				new Thread(new Runnable() {
//
//					@Override
//					public void run() {
//						HttpPostData();
//					}
//				}).start();
				
			}
			System.out.println("点击了提交");
			if(judgehomework.equals("true"))
			{
				finish();
				
			}
			else
			{
				
			}
			
		}
			else if(getalldata.equals("false"))
			{
				Toast.makeText(mActivity, "班级、题目名称、截止时间必填",
						Toast.LENGTH_SHORT).show();
				dialog.dismiss();
				
			}
			
			break;
		}
		case R.id.addhomework_addImg: {
			//AddImg();
			gethomeworkdata();
			if(getalldata.equals("false"))
			{
				Toast.makeText(mActivity, "班级、题目名称、截止时间必填",
						Toast.LENGTH_SHORT).show();
			}
			else if(getalldata.equals("true"))
			{
				Intent intent=new Intent(homework_tea_addhomework.this,Homework_tea_addhomework_onweb.class);
				intent.putExtra("TeachingClassIDListString", TeachingClassIDListString);
				intent.putExtra("TeacherName", TeacherName);
				intent.putExtra("WorkName", WorkName);
				intent.putExtra("WorkDesc", WorkDesc);
				intent.putExtra("DeadTime", DeadTime2);
				intent.putExtra("IsStuSee", IsStuSee);
				intent.putExtra("Memo", Memo);
				startActivity(intent);
			}
			break;
		}
		case R.id.addhomework_studentVisible: {
			studentVisible();
			break;
		}
		case R.id.addhomework_the_last_date: {
			Set_Last_Date();
			break;
		}
		}
	}

	

	public void onItemClick(AdapterView<?> parent, View view,
            int position, long id) {
        boolean state;
        cb = (CheckBox) view.findViewById(R.id.addHomework_className);
        state = (cb.isChecked()) ? false : true;
        cb.setChecked(state);
	}
	
	
	
	// 弹出学生可见或者不可见
	private void studentVisible() {
		LayoutInflater inflater = LayoutInflater.from(mActivity);
		View visibleview = inflater.inflate(
				R.layout.activity_homework_add_studentvisible, null);
		Button button1 = (Button) visibleview
				.findViewById(R.id.add_homework_stu_visible);
		Button button2 = (Button) visibleview
				.findViewById(R.id.add_homework_stu_not_visible);
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		builder.setView(visibleview);
		final AlertDialog dialog = builder.create();
		dialog.show();
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				addhomework_studentVisible.setText("学生可见");
			}
		});
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
				addhomework_studentVisible.setText("学生不可见");
			}
		});
	}

	// 弹出截止日期选择
	private void Set_Last_Date() {
		DateTimePickerDialog dialog = new DateTimePickerDialog(this,
				System.currentTimeMillis());
		dialog.setOnDateTimeSetListener(new OnDateTimeSetListener() {
			public void OnDateTimeSet(AlertDialog dialog, long date) {
				// Toast.makeText(mActivity, "您输入的日期是："+getStringDate(date),
				// Toast.LENGTH_LONG).show();
				String[] s = getStringDate(date).split(":");
				addhomework_last_date.setText(s[0]);
				addhomework_hour.setText(s[1]);
				addhomework_minute.setText(s[2]);
			}
		});
		dialog.show();
	}

	/**
	 * 将长时间格式字符串转换为时间 yyyy-MM-dd HH:mm:ss
	 * 
	 */
	public static String getStringDate(Long date) {
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd:HH:mm:ss");
		String dateString = formatter.format(date);
		return dateString;
	}

	// 选择添加图片方法
	public void AddImg() {
		View view = getLayoutInflater().inflate(
				R.layout.activity_homework_photo_choose_dialog, null);
		Button button1 = (Button) view
				.findViewById(R.id.homework_choose_localphoto);
		Button button2 = (Button) view
				.findViewById(R.id.homework_choose_takephoto);
		Button button3 = (Button) view
				.findViewById(R.id.homework_choose_canclephoto);
		final Dialog dialog = new Dialog(mActivity,
				R.style.transparentFrameWindowStyle);
		dialog.setContentView(view, new LayoutParams(LayoutParams.FILL_PARENT,
				LayoutParams.WRAP_CONTENT));
		Window window = dialog.getWindow();
		// 设置显示动画
		window.setWindowAnimations(R.style.main_menu_animstyle);
		WindowManager.LayoutParams wl = window.getAttributes();
		wl.x = 0;
		wl.y = getWindowManager().getDefaultDisplay().getHeight();
		// 以下这两句是为了保证按钮可以水平满屏
		wl.width = ViewGroup.LayoutParams.MATCH_PARENT;
		wl.height = ViewGroup.LayoutParams.WRAP_CONTENT;

		// 设置显示位置
		dialog.onWindowAttributesChanged(wl);
		// 设置点击外围解散
		dialog.setCanceledOnTouchOutside(true);
		dialog.show();
		button1.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				openAlbum();
				dialog.dismiss();
			}
		});
		button2.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				takePicture();
				dialog.dismiss();
			}
		});
		button3.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.dismiss();
			}
		});
	}

	// 拍照
	public void takePicture() {
		String state = Environment.getExternalStorageState();
		if (state.equals(Environment.MEDIA_MOUNTED)) {
			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
			File outDir = Environment
					.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES);
			if (!outDir.exists()) {
				outDir.mkdirs();
			}
			File outFile = new File(outDir, System.currentTimeMillis() + ".jpg");
			realPath = outFile.getAbsolutePath();
			intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outFile));
			intent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
			startActivityForResult(intent, CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE);
		} else {
			Log.d(TAG, "请确认已经插入SD卡");
		}
	}

	// 打开本地相册
	public void openAlbum() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		this.startActivityForResult(intent, PICK_IMAGE_ACTIVITY_REQUEST_CODE);
	}

	// 与拍照有关的
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CAPTURE_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				Log.e(TAG, "获取图片成功，path=" + realPath);
				toast("获取图片成功，path=" + realPath);
				setImageView(realPath);
			} else if (resultCode == RESULT_CANCELED) {
				// 用户取消了图像捕获
			} else {
				// 图像捕获失败，提示用户
				Log.e(TAG, "拍照失败");
			}
		} else if (requestCode == PICK_IMAGE_ACTIVITY_REQUEST_CODE) {
			if (resultCode == RESULT_OK) {
				uri = data.getData();
				if (uri != null) {
					realPath = getRealPathFromURI(uri);
					Log.e(TAG, "获取图片成功，path=" + realPath);
					toast("获取图片成功，path=" + realPath);
					setImageView(realPath);
				} else {
					Log.e(TAG, "从相册获取图片失败");
				}
			}
		}
	}

	// 与拍照有关的
	private void setImageView(String realPath) {
		bmp = BitmapFactory.decodeFile(realPath);
		int degree = readPictureDegree(realPath);
		if (degree <= 0) {
			imageView.setImageBitmap(bmp);
		} else {
			Log.e(TAG, "rotate:" + degree);
			// 创建操作图片是用的matrix对象
			Matrix matrix = new Matrix();
			// 旋转图片动作
			matrix.postRotate(degree);
			// 创建新图片
			resizedBitmap = Bitmap.createBitmap(bmp, 0, 0, bmp.getWidth(),
					bmp.getHeight(), matrix, true);
			imageView.setImageBitmap(resizedBitmap);
		}
	}

	/**
	 * This method is used to get real path of file from from uri<br/>
	 * http://stackoverflow.com/questions/11591825/how-to-get-image-path-just-
	 * captured-from-camera //与拍照有关的
	 * 
	 * @param contentUri
	 * @return String
	 */
	public String getRealPathFromURI(Uri contentUri) {
		try {
			String[] proj = { MediaStore.Images.Media.DATA };
			// Do not call Cursor.close() on a cursor obtained using this
			// method,
			// because the activity will do that for you at the appropriate time
			Cursor cursor = this.managedQuery(contentUri, proj, null, null,
					null);
			int column_index = cursor
					.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
			cursor.moveToFirst();
			return cursor.getString(column_index);
		} catch (Exception e) {
			return contentUri.getPath();
		}
	}

	/**
	 * 读取照片exif信息中的旋转角度<br/>
	 * http://www.eoeandroid.com/thread-196978-1-1.html //与拍照有关的
	 * 
	 * @param path
	 *            照片路径
	 * @return角度
	 */
	public static int readPictureDegree(String path) {
		int degree = 0;
		try {
			ExifInterface exifInterface = new ExifInterface(path);
			int orientation = exifInterface.getAttributeInt(
					ExifInterface.TAG_ORIENTATION,
					ExifInterface.ORIENTATION_NORMAL);
			switch (orientation) {
			case ExifInterface.ORIENTATION_ROTATE_90:
				degree = 90;
				break;
			case ExifInterface.ORIENTATION_ROTATE_180:
				degree = 180;
				break;
			case ExifInterface.ORIENTATION_ROTATE_270:
				degree = 270;
				break;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return degree;
	}

	// 与拍照有关的
	public void toast(String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}


	private Response.Listener<JSONObject> successListener() {

		return new Response.Listener<JSONObject>() {
			public void onResponse(JSONObject response) {

				System.out.println("成功");
				if (response != null) {
					L.d(TAG, response.toString());
					Toast.makeText(mActivity, response.toString(),
							Toast.LENGTH_SHORT).show();
					System.out.println("请求是：" + response);
				}
				System.out.println("请求是： null");
			}
		};
	}

	// 本地url 图片转换成 bitmap
	public static Bitmap GetLocalOrNetBitmap(String url) {
		Bitmap bitmap = null;
		File file = new File(url);
		if (file.exists()) {
			bitmap = BitmapFactory.decodeFile(url);
		}
		return bitmap;
	}


	private void gethomeworkdata() {
		HashMap<Integer, Boolean> state =AddhomeworkAdapter.state;
		TeachingClassIDdata=new String[listItemAdapter.getCount()];
		for(int j=0;j<listItemAdapter.getCount();j++)
		{
			
			if(state.get(j)!=null)
			{
				@SuppressWarnings("unchecked")
				HashMap<String, Object> map=(HashMap<String, Object>) listItemAdapter.getItem(j);
				TeachingClassIDdata[j]=map.get("TeachingClassID").toString();

				
			}
			else
			{
				
			}
		}
		System.out.println(TeachingClassIDdata.toString());
		for(int i=0;i<TeachingClassIDdata.length;i++)
		{
			if(TeachingClassIDdata[i]!=null)
			{
				TeachingClassIDListString+=TeachingClassIDdata[i]+"|";
			}
			else 
			{
				
			}
		}
		
		if(TeachingClassIDListString.equals(""))
		{
			
		}
		else
		{
			System.out.println("最后拼接出来的ID"+TeachingClassIDListString);
			TeachingClassIDListString=TeachingClassIDListString.substring(0, TeachingClassIDListString.length()-1);
			
			System.out.println("最后拼接出来的ID"+TeachingClassIDListString);
		}
		TeacherName = sp.getString(Constant.SPFIELD.REALNAME, "");
		WorkName = addhomework_homeworkTitle.getText().toString();
		WorkDesc = addhomework_homeworkContent.getText().toString();
		Memo = addhomework_note.getText().toString();
		// WorkScore=addhomework_score.getText().toString();
		//TODO:
		DeadTime = addhomework_last_date.getText().toString() + " "
				+ addhomework_hour.getText().toString() + ":"
				+ addhomework_minute.getText().toString();
//				+ ":00.000";
		DeadTime2 = addhomework_last_date.getText().toString() + "%20"
				+ addhomework_hour.getText().toString() + ":"
				+ addhomework_minute.getText().toString();
//				+ ":00.000";
		System.out.println("这个拼接的截止时间：" + DeadTime);
		System.out.println("这个拼接的截止时间：" + DeadTime2);
		if(DeadTime.equals("截止日期 :"))
		{
			DeadTime="";
		}
		else 
		{
			
		}
		if(DeadTime2.equals("截止日期%20:"))
		{
			DeadTime2="";
		}
		else
		{
			
		}
		
		if (addhomework_studentVisible.getText().toString().equals("学生可见")) {
			IsStuSee = "yes";
		} else if (addhomework_studentVisible.getText().toString()
				.equals("学生不可见")) {
			IsStuSee = "no";
			
		}
		if(WorkName.equals("")||DeadTime.equals("")||TeachingClassIDListString.equals(""))
		{
			getalldata="false";
			
		}
		else
		{
			getalldata="true";
		}
		// 测试数据
//		TeachingClassIDListString = "9b61baf866d044819887dd2c65f67334";
		System.out.println(TeacherName + " " + WorkName + " " + WorkDesc + " "
				+ Memo + " " + DeadTime + " " + IsStuSee + " "
				+ TeachingClassIDListString);
	}

	// http post
	private void HttpPostData() {
		int code;
		System.out.println("开始httppost");
		try {

			if(realPath==null)
			{
			HttpParams params = new BasicHttpParams();
			HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
			HttpProtocolParams.setContentCharset(params, "UTF-8");
			HttpConnectionParams.setConnectionTimeout(params, 5000);  
            HttpConnectionParams.setSoTimeout(params, 35000);  
			HttpClient httpclient = new DefaultHttpClient();
			HttpPost httppost = new HttpPost(urlPath);
			System.out.println("urlPath" + urlPath);
			// 添加http头信息
			httppost.addHeader("Content-Type", "application/octet-stream");
			JSONObject obj = new JSONObject();
			obj.put("TeacherName", TeacherName);
			obj.put("TeachingClassIDListString", TeachingClassIDListString);
			obj.put("WorkName", WorkName);
			obj.put("WorkDesc", WorkDesc);
			obj.put("DeadTime", DeadTime);
			obj.put("IsStuSee", IsStuSee);
			obj.put("Memo", Memo);
			//
			httppost.setEntity(new StringEntity(obj.toString(), "UTF-8"));

			System.out.println("obj" + obj.toString());

			HttpResponse response;
			response = httpclient.execute(httppost);
			System.out.println("请求" + response.toString());
			code = response.getStatusLine().getStatusCode();
			System.out.println("这个code是  " + code);
			// 检验状态码，如果成功接收数据
			if (code == 200) {

				dialog.dismiss();
				String rev = EntityUtils
						.toString(response.getEntity(), "UTF-8");// 返回json格式：
				JSONObject result = new JSONObject(rev);
				if (result.get("Success").equals("true")) {
					mHandler.sendEmptyMessage(MSG_POST_HOME_WORKE);
				}
				L.e("TAG", rev);
				judgehomework="true";
				finish();
				// String id = obj.getString("id");
				// String version = obj.getString("version");
			}
			else if(code != 200)
			{

				dialog.dismiss();
				Toast.makeText(mActivity, "添加作业失败",
						Toast.LENGTH_SHORT).show();
			}
			}
			
			else if(realPath!=null)
			{
				

				final File file = new File(realPath);
				Bitmap bit = null;
				Bitmap bitmap=null;
				Bitmap smallmapBitmap=null;
				if (file.exists()) {
					bit = BitmapFactory.decodeFile(realPath);
				}
				if(bit!=null ){
					// 下面这两句是对图片按照一定的比例缩放，这样就可以完美地显示出来。
					int scale = ImageThumbnail.reckonThumbnail(bit.getWidth(),bit.getHeight(), 400, 720);  
					
					bitmap = ImageThumbnail.PicZoom(bit, bit.getWidth()/scale,bit.getHeight()/scale); 
					
					smallmapBitmap=compressImage(bitmap);
					
					//由于Bitmap内存占用较大，这里需要回收内存，否则会报out of memory异常  
//					bit.recycle();
					//将处理过的图片显示在界面上 
					
					
				}
				InputStream is = null ;
			     is = Bitmap2InputStream(smallmapBitmap , 100) ;
				
				
			     try {
						TeachingClassIDListString=URLEncoder.encode(TeachingClassIDListString,"utf-8");
					} catch (UnsupportedEncodingException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
			     
				TeacherName=URLEncoder.encode(TeacherName, "UTF-8");
				WorkName=URLEncoder.encode(WorkName, "UTF-8");
				WorkDesc=URLEncoder.encode(WorkDesc, "UTF-8");
				Memo=URLEncoder.encode(Memo, "UTF-8");
				urlPathwithimage = Constant.INTERFACE_SITE + actionwithimage + "?"
					+ "TeacherName=" + TeacherName + "&"
					+ "TeachingClassIDListString=" + TeachingClassIDListString
					+ "&" + "WorkName=" + WorkName + "&" + "WorkDesc="
					+ WorkDesc + "&" + "DeadTime=" + DeadTime2 + "&"
					+ "IsStuSee=" + IsStuSee + "&" + "Memo=" + Memo 
					+ "&ImgStream=" + is ;
				
				
				
				
				
				System.out.println("这个urlPathwithimage是"+urlPathwithimage);
				if (file.exists() && file.length() > 0)
				{
					
					
					
					//base64更改
					String strImg = BitmapToBase64.bitmaptoString(bitmap);
					 List<NameValuePair> params2 = new ArrayList<NameValuePair>();
					params2.add(new BasicNameValuePair("base64pic", strImg));
					final List<NameValuePair> mParams = params2;
					
					
					
					new Thread(new Runnable() {
						@Override
						public void run() {
							String url = Constant.INTERFACE_SITE + actionwithimage + "?"
									+ "TeacherName=" + TeacherName + "&"
									+ "TeachingClassIDListString=" + TeachingClassIDListString
									+ "&" + "WorkName=" + WorkName + "&" + "WorkDesc="
									+ WorkDesc + "&" + "DeadTime=" + DeadTime2 + "&"
									+ "IsStuSee=" + IsStuSee + "&" + "Memo=" + Memo ;
							HttpPost post = new HttpPost(url);
							HttpParams params1 = new BasicHttpParams();
							HttpConnectionParams.setConnectionTimeout(params1, 1000*10);
							HttpConnectionParams.setConnectionTimeout(params1, 1000*10);
							HttpProtocolParams.setVersion(params1, HttpVersion.HTTP_1_1);
							HttpClient client1 = new DefaultHttpClient(params1);
							
							Options options = BitmapHelper.getBitmapOptions(realPath);
							int size = options.outHeight * options.outWidth;
							File file = null;
								Log.e("size--->", options.outWidth +"----" + options.outHeight);
								/*if (size >= 480 * 800) {
									file = new File(BitmapHelper.compressBitmap(homework_tea_addhomework.this, realPath,
											options.outHeight > options.outWidth ? 480 : 800, options.outHeight > options.outWidth ? 800 : 480, true));
								} else if (size > 320 * 480 && size < 480 * 800) {
									file = new File(BitmapHelper.compressBitmap(homework_tea_addhomework.this, realPath,
											options.outHeight > options.outWidth ? 320 : 480, options.outHeight > options.outWidth ? 480 : 320, true));
								}*/  
								file = new File(BitmapHelper.compressBitmap(homework_tea_addhomework.this, realPath,
										320,480, true));
							try {
								
								FileEntity entity = new FileEntity(file, "binary/octet-stream");
								entity.setContentEncoding("binary/octet-stream");
								
								
								//base64编码更改
//								post.setEntity(new UrlEncodedFormEntity(mParams));
								post.setEntity(entity);
								
								
								
								//base64更改
								HttpResponse response = client1.execute(post);
//								HttpResponse response = getclient().execute(post);
								
								
								 oode1 = response.getStatusLine().getStatusCode();
								Log.e("RESP",oode1+"");
								if(oode1==200)
								{
									dialog.dismiss();
									Looper.prepare();
									Toast.makeText(mActivity, "添加作业成功",
											Toast.LENGTH_SHORT).show();
									judgehomework="true";
									finish();
									Looper.loop();
									
								}
								else if(oode1!=200)
								{
									if(file.length()>(80000))
									{
										dialog.dismiss();
										Looper.prepare();
										
										Toast.makeText(mActivity, "上传图片过大",
												Toast.LENGTH_LONG).show();
										Looper.loop();
									}
									else {
									dialog.dismiss();
									Looper.prepare();
									
									Toast.makeText(mActivity, "添加作业失败",
											Toast.LENGTH_LONG).show();
									Looper.loop();
									}
								}
								
								}
							catch(Exception ex){
								ex.printStackTrace();
							}
							
						}
					}).start();
					
					
					
					
				}
			}
			
		} catch (Exception ex) {
			
			ex.printStackTrace();

		}
	}

	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		T.showShort(mActivity, "选择了");
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {

	}

	@Override
	public void onCheckedChanged(RadioGroup arg0, int arg1) {
		T.showShort(mActivity, "选择了");
	}
	private Bitmap compressImage(Bitmap image) {  
		  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 85, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
        int options = 100;  
        while ( baos.toByteArray().length / 1024>50) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
            baos.reset();//重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
            options -= 10;//每次都减少10  
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
        return bitmap;  
    } 

	 // 将Bitmap转换成InputStream  
    public InputStream Bitmap2InputStream(Bitmap bm, int quality) {  
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        bm.compress(Bitmap.CompressFormat.PNG, quality, baos);  
        InputStream is = new ByteArrayInputStream(baos.toByteArray());  
        return is;  
    }
    public static HttpClient getclient() {// 单例模式

		if (client == null) {
			BasicHttpParams httpParams = new BasicHttpParams();
			ConnManagerParams.setTimeout(httpParams,10*1000);
			HttpConnectionParams.setConnectionTimeout(httpParams,
					Constant.REQUEST_TIMEOUT); // 请求超时
			HttpConnectionParams.setSoTimeout(httpParams, Constant.SO_TIMEOUT); // 连接超时
			SchemeRegistry schReg =new SchemeRegistry();
			 // 设置我们的HttpClient支持HTTP和HTTPS两种模式
			schReg.register(new Scheme("http", PlainSocketFactory
                    .getSocketFactory(), 80));
            schReg.register(new Scheme("https", SSLSocketFactory
                    .getSocketFactory(), 443));
            // 使用线程安全的连接管理来创建HttpClient
			ClientConnectionManager conMgr =new ThreadSafeClientConnManager(httpParams, schReg);
			client = new DefaultHttpClient(conMgr,httpParams);
			client.getParams().setParameter("http.socket.timeout", new Integer(30000));
		}
		else 
		{
			System.out.println("测试");
		}
		return client;
	}
    @Override
    protected void onRestart() {
    	// TODO 自动生成的方法存根
    	super.onRestart();
    	finish();
    }
    
}






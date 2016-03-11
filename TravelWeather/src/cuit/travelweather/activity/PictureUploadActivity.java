package cuit.travelweather.activity;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cuit.travelweather.R;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.protocol.HTTP;
import org.apache.http.util.EntityUtils;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.net.ParseException;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Base64;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.ImageView.ScaleType;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import cuit.travelweather.util.BitmapToBase64;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.MyHttpClient;
import cuit.travelweather.util.UploadUtil;
import cuit.travelweather.util.UploadUtil.OnUploadProcessListener;

public class PictureUploadActivity extends BaseAct {
	private static final String TAG = "PictureUploadActivity";
	Context context = PictureUploadActivity.this;

	ImageButton ib_comment_back;
	ImageView iv_picture_upload;
	EditText et_picture_upload_comment;
	Button btn_picture_upload_addComment;
	TextView uploadImageResult;
	ProgressBar progressBar;
	ProgressDialog progressDialog;
	Spinner spinnerPicType;

	String picType = "FOOD";

	private static final int SHOW_PROCESS = 1;
	private static final int DISMISS_PROCESS = 0;
	private static ProgressDialog pdialog;
	private Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
				case SHOW_PROCESS:
					pdialog.show();
					break;
				case DISMISS_PROCESS:
					pdialog.dismiss();

					try {
						if (jo.getInt("status") == 1) {
							Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT).show();
							finish();
						} else {
							Toast.makeText(context, "上传失败", Toast.LENGTH_SHORT).show();
						}
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}

					break;
			}
		}

	};

	private void initViews() {
		pdialog = new ProgressDialog(context);
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("正在上传评论……");

		ib_comment_back = (ImageButton) this.findViewById(R.id.ib_comment_back);
		iv_picture_upload = (ImageView) this.findViewById(R.id.iv_picture_upload);
		et_picture_upload_comment = (EditText) this.findViewById(R.id.et_picture_upload_comment);
		btn_picture_upload_addComment = (Button) this.findViewById(R.id.btn_picture_upload_addComment);

		uploadImageResult = (TextView) findViewById(R.id.uploadImageResult);
		progressBar = (ProgressBar) findViewById(R.id.progressBar1);

		progressDialog = new ProgressDialog(this);

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
		String[] m = {
				"FOOD",
				"HOTEL",
				"routeline",
				"SHOP"
		};

		@Override
		public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2, long arg3) {

			picType = m[arg2];

		}

		@Override
		public void onNothingSelected(AdapterView<?> arg0) {
			picType = m[0];

		}

	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_picture_upload);

		initViews();

		ib_comment_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent();

				mIntent.putExtra("comment", "");
				PictureUploadActivity.this.setResult(RESULT_CANCELED, mIntent);
				PictureUploadActivity.this.finish();
			}
		});

		String picPath = this.getIntent().getStringExtra(MainActivity.KEY_PHOTO_PATH);//在MAIN中就选择确定了图片的Url 
		Log.i(TAG, "最终选择的图片=" + picPath);
		/*************** set imageView ****************/
		iv_picture_upload.setScaleType(ScaleType.CENTER_CROP);
		iv_picture_upload.setBackgroundResource(R.drawable.image_border);
		RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) iv_picture_upload
				.getLayoutParams();
		Display display = getWindowManager().getDefaultDisplay();
		layoutParams.width = display.getWidth();
		layoutParams.height = (int) (display.getHeight() * 0.618);
		iv_picture_upload.setLayoutParams(layoutParams);

		final Bitmap bm = decodeFile(picPath, layoutParams.width);
		iv_picture_upload.setImageBitmap(bm);
		System.out.println("高：" + bm.getHeight() + "   宽：" + bm.getWidth());
		/*************** set imageView ****************/

		uploadImageResult.setText("点击确定开始上传图片。");

		btn_picture_upload_addComment.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// // 根据当前时间生成文件名
				// 读取设置文件
				SharedPreferences sp = context.getSharedPreferences("User_SP", context.MODE_PRIVATE);
				// 把图片转换为base64编码
				// String strImg = Bitmap2StrByBase64(bm);
				String strImg = BitmapToBase64.bitmaptoString(bm);
				System.out.println("strImg>>>  " + strImg);

				// 获取位置信息
				LocationListener locationListener = new MyLocationListener();
				LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
				Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				double latitude = 0;
				double longitude = 0;
				double altitude = 0;
				if (location != null) {
					latitude = location.getLatitude();     // 经度
					longitude = location.getLongitude(); // 纬度
					altitude = location.getAltitude();     // 海拔
				}
				Log.v("tag", "latitude " + latitude + "  longitude:" + longitude + " altitude:" + altitude);
				String lat = String.valueOf(latitude);
				String lon = String.valueOf(longitude);
				// 获取位置信息

				// 设置访问服务器的参数
				List<NameValuePair> params = new ArrayList<NameValuePair>();

				try {
					params.add(new BasicNameValuePair("name", URLEncoder.encode(sp.getString("userName", ""),
							"UTF-8")));
					params.add(new BasicNameValuePair("pictureType", URLEncoder.encode(picType, "UTF-8")));
					params.add(new BasicNameValuePair("pictureTypeID", URLEncoder.encode("", "UTF-8")));

					params.add(new BasicNameValuePair("lat", URLEncoder.encode(lat, "UTF-8")));
					params.add(new BasicNameValuePair("lon", URLEncoder.encode(lon, "UTF-8")));
					params.add(new BasicNameValuePair("pictureLocation", URLEncoder.encode(lat + "," + lon,
							"UTF-8")));
					params.add(new BasicNameValuePair("pictureDescribe", URLEncoder
							.encode(et_picture_upload_comment.getText().toString(), "UTF-8")));
					params.add(new BasicNameValuePair("keyWords", URLEncoder.encode("", "UTF-8")));
					params.add(new BasicNameValuePair("flag", URLEncoder.encode("", "UTF-8")));
					params.add(new BasicNameValuePair("picture", URLEncoder.encode(strImg, "UTF-8")));

				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				final List<NameValuePair> mParams = params;

				Runnable runnable = new Runnable() {
					
					@Override
					public void run() {
						// uploadImageResult.setText("稍等，正在上传图片。");
						handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
						jo = MyHttpClient.getJson(context, Constant.addPicture, mParams);/////////////////////////////////////////////////
						// uploadImageResult.setText("上传图片完成。");
						handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));

					}
				};
				new Thread(runnable).start();

			}
		});
	}

	JSONObject jo;

	public class MyLocationListener implements LocationListener {

		@Override
		public void onLocationChanged(Location location) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderDisabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onProviderEnabled(String provider) {
			// TODO Auto-generated method stub

		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			// TODO Auto-generated method stub

		}

	}

	/****************************** base64 upload image *********************************/
	/**
	 * 通过Base32将Bitmap转换成Base64字符串
	 * 
	 * @param bit
	 * @return
	 */
	public String Bitmap2StrByBase64(Bitmap bit) {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		bit.compress(CompressFormat.JPEG, 40, bos);// 参数100表示不压缩
		byte[] bytes = bos.toByteArray();
		return Base64.encodeToString(bytes, Base64.DEFAULT);
	}

	/**
	 * 图片转换base64
	 */
	public String bitmaptoString(Bitmap bitmap) {
		// 将Bitmap转换成字符串
		String string = null;
		ByteArrayOutputStream baos = null;
		try {
			if (bitmap != null) {
				baos = new ByteArrayOutputStream();
				bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
				baos.flush();
				baos.close();
				byte[] bitmapBytes = baos.toByteArray();
				string = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
			}
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (baos != null) {
					baos.flush();
					baos.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return string;
	}

	/************************************************************************/
	// TODO
	public static String host = Constant.baseURL + Constant.uploadPic;

	public static String getContent(String url) throws Exception {

		StringBuilder sb = new StringBuilder();

		HttpClient client = new DefaultHttpClient();
		HttpParams httpParams = client.getParams();
		// 设置网络超时参数
		HttpConnectionParams.setConnectionTimeout(httpParams, 3000);

		HttpConnectionParams.setSoTimeout(httpParams, 5000);
		HttpResponse response = client.execute(new HttpGet(url));
		HttpEntity entity = response.getEntity();
		if (entity != null) {
			BufferedReader reader = new BufferedReader(new InputStreamReader(entity.getContent(), "UTF-8"),
					8192);

			String line = null;
			while ((line = reader.readLine()) != null) {
				sb.append(line + "\n");
			}
			reader.close();

		}

		return sb.toString();
	}

	public static HttpResponse post(Map<String, Object> params, String url) {

		HttpClient client = new DefaultHttpClient();
		HttpPost httpPost = new HttpPost(url);
		httpPost.addHeader("charset", HTTP.UTF_8);
		httpPost.setHeader("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
		HttpResponse response = null;
		if (params != null && params.size() > 0) {
			List<NameValuePair> nameValuepairs = new ArrayList<NameValuePair>();
			for (String key : params.keySet()) {
				nameValuepairs.add(new BasicNameValuePair(key, (String) params.get(key)));
			}
			try {
				httpPost.setEntity(new UrlEncodedFormEntity(nameValuepairs, HTTP.UTF_8));
				response = client.execute(httpPost);
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (RuntimeException e) {
				e.printStackTrace();
			}
		} else {
			try {
				response = client.execute(httpPost);
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return response;

	}

	public static Object getValues(Map<String, Object> params, String url) {
		String token = "";
		HttpResponse response = post(params, url);
		if (response != null) {
			try {
				token = EntityUtils.toString(response.getEntity());
				response.removeHeaders("operator");
			} catch (ParseException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return token;
	}

	public static Object setImgByStr(String imgStr, String imgName) {
		String url = host + "channel-uploadImage.action";
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("imgStr", imgStr);
		params.put("imgName", imgName);
		return getValues(params, url);
	}

	/*************************************************************************/
	/****************************** base64 upload image *********************************/

	public Bitmap decodeFile(String path, int REQUIRED_SIZE) {
		try {
			// Decode image size
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = true;
			BitmapFactory.decodeFile(path, options);
			// The new size we want to scale to

			// Find the correct scale value. It should be the power of 2.
			int scale = 1;
			while (options.outWidth / scale / 2 >= REQUIRED_SIZE
					&& options.outHeight / scale / 2 >= REQUIRED_SIZE)
				scale *= 2;

			// Decode with inSampleSize
			BitmapFactory.Options o2 = new BitmapFactory.Options();
			o2.inSampleSize = scale;
			return BitmapFactory.decodeFile(path, o2);
		} catch (Throwable e) {
			e.printStackTrace();
		}
		return null;

	}

}

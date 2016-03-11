package cuit.travelweather.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
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
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import cuit.travelweather.R;
import cuit.travelweather.activity.PictureUploadActivity.MyLocationListener;
import cuit.travelweather.activity.PictureUploadActivity.SpinnerSelectedListener;
import cuit.travelweather.util.BitmapToBase64;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.MyHttpClient;

public class LinePicActivity extends BaseAct{
	
	private static final String TAG = "LinePicActivity";
	Context context = LinePicActivity.this; 
	ImageView iv_picture_upload,ib_comment_back;
	EditText et_picture_upload_comment;
	Button btn_picture_upload_addComment;
	TextView uploadImageResult;
	ProgressBar progressBar;
	ProgressDialog progressDialog;
	Spinner spinnerPicType;

	String picType = "FOOD";
	protected JSONObject jo;

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
							Toast.makeText(context, "�ϴ��ɹ�", Toast.LENGTH_SHORT).show();
							finish();
						} else {
							Toast.makeText(context, "�ϴ�ʧ��", Toast.LENGTH_SHORT).show();
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
		pdialog.setMessage("�����ϴ����ۡ���");

		ib_comment_back = (ImageButton) this.findViewById(R.id.ib_comment_back11);
		iv_picture_upload = (ImageView) this.findViewById(R.id.iv_picture_upload1);
		et_picture_upload_comment = (EditText) this.findViewById(R.id.et_picture_upload_comment1);
		btn_picture_upload_addComment = (Button) this.findViewById(R.id.btn_picture_upload_addComment1);
	}

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_line_pic);
		initViews();
		
		ib_comment_back.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				Intent mIntent = new Intent();
				mIntent.putExtra("comment", "");
				LinePicActivity.this.setResult(RESULT_CANCELED, mIntent);
				LinePicActivity.this.finish();
			}
		});
	String picPath = this.getIntent().getStringExtra(MainActivity.KEY_PHOTO_PATH);//��MAIN�о�ѡ��ȷ����ͼƬ��Url 
	Log.i(TAG, "����ѡ���ͼƬ=" + picPath);
//	/*************** set imageView ****************/
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
	
	//mjwnqnduqwuw0
	
	btn_picture_upload_addComment.setOnClickListener(new OnClickListener() {

		@Override
		public void onClick(View v) {
			// // ���ݵ�ǰʱ�������ļ���
			// ��ȡ�����ļ�
			SharedPreferences sp = context.getSharedPreferences("User_SP", context.MODE_PRIVATE);
			// ��ͼƬת��Ϊbase64����
			// String strImg = Bitmap2StrByBase64(bm);
			String strImg = BitmapToBase64.bitmaptoString(bm);
			System.out.println("strImg>>>  " + strImg);

			// ��ȡλ����Ϣ
			LocationListener locationListener = new MyLocationListener();
			LocationManager locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
			locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
			Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
			double latitude = 0;
			double longitude = 0;
			double altitude = 0;
			if (location != null) {
				latitude = location.getLatitude();     // ����
				longitude = location.getLongitude(); // γ��
				altitude = location.getAltitude();     // ����
			}
			Log.v("tag", "latitude " + latitude + "  longitude:" + longitude + " altitude:" + altitude);
			String lat = String.valueOf(latitude);
			String lon = String.valueOf(longitude);
			// ��ȡλ����Ϣ

			// ���÷��ʷ������Ĳ���
			List<NameValuePair> params = new ArrayList<NameValuePair>();

			try {
				params.add(new BasicNameValuePair("name", URLEncoder.encode(sp.getString("userName", ""),
						"UTF-8")));
				params.add(new BasicNameValuePair("pictureType", URLEncoder.encode("routeline", "UTF-8")));//·��
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
					// uploadImageResult.setText("�Եȣ������ϴ�ͼƬ��");
					handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
					jo = MyHttpClient.getJson(context, Constant.addPicture, mParams);/////////////////////////////////////////////////
					// uploadImageResult.setText("�ϴ�ͼƬ��ɡ�");
					handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));
				}
			};
			new Thread(runnable).start();

		}
	});
	}

	
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
	
	
	
	
	
	

}

package cuit.travelweather.activity;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;




//import org.apache.commons.codec.binary.Base64;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import cuit.travelweather.R;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.MyFont;
import cuit.travelweather.util.MyHttpClient;
import cuit.travelweather.util.Split;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.text.format.DateFormat;
import android.util.Base64;
//import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;

public class TravelNewRoadActivity extends BaseAct {

	private Context context = TravelNewRoadActivity.this;
	private double latitude=0.0;  
	private double longitude=0.0;
	private String picString;	
	private TextView position;
	private TextView weather;
	private TextView description;
	private ImageView pic;
	private EditText site_1;
	private TextView site_2;
	
	private String picFileName;
	
	private OnClickListener myListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.travel_et_site:
				Intent intent = new Intent(context, AreaChooseActivity.class);
				intent.putExtra("listType",
						AreaChooseActivity.LIST_TYPE_PROVINCE);
				intent.putExtra("backActivityCode", 999);
				TravelNewRoadActivity.this.startActivityForResult(intent, 999);
				break;
			case R.id.travel_et_weather:
				chooseweather();
				break;
			case R.id.travel_et_description:
				chooseDescription();
				break;
			case R.id.travel_et_site_2:
				choosePosition();
				break;
			}
		}
	};

	protected JSONObject jsonStr;
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
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.travel_road_new);
		MyFont.setTypeface(this, new int[] { R.id.travel_tv_new_road,
				R.id.travel_tv_site,R.id.travel_tv_site_1,R.id.travel_tv_site_2, R.id.travel_tv_description,
				R.id.travel_tv_weather, R.id.travel_tv_pic });
		init();
		
	}

	private void init() {
		// TODO Auto-generated method stub
		position = (TextView) findViewById(R.id.travel_et_site);
		weather = (TextView) findViewById(R.id.travel_et_weather);
		description = (TextView) findViewById(R.id.travel_et_description);
		pic = (ImageView) findViewById(R.id.travel_iv_pic);
		pic.setDrawingCacheEnabled(true);
		site_1 = (EditText)findViewById(R.id.travel_et_site_1);
		site_2 = (TextView)findViewById(R.id.travel_et_site_2);
		site_2.setOnClickListener(myListener);
		position.setOnClickListener(myListener);
		weather.setOnClickListener(myListener);
		description.setOnClickListener(myListener);
	}

	public void takephoto(View v) {
		Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(it, 123);
	}
	/**
	 * 上传图片按钮
	 */
	public void add(View v) {
		setData();
	}
	/**
	 * 上传图片
	 */
	private void setData() {
		// TODO Auto-generated method stub
		if (position.getText().toString().trim().equals("")) {
			Toast.makeText(context, "请选择地区信息", Toast.LENGTH_SHORT).show();
			return;
		}
		if (site_1.getText().toString().trim().equals("")) {
			Toast.makeText(context, "请填写街道信息", Toast.LENGTH_SHORT).show();
			return;
		}
		if (weather.getText().toString().trim().equals("")) {
			Toast.makeText(context, "请选择天气情况", Toast.LENGTH_SHORT).show();
			chooseweather();
			return;
		}
		if (description.getText().toString().trim().equals("")) {
			Toast.makeText(context, "请选择描述", Toast.LENGTH_SHORT).show();
			chooseDescription();
			return;
		}
		
		Bitmap bmp = Bitmap.createBitmap(pic.getDrawingCache());
		pic.setDrawingCacheEnabled(false);
		picString = bitmaptoString(bmp);
		pdialog = new ProgressDialog(context);
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("正在上传路况……");
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... param) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
				try {
					params.add(new BasicNameValuePair("name", URLEncoder
							.encode("zhang", "UTF-8")));
					params.add(new BasicNameValuePair("pictureType", URLEncoder
							.encode("routeline", "UTF-8")));
					params.add(new BasicNameValuePair("pictureTypeID", URLEncoder
							.encode("1527", "UTF-8")));
					params.add(new BasicNameValuePair("lat", URLEncoder
							.encode(String.valueOf(latitude), "UTF-8")));
					params.add(new BasicNameValuePair("lon", URLEncoder
							.encode(String.valueOf(longitude), "UTF-8")));
					params.add(new BasicNameValuePair("pictureLocation", URLEncoder
							.encode(position.getText().toString()+site_1.getText().toString(), "UTF-8")));
					params.add(new BasicNameValuePair("pictureDescribe", URLEncoder
							.encode(description.getText().toString().trim(), "UTF-8")));
					params.add(new BasicNameValuePair("keyWords", URLEncoder
							.encode(weather.getText().toString(), "UTF-8")));
					params.add(new BasicNameValuePair("flag", URLEncoder
							.encode(description.getText().toString().trim(), "UTF-8")));
					//params.add(new BasicNameValuePair("picture", URLEncoder.encode(picString, "UTF-8")));
					params.add(new BasicNameValuePair("picture", picString));
				} catch (UnsupportedEncodingException e) {
					System.out.print(e.getMessage());
				}
				jsonStr = MyHttpClient.getJson(context,
						Constant.addPicture, params);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));
				 try {
					if (jsonStr.getInt("status")==1) {
						 Toast.makeText(context, "上传成功", Toast.LENGTH_SHORT ).show();
						 finish();
					 } else {
						 Toast.makeText(context, "上传失败", Toast.LENGTH_SHORT ).show();
					 }
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.execute();
	}
	/**
	 * gps定位
	 */
	public void choosePosition() {
		LocationManager locationManager = null;
		LocationListener iListener;
		String provider;
		locationManager = (LocationManager) TravelNewRoadActivity.this.getSystemService(Context.LOCATION_SERVICE);
		
		Criteria criteria = new Criteria();//Criteria，可根据当前设备情况自动选择哪种location provider
		criteria.setAccuracy(Criteria.ACCURACY_FINE);//设置为最大精度
		//criteria.setAccuracy(Criteria.ACCURACY_COARSE); //获取大体的位置
		criteria.setAltitudeRequired(false);//不要求海拔信息
		criteria.setBearingRequired(false);//不要求方位信息 
		criteria.setCostAllowed(true);//是否允许付费 = = ！ 这个没搞懂为啥子要付费
		criteria.setPowerRequirement(Criteria.POWER_LOW);//对电量的要求
		provider = locationManager.getBestProvider(criteria, true);//获取一个最符合查询条件的位置提供者
		iListener = new LocationListener() {
			
			@Override
			public void onStatusChanged(String provider, int status, Bundle extras) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderEnabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onProviderDisabled(String provider) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onLocationChanged(Location location) {
				// TODO Auto-generated method stub
				if (location != null) {     
					latitude = location.getLatitude();  
	                longitude = location.getLongitude();
					Log.e("Map", "Location changed : Lat: "    
                    + location.getLatitude() + " Lng: "    
                    + location.getLongitude());    
					site_2.setText("经度："+latitude+"\t纬度："+longitude);
                }  
			}
		};
		if (provider!=null) {
			locationManager.requestLocationUpdates(provider, 1000, 0, iListener);
			Toast.makeText(TravelNewRoadActivity.this, "正在定位",Toast.LENGTH_SHORT).show();
		} else {
			Toast.makeText(TravelNewRoadActivity.this, "请打开访问我的位置信息",Toast.LENGTH_SHORT).show();
			//locationManager.requestLocationUpdates(LocationManager.NETWORK_PROVIDER, 1000, 0, iListener);
		}
		
		
	}
	/**
	 * 选择描述
	 */
    public void chooseDescription() {
		final String[] des = new String[] { "车祸", "泥石流", "塌方", "大雾", "其他" };
		new AlertDialog.Builder(this).setTitle("选择描述")
				.setIcon(getResources().getDrawable(R.drawable.ic_launcher))
				.setItems(des, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						description.setText(des[which]);
					}
				}).show();
	}
    /**
	 * 选择天气
	 */
	public void chooseweather() {
		final String[] des = new String[] { "小雨", "中雨", "大雨", "暴雨", "阵雨","阴","晴","多云","雾霾","大雾","其他" };
		new AlertDialog.Builder(this).setTitle("选择天气情况")
				.setIcon(getResources().getDrawable(R.drawable.ic_launcher))
				.setItems(des, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						// TODO Auto-generated method stub
						weather.setText(des[which]);
					}
				}).show();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if (resultCode == Activity.RESULT_OK) {  
            String sdStatus = Environment.getExternalStorageState();  
            if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用  
                Log.i("TestFile",  "SD card is not avaiable/writeable right now.");  
                Toast.makeText(context, "请插入内存卡", Toast.LENGTH_LONG).show();
                return;  
            }  
            String name = new DateFormat().format("yyyyMMdd_hhmmss",Calendar.getInstance(Locale.CHINA)) + ".jpg";     
            Toast.makeText(this, name, Toast.LENGTH_LONG).show();  
            Bundle bundle = data.getExtras();  
            Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式  
          
            FileOutputStream b = null;  
           //???????????????????????????????为什么不能直接保存在系统相册位置呢？？？？？？？？？？？？  
            File file = new File("/sdcard/myImage/");  
            file.mkdirs();// 创建文件夹  
            String fileName = "/sdcard/myImage/"+name;  
            picFileName = fileName;
            try {  
                b = new FileOutputStream(fileName);  
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, b);// 把数据写入文件  
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
            } finally {  
                try {  
                    b.flush();  
                    b.close();  
                } catch (IOException e) {  
                    e.printStackTrace();  
                }  
            }  
            pic.setImageBitmap(bitmap);// 将图片显示在ImageView里 
            
        }  else if(resultCode == 999) {
			if (data.getStringExtra("area") != null) {
				position.setText(Split.cut_r_all(data.getStringExtra("area")));
			}
		} 
    }

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		
		super.onDestroy();
	}  
	// 工具
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
	
	
	public void base64ToFile(String base64Data,String targetPath) throws Exception {
		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		FileOutputStream out = new FileOutputStream(targetPath);
		out.write(bytes);
		out.close();
	}
}

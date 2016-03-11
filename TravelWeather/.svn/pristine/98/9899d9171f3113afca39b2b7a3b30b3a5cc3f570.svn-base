package cuit.travelweather.activity;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import cuit.travelweather.R;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.MyHttpClient;
import cuit.travelweather.util.Split;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class NewScenicActivity extends BaseAct {

	private Context context = NewScenicActivity.this;

	private EditText name;
	private TextView position;
	private TextView level;
	private EditText des;
	private String province;
	private ImageView pic;
	private EditText et_address;

	private OnClickListener myListener = new OnClickListener() {
		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			switch (v.getId()) {
			case R.id.scenic_et_position:
				Intent intent = new Intent(context, AreaChooseActivity.class);
				intent.putExtra("listType",
						AreaChooseActivity.LIST_TYPE_PROVINCE);
				intent.putExtra("backActivityCode", 999);
				NewScenicActivity.this.startActivityForResult(intent, 999);
				break;
			case R.id.scenic_et_level:
				chooselevel();
				break;
			}
		}
	};

	protected JSONObject jsonStr;

	private SharedPreferences sp;
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
		setContentView(R.layout.activity_new_scenic);
		
		TextView tv1 = (TextView)findViewById(R.id.travel_tv_new_road);
		TextView tv2 = (TextView)findViewById(R.id.scenic_tv_name);
		TextView tv3 = (TextView)findViewById(R.id.scenic_tv_position);
		TextView tv4 = (TextView)findViewById(R.id.scenic_tv_level);
		TextView tv5 = (TextView)findViewById(R.id.scenic_tv_des);
		TextView tv6 = (TextView)findViewById(R.id.scenic_tv_pic);
		Button tv7 = (Button)findViewById(R.id.scenic_btn_takephoto);
		Button tv8 = (Button)findViewById(R.id.scenic_btn_add);
		
		sp= getSharedPreferences("test", Activity.MODE_PRIVATE); 
		 String size = sp.getString("affairinfo_fontsize", "2");
		if(size.equals("1")){
			tv1.setTextSize(10);
			tv2.setTextSize(10);
			tv3.setTextSize(10);
			tv4.setTextSize(10);
			tv5.setTextSize(10);
			tv6.setTextSize(10);
			tv7.setTextSize(10);
			tv8.setTextSize(10);
		}else if(size.equals("2")){
		}else{
			tv1.setTextSize(30);
			tv2.setTextSize(30);
			tv3.setTextSize(30);
			tv4.setTextSize(30);
			tv5.setTextSize(30);
			tv6.setTextSize(30);
			tv7.setTextSize(30);
			tv8.setTextSize(30);
		}

		init();
	}

	private void init() {
		// TODO Auto-generated method stub
		name = (EditText) findViewById(R.id.scenic_et_name);
		position = (TextView) findViewById(R.id.scenic_et_position);
		level = (TextView) findViewById(R.id.scenic_et_level);
		des = (EditText) findViewById(R.id.scenic_et_des);
		pic = (ImageView) findViewById(R.id.scenic_iv_pic);
		et_address = (EditText) findViewById(R.id.et_address);
		position.setOnClickListener(myListener);
		level.setOnClickListener(myListener);

	}

	/**
	 * 选择星级
	 */
	public void chooselevel() {
		final String[] level1 = new String[] { "市州级", "国家级", "世界级", "其他" };
		final String[] level2 = new String[] { "A级", "AA级", "AAA级", "AAAA级",
				"AAAAA级" };
		new AlertDialog.Builder(this).setTitle("选择星级")
				.setIcon(getResources().getDrawable(R.drawable.ic_launcher))
				.setItems(level1, new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, final int which) {
						// TODO Auto-generated method stub
						new AlertDialog.Builder(context)
								.setTitle("选择星级")
								.setIcon(
										getResources().getDrawable(
												R.drawable.ic_launcher))
								.setItems(level2,
										new DialogInterface.OnClickListener() {
											@Override
											public void onClick(
													DialogInterface dialog,
													int a) {
												// TODO Auto-generated method
												// stub
												level.setText(level1[which]
														+ "/ " + level2[a]);
											}
										}).show();
					}
				}).show();
	}

	public void takephoto(View v) {
		Intent it = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		startActivityForResult(it, 123);
	}

	public void add(View v) {
		if (name.getText().toString().equals("")) {
			Toast.makeText(context, "请输入景区名称", Toast.LENGTH_SHORT).show();
			return;
		}
		if (position.getText().toString().equals("")) {
			Toast.makeText(context, "请选择景区所在地", Toast.LENGTH_SHORT).show();
			return;
		}
		if (level.getText().toString().equals("")) {
			Toast.makeText(context, "请选择景区等级", Toast.LENGTH_SHORT).show();
			return;
		}
		if (des.getText().toString().equals("")) {
			Toast.makeText(context, "请输入景区描述", Toast.LENGTH_SHORT).show();
			return;
		}

		pdialog = new ProgressDialog(context);
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("正在上传景点……");
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... param) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
				try {
					params.add(new BasicNameValuePair("scenicLevel", URLEncoder
							.encode(level.getText().toString().trim(), "UTF-8")));
					params.add(new BasicNameValuePair("scenicDescribe",
							URLEncoder.encode(des.getText().toString().trim(),
									"UTF-8")));
					params.add(new BasicNameValuePair("lat", URLEncoder.encode(
							"", "UTF-8")));
					params.add(new BasicNameValuePair("lon", URLEncoder.encode(
							"", "UTF-8")));
					params.add(new BasicNameValuePair("scenicLocation",
							URLEncoder.encode(position.getText().toString()
									.trim()
									+ et_address.getText().toString().trim(),
									"UTF-8")));

					params.add(new BasicNameValuePair("scenicName", URLEncoder
							.encode(name.getText().toString().trim(), "UTF-8")));
					params.add(new BasicNameValuePair("province", URLEncoder
							.encode(province, "UTF-8")));
					params.add(new BasicNameValuePair("remark", URLEncoder
							.encode("无", "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					System.out.print(e.getMessage());
				}
				System.out.println("s上传地址    "
						+ position.getText().toString().trim()
						+ et_address.getText().toString().trim());
				jsonStr = MyHttpClient.getJson(NewScenicActivity.this,
						Constant.addScenic, params);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));
				try {
					if (jsonStr.getInt("status") == 1) {
						Toast.makeText(context, "添加成功", Toast.LENGTH_SHORT)
								.show();
						finish();
					} else {
						Toast.makeText(context, "添加失败", Toast.LENGTH_SHORT)
								.show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}.execute();

	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		if (resultCode == 999) {
			if (data.getStringExtra("area") != null) {
				position.setText(Split.cut_r_all(data.getStringExtra("area")));
				province = Split.cut_r1(data.getStringExtra("area"));
			}
		} else if (resultCode == Activity.RESULT_OK) {
			String sdStatus = Environment.getExternalStorageState();
			if (!sdStatus.equals(Environment.MEDIA_MOUNTED)) { // 检测sd是否可用
				Log.i("TestFile",
						"SD card is not avaiable/writeable right now.");
				Toast.makeText(context, "请插入内存卡", Toast.LENGTH_LONG).show();
				return;
			}
			String name = new DateFormat().format("yyyyMMdd_hhmmss",
					Calendar.getInstance(Locale.CHINA))
					+ ".jpg";
			Toast.makeText(this, name, Toast.LENGTH_LONG).show();
			Bundle bundle = data.getExtras();
			Bitmap bitmap = (Bitmap) bundle.get("data");// 获取相机返回的数据，并转换为Bitmap图片格式

			FileOutputStream b = null;
			// ???????????????????????????????为什么不能直接保存在系统相册位置呢？？？？？？？？？？？？
			File file = new File("/sdcard/myImage/");
			file.mkdirs();// 创建文件夹
			String fileName = "/sdcard/myImage/" + name;
			// picFileName = fileName;
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

		}
		super.onActivityResult(requestCode, resultCode, data);
	}

}

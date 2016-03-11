package cuit.travelweather.activity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
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
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cn.trinea.android.common.entity.FailedReason;
import cn.trinea.android.common.service.impl.ImageMemoryCache.OnImageCallbackListener;
import cn.trinea.android.common.service.impl.RemoveTypeLastUsedTimeFirst;
import cuit.travelweather.R;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.ImageCache;
import cuit.travelweather.util.MyHttpClient;

public class PlaceDetailInfo extends BaseAct {
	protected static final int DISSHOW_PROCESS = 1;
	protected static final int SHOW_PROCESS = 0;
	private TextView tv_poidetail_itemtitle;
	private RatingBar rb_poidetail_rate;
	private TextView tv_poidetail_price;
	private TextView tv_poidetail_address;
	private TextView tv_poidetail_reason;
	private TextView tv_poidetail_recommand;
	private TextView tv_phone;
	private String str;
	private String infos;
	private String itemid;
	private String method;
	private String way; 	
	private JSONObject json;
	private String playphone;
	private static ProgressDialog pdialog;

	// 图片缓存相关
	private ImageView ivImageView;
	private String pictureLink;
	private SharedPreferences sp;
	public static final ImageCache IMAGE_CACHE = new ImageCache(128, 512);
	public static final String TAG_CACHE = "image_cache";

	public static final String DEFAULT_CACHE_FOLDER =       new StringBuilder()
			.append(Environment.getExternalStorageDirectory().getAbsolutePath())
			.append(File.separator).append("xiao").append(File.separator)
			.append("AndroidDemo").append(File.separator).append("ImageCache")
			.toString();

	private static Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			super.handleMessage(msg);
			switch (msg.what) {
			case SHOW_PROCESS:
				pdialog.show();
				break;
			case DISSHOW_PROCESS:
				pdialog.dismiss();
				break;
			}
		}

	};

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.place_detail_normal);
		init();
		Context context = getApplicationContext();
		IMAGE_CACHE.initData(this, TAG_CACHE);
		IMAGE_CACHE.setContext(context);
		IMAGE_CACHE.setCacheFolder(DEFAULT_CACHE_FOLDER);

	}

	private void init() {

		tv_poidetail_itemtitle = (TextView) findViewById(R.id.tv_poidetail_itemtitle);
		tv_poidetail_price = (TextView) findViewById(R.id.tv_poidetail_price);
		rb_poidetail_rate = (RatingBar) findViewById(R.id.rb_poidetail_rate);
		tv_poidetail_address = (TextView) findViewById(R.id.poidetail_normal_thereis);
		tv_poidetail_reason = (TextView) findViewById(R.id.poidetail_normal_thereis2);
		tv_poidetail_recommand = (TextView) findViewById(R.id.poidetail_normal_thereis3);
		tv_phone = (TextView) findViewById(R.id.tv_phone);
		ivImageView = (ImageView) findViewById(R.id.iv_place_nomal_image);
		
		 sp= getSharedPreferences("test", Activity.MODE_PRIVATE); 
		 String size = sp.getString("affairinfo_fontsize", "2");
		if(size.equals("1")){
			tv_poidetail_itemtitle.setTextSize(10);
			tv_poidetail_price.setTextSize(10);
			tv_poidetail_address.setTextSize(10);
			tv_poidetail_reason.setTextSize(10);
			tv_poidetail_recommand.setTextSize(10);
			tv_phone.setTextSize(10);
		}else if(size.equals("2")){
//			tv1.setTextSize(20);
		}else{
			tv_poidetail_itemtitle.setTextSize(30);
			tv_poidetail_price.setTextSize(30);
			tv_poidetail_address.setTextSize(30);
			tv_poidetail_reason.setTextSize(30);
			tv_poidetail_recommand.setTextSize(30);
			tv_phone.setTextSize(10);
		}
		
		
		
		Intent intent = getIntent();
		str = intent.getStringExtra("info");
		itemid = intent.getStringExtra("id");
		if (str.equals("foodlist")) {
			method = Constant.getFoodSingle;
			way = "foodID";
			getdata();
		} else if (str.equals("Hotellist")) {
			method = Constant.getHotelSingle;
			way = "hotelID";
			getdata();
		} else if (str.equals("Shoplist")) {
			method = Constant.getShopsSingle;
			way = "shopsID";
			getdata();
		} else {
			Toast.makeText(PlaceDetailInfo.this, "对不起，数据不见了", Toast.LENGTH_LONG)
					.show();
		}
	}

	private void getdata() {
		pdialog = new ProgressDialog(PlaceDetailInfo.this);
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("正在加载数据");
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... params) {
				ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
				List<NameValuePair> param = new ArrayList<NameValuePair>();
				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
				try {
					param.add(new BasicNameValuePair(way, URLEncoder.encode(
							itemid, "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
				json = MyHttpClient
						.getJson(PlaceDetailInfo.this, method, param);
				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				handler.sendMessage(handler.obtainMessage(DISSHOW_PROCESS));
				if (str.equals("foodlist")) {
					try {
						tv_poidetail_itemtitle.setText(json.getString(
								"foodHolderName").toString());
						tv_poidetail_price.setText("   人均："
								+ json.getString("foodMeanPrice").toString());
						rb_poidetail_rate.setNumStars(json.getInt("foodLevel"));
						tv_phone.setText("TEL:"
								+ json.getString("foodPhoneNo").toString());
						tv_poidetail_address.setText("地址： "
								+ json.getString("foodAddress").toString());
						tv_poidetail_recommand.setText("推荐理由： "
								+ json.getString("foodDescribe").toString());
						tv_poidetail_reason.setText("特色推荐："
								+ json.getString("foodRecommend").toString());
						System.out.println("json.getInt(foodLevel>>>>>>>>>>>"
								+ json.getInt("foodLevel"));
						playphone = json.getString("foodPhoneNo").toString();
						pictureLink = Constant.getFoodpic
								+ json.getInt("foodId")
								+ json.getString("foodPictureLinks");
						System.out
								.println("link????????????????" + pictureLink);
						IMAGE_CACHE
								.get("http://t1.baidu.com/it/u=65483540,367132666&fm=15&gp=0.jpg",
										ivImageView);
					} catch (JSONException e) {
						e.printStackTrace();
					}

				} else if (str.equals("Hotellist")) {
					try {
						tv_poidetail_itemtitle.setText(json.getString(
								"hotelName").toString());
						tv_poidetail_price.setText("推荐：" + "赞"
								+ json.getString("hotelGood") + "\t踩"
								+ json.getString("hotelBad"));
						tv_phone.setText("TEL:"
								+ json.getString("hotelPhoneNo").toString());
						tv_poidetail_address.setText("地址： "
								+ json.getString("hotelAddress").toString());
						tv_poidetail_recommand.setText("推荐理由： "
								+ json.getString("hotelDescribe").toString());
						tv_poidetail_reason.setText("特色推荐："
								+ json.getString("hotelRecommend").toString());
						playphone = json.getString("hotelPhoneNo").toString();
						rb_poidetail_rate
								.setNumStars(json.getInt("hotelLevel"));

						IMAGE_CACHE
								.get("http://t1.baidu.com/it/u=65483540,367132666&fm=15&gp=0.jpg",
										ivImageView);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					try {
						tv_poidetail_itemtitle.setText(json.getString(
								"shopName").toString());
						tv_poidetail_price.setText("推荐：" + "赞"
								+ json.getString("shopGood") + "\t踩"
								+ json.getString("shopBad"));
						tv_phone.setText("TEL:"
								+ json.getString("shopPhoneNo").toString());
						tv_poidetail_address.setText("地址： "
								+ json.getString("shopAddress").toString());
						tv_poidetail_recommand.setText("推荐理由： "
								+ json.getString("shopDescribe").toString());
						tv_poidetail_reason.setText("特色推荐："
								+ json.getString("shopRecommend").toString());
						playphone = json.getString("shopPhoneNo").toString();
						rb_poidetail_rate.setNumStars(json.getInt("shopLevel"));
						IMAGE_CACHE
								.get("http://a.hiphotos.baidu.com/image/w%3D2048/sign=72bf01b0eb50352ab1612208677bfaf2/2e2eb9389b504fc29d2ebe02e7dde71190ef6d47.jpg",
										ivImageView);
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}

				super.onPostExecute(result);
			}

		}.execute();

	}

	public void beakonclick(View view) {
			finish();
	}

	public void share(View v) {
		share();
	}

	private void share() {
		OnekeyShare oks = new OnekeyShare();
		// 分享时Notification的图标和文字
		oks.setNotification(R.drawable.ic_launcher,
				getString(R.string.app_name));
		// address是接收人地址，仅在信息和邮件使用
		oks.setAddress("12345678901");
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		Intent intents = new Intent();
		if (str.equals("foodlist")) {
			try {
				oks.setText("这里是  ："
						+ json.getString("foodHolderName").toString()
						+ "\n地址  ：" + json.getString("foodAddress").toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		} else if (str.equals("Hotellist")) {

			try {
				oks.setText("这里是  ：" + json.getString("hotelName").toString()
						+ "\n地址  ：" + json.getString("hotelAddress").toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}

		} else {
			try {
				oks.setText("这里是：" + json.getString("shopName").toString()
						+ "\n地址：" + json.getString("shopAddress").toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}
		// imagePath是图片的本地路径，Linked-In 以外的平台都支持此参数
		// 设置自定义的外部回调
		oks.setCallback(new OneKeyShareCallback());
		oks.show(this, null);
	}

	public void phoneonclick(View view) {
		Uri uri = Uri.parse("tel:" + playphone);
		Intent intent = new Intent(Intent.ACTION_DIAL, uri);
		startActivity(intent);
	}

	public void lineonclick(View view) {

	}
	
	public void addcomment(View v){
		Intent intent = new Intent(PlaceDetailInfo.this, Comment4ShopActivity.class);
		startActivity(intent);
		
	}

	static {
		/** init icon cache **/
		OnImageCallbackListener imageCallBack = new OnImageCallbackListener() {

			/**
			 * callback function after get image successfully, run on ui thread
			 * 
			 * @param imageUrl
			 *            imageUrl
			 * @param loadedImage
			 *            bitmap
			 * @param view
			 *            view need the image
			 * @param isInCache
			 *            whether already in cache or got realtime
			 */
			@Override
			public void onGetSuccess(String imageUrl, Bitmap loadedImage,
					View view, boolean isInCache) {
				if (view != null && loadedImage != null) {
					ImageView imageView = (ImageView) view;
					// Bitmap bitMap = loadedImage;
					// bitMap = modifyBitmapSize(bitMap);
					imageView.setImageBitmap(loadedImage);
					// first time show with animation
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
			public void onGetFailed(String imageUrl, Bitmap loadedImage,
					View view, FailedReason failedReason) {
				Log.e(TAG_CACHE,
						new StringBuilder(128).append("get image ")
								.append(imageUrl)
								.append(" error, failed type is: ")
								.append(failedReason.getFailedType())
								.append(", failed reason is: ")
								.append(failedReason.getCause().getMessage())
								.toString());
			}

			@Override
			public void onGetNotInCache(String imageUrl, View view) {
				if (view != null && view instanceof ImageView) {
					((ImageView) view).setImageResource(R.drawable.ic_food);// 等待载入时的图片
				}
			}
		};
		IMAGE_CACHE.setOnImageCallbackListener(imageCallBack);
		IMAGE_CACHE
				.setCacheFullRemoveType(new RemoveTypeLastUsedTimeFirst<Bitmap>());

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
}

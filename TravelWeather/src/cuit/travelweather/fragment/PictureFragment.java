package cuit.travelweather.fragment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.ImageView.ScaleType;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout.LayoutParams;
import android.widget.Toast;

import cn.trinea.android.common.entity.FailedReason;
import cn.trinea.android.common.service.impl.ImageCache;
import cn.trinea.android.common.service.impl.RemoveTypeLastUsedTimeFirst;
import cn.trinea.android.common.service.impl.ImageMemoryCache.OnImageCallbackListener;

import cuit.travelweather.R;
import cuit.travelweather.activity.DialogPictureActivity;
import cuit.travelweather.activity.MainActivity;
import cuit.travelweather.activity.PictureDetailActivity;
import cuit.travelweather.fragment.PictureFragmentShake.OnShakeListener;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.LazyScrollView;
import cuit.travelweather.util.LazyScrollView.OnScrollListener;

/*******************************************/
/*
 * 请补充两点： 1.吃，住，行，购页面右上角都应该有个“新增”按钮，新增页面先不管。 2.把点击图片显示这个图片的详情做了。详情页面的内容，请参考墨迹天气
 * 的时景点击进去的，就是一个放大的图片，下面图片的描述，对应的地图位置，有多少个踩和赞，最下面是一个评论按钮
 */
/*******************************************/

public class PictureFragment extends Fragment {
	private Context context;
	// 图片网址前缀
	public static  String baseURL = Constant.baseURL;
	// 图片类型常量
	public static final int CONSTANT_PICTURE_BTN_HOT = 0;
	public static final int CONSTANT_PICTURE_BTN_EAT = 1;
	public static final int CONSTANT_PICTURE_BTN_HOTEL = 2;
	public static final int CONSTANT_PICTURE_BTN_ROAD = 3;
	public static final int CONSTANT_PICTURE_BTN_SHOP = 4;
	// 指示当前的图片类型，便于知道滚动的时候选择加载什么图片
	public int currentImgType = -1;
	// 当前在接口查找的页
	private int current_page = 0;
	// 每页加载多少图片
	public static final int IMAGES_NUM_PER_PAGE = 20;
	public static final int COLUMNS = 3;
	/** imageView default height **/
	public static final int IMAGEVIEW_DEFAULT_HEIGHT = 400;
	public static final String TAG_CACHE = "image_cache";
	/** cache folder path which be used when saving images **/
	// TODO 修改！！！
	public static final String DEFAULT_CACHE_FOLDER = new StringBuilder()
			.append(Environment.getExternalStorageDirectory().getAbsolutePath()).append(File.separator)
			.append("TravelWeather").append(File.separator).append("ImageCache").toString();

	// /////////////////////////////////////////////
	private RelativeLayout parentLayout;
	private cuit.travelweather.util.LazyScrollView scrollView;

	private View view;
	private LayoutInflater inflater;
	public RadioGroup radioGroup;
	private LinearLayout progressbar;
	private ImageButton function;
	/** icon cache **/
	public static final ImageCache IMAGE_CACHE = new ImageCache(128, 512);
	public PictureFragmentShake shakeListener;
	OnShakeListener onShake = new OnShakeListener() {       // 调用setOnShakeListener方法进行监听
		public void onShake() {
			// 对手机摇晃后的处理（如换歌曲，换图片……）

			if (parentLayout.getChildCount() != 0) {
				int min = 0, max = parentLayout.getChildCount() - 1;
				Random random = new Random();
				int index = random.nextInt(max) % (max - min + 1) + min;
				ImageView iv = (ImageView) parentLayout.getChildAt(index);
				iv.performClick();
			}
		}
	};
	public void initView() {
		parentLayout = (RelativeLayout) view.findViewById(R.id.image_cache_parent_pictureFragment_layout);
		IMAGE_CACHE.initData(context, TAG_CACHE);
		IMAGE_CACHE.setContext(context);
		IMAGE_CACHE.setCacheFolder(DEFAULT_CACHE_FOLDER);
		function = (ImageButton)view.findViewById(R.id.picture_function);
		function.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				startActivity(new Intent(context,DialogPictureActivity.class));
			}
		});
		shakeListener = new PictureFragmentShake(context);
		shakeListener.registerOnShakeListener(onShake);

		progressbar = (LinearLayout) view.findViewById(R.id.progressbar);
		progressbar.setVisibility(View.GONE);
		// 为每个tab页生成一个数组
		radioGroup = (RadioGroup) view.findViewById(R.id.picture_topbar);
		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				// 切换选项卡
				switch (checkedId) {
					case R.id.picture_btn_hot:
						baseURL = Constant.baseURL;
						initLoad(CONSTANT_PICTURE_BTN_HOT);
						break;
					case R.id.picture_btn_eat:
						baseURL = Constant.baseURL;
						initLoad(CONSTANT_PICTURE_BTN_EAT);
						break;
					case R.id.picture_btn_hotel:
						baseURL = Constant.baseURL;
						initLoad(CONSTANT_PICTURE_BTN_HOTEL);
						break;
					case R.id.picture_btn_road:
						baseURL = Constant.baseURL;
						initLoad(CONSTANT_PICTURE_BTN_ROAD);
						break;
					case R.id.picture_btn_shop:
						baseURL = Constant.baseURL;
						initLoad(CONSTANT_PICTURE_BTN_SHOP);
						break;
				}
			}
		});

		scrollView = (LazyScrollView) view.findViewById(R.id.scrollView);
		scrollView.getView();
		scrollView.setOnScrollListener(new OnScrollListener() {
			// 控制滚动的行为
			@Override
			public void onBottom() {
				if (hasMorePics)
					addImage(current_page, IMAGES_NUM_PER_PAGE, currentImgType);
			}

			@Override
			public void onTop() {
			}

			@Override
			public void onScroll() {
				//
			}

		});

	}

	// 初次打开某个tab以后载入图片
	public void initLoad(int imgType) {
		this.hasMorePics = true;
		this.current_page = 0;
		this.currentImgType = imgType;
		parentLayout.removeAllViews();

		addImage(current_page, IMAGES_NUM_PER_PAGE, currentImgType);

	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		context = inflater.getContext();
		this.inflater = inflater;
		view = this.inflater.inflate(R.layout.main_picture, container, false);
		initView();

		return view;
	}
    
	public static boolean isVisable = false;
	public static boolean isBackFromPictureDetailActivity = false;

	@Override
	public void onResume() {
		
		initView();
		
		
		System.out.println("PictureFragment 继续了");
		System.out.println("isVisable>>>" + isVisable);
		if (isBackFromPictureDetailActivity) {
			isVisable = true;
		}

		if (isVisable) {
			shakeListener.setRecoding(false);
			shakeListener.start();
		}
		//
		if(DialogPictureActivity.flag!=-1){
			//initView();
			initLoad(currentImgType);
			DialogPictureActivity.flag=-1;
		}
		super.onResume();
	}

	@Override
	public void onPause() {
		System.out.println("PictureFragment 暂停了");
		isVisable = false;

		shakeListener.stop();
		super.onPause();
	}

	@Override
	public void onDestroy() {
		IMAGE_CACHE.saveDataToDb(context, TAG_CACHE);
		super.onDestroy();
	}

	boolean hasMorePics = true;
	Handler mHandler = new Handler() {

		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
		}

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {

				case 1:
					Toast.makeText(context, "没有读取到更多图片。", Toast.LENGTH_SHORT).show();
					hasMorePics = false;
					break;

			}

			super.handleMessage(msg);
		}

	};

	public boolean addImage(final int current_page, final int count, final int currentImgType) {
		progressbar.setVisibility(View.VISIBLE);
		List<HashMap<String, String>> list = null;
		try {
			list = readImageListFromInterface(current_page, count, currentImgType);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		// 发送message给handler并退出该方法
		if (list == null) {
			progressbar.setVisibility(View.GONE);
			mHandler.obtainMessage(1).sendToTarget();
			return false;
		}

		int counts = 0, viewId = 0x7F24FFF0;
		int verticalSpacing, horizontalSpacing;
		verticalSpacing = horizontalSpacing = getResources().getDimensionPixelSize(R.dimen.dp_4);
		Display display = getActivity().getWindowManager().getDefaultDisplay();
		int imageWidth = (display.getWidth() - (COLUMNS + 1) * horizontalSpacing) / COLUMNS;
		for (HashMap<String, String> map : list) {
			ImageView imageView = new ImageView(context);
			imageView.setId(++viewId);
			imageView.setScaleType(ScaleType.CENTER);
			imageView.setBackgroundResource(R.drawable.image_border);
			final HashMap<String, String> m = map;

			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					Intent intent = new Intent(context, PictureDetailActivity.class);
					intent.putExtra("map", m);

					context.startActivity(intent);
					// TODO 此处有刷赞的问题。。。不知道怎么解决
				}
			});
			// 把imageView添加到父控件里。IMPORTANT!!!
			parentLayout.addView(imageView);
			// scrollView.addView(imageView);
			// set imageView layout params
			LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView.getLayoutParams();
			layoutParams.width = imageWidth;
			layoutParams.height = IMAGEVIEW_DEFAULT_HEIGHT;
			layoutParams.topMargin = verticalSpacing;
			layoutParams.rightMargin = horizontalSpacing;
			int column = counts % COLUMNS;
			int row = counts / COLUMNS;
			if (row > 0) {
				layoutParams.addRule(RelativeLayout.BELOW, viewId - COLUMNS);
			}
			if (column > 0) {
				layoutParams.addRule(RelativeLayout.RIGHT_OF, viewId - 1);
			}

			String imgLink = baseURL + map.get("pictureIconLink");
			// get image
			IMAGE_CACHE.get(imgLink, imageView);
			counts++;
		}

		this.current_page++;
		progressbar.setVisibility(View.GONE);
		return true;

	}

	public String genQueryUrl(int current_page, int count, int currentImgType) {
		// 根据参数生成查询的URL
		String pageNo = String.valueOf(current_page);
		String pageSize = String.valueOf(count);
		String picType = null;

		switch (currentImgType) {
			case CONSTANT_PICTURE_BTN_HOT:
				// TODO
				picType = "HOTLEVEL";
				break;
			case CONSTANT_PICTURE_BTN_EAT:
				picType = "FOOD";
				break;
			case CONSTANT_PICTURE_BTN_HOTEL:
				picType = "HOTEL";
				break;
			case CONSTANT_PICTURE_BTN_ROAD:
				picType = "routeline";
				break;
			case CONSTANT_PICTURE_BTN_SHOP:
				picType = "SHOP";
				break;
		}
		String searchKey = null;
		String searchValue = null;
		/*
		 * searchKey 0：搜索出isShow = 1的记录 1: 按距离搜索 SearchValue
		 * 需要三个参数用逗号隔开：第一个为距离该点的距离，第二个为点的经度，第三个为该点的纬度 2: 按推荐度排序 pictureGood
		 * SearchValue 为0递减 否则递增
		 */
		int orderBy = MainActivity.orderBy;
		switch (orderBy) {
			case MainActivity.MENU_ORDER_BY_HOT:
				searchKey = "2";
				searchValue = "0";
				break;
			case MainActivity.MENU_ORDER_BY_DISTANCE:
				searchKey = "1";
				// 获取位置信息
				LocationListener locationListener = new MyLocationListener();
				LocationManager locationManager = (LocationManager) context
						.getSystemService(Context.LOCATION_SERVICE);
				locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, 0, 0, locationListener);
				Location location = locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER);
				double latitude = 0;
				double longitude = 0;
				double altitude = 0;
				if (location != null) {
					longitude = location.getLongitude(); // 经度
					latitude = location.getLatitude();     // 纬度
					altitude = location.getAltitude();     // 海拔
				}
				Log.v("tag", "latitude " + latitude + "  longitude:" + longitude + " altitude:" + altitude);
				String lon = String.valueOf(longitude);
				String lat = String.valueOf(latitude);

				// 获取位置信息
				searchValue = distanceWithCurrentLocation + "," + lon + "," + lat;
				System.out.println("searchKey>>" + searchKey);
				System.out.println("searchValue>>" + searchValue);
				break;
		}

		String queryUrl = Constant.baseURL + "/liveaction/GetLiveactionList?pageNo=" + pageNo + "&pageSize="
				+ pageSize + "&picType=" + picType + "&searchKey=" + searchKey + "&searchValue="
				+ searchValue;
		System.out.println("queryUrl>>>>" + queryUrl);
		return queryUrl;
	}

	int distanceWithCurrentLocation = 5000;

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

	// read image list from interface
	// use parameters to modify interfaceUrl
	private List<HashMap<String, String>> readImageListFromInterface(int current_page, int count,
			int currentImgType) throws InterruptedException, ExecutionException {
		String interfaceUrl = this.genQueryUrl(current_page, count, currentImgType);
		// run async task to get json
		String jsonString = new LoadJsonTask().execute(interfaceUrl).get();
		List<HashMap<String, String>> imageList = null;
		if(jsonString != null){//ys
			try {
				JSONObject obj = new JSONObject(jsonString);
				JSONArray array = obj.getJSONArray("liveactions");
				imageList = getListFromJSONArray(array);
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}else{
			//Toast.makeText(getActivity(), "请设置你的网络！", 1000).show();
		}
		return imageList;
	}

	//
	public static HashMap<String, String> getHashMapFromJSONObject(JSONObject json) throws JSONException {
		HashMap<String, String> map = new HashMap<String, String>();

		// get all col's name
		JSONArray keys = json.names();
		int columnNum = keys.length();
		for (int i = 0; i < columnNum; i++) {
			String key = keys.getString(i);
			String value = json.getString(key);
			map.put(key, value);
		}
		return map;
	}

	//
	public static List<HashMap<String, String>> getListFromJSONArray(JSONArray array) throws JSONException {
		List<HashMap<String, String>> list = new ArrayList<HashMap<String, String>>();
		int arrayLength = array.length();
		for (int i = 0; i < arrayLength; i++) {
			JSONObject json = array.getJSONObject(i);
			HashMap<String, String> map = getHashMapFromJSONObject(json);
			list.add(map);
		}
		return list;
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
			public void onGetSuccess(String imageUrl, Bitmap loadedImage, View view, boolean isInCache) {
				if (view != null && loadedImage != null) {
					ImageView imageView = (ImageView) view;
					imageView.setImageBitmap(loadedImage);
					// first time show with animation
					if (!isInCache) {
						imageView.startAnimation(getInAlphaAnimation(2000));
					}

					// auto set height accroding to rate between height and
					// weight
					LayoutParams imageParams = (LayoutParams) imageView.getLayoutParams();
					imageParams.height = imageParams.width * loadedImage.getHeight() / loadedImage.getWidth();
					imageView.setScaleType(ScaleType.FIT_XY);
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
					((ImageView) view).setImageResource(R.drawable.not_loaded);
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

	/***********************************************************************/
	// AsyncTask
	// get JSON from server's interface
	public class LoadJsonTask extends AsyncTask<String, Integer, String> {

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
		}

		@Override
		protected String doInBackground(String... params) {
			String result = null;
			HttpGet httpGet = new HttpGet(params[0]);
			HttpClient httpClient = new DefaultHttpClient();
			try {
				HttpResponse httpResponse = httpClient.execute(httpGet);

				if (httpResponse.getStatusLine().getStatusCode() == 200) {
					result = EntityUtils.toString(httpResponse.getEntity(), "utf-8");
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return result;
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			super.onProgressUpdate(values);
		}

		@Override
		protected void onPostExecute(String result) {
			super.onPostExecute(result);
		}
	}

	/***********************************************************************/
}

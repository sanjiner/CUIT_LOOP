package cuit.travelweather.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ExecutionException;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cuit.travelweather.R;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.LazyScrollView;
import cuit.travelweather.util.LazyScrollView.OnScrollListener;
import cn.trinea.android.common.entity.FailedReason;
import cn.trinea.android.common.service.impl.ImageCache;
import cn.trinea.android.common.service.impl.RemoveTypeLastUsedTimeFirst;
import cn.trinea.android.common.service.impl.ImageMemoryCache.OnImageCallbackListener;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.util.Log;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.Toast;
import android.widget.ImageView.ScaleType;
import android.widget.RelativeLayout.LayoutParams;

public class RoadActivity extends BaseAct {

	private Context context = RoadActivity.this;
	// 图片网址前缀
	private static final String pictureBaseUrl = "http://222.18.158.197:8080/scnjwTravel";
	// 图片类型常量
	private static final int CONSTANT_PICTURE_BTN_ROAD = 3;
	// 指示当前的图片类型，便于知道滚动的时候选择加载什么图片
	private int currentImgType = -1;
	// 当前在接口查找的页
	private int current_page = 0;
	// 每页加载多少图片
	private static final int IMAGES_NUM_PER_PAGE = 20;
	private static final int COLUMNS = 3;
	/** imageView default height **/
	public static final int IMAGEVIEW_DEFAULT_HEIGHT = 400;
	public static final String TAG_CACHE = "image_cache";
	/** cache folder path which be used when saving images **/
	// TODO 修改！！！
	public static final String DEFAULT_CACHE_FOLDER = new StringBuilder()
			.append(Environment.getExternalStorageDirectory().getAbsolutePath())
			.append(File.separator).append("TravelWeather")
			.append(File.separator).append("ImageCache").toString();
	private RelativeLayout parentLayout;
	private cuit.travelweather.util.LazyScrollView scrollView;
	private LinearLayout progressbar;
	/** icon cache **/
	public static final ImageCache IMAGE_CACHE = new ImageCache(128, 512);
	public boolean isVisable = false;
	boolean hasMorePics = true;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_road);
		parentLayout = (RelativeLayout) findViewById(R.id.image_cache_parent_layout);
		IMAGE_CACHE.initData(context, TAG_CACHE);
		IMAGE_CACHE.setContext(context);
		IMAGE_CACHE.setCacheFolder(DEFAULT_CACHE_FOLDER);
		progressbar = (LinearLayout) findViewById(R.id.progressbar);
		scrollView = (LazyScrollView) findViewById(R.id.scrollView);
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
		initLoad(CONSTANT_PICTURE_BTN_ROAD);
	}

	// 初次打开某个tab以后载入图片
	private void initLoad(int imgType) {
		this.hasMorePics = true;
		this.current_page = 0;
		this.currentImgType = imgType;
		parentLayout.removeAllViews();

		addImage(current_page, IMAGES_NUM_PER_PAGE, currentImgType);

	}

	@Override
	public void onDestroy() {
		IMAGE_CACHE.saveDataToDb(context, TAG_CACHE);
		super.onDestroy();
	}

	Handler mHandler = new Handler() {

		@Override
		public void dispatchMessage(Message msg) {
			super.dispatchMessage(msg);
		}

		@Override
		public void handleMessage(Message msg) {
			// TODO handler diapatchmessage handleMessage 区别
			switch (msg.what) {

			case 1:
				Toast.makeText(context, "没有读取到更多图片。", Toast.LENGTH_SHORT)
						.show();
				hasMorePics = false;
				break;

			}

			super.handleMessage(msg);
		}

	};

	private boolean addImage(final int current_page, final int count,
			final int currentImgType) {

		progressbar.setVisibility(View.VISIBLE);
		List<HashMap<String, String>> list = null;
		try {
			list = readImageListFromInterface(current_page, count,
					currentImgType);
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
		verticalSpacing = horizontalSpacing = getResources()
				.getDimensionPixelSize(R.dimen.dp_4);
		Display display = RoadActivity.this.getWindowManager()
				.getDefaultDisplay();
		int imageWidth = (display.getWidth() - (COLUMNS + 1)
				* horizontalSpacing)
				/ COLUMNS;
		for (HashMap<String, String> map : list) {
			ImageView imageView = new ImageView(context);
			imageView.setId(++viewId);
			imageView.setScaleType(ScaleType.CENTER);
			imageView.setBackgroundResource(R.drawable.image_border);
			final HashMap<String, String> m = map;

			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent intent = new Intent(context,
							PictureDetailActivity.class);
					intent.putExtra("map", m);
					context.startActivity(intent);
				}
			});
			parentLayout.addView(imageView);

			// set imageView layout params
			LayoutParams layoutParams = (RelativeLayout.LayoutParams) imageView
					.getLayoutParams();
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

			String imgLink = pictureBaseUrl + map.get("pictureIconLink");
			// get image
			IMAGE_CACHE.get(imgLink, imageView);
			counts++;
		}

		this.current_page++;
		progressbar.setVisibility(View.GONE);
		return true;

	}

	private String genQueryUrl(int current_page, int count, int currentImgType) {
		// 根据参数生成查询的URL
		String pageNo = String.valueOf(current_page);
		String pageSize = String.valueOf(count);
		String picType = "VIEW";
		// picType = "FOOD";

		String searchKey = "2";
		String searchValue = "0";

		String queryUrl = Constant.baseURL
				+ "/liveaction/GetLiveactionList?pageNo=" + pageNo
				+ "&pageSize=" + pageSize + "&picType=" + picType
				+ "&searchKey=" + searchKey + "&searchValue=" + searchValue;
		return queryUrl;
	}

	// read image list from interface
	// use parameters to modify interfaceUrl
	private List<HashMap<String, String>> readImageListFromInterface(
			int current_page, int count, int currentImgType)
			throws InterruptedException, ExecutionException {
		String interfaceUrl = this.genQueryUrl(current_page, count,
				currentImgType);
		// run async task to get json
		String jsonString = new LoadJsonTask().execute(interfaceUrl).get();
		if (jsonString.length() < 30){
			Toast.makeText(context, "暂无路况", Toast.LENGTH_LONG).show();
			return null;
		}
		List<HashMap<String, String>> imageList = null;
		try {
			JSONObject obj = new JSONObject(jsonString);
			JSONArray array = obj.getJSONArray("liveactions");
			imageList = getListFromJSONArray(array);
		} catch (JSONException e) {
			e.printStackTrace();
		}

		return imageList;
	}

	//
	public static HashMap<String, String> getHashMapFromJSONObject(
			JSONObject json) throws JSONException {
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
	public static List<HashMap<String, String>> getListFromJSONArray(
			JSONArray array) throws JSONException {
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
			public void onGetSuccess(String imageUrl, Bitmap loadedImage,
					View view, boolean isInCache) {
				if (view != null && loadedImage != null) {
					ImageView imageView = (ImageView) view;
					imageView.setImageBitmap(loadedImage);
					// first time show with animation
					if (!isInCache) {
						imageView.startAnimation(getInAlphaAnimation(2000));
					}

					// auto set height accroding to rate between height and
					// weight
					LayoutParams imageParams = (LayoutParams) imageView
							.getLayoutParams();
					imageParams.height = imageParams.width
							* loadedImage.getHeight() / loadedImage.getWidth();
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
					((ImageView) view).setImageResource(R.drawable.not_loaded);
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
					result = EntityUtils.toString(httpResponse.getEntity(),
							"utf-8");
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

	// xml绑定方法
	public void road_return(View v) {
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);
		menu.add(Menu.NONE, 0, 0, "新增路况");
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case 0:
			Intent it = new Intent().setClass(RoadActivity.this,
					TravelNewRoadActivity.class);
			startActivity(it);
			break;
		default:

		}
		return super.onMenuItemSelected(featureId, item);
	}


}

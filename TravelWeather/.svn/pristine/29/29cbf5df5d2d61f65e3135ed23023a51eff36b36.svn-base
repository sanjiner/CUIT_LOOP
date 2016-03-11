package cuit.travelweather.fragment;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import cuit.travelweather.R;
import cuit.travelweather.activity.JpushApplication;
import cuit.travelweather.activity.Keyword;
import cuit.travelweather.activity.MainActivity;
import cuit.travelweather.activity.NewScenicActivity;
import cuit.travelweather.activity.PlaceActivity;
import cuit.travelweather.activity.PlacelistActivity;
import cuit.travelweather.activity.PrePlaceDetailActivity;
import cuit.travelweather.zxing.decoding.FinishListener;

/*
 * [景点]页面
 */
@SuppressLint("SetJavaScriptEnabled")
public class PlaceFragment extends Fragment {
	private JSONObject jsonObject;
	private View view;
	private LinearLayout lv_place;
	private ListView palce_list;
	private boolean loaded = false;

	PopupOverlay pop = null;
	ArrayList<OverlayItem> mItems = null;
	TextView popupText = null;
	private MapView.LayoutParams layoutParam = null;

	MapView mMapView = null;// 地图View
	MKSearch mSearch = null;// 搜索模块，也可去掉地图模块独立使用

	private ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
	private SimpleAdapter adapter;
	private String[] cityStr = new String[] { "北京市", "上海市", "天津", "重庆", "哈尔滨",
			"长春", "沈阳", "呼和浩特", "石家庄", "太原", "济南", "郑州", "西安", "兰州", "银川",
			"西宁", "乌鲁木齐", "合肥", "南京", "杭州", "长沙", "南昌", "武汉", "成都", "贵阳", "福州",
			"台北", "广州", "海口", "南宁", "昆明", "拉萨", "香港", "澳门" };
	private String[] proStr = new String[] { "北京", "上海", "天津", "重庆",
			"黑龙江", "吉林", "辽宁", "内蒙古", "河北", "山西", "山东", "河南", "陕西",
			"甘肃", "宁夏", "青海", "新疆", "安徽", "江苏", "浙江", "湖南",
			"江西", "湖北", "四川", "贵州", "福建", "台湾", "广东", "海南", "广西",
			"云南", "西藏", "香港", "澳门" };
	private ArrayList<GeoPoint> cityGeoArrayList;
	private Button[] cityButtons = new Button[34];
	protected String keyWord;
	private SharedPreferences sp;
	

	@SuppressLint("JavascriptInterface")
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

//		JpushApplication app = (JpushApplication) getActivity()
//				.getApplication();
//		if (app.mBMapManager == null) {
//			app.mBMapManager = new BMapManager(getActivity());
//			app.mBMapManager.init(JpushApplication.strKey,
//					new JpushApplication.MyGeneralListener());
//		}

		view = inflater.inflate(R.layout.main_place, container, false);
		mMapView = (MapView) view.findViewById(R.id.place_bmapView);
		mMapView.setBuiltInZoomControls(true);
		// 设置启用内置的缩放控件
		MapController mMapController = mMapView.getController();
		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		GeoPoint point = new GeoPoint((int) (30.66667 * 1E6),
				(int) (104.06667 * 1E6));
		// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		//mMapController.isOverlookingGesturesEnabled();
		mMapController.setZoomGesturesEnabled(false);//是否启用缩放手势
		mMapController.setScrollGesturesEnabled(true);//是否启用平移手势
		mMapView.setDoubleClickZooming(false);//是否启用双击放大
		mMapController.setRotationGesturesEnabled(false);// 是否启用旋转手势
		mMapController.setOverlookingGesturesEnabled(false);//是否启用俯视手势
		 mMapView.setBuiltInZoomControls(false);// 是否显示内置绽放控件
//		mMapController.setCenter(point);// 设置地图中心点
		mMapController.setZoom(7);// 设置地图zoom级别
//		mSearch = new MKSearch();
//		mSearch.init(app.mBMapManager, new MySearchListener());
		init();
		
		return view;
	}

	private void init() {
		// TODO Auto-generated method stub
		palce_list = (ListView) view.findViewById(R.id.palce_list);

		lv_place = (LinearLayout) view.findViewById(R.id.lv_place);
		CheckBox check = (CheckBox) view.findViewById(R.id.place_switch);
		ImageButton button = (ImageButton) view.findViewById(R.id.place_search);
		//ys
		view.findViewById(R.id.place_add).setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Intent it = new Intent().setClass(getActivity(),
						NewScenicActivity.class);
				startActivity(it);
			}
		});
		//ys
		check.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(CompoundButton buttonView,
					boolean isChecked) {
				// TODO Auto-generated method stub
				Intent it = new Intent().setClass(getActivity(),PlaceActivity.class);
				startActivity(it);
//				if (isChecked == true) {
//					lv_place.setVisibility(View.GONE);
//					mMapView.setVisibility(View.VISIBLE);
//					palce_list.setVisibility(View.INVISIBLE);
//					
//				} else {
//					if (loaded == true) {
//						palce_list.setVisibility(View.VISIBLE);
//						lv_place.setVisibility(View.GONE);
//						mMapView.setVisibility(View.INVISIBLE);
//					} else {
//						lv_place.setVisibility(View.VISIBLE);
//						mMapView.setVisibility(View.INVISIBLE);
//					}
//
//				}

			}
		});
//		showMap();
		button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				if (loaded == false) {
					loaded = true;
					EditText ed = (EditText) view
							.findViewById(R.id.place_edit_search);
					 keyWord = ed.getText().toString().trim();
					////////////////////////////////////////////////////////////////////////////
//						Constant.keyvalue = keyWord;
						Intent it = new Intent().setClass(getActivity(),Keyword.class);
						 it.putExtra("key", keyWord);
						startActivity(it);
					 /////////////////////////////////////////////////////////////////////////////////////////
					if (mMapView.getVisibility() == View.VISIBLE) {
						// 上海  海南
					} else {
						palce_list.setVisibility(View.VISIBLE);
					}
				} else {
					EditText ed = (EditText) view
							.findViewById(R.id.place_edit_search);
					 String keyWord = ed.getText().toString().trim();
					Intent it = new Intent().setClass(getActivity(),
							Keyword.class);
					it.putExtra("key", "keyWord");
					startActivity(it);
				}
			}
		});

		palce_list.setOnItemClickListener(new OnItemClickListener() {

			
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				@SuppressWarnings("unchecked")
				HashMap<String, Object> map = (HashMap<String, Object>) parent
						.getItemAtPosition(position);
				String addr;
				addr = (String) map.get("name");
				Intent i = new Intent();
				i.putExtra("addr", addr);
				i.setClass(getActivity(),
						PrePlaceDetailActivity.class);
				startActivity(i);
				
			}
		});

	}


//	@SuppressLint("NewApi")
//	private void showMap() {
//		// TODO Auto-generated method stub
//		cityGeoArrayList = new ArrayList<GeoPoint>();
//		MapView.LayoutParams buttonLayoutParams = null;
//		addGeoPoint();
//		// 自定义按钮
//		int bg = 0xF00000FF;
//		int tc = 0xFFAB1203;
//		float btn_tx = 14;
//		for (int i=0;i<34;i++) {
//			final int j=i;
//			cityButtons[i] = new Button(getActivity()); 
//			cityButtons[i].setText(proStr[i] +"景点");
//			//cityButtons[i].setBackgroundColor(bg);
//			cityButtons[i].setBackground(getResources().getDrawable(R.drawable.login_edit));
//			cityButtons[i].setTextColor(tc);
//			cityButtons[i].setTextSize(btn_tx);
//			buttonLayoutParams = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT, MapView.LayoutParams.WRAP_CONTENT, cityGeoArrayList.get(i), 0);
//			cityButtons[i].setOnClickListener(new OnClickListener() {
//				
//				@Override
//				public void onClick(View v) {
//					// TODO Auto-generated method stub
//					Intent it = new Intent().setClass(getActivity(), PlacelistActivity.class);
//					it.putExtra("cityName", cityStr[j]);
//					it.putExtra("proName", proStr[j]);
//					startActivity(it);
//				}
//			});
//			mMapView.addView(cityButtons[i], buttonLayoutParams);
//		}
//	}

//	private void addGeoPoint() {
//		// TODO Auto-generated method stub
//		cityGeoArrayList.add(0, new GeoPoint((int)(39.91667*1e6), (int)(116.41667*1e6)));
//		cityGeoArrayList.add(1, new GeoPoint((int)(31.14*1e6), (int)(121.43333*1e6)));
//		cityGeoArrayList.add(2, new GeoPoint((int)(39.13333*1e6), (int)(117.20000*1e6)));
//		cityGeoArrayList.add(3, new GeoPoint((int)(29.56667*1e6), (int)(106.45000*1e6)));
//		cityGeoArrayList.add(4, new GeoPoint((int)(45.75000*1e6), (int)(126.63333*1e6)));
//		cityGeoArrayList.add(5, new GeoPoint((int)(43.88333*1e6), (int)(125.35000*1e6)));
//		cityGeoArrayList.add(6, new GeoPoint((int)(41.80000*1e6), (int)(123.38333*1e6)));
//		cityGeoArrayList.add(7, new GeoPoint((int)(40.48*1e6), (int)(111.41*1e6)));
//		cityGeoArrayList.add(8, new GeoPoint((int)(38.03333*1e6), (int)(114.48333*1e6)));
//		cityGeoArrayList.add(9, new GeoPoint((int)(37.86667*1e6), (int)(112.53333*1e6)));
//		cityGeoArrayList.add(10, new GeoPoint((int)(36.40*1e6), (int)(117.00*1e6)));
//		cityGeoArrayList.add(11, new GeoPoint((int)(34.76667*1e6), (int)(113.65000*1e6)));
//		cityGeoArrayList.add(12, new GeoPoint((int)(34.26667*1e6), (int)(108.95000*1e6)));
//		cityGeoArrayList.add(13, new GeoPoint((int)(36.03333*1e6), (int)(103.73333*1e6)));
//		cityGeoArrayList.add(14, new GeoPoint((int)(38.46667*1e6), (int)(106.26667*1e6)));
//		cityGeoArrayList.add(15, new GeoPoint((int)(36.56667*1e6), (int)(101.75000*1e6)));
//		cityGeoArrayList.add(16, new GeoPoint((int)(43.76667*1e6), (int)(87.68333*1e6)));
//		cityGeoArrayList.add(17, new GeoPoint((int)(31.52*1e6), (int)(117.17*1e6)));
//		cityGeoArrayList.add(18, new GeoPoint((int)(32.05000*1e6), (int)(118.78333*1e6)));
//		cityGeoArrayList.add(19, new GeoPoint((int)(30.26667*1e6), (int)(120.20000*1e6)));
//		cityGeoArrayList.add(20, new GeoPoint((int)(28.21667*1e6), (int)(113.00000*1e6)));
//		cityGeoArrayList.add(21, new GeoPoint((int)(28.68333*1e6), (int)(115.90000*1e6)));
//		cityGeoArrayList.add(22, new GeoPoint((int)(30.51667*1e6), (int)(114.31667*1e6)));
//		cityGeoArrayList.add(23, new GeoPoint((int)(30.66667*1e6), (int)(104.06667*1e6)));
//		cityGeoArrayList.add(24, new GeoPoint((int)(26.56667*1e6), (int)(106.71667*1e6)));
//		cityGeoArrayList.add(25, new GeoPoint((int)(26.08333*1e6), (int)(119.30000*1e6)));
//		cityGeoArrayList.add(26, new GeoPoint((int)(25.03*1e6), (int)(121.30*1e6)));
//		cityGeoArrayList.add(27, new GeoPoint((int)(23.16667*1e6), (int)(113.23333*1e6)));
//		cityGeoArrayList.add(28, new GeoPoint((int)(20.01667*1e6), (int)(110.35000*1e6)));
//		cityGeoArrayList.add(29, new GeoPoint((int)(22.48*1e6), (int)(108.19*1e6)));
//		cityGeoArrayList.add(30, new GeoPoint((int)(25.05000*1e6), (int)(102.73333*1e6)));
//		cityGeoArrayList.add(31, new GeoPoint((int)(29.60000*1e6), (int)(91.00000*1e6)));
//		cityGeoArrayList.add(32, new GeoPoint((int)(22.20000*1e6), (int)(114.10000*1e6)));
//		cityGeoArrayList.add(33, new GeoPoint((int)(22.20000*1e6), (int)(113.50000*1e6)));
//	}

//	public class MySearchListener implements MKSearchListener {
//
//		@Override
//		public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onGetPoiDetailSearchResult(int arg0, int arg1) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@SuppressLint("NewApi")
//		@Override
//		public void onGetPoiResult(MKPoiResult result, int type, int iError) {
//			// TODO Auto-generated method stub
//			if (iError != 0 || result == null) {
//				Toast.makeText(getActivity(), "抱歉，未找到这个地方", Toast.LENGTH_SHORT)
//						.show();
//				return;
//			}
//			
//			if (cityButtons[0].length()>0) {
//				for(int i=0;i<34;i++) {
//					mMapView.removeView(cityButtons[i]);
//				}
//			}
//			mMapView.getOverlays().clear();
//			PoiOverlay poioverlay = new PoiOverlay(getActivity(), mMapView);
//			poioverlay.setData(result.getAllPoi());
//			mMapView.getOverlays().add(poioverlay);
//
//			if (result.getNumPois() > 0) {
//				MKPoiInfo poiInfo = result.getPoi(0);
//				mMapView.getController().setCenter(poiInfo.pt);
//				mMapView.getController().setZoom(18);
//				data.clear();
//				int bg = 0xf0bed742;
//				int tc = 0xFFAB1203;
//				for (int i = 0; i < 10; i++) {
//					HashMap<String, Object> map = new HashMap<String, Object>();
//					map.put("name", result.getPoi(i).name);
//					data.add(map);
//					final String addr = result.getPoi(i).name;
//					Button button1 = new Button(getActivity());
//					//button1.setBackgroundColor(bg);
//					button1.setBackground(getResources().getDrawable(R.drawable.login_edit));
//					button1.setTextColor(tc);
//					button1.setTextSize(14);
//					button1.setText(result.getPoi(i).name);
//					layoutParam = new MapView.LayoutParams(
//							MapView.LayoutParams.WRAP_CONTENT,
//							MapView.LayoutParams.WRAP_CONTENT,
//							result.getPoi(i).pt, 0);
//					mMapView.addView(button1, layoutParam);
//					button1.setOnClickListener(new OnClickListener() {
//
//						@Override
//						public void onClick(View v) {
//							// TODO Auto-generated method stub
//							Intent it = new Intent(getActivity(),
//									PrePlaceDetailActivity.class);
//							it.putExtra("addr", addr);
//							startActivity(it);
//						}
//					});
//				}
//			}
//			mMapView.refresh();
//			// System.out.println(result.getCurrentNumPois());
//		}
//
//		@Override
//		public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1,
//				int arg2) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
//			// TODO Auto-generated method stub
//
//		}
//
//		@Override
//		public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
//			// TODO Auto-generated method stub
//
//		}
//
//	}

	// 添加城市列表适配器
	private SimpleAdapter getAdapter() {

		adapter = new SimpleAdapter(getActivity(), data,
				R.layout.place_list_itme, new String[] { "name" },
				new int[] { R.id.place_list_tv });
		return adapter;
	}

//	@Override
//	public void onDestroy() {
//		// TODO Auto-generated method stub
//		mMapView.destroy();
//		super.onDestroy();
//	}
//
//	@Override
//	public void onPause() {
//		// TODO Auto-generated method stub
//		mMapView.onPause();
//		super.onPause();
//	}
//
//	@Override
//	public void onResume() {
//		// TODO Auto-generated method stub
//		mMapView.onResume();
//		super.onResume();
//	}

}

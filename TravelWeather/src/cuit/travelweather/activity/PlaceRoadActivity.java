package cuit.travelweather.activity;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.PoiOverlay;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPoiInfo;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import cuit.travelweather.R;
import cuit.travelweather.R.id;
import cuit.travelweather.R.layout;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class PlaceRoadActivity extends BaseAct {
	
	MapView mMapView = null;// 地图View
	MKSearch mSearch = null;// 搜索模块，也可去掉地图模块独立使用
	private TextView place_road_title;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		JpushApplication app = (JpushApplication) this.getApplication();
		if (app.mBMapManager == null) {
			app.mBMapManager = new BMapManager(this);
			app.mBMapManager.init(JpushApplication.strKey,
					new JpushApplication.MyGeneralListener());
		}
		setContentView(R.layout.activity_place_road);
		MapInit(app);
		init();
	}
	
	private void init() {
		// TODO Auto-generated method stub
		String place;
		Intent it = getIntent();
		place = it.getStringExtra("placeName");
		if (place.equals("")){
			place = "武侯祠";
		}
		place_road_title = (TextView) findViewById(R.id.place_road_title);
		place_road_title.setText(place);
		mSearch.poiSearchInCity("", place);
	}

	private void MapInit(JpushApplication app) {
		// TODO Auto-generated method stub
		mMapView = (MapView) findViewById(R.id.road_bmapView);
		mMapView.setBuiltInZoomControls(true);		// 设置启用内置的缩放控件
		MapController mMapController = mMapView.getController();		// 得到mMapView的控制权,可以用它控制和驱动平移和缩放
		GeoPoint point = new GeoPoint((int) (30.585 * 1E6),
				(int) (103.995 * 1E6));// 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
		mMapController.setCenter(point);// 设置地图中心点
		mMapController.setZoom(15);// 设置地图zoom级别
		mMapView.getController().enableClick(true);
		mSearch = new MKSearch();
		mSearch.init(app.mBMapManager, new MKSearchListener() {
			
			@Override
			public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetPoiResult(MKPoiResult res, int type, int error) {
				// TODO Auto-generated method stub
				// 错误号可参考MKEvent中的定义  
				if ( error == MKEvent.ERROR_RESULT_NOT_FOUND){  
				Toast.makeText(PlaceRoadActivity.this, "抱歉，未找到结果",Toast.LENGTH_LONG).show();  
				return ;  
				        }  
				        else if (error != 0 || res == null) {  
				Toast.makeText(PlaceRoadActivity.this, "搜索出错啦..", Toast.LENGTH_LONG).show();  
				return;
				        }
				//将poi结果显示到地图上  
				PoiOverlay poiOverlay = new PoiOverlay(PlaceRoadActivity.this, mMapView); 
				poiOverlay.setData(res.getAllPoi());  
				mMapView.getOverlays().clear();  
				mMapView.getOverlays().add(poiOverlay);
				// 自定义textview
				int bg = 0xf0bed742;
				int tc = 0xfffeeeed;
				Button button = new Button(PlaceRoadActivity.this);
				button.setBackgroundColor(bg);
				button.setText(res.getPoi(0).name);
				button.setTextColor(tc);
				button.setTextSize(20);
				MapView.LayoutParams bLayoutParams = null;
				bLayoutParams = new MapView.LayoutParams(
						MapView.LayoutParams.WRAP_CONTENT,
						MapView.LayoutParams.WRAP_CONTENT, res.getPoi(0).pt, 0);
				//mMapView.addView(button, bLayoutParams);
				button.setOnClickListener(new OnClickListener() {
					
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						
					}
				});
				mMapView.refresh();  
				//当ePoiType为2（公交线路）或4（地铁线路）时， poi坐标为空  
				for(MKPoiInfo info : res.getAllPoi() ){  
				if ( info.pt != null ){  
				mMapView.getController().animateTo(info.pt);  
				break;
				}  
				    }  
			}
			
			@Override
			public void onGetPoiDetailSearchResult(int arg0, int arg1) {
				// TODO Auto-generated method stub
			
			}
			
			@Override
			public void onGetDrivingRouteResult(MKDrivingRouteResult arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetBusDetailResult(MKBusLineResult arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onGetAddrResult(MKAddrInfo arg0, int arg1) {
				// TODO Auto-generated method stub
				
			}
		});
	}
	
	public void road_returen(View v) {
		finish();
	}
	
	@Override
	protected void onPause() {
		mMapView.onPause();
		super.onPause();
	}

	@Override
	protected void onResume() {
		mMapView.onResume();
		super.onResume();
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		mMapView.destroy();
		super.onDestroy();
	}
}

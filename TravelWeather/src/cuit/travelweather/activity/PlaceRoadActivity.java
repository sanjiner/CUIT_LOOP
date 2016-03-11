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
	
	MapView mMapView = null;// ��ͼView
	MKSearch mSearch = null;// ����ģ�飬Ҳ��ȥ����ͼģ�����ʹ��
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
			place = "�����";
		}
		place_road_title = (TextView) findViewById(R.id.place_road_title);
		place_road_title.setText(place);
		mSearch.poiSearchInCity("", place);
	}

	private void MapInit(JpushApplication app) {
		// TODO Auto-generated method stub
		mMapView = (MapView) findViewById(R.id.road_bmapView);
		mMapView.setBuiltInZoomControls(true);		// �����������õ����ſؼ�
		MapController mMapController = mMapView.getController();		// �õ�mMapView�Ŀ���Ȩ,�����������ƺ�����ƽ�ƺ�����
		GeoPoint point = new GeoPoint((int) (30.585 * 1E6),
				(int) (103.995 * 1E6));// �ø����ľ�γ�ȹ���һ��GeoPoint����λ��΢�� (�� * 1E6)
		mMapController.setCenter(point);// ���õ�ͼ���ĵ�
		mMapController.setZoom(15);// ���õ�ͼzoom����
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
				// ����ſɲο�MKEvent�еĶ���  
				if ( error == MKEvent.ERROR_RESULT_NOT_FOUND){  
				Toast.makeText(PlaceRoadActivity.this, "��Ǹ��δ�ҵ����",Toast.LENGTH_LONG).show();  
				return ;  
				        }  
				        else if (error != 0 || res == null) {  
				Toast.makeText(PlaceRoadActivity.this, "����������..", Toast.LENGTH_LONG).show();  
				return;
				        }
				//��poi�����ʾ����ͼ��  
				PoiOverlay poiOverlay = new PoiOverlay(PlaceRoadActivity.this, mMapView); 
				poiOverlay.setData(res.getAllPoi());  
				mMapView.getOverlays().clear();  
				mMapView.getOverlays().add(poiOverlay);
				// �Զ���textview
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
				//��ePoiTypeΪ2��������·����4��������·��ʱ�� poi����Ϊ��  
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

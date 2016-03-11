package cuit.travelweather.activity;

import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.OverlayItem;
import com.baidu.mapapi.map.PopupOverlay;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import cuit.travelweather.R;

public class PlaceActivity extends BaseAct{
	private View view;
	private Context contex = PlaceActivity.this;
	PopupOverlay pop = null;
	ArrayList<OverlayItem> mItems = null;
	TextView popupText = null;
	private MapView.LayoutParams layoutParam = null;

	MapView mMapView = null;// ��ͼView

	private ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
	private SimpleAdapter adapter;
	private String[] cityStr = new String[] { "������", "�Ϻ���", "���", "����", "������",
			"����", "����", "���ͺ���", "ʯ��ׯ", "̫ԭ", "����", "֣��", "����", "����", "����",
			"����", "��³ľ��", "�Ϸ�", "�Ͼ�", "����", "��ɳ", "�ϲ�", "�人", "�ɶ�", "����", "����",
			"̨��", "����", "����", "����", "����", "����", "���", "����" };
	private String[] proStr = new String[] { "����", "�Ϻ�", "���", "����",
			"������", "����", "����", "���ɹ�", "�ӱ�", "ɽ��", "ɽ��", "����", "����",
			"����", "����", "�ຣ", "�½�", "����", "����", "�㽭", "����",
			"����", "����", "�Ĵ�", "����", "����", "̨��", "�㶫", "����", "����",
			"����", "����", "���", "����" };
	private ArrayList<GeoPoint> cityGeoArrayList;
	private Button[] cityButtons = new Button[34];
	protected String keyWord;
	private JpushApplication app;
	private SharedPreferences sp;
	

	@SuppressLint("JavascriptInterface")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		app = (JpushApplication) this.getApplication();
		
		app.mBMapManager = new BMapManager(contex);
		app.mBMapManager.init(JpushApplication.strKey,
				new JpushApplication.MyGeneralListener());
//		if (app.mBMapManager == null) {
//			app.mBMapManager = new BMapManager(contex);
//			app.mBMapManager.init(JpushApplication.strKey,
//					new JpushApplication.MyGeneralListener());
//		}

		setContentView(R.layout.activity_map);
		TextView tv1 = (TextView)findViewById(R.id.ditu);
		sp= getSharedPreferences("test", Activity.MODE_PRIVATE); 
		 String size = sp.getString("affairinfo_fontsize", "2");
		if(size.equals("1")){
			tv1.setTextSize(10);
		}else if(size.equals("2")){
		}else{
			tv1.setTextSize(30);
		}
		mMapView = (MapView)findViewById(R.id.place_bmapView1);
		mMapView.setBuiltInZoomControls(true);
		// �����������õ����ſؼ�
		MapController mMapController = mMapView.getController();
		// �õ�mMapView�Ŀ���Ȩ,�����������ƺ�����ƽ�ƺ�����
		GeoPoint point = new GeoPoint((int) (30.66667 * 1E6),
				(int) (104.06667 * 1E6));
		// �ø����ľ�γ�ȹ���һ��GeoPoint����λ��΢�� (�� * 1E6)
		//mMapController.isOverlookingGesturesEnabled();
		mMapController.setZoomGesturesEnabled(false);//�Ƿ�������������
		mMapController.setScrollGesturesEnabled(true);//�Ƿ�����ƽ������
		mMapView.setDoubleClickZooming(false);//�Ƿ�����˫���Ŵ�
		mMapController.setRotationGesturesEnabled(false);// �Ƿ�������ת����
		mMapController.setOverlookingGesturesEnabled(false);//�Ƿ����ø�������
		 mMapView.setBuiltInZoomControls(false);// �Ƿ���ʾ�������ſؼ�
		mMapController.setCenter(point);// ���õ�ͼ���ĵ�
		mMapController.setZoom(7);// ���õ�ͼzoom����
		showMap();	
	}

	
		
	
	@SuppressLint("NewApi")
	private void showMap() {
//		handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
		// TODO Auto-generated method stub
		cityGeoArrayList = new ArrayList<GeoPoint>();
		MapView.LayoutParams buttonLayoutParams = null;
		addGeoPoint();
		// �Զ��尴ť
		int bg = 0xF00000FF;
		int tc = 0xFFAB1203;
		float btn_tx = 14;
		for (int i=0;i<34;i++) {
			final int j=i;
			cityButtons[i] = new Button(this); 
			cityButtons[i].setText(proStr[i] +"����");
			//cityButtons[i].setBackgroundColor(bg);
			cityButtons[i].setBackground(getResources().getDrawable(R.drawable.login_edit));
			cityButtons[i].setTextColor(tc);
			cityButtons[i].setTextSize(btn_tx);
			buttonLayoutParams = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT, MapView.LayoutParams.WRAP_CONTENT, cityGeoArrayList.get(i), 0);
			cityButtons[i].setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					// TODO Auto-generated method stub
					Intent it = new Intent().setClass(PlaceActivity.this, PlacelistActivity.class);
					it.putExtra("cityName", cityStr[j]);
					it.putExtra("proName", proStr[j]);
					startActivity(it);
				}
			});
			mMapView.addView(cityButtons[i], buttonLayoutParams);
		}
	}

	private void addGeoPoint() {
		// TODO Auto-generated method stub
		cityGeoArrayList.add(0, new GeoPoint((int)(39.91667*1e6), (int)(116.41667*1e6)));
		cityGeoArrayList.add(1, new GeoPoint((int)(31.14*1e6), (int)(121.43333*1e6)));
		cityGeoArrayList.add(2, new GeoPoint((int)(39.13333*1e6), (int)(117.20000*1e6)));
		cityGeoArrayList.add(3, new GeoPoint((int)(29.56667*1e6), (int)(106.45000*1e6)));
		cityGeoArrayList.add(4, new GeoPoint((int)(45.75000*1e6), (int)(126.63333*1e6)));
		cityGeoArrayList.add(5, new GeoPoint((int)(43.88333*1e6), (int)(125.35000*1e6)));
		cityGeoArrayList.add(6, new GeoPoint((int)(41.80000*1e6), (int)(123.38333*1e6)));
		cityGeoArrayList.add(7, new GeoPoint((int)(40.48*1e6), (int)(111.41*1e6)));
		cityGeoArrayList.add(8, new GeoPoint((int)(38.03333*1e6), (int)(114.48333*1e6)));
		cityGeoArrayList.add(9, new GeoPoint((int)(37.86667*1e6), (int)(112.53333*1e6)));
		cityGeoArrayList.add(10, new GeoPoint((int)(36.40*1e6), (int)(117.00*1e6)));
		cityGeoArrayList.add(11, new GeoPoint((int)(34.76667*1e6), (int)(113.65000*1e6)));
		cityGeoArrayList.add(12, new GeoPoint((int)(34.26667*1e6), (int)(108.95000*1e6)));
		cityGeoArrayList.add(13, new GeoPoint((int)(36.03333*1e6), (int)(103.73333*1e6)));
		cityGeoArrayList.add(14, new GeoPoint((int)(38.46667*1e6), (int)(106.26667*1e6)));
		cityGeoArrayList.add(15, new GeoPoint((int)(36.56667*1e6), (int)(101.75000*1e6)));
		cityGeoArrayList.add(16, new GeoPoint((int)(43.76667*1e6), (int)(87.68333*1e6)));
		cityGeoArrayList.add(17, new GeoPoint((int)(31.52*1e6), (int)(117.17*1e6)));
		cityGeoArrayList.add(18, new GeoPoint((int)(32.05000*1e6), (int)(118.78333*1e6)));
		cityGeoArrayList.add(19, new GeoPoint((int)(30.26667*1e6), (int)(120.20000*1e6)));
		cityGeoArrayList.add(20, new GeoPoint((int)(28.21667*1e6), (int)(113.00000*1e6)));
		cityGeoArrayList.add(21, new GeoPoint((int)(28.68333*1e6), (int)(115.90000*1e6)));
		cityGeoArrayList.add(22, new GeoPoint((int)(30.51667*1e6), (int)(114.31667*1e6)));
		cityGeoArrayList.add(23, new GeoPoint((int)(30.66667*1e6), (int)(104.06667*1e6)));
		cityGeoArrayList.add(24, new GeoPoint((int)(26.56667*1e6), (int)(106.71667*1e6)));
		cityGeoArrayList.add(25, new GeoPoint((int)(26.08333*1e6), (int)(119.30000*1e6)));
		cityGeoArrayList.add(26, new GeoPoint((int)(25.03*1e6), (int)(121.30*1e6)));
		cityGeoArrayList.add(27, new GeoPoint((int)(23.16667*1e6), (int)(113.23333*1e6)));
		cityGeoArrayList.add(28, new GeoPoint((int)(20.01667*1e6), (int)(110.35000*1e6)));
		cityGeoArrayList.add(29, new GeoPoint((int)(22.48*1e6), (int)(108.19*1e6)));
		cityGeoArrayList.add(30, new GeoPoint((int)(25.05000*1e6), (int)(102.73333*1e6)));
		cityGeoArrayList.add(31, new GeoPoint((int)(29.60000*1e6), (int)(91.00000*1e6)));
		cityGeoArrayList.add(32, new GeoPoint((int)(22.20000*1e6), (int)(114.10000*1e6)));
		cityGeoArrayList.add(33, new GeoPoint((int)(22.20000*1e6), (int)(113.50000*1e6)));
	}

	
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		mMapView.destroy();
		super.onDestroy();
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		mMapView.onPause();
		super.onPause();
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		mMapView.onResume();
		super.onResume();
	}


}

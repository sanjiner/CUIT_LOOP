package com.example.thesis;


import com.baidu.mapapi.SDKInitializer;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.exam.ThreeSystem.R;
import com.example.PCenter.BaseActivity;

import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.ScrollView;

public class MoreAboutUs extends BaseActivity implements
		OnClickListener {
	private Button mBack;
	private MapView mMapView = null; 
	private ScrollView mSvBrief;
	private RadioButton mTvBrief, mTvLocal;
	
	private BaiduMap mBaiduMap;	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		//在使用SDK各组件之前初始化context信息，传入ApplicationContext  
        //注意该方法要再setContentView方法之前实现  
        SDKInitializer.initialize(getApplicationContext()); 
		setContentView(R.layout.tea_moreaboutus);
		//初始化定位
		init();
	}


	private void init() {
		getViewObj();
		setListener();
	}

	private void getViewObj() {
		mBack = (Button) findViewById(R.id.b_more_aboutus_back);
		mSvBrief = (ScrollView) findViewById(R.id.sv_more_aboutus_brief);
		mMapView = (MapView) findViewById(R.id.more_aboutus_mapcontainer);
		mTvBrief = (RadioButton) findViewById(R.id.rb_more_aboutus_brief);
		mTvLocal = (RadioButton) findViewById(R.id.rb_more_aboutus_local);
		
		mBaiduMap = mMapView.getMap();
		MapStatusUpdate msu = MapStatusUpdateFactory.newLatLng(new LatLng(30.586778, 103.996917));//.zoomTo(15.0f);
		MapStatusUpdate msu_zoom = MapStatusUpdateFactory.zoomTo(15.0f);
		LatLng latLng  = new LatLng(30.586778, 103.996917);
		BitmapDescriptor  bitmap_descripter = BitmapDescriptorFactory.fromResource(R.drawable.ic_baidumap_icon);
		OverlayOptions options = new MarkerOptions().position(latLng).icon(bitmap_descripter);
		mBaiduMap.addOverlay(options);
		mBaiduMap.animateMapStatus(msu);
		mBaiduMap.animateMapStatus(msu_zoom);
	}

	private void setListener() {
		mBack.setOnClickListener(this);
		mSvBrief.setOnClickListener(this);
		mMapView.setOnClickListener(this);
		mTvBrief.setOnClickListener(this);
		mTvLocal.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.b_more_aboutus_back:
			finish();
			break;
		case R.id.rb_more_aboutus_brief:
			mSvBrief.setVisibility(View.VISIBLE);
			mMapView.setVisibility(View.GONE);
			break;
		case R.id.rb_more_aboutus_local:
			mSvBrief.setVisibility(View.GONE);
			mMapView.setVisibility(View.VISIBLE);
			break;
		}
	}

	
	@Override
	protected void onResume() {
		super.onResume();
		//在activity执行onResume时执行mMapView. onResume ()，实现地图生命周期管理  
        mMapView.onResume();
	}
}

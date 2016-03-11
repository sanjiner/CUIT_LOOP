package cuit.travelweather.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;

/**
 * 保证这个类只存在一个实例
 * 
 * @author xiao
 * 
 */
public class GPSInfoProvider {
	private static GPSInfoProvider mGpsInfoProvider;
	private static Context context;
	private LocationManager manager;
	private static MyLocationListener listener;
	
	private double lati;
	private double longt;
	// 1.私有化构造方法
	private GPSInfoProvider() {
	};

	// 2.提供静态方法返后一个实例
	public static synchronized GPSInfoProvider getInstance(Context context) {
		if (mGpsInfoProvider == null) {
			mGpsInfoProvider = new GPSInfoProvider();
			GPSInfoProvider.context = context;
		}
		return mGpsInfoProvider;
	}
	
	//获取纬度
	public String getLocationlatitude(){
		manager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);
		String provider = getProvider(manager);
		//注册位置监听器
		manager.requestLocationUpdates(provider, 1000, 1000, getListener());
		SharedPreferences sp =context.getSharedPreferences("config", Context.MODE_PRIVATE);
		String latitude = sp.getString("latitude", "");
		return latitude; 
	}

	//获取经度
	public String getLocationlongtitude(){
		SharedPreferences sp =context.getSharedPreferences("config", Context.MODE_PRIVATE);
		String longtitude = sp.getString("location", "");
		return longtitude; 
	}
	
	private synchronized MyLocationListener getListener(){
		if(listener==null){
			listener =  new MyLocationListener();
		}
		return listener;
	}
	private class MyLocationListener implements LocationListener{

		@Override
		public void onLocationChanged(Location location) {
			String latitude = ""+location.getLatitude();//获取纬度
			setLati(location.getLatitude());//获取纬度
			String longtitude = ""+location.getLongitude();
			setLongt(location.getLongitude());//获取经度
			SharedPreferences sp = context.getSharedPreferences("config", Context.MODE_PRIVATE);
			Editor editor = sp.edit();
			editor.putString("location", longtitude);
			editor.putString("latitude", latitude);
			editor.putString("longtitude",longtitude);
			System.out.println("3333333333333333333333"+longtitude);
			editor.commit();//最后一次获取到的数据存到sharepreferences里面
		}

		@Override
		public void onProviderDisabled(String provider) {
			
		}

		@Override
		public void onProviderEnabled(String provider) {
			
		}

		@Override
		public void onStatusChanged(String provider, int status, Bundle extras) {
			
		}
		
	}
	
	
	// 停止gps监听
		public void stopGPSListener(){
			manager.removeUpdates(getListener());
		}
		
	
	
	private String getProvider(LocationManager manager) {
		Criteria criteria  = new Criteria();
		criteria.setAccuracy(Criteria.ACCURACY_FINE);
		//对海拔是否敏感
		criteria.setAltitudeRequired(false);
		criteria.setPowerRequirement(Criteria.POWER_LOW);
		criteria.setSpeedRequired(true);
		criteria.setCostAllowed(true);
		return manager.getBestProvider(criteria, true);
	}

	public double getLongt() {
		return longt;
	}

	public void setLongt(double longt) {
		this.longt = longt;
	}

	public double getLati() {
		return lati;
	}

	public void setLati(double lati) {
		this.lati = lati;
	}
	
	
	
	
	

}

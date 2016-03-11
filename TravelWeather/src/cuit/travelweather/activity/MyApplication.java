package cuit.travelweather.activity;

import android.app.Application;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.SkinManager;
import cuit.travelweather.util.StringUtils;

public class MyApplication extends Application{

	static MyApplication application;
	public static MyApplication getApplication(){
		return application;
	}
	@Override
	public void onCreate() {		
		super.onCreate();
		application = this;	
		Constant.initSkinDir(this);		
		initSkins();
	}
	
	
	public  void initSkins(){
		 
		  String skinPack = Constant.getAppSkin(this);		
		  if(!StringUtils.isNull(skinPack)){			  		
		      SkinManager.setSkinContext(this, skinPack);		    
		  }
	
	}
	
	

}

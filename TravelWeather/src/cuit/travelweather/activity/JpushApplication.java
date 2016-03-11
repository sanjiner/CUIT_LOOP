package cuit.travelweather.activity;

import org.json.JSONObject;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.MKGeneralListener;
import com.baidu.mapapi.map.MKEvent;

import cuit.travelweather.util.Constant;
import cuit.travelweather.util.SkinManager;
import cuit.travelweather.util.StringUtils;
import cn.jpush.android.api.JPushInterface;
import android.app.Application;
import android.content.Context;
import android.os.Process;
import android.util.Log;
import android.widget.Toast;

public class JpushApplication extends Application {
	
	private static final String TAG = "JPush";
//	private String mData;  
	//JOSN相关
	private Context context = JpushApplication.this;
	private JSONObject json4weather;
	private JSONObject json4travle_forcast;
	private JSONObject json4travle_live;
	private JSONObject json4travle_warning;
	// 百度地图
	private static JpushApplication mInstance = null;
    public boolean m_bKeyRight = true;
    public BMapManager mBMapManager = null;
//    public static final String strKey = "mv3BS63FXfqGeWXBeGt3Xef9"; xadUU3XPdDt31YRTiSMSLgME；// 导出版本
    public static final String strKey = "2EOaPsjReGasptZTX0i5ArGv";//"O7QxBSHulObC7Qr4M6siQVGG";//   Cz8pUunGtymWttfjTrEwlbKR;;mv3BS63FXfqGeWXBeGt3Xef9
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		// 百度地图相关
		mInstance = this;
		initEngineManager(context);
		// Jpush相关
		Log.d(TAG, "[ExampleApplication] onCreate");
        super.onCreate();     
        JPushInterface.init(this);     		// 初始化 JPush
 		super.onCreate(); 
 		Log.d("locSDK_Demo1", "... Application onCreate... pid=" + Process.myPid());
 		
 		Constant.initSkinDir(this);		
		initSkins();
	}
	
	public  void initSkins(){
		 
		  String skinPack = Constant.getAppSkin(this);		
		  if(!StringUtils.isNull(skinPack)){			  		
		      SkinManager.setSkinContext(this, skinPack);		    
		  }
	
	}
	
	public void initEngineManager(Context context) {
		// TODO Auto-generated method stub
        if (mBMapManager == null) {
            mBMapManager = new BMapManager(context);
        }
//        mBMapManager = new BMapManager(context);
        if (!mBMapManager.init(strKey,new MyGeneralListener())) {
            Toast.makeText(JpushApplication.getInstance().getApplicationContext(), 
                    "BMapManager  初始化错误!", Toast.LENGTH_LONG).show();
        }		
	}
	
	public static JpushApplication getInstance() {
		return mInstance;
	}
	
	public static JpushApplication getApplication(){
		return mInstance;
	}
	
	// 常用事件监听，用来处理通常的网络错误，授权验证错误等
    public static class MyGeneralListener implements MKGeneralListener {
        
        @Override
        public void onGetNetworkState(int iError) {
            if (iError == MKEvent.ERROR_NETWORK_CONNECT) {
                Toast.makeText(JpushApplication.getInstance().getApplicationContext(), "正在获取网络",
                    Toast.LENGTH_LONG).show();
            }
            else if (iError == MKEvent.ERROR_NETWORK_DATA) {
                Toast.makeText(JpushApplication.getInstance().getApplicationContext(), "输入正确的检索条件！",
                        Toast.LENGTH_LONG).show();
            }
            // ...
        }

        @Override
        public void onGetPermissionState(int iError) {
            if (iError ==  MKEvent.ERROR_PERMISSION_DENIED) {
                //授权Key错误：
                Toast.makeText(JpushApplication.getInstance().getApplicationContext(), 
                        "请在 DemoApplication.java文件输入正确的授权Key！", Toast.LENGTH_LONG).show();
                JpushApplication.getInstance().m_bKeyRight = false;
            }
        }
    }
	
	public JSONObject getJson4weather() {
		return json4weather;
	}

	public void setJson4weather(JSONObject json4weather) {
		this.json4weather = json4weather;
	}

	public JSONObject getJson4travle_forcast() {
		return json4travle_forcast;
	}

	public void setJson4travle_forcast(JSONObject json4travle_forcast) {
		this.json4travle_forcast = json4travle_forcast;
	}

	public JSONObject getJson4travle_live() {
		return json4travle_live;
	}

	public void setJson4travle_live(JSONObject json4travle_live) {
		this.json4travle_live = json4travle_live;
	}

	public JSONObject getJson4travle_warning() {
		return json4travle_warning;
	}

	public void setJson4travle_warning(JSONObject json4travle_warning) {
		this.json4travle_warning = json4travle_warning;
	}
	

}

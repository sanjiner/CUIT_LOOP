package cuit.emergency;

import java.util.ArrayList;
import java.util.List;

import com.bairuitech.anychat.AnyChatBaseEvent;
import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatDefine;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ListView;
import android.widget.TextView;

public class WaitActivity extends Activity implements AnyChatBaseEvent {
	public static boolean finish=false;

	private ListView mRoleList;
	
	private String   mStrIP = "222.18.158.220";
	private String   mStrName = "name";
	private int      mSPort = 8906;
	private int      mSRoomID = 1;
	
	private final int LOCALVIDEOAUTOROTATION = 0; //本地视频自动旋转控制
	
	private List<RoleInfo> mRoleInfoList = new ArrayList<RoleInfo>();
//	private RoleListAdapter mAdapter;

	static boolean bNeedRelease = false;
	private int UserselfID;
	
	public AnyChatCoreSDK 	anyChatSDK;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(!finish)
		{
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_wait);
		
		InitSDK();
		AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_LOCALVIDEO_AUTOROTATION, 1);
		
		TextView tv = (TextView)findViewById(R.id.mainUITitle);
		Animation animation = AnimationUtils.loadAnimation(this, R.anim.text_viewwave);
		tv.startAnimation(animation);

		
		
		Intent intent=getIntent();
		
		mSRoomID = Integer.parseInt(intent.getStringExtra("roomID"));
		mStrName = intent.getStringExtra("Name");
		mStrIP = intent.getStringExtra("Ip");
		mSPort = Integer.parseInt(intent.getStringExtra("Port"));
		
		anyChatSDK.Connect(mStrIP, mSPort);
		anyChatSDK.Login(mStrName, "");
		
		
//
//		readLoginDate();

        registerBoradcastReceiver();  
		}
		else
		{
			finish();
			finish=false;
		}
	}

	private void InitSDK() {
		if (anyChatSDK == null) {
			anyChatSDK = AnyChatCoreSDK.getInstance(this);
			anyChatSDK.SetBaseEvent(this);
			anyChatSDK.InitSDK(android.os.Build.VERSION.SDK_INT, 0);
			
			// 视频采集驱动设置
			AnyChatCoreSDK.SetSDKOptionInt(
					AnyChatDefine.BRAC_SO_LOCALVIDEO_CAPDRIVER, AnyChatDefine.VIDEOCAP_DRIVER_JAVA);
			// 视频显示驱动设置
			AnyChatCoreSDK.SetSDKOptionInt(
					AnyChatDefine.BRAC_SO_VIDEOSHOW_DRIVERCTRL, AnyChatDefine.VIDEOSHOW_DRIVER_JAVA);
			// 音频播放驱动设置
			AnyChatCoreSDK.SetSDKOptionInt(
					AnyChatDefine.BRAC_SO_AUDIO_PLAYDRVCTRL, AnyChatDefine.AUDIOPLAY_DRIVER_JAVA);
			// 音频采集驱动设置
			AnyChatCoreSDK.SetSDKOptionInt(
					AnyChatDefine.BRAC_SO_AUDIO_RECORDDRVCTRL, AnyChatDefine.AUDIOREC_DRIVER_JAVA);
			AnyChatCoreSDK.SetSDKOptionInt(
					AnyChatDefine.BRAC_SO_LOCALVIDEO_AUTOROTATION, LOCALVIDEOAUTOROTATION);

			bNeedRelease = true;
		}
	}


//	//读取登陆数据
//	private void readLoginDate()
//	{
//		SharedPreferences preferences = getSharedPreferences("LoginInfo", 0);
//		mStrIP = preferences.getString("UserIP", "222.18.158.220");
//		mStrName = preferences.getString("UserName", "name");
//		mSPort = preferences.getInt("UserPort", 8906);
//		mSRoomID = preferences.getInt("UserRoomID", 1);
//	}
//	


//	private void hideKeyboard() {
//		InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
//		if (imm.isActive()) {
//			imm.hideSoftInputFromWindow(getCurrentFocus()
//					.getApplicationWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
//		}
//	}

	
	
	
	protected void onDestroy() {
	
		if (bNeedRelease) {
			anyChatSDK.LeaveRoom(-1);
			anyChatSDK.Logout();
			anyChatSDK.Release();
			bNeedRelease=false;
		}
	    finish();
		super.onDestroy();
	}

	protected void onResume() {
		super.onResume();
	}
	

	@Override
	protected void onRestart() {
		// TODO Auto-generated method stub
		super.onRestart();
		anyChatSDK.SetBaseEvent(this);
	}

	@Override
	public void OnAnyChatConnectMessage(boolean bSuccess) {
		if (!bSuccess) {

		}
	}

	@Override
	public void OnAnyChatLoginMessage(int dwUserId, int dwErrorCode) {
		if (dwErrorCode == 0) {

//			hideKeyboard();

			bNeedRelease = false;
			
			
			anyChatSDK.EnterRoom(mSRoomID, "");
			
			UserselfID = dwUserId;
			// finish();
		} else {
		}
	}

	@Override
	public void OnAnyChatEnterRoomMessage(int dwRoomId, int dwErrorCode) {
		System.out.println("OnAnyChatEnterRoomMessage" +dwRoomId +"err:"+dwErrorCode);
	}

	@Override
	public void OnAnyChatOnlineUserMessage(int dwUserNum, int dwRoomId) {
		mRoleInfoList.clear();
		int[] userID = anyChatSDK.GetOnlineUser();
		RoleInfo userselfInfo = new RoleInfo();
		userselfInfo.setName(anyChatSDK.GetUserName(UserselfID)+"(自己)");
		userselfInfo.setUserID(String.valueOf(UserselfID));
		mRoleInfoList.add(userselfInfo);
		
		
		for (int index = 0; index < userID.length; ++index) {
			RoleInfo info = new RoleInfo();
			info.setName(anyChatSDK.GetUserName(userID[index]));
			info.setUserID(String.valueOf(userID[index]));
			mRoleInfoList.add(info);
			}
	
		
		if(mRoleInfoList.size()>1)
		{
			String strUserID = mRoleInfoList.get(1).getUserID();
			Intent intent = new Intent();
			intent.putExtra("UserID", strUserID);
			intent.setClass(this, VideoActivity.class);
			startActivity(intent);
		}
		else{
			while(true)
			{
			userID = anyChatSDK.GetOnlineUser();

			for (int index = 0; index < userID.length; ++index) {
				RoleInfo info = new RoleInfo();
				info.setName(anyChatSDK.GetUserName(userID[index]));
				info.setUserID(String.valueOf(userID[index]));
				mRoleInfoList.add(info);
				}
			if(mRoleInfoList.size()>1)
			{
				String strUserID = mRoleInfoList.get(1).getUserID();
				Intent intent = new Intent();
				intent.putExtra("UserID", strUserID);
				intent.setClass(this, VideoActivity.class);
				startActivity(intent);
				break;
			}
			}
		}
	
		

	}

	

	@Override
	public void OnAnyChatUserAtRoomMessage(int dwUserId, boolean bEnter) {
		if (bEnter) {
			RoleInfo info = new RoleInfo();
			info.setUserID(String.valueOf(dwUserId));
			info.setName(anyChatSDK.GetUserName(dwUserId));
			mRoleInfoList.add(info);
		} else {

			for (int i = 0; i < mRoleInfoList.size(); i++) {
				if (mRoleInfoList.get(i).getUserID().equals("" + dwUserId)) {
					mRoleInfoList.remove(i);
				}
			}
		}
	}

	@Override
	public void OnAnyChatLinkCloseMessage(int dwErrorCode) {
		mRoleList.setAdapter(null);
	}
	
	//广播
	private BroadcastReceiver mBroadcastReceiver = new BroadcastReceiver(){  
        @Override  
        public void onReceive(Context context, Intent intent) {  
            String action = intent.getAction();  
            if(action.equals("VideoActivity")){  
        		mRoleList.setAdapter(null);
            }  
        }  
    }; 
    
    public void registerBoradcastReceiver(){  
        IntentFilter myIntentFilter = new IntentFilter();  
        myIntentFilter.addAction("VideoActivity");  
        //注册广播        
        registerReceiver(mBroadcastReceiver, myIntentFilter);  
    }  
}

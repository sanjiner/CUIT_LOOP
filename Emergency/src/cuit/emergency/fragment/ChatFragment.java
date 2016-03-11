package cuit.emergency.fragment;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.drawable.AnimationDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import cc.util.android.core.BaseFragment;
import cc.util.android.core.SharedPreferenceUtil;
import cc.util.android.core.ToastUtil;
import cc.util.android.image.BitmapHelper;
import cc.util.android.view.PullListView;
import cc.util.android.viewInject.ResInject;
import cc.util.android.viewInject.ViewInjectUtil;
import cc.util.android.viewInject.ViewListenerInject;
import cc.util.android.viewInject.ViewListenerType;
import cc.util.java.core.ConcreteObservable;
import cc.util.java.core.Observer;

import com.bairuitech.anychat.AnyChatCoreSDK;
import com.bairuitech.anychat.AnyChatDefine;


import cuit.emergency.R;
import cuit.emergency.WaitActivity;
import cuit.emergency.adapter.ChatAdapter;
import cuit.emergency.tool.ChatOffline;
import cuit.emergency.tool.DBHelper;
import cuit.emergency.tool.Recorder;
import cuit.emergency.tool.SmackerHelper;
import cuit.emergency.util.Constants;
import cuit.emergency.util.ToolUtil;

public class ChatFragment extends BaseFragment implements Observer {
	public AnyChatCoreSDK anychat;
	public final static String ROSTER = "roster";
	public enum MessageType { TEXT, VOICE, PICTURE };
	
	@ResInject(R.id.chat_listView)
	PullListView mPullListView;
	private List<Map<String, String>> mMsgList;
	private ChatAdapter mAdapter;
	public String RoomID="0";
   
	//更多
	@ResInject(R.id.chat_more_linear)
	private LinearLayout mlinearMore;
	
	@ResInject(R.id.chat_edit)
	EditText medtMsg;

	//录音
	@ResInject(R.id.chat_voice_button)
	Button btnVoiceLong;
	@ResInject(R.id.record_view_linear)
	LinearLayout recordLayout;
	@ResInject(R.id.record_view_image)
	ImageView recordImage;
	private AnimationDrawable animationDrawable;
//	private AlertDialog dialog;
//	private AlertDialog dialog2;
	private AlertDialog videoDialog;
	
	private ChatOffline mChatOffline;
	private final Handler mHandler = new Handler();
	
	private HashMap<String, String> rosterInfo;
	static boolean Video=false;
	//图片
	private String picPath = "";
	private static final int GET_PIC_FROM_LOCAL = 0;
	private static final int GET_PIC_FROM_CAMERA = 1;
	
	//音频
	private String voicePath = "";
	private Recorder mRecorder;
	private double mRecorderStartTime = 0.0;  //录音开始时间
	ProgressDialog mDialog;
	@SuppressWarnings("unchecked")
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ConcreteObservable.newInstance().registerObserver(this);
		rosterInfo = (HashMap<String, String>)getArguments().getSerializable(ROSTER);
		
		mMsgList = new ArrayList<Map<String,String>>();
		mAdapter = new ChatAdapter(getActivity(), mMsgList);
		mAdapter.setFragment(this);
		mChatOffline = new ChatOffline(getActivity());
//		creatDialog();
		if(Video)
		{
			Isopenvideo("ROPEN");
			Video=false;
		}
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_chat, container, false);
		ViewInjectUtil.inject(this, view);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mPullListView.setPullLoadEnable(false);
		mPullListView.setAdapter(mAdapter);
		animationDrawable = (AnimationDrawable) recordImage.getBackground();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mChatOffline.updateToReaded(rosterInfo.get(DBHelper.C_CHAT_ROSTER));
				ConcreteObservable.newInstance().notifyObserver(MessageFragment.class, Constants.NEW_MESSAGE);
			}
		}, 2 * 1000);
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (!hidden) {
			mActionBar.setLeftViewResourceId(R.drawable.selector_back);
			mActionBar.setTitle(rosterInfo.get(DBHelper.R_NICKNAME));
			
		} else {
			mActionBar.hideLeftView();
		}
	}
	
	@Override
	public void onActivityResult(int request, int response, Intent intent) {
		// TODO Auto-generated method stub
		super.onActivityResult(request, response, intent);
		mlinearMore.setVisibility(mlinearMore.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE);
		switch (request) {
		case GET_PIC_FROM_CAMERA:
			if (response == Activity.RESULT_OK) {
				new AsyncBitmapTask().execute(picPath);
			}
			break;
		default:
			if (response == Activity.RESULT_OK) {
				Uri originalUri = intent.getData(); // 获得图片的uri
				String[] proj = { MediaStore.Images.Media.DATA };
				Cursor cursor = getActivity().getContentResolver().query(originalUri, proj, null, null, null);
				int column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
				cursor.moveToFirst();
				picPath = cursor.getString(column_index);
				new AsyncBitmapTask().execute(picPath);
			}
			break;
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ConcreteObservable.newInstance().unRegisterObserver(this);
	}

	@ViewListenerInject(value = {R.id.chat_listView}, type = ViewListenerType.onPullRefresh)
	void onPullRefresh(PullListView view) {
		new AsyncQuery().execute();
	}

	@ViewListenerInject(value = {R.id.chat_listView}, type = ViewListenerType.onPullLoadMore)
	void onPullLoadMore(PullListView view) {
	}
	public void sendSingleMessage(String Message){
		Map<String, String> tempMapvidio = new HashMap<String, String>();
		tempMapvidio.put(DBHelper.C_PACKET_ID, "");
		tempMapvidio.put(DBHelper.C_CUR_ROSTER, SharedPreferenceUtil.getPrefString(getActivity(), Constants.UNAME, ""));
		tempMapvidio.put(DBHelper.C_CHAT_ROSTER, rosterInfo.get(DBHelper.C_CHAT_ROSTER));
		tempMapvidio.put(DBHelper.C_CHAT_NICKNAME, rosterInfo.get(DBHelper.C_CHAT_NICKNAME));
		tempMapvidio.put(DBHelper.C_MSG_TIME, ToolUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		tempMapvidio.put(DBHelper.C_MSG, Message);
		tempMapvidio.put(DBHelper.C_MSG_PATH, "");
		tempMapvidio.put(DBHelper.C_MSG_FROM, DBHelper.C_MSG_FROM_ME + "");
		tempMapvidio.put(DBHelper.C_MSG_TYPE, Constants.MSG_TYPE_CHAT);
//		mMsgList.add(tempMapvidio);
//		mAdapter.notifyDataSetChanged();
//		mPullListView.setSelection(mPullListView.getBottom());
		SmackerHelper.newInstance(getActivity()).sendMessage(tempMapvidio);
		picPath = "";
		voicePath = "";
		medtMsg.setText("");
		
	}
	@ViewListenerInject(value = {R.id.chat_more_button, R.id.chat_more_from_local, R.id.chat_more_from_camera,
			R.id.chat_send_button}, type = ViewListenerType.OnClick)
	void onClick(View view) {
		switch (view.getId()) {
		case R.id.chat_more_button:
			mlinearMore.setVisibility(mlinearMore.getVisibility() == View.VISIBLE ?
					View.GONE : View.VISIBLE);
			break;
		case R.id.chat_more_from_local:
			Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
			intent.setType("image/*");
			startActivityForResult(intent, GET_PIC_FROM_LOCAL);
			break;
		case R.id.chat_more_from_camera:
//			takePhoto();
//			Intent itent = new Intent(this.getActivity(),Login2Activity.class);
//			 
//			startActivity(itent);
			
////			String wodeid="123";
//			String bierenid=rosterInfo.get(DBHelper.C_CHAT_ROSTER);
//			String c=bierenid.substring(0, 3);
//			RoomID=SharedPreferenceUtil.getPrefString(getActivity(), "EnterRoomID", "");
//			if(RoomID.equals(c))
//			{
//				SharedPreferenceUtil.setPrefString(getActivity(), "EnterRoomID", RoomID);
//			}
//			else{
//			sendSingleMessage("@&+%z"+wodeid);
//			mAdapter.notifyDataSetChanged();
//			mPullListView.setSelection(mPullListView.getBottom());
//			SharedPreferenceUtil.setPrefString(getActivity(), "EnterRoomID", wodeid);
//			}
			
		
			
//			sendSingleMessage("@&+%zROPEN"+wodeid+"");
			sendSingleMessage("@&+%zROPEN");
			Isopenvideo("WOPEN");
			
		
			mAdapter.notifyDataSetChanged();
			mPullListView.setSelection(mPullListView.getBottom());
//			SmackerHelper.newInstance(getActivity()).sendMessage(msgMap)
			
			
			break;
		case R.id.chat_send_button:
			if (!TextUtils.equals(medtMsg.getText().toString(), ""))
				dealMessage(MessageType.TEXT);
			break;
		}
	}
	
	@ViewListenerInject(value = {R.id.chat_voice_button}, type = ViewListenerType.OnTouch)
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		switch (event.getAction()) {
		case MotionEvent.ACTION_UP:
			stopRecorder();
			break;
		case MotionEvent.ACTION_DOWN:
			startRecorder();
			break;
		default:
			break;
		}
		return false;
	}

	@ViewListenerInject(value  = {R.id.chat_voice_button}, type = ViewListenerType.OnLongClick)
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		return false;
	}
	
	@ViewListenerInject(value = {R.id.chat_keyboard_checkBox}, type = ViewListenerType.OnCheckedChange)
	void onCheckedChanged(CompoundButton arg0, boolean arg1) {
		switch (arg0.getId()) {
		case R.id.chat_keyboard_checkBox:
			medtMsg.setVisibility(arg1 ? View.GONE: View.VISIBLE);
			btnVoiceLong.setVisibility(arg1 ? View.VISIBLE: View.GONE);
			break;
		default:
			break;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public void update(Object... objs) {
		// TODO Auto-generated method stub
		if (objs[0].equals(Constants.NEW_MESSAGE)) {
			Map<String, String> msgMap = (Map<String, String>) objs[1];
			if (msgMap.get(DBHelper.C_CHAT_ROSTER).equals(rosterInfo.get(DBHelper.C_CHAT_ROSTER))) {
				msgMap.put(DBHelper.C_MSG_STATUS, DBHelper.C_MSG_READED+"");
				ConcreteObservable.newInstance().notifyObserver(MessageFragment.class, Constants.NEW_MESSAGE);
			}
			String textMessage=msgMap.get("msg")+"";
			if(textMessage.length()>=5){
			String VidioFlage=textMessage.substring(0,5);
			mAdapter.notifyDataSetChanged();
			mPullListView.setSelection(mPullListView.getBottom());
			if(VidioFlage.equals("@&+%z"))
			{ 
				if(textMessage.substring(5,10).equals("AOPEN"))
				{
					RoomID=textMessage.substring(10);
					SharedPreferenceUtil.setPrefString(getActivity(), "EnterRoomID", RoomID);
					sendSingleMessage("视频消息");
					
					openvideo();
					videoDialog.dismiss();
//					dialog2.cancel();
//					videoDialog.cancel();
					
					
				}
				else if(textMessage.substring(5,10).equals("ACLSE"))
				{
					videoDialog.dismiss();
//					dialog.cancel();
//					dialog2.cancel();
				}
				else if(textMessage.substring(5,10).equals("ROPEN"))
				{
//					RoomID=textMessage.substring(10);
//					SharedPreferenceUtil.setPrefString(getActivity(), "EnterRoomID", RoomID);
					Isopenvideo("ROPEN");
				
					
				}
				else if(textMessage.substring(5,10).equals("RCLSE"))
				{
					videoDialog.dismiss();
					sendSingleMessage("视频被取消啦");
//					dialog.cancel();
//					dialog2.cancel();
				}
				mAdapter.notifyDataSetChanged();
				mPullListView.setSelection(mPullListView.getBottom());
				
//				RoomID=textMessage.substring(5);
//				SharedPreferenceUtil.setPrefString(getActivity(), "EnterRoomID", RoomID);
//				sendSingleMessage("您的视频消息");
				
				}
			else {
				mChatOffline.insert(msgMap);
				mMsgList.add(msgMap);
				mAdapter.notifyDataSetChanged();
				mPullListView.setSelection(mPullListView.getBottom());
				}
			}
			else {
			mChatOffline.insert(msgMap);
			mMsgList.add(msgMap);
			mAdapter.notifyDataSetChanged();
			mPullListView.setSelection(mPullListView.getBottom());
			}
		}
	}
	public void openvideo(){
		
		
//		configEntity = ConfigService.LoadConfig(this.getActivity());
//		anychat = new AnyChatCoreSDK();
//		if (configEntity.useARMv6Lib != 0)
//			AnyChatCoreSDK.SetSDKOptionInt(AnyChatDefine.BRAC_SO_CORESDK_USEARMV6LIB, 1);
//		anychat.InitSDK(android.os.Build.VERSION.SDK_INT, 0);
//		this.anychat.Connect(configEntity.ip, configEntity.port);
//		
//		this.anychat.Login( SharedPreferenceUtil.getPrefString(this.getActivity(),"uname", "")+"",  SharedPreferenceUtil.getPrefString(this.getActivity(),"upasswd", ""));
	
		
		
		Intent itent = new Intent(this.getActivity(),WaitActivity.class);
		
		
		
		itent.putExtra("roomID", SharedPreferenceUtil.getPrefString(getActivity(), "EnterRoomID",""));
		itent.putExtra("Ip", Constants.DEFAULT_SERVER);
		itent.putExtra("Port", "8906");
		itent.putExtra("Name", SharedPreferenceUtil.getPrefString(this.getActivity(),"uname", "")+"");
		startActivity(itent);
	}
	
	private void creatRopenDialog(String string){
		
		videoDialog = new AlertDialog.Builder(getActivity()).create();
		videoDialog.show();
		videoDialog.setCancelable(false);
		Window window = videoDialog.getWindow();
		window.setContentView(R.layout.dialog_vidio_ask);
		
		if(string.equals("ROPEN"))
		{
		ImageButton ok=(ImageButton) window.findViewById(R.id.btn_ok);
		ok.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				long roomid=Math.round(Math.random()*8999+1000);
				RoomID=roomid+"";
				SharedPreferenceUtil.setPrefString(getActivity(), "EnterRoomID", RoomID);
				sendSingleMessage("@&+%zAOPEN"+RoomID);//打开视频回复AOPEN
				openvideo();
				
				
				
				mAdapter.notifyDataSetChanged();
				mPullListView.setSelection(mPullListView.getBottom());
				videoDialog.dismiss();
				// TODO Auto-generated method stub
				
			}
		});
		ImageButton cancel =(ImageButton)window.findViewById(R.id.btn_cancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendSingleMessage("@&+%zRCLSE");
				mAdapter.notifyDataSetChanged();
				mPullListView.setSelection(mPullListView.getBottom());
				videoDialog.dismiss();
				}
			});
		}
		else if(string.equals("WOPEN")){
			ImageButton ok=(ImageButton) window.findViewById(R.id.btn_ok);
			ok.setVisibility(View.GONE);
		
		ImageButton cancel =(ImageButton)window.findViewById(R.id.btn_cancel);
		cancel.setOnClickListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				sendSingleMessage("@&+%zACLSE");
				mAdapter.notifyDataSetChanged();
				mPullListView.setSelection(mPullListView.getBottom());
				videoDialog.dismiss();    
				}
			});
		}
		else{

			videoDialog.dismiss();
		}
	}
	
	
	
//	private void creatDialog(){
//		
//	
//		
//		dialog = new AlertDialog.Builder(getActivity())
//		.setTitle("视频请求")
//		.setItems(new String[] { "接听", "挂断" }, new OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				if(which==0){
//					long roomid=Math.round(Math.random()*8999+1000);
//					RoomID=roomid+"";
//					SharedPreferenceUtil.setPrefString(getActivity(), "EnterRoomID", RoomID);
//					sendSingleMessage("@&+%zAOPEN"+RoomID);//打开视频回复AOPEN
//					openvideo();
//					
//					
//					
//					mAdapter.notifyDataSetChanged();
//					mPullListView.setSelection(mPullListView.getBottom());
//				}
//				else{
//					sendSingleMessage("@&+%zACLSE");
//					mAdapter.notifyDataSetChanged();
//					mPullListView.setSelection(mPullListView.getBottom());
//				}
//				
//			}
//		}).create();
//		//dialog.setCanceledOnTouchOutside(false);
//		dialog.setCancelable(false);
//		
//		dialog2 = new AlertDialog.Builder(getActivity())
//		.setTitle("视频请求")
//		.setItems(new String[] { "挂断"}, new OnClickListener() {
//			
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				// TODO Auto-generated method stub
//				sendSingleMessage("@&+%zRCLSE");//打开视频回复AOPEN
//				mAdapter.notifyDataSetChanged();
//				mPullListView.setSelection(mPullListView.getBottom());
//			}
//		}).create();
//		dialog2.setCancelable(false);
//	}
	
	public void Isopenvideo(String string){
		
		if(string.equals("ROPEN"))
		{
			creatRopenDialog("ROPEN");
//			dialog.show();
		}
		else if(string.equals("WOPEN")){
//			dialog2.show();
			creatRopenDialog("WOPEN");
		}
	}
	
	/* 拍照 */
	protected void takePhoto() {
		// TODO Auto-generated method stub
		Intent imageCaptureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
		String fileName = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date())+ ".png";// 照片命名
		picPath = ToolUtil.getIMGCachePath(getActivity())+fileName;
		File file = new File(picPath);
		Uri uri = Uri.fromFile(file);
		imageCaptureIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
		imageCaptureIntent.putExtra(MediaStore.EXTRA_VIDEO_QUALITY, 1);
		startActivityForResult(imageCaptureIntent, GET_PIC_FROM_CAMERA);
	}
	
	/**
	 *  封装发送信息
	 * @param type
	 * @return
	 */
	public Map<String, String> encloseMsg(MessageType type) {
		Map<String, String> tempMap = new HashMap<String, String>();
		tempMap.put(DBHelper.C_PACKET_ID, "");
		tempMap.put(DBHelper.C_CUR_ROSTER, SharedPreferenceUtil.getPrefString(getActivity(), Constants.UNAME, ""));
		tempMap.put(DBHelper.C_CHAT_ROSTER, rosterInfo.get(DBHelper.C_CHAT_ROSTER));
		tempMap.put(DBHelper.C_CHAT_NICKNAME, rosterInfo.get(DBHelper.C_CHAT_NICKNAME));
		tempMap.put(DBHelper.C_MSG_TIME, ToolUtil.formatDate(new Date(), "yyyy-MM-dd HH:mm:ss"));
		if (type == MessageType.TEXT) {
			tempMap.put(DBHelper.C_MSG, medtMsg.getText().toString());
			tempMap.put(DBHelper.C_MSG_PATH, "");
		} else if (type == MessageType.PICTURE) {
			byte[] bts = ToolUtil.getFileBytes(new File(picPath));
			String base64 = ToolUtil.bytesToString(bts);
			tempMap.put(DBHelper.C_MSG, ToolUtil.strToFile(base64));
			tempMap.put(DBHelper.C_MSG_PATH, picPath);
		} else {
			tempMap.put(DBHelper.C_MSG, ToolUtil.strToVoice(ToolUtil.bytesToBinary(new File(voicePath))));
			tempMap.put(DBHelper.C_MSG_PATH, voicePath);
			tempMap.put(DBHelper.C_MSG_DURA, mRecorderStartTime+"");
		}
		tempMap.put(DBHelper.C_MSG_FROM, DBHelper.C_MSG_FROM_ME + "");
		tempMap.put(DBHelper.C_MSG_TYPE, Constants.MSG_TYPE_CHAT);
		return tempMap;
	}
	
	//将消息封装后插入数据库保存并send消息
	private void dealMessage(MessageType type) {
		final Map<String, String> msgMap = encloseMsg(type);
		mMsgList.add(msgMap);
		mAdapter.notifyDataSetChanged();
		mPullListView.setSelection(mPullListView.getBottom());
		SmackerHelper.newInstance(getActivity()).sendMessage(msgMap);
		picPath = "";
		voicePath = "";
		medtMsg.setText("");
	}
	
	//开始录音
	private void startRecorder() {
		if (mRecorder == null) mRecorder = new Recorder();
		if (mRecorderStartTime != 0 && System.currentTimeMillis() - mRecorderStartTime < 350) return;
		recordLayout.setVisibility(View.VISIBLE);
		animationDrawable.start();
		mRecorderStartTime = System.currentTimeMillis();
		String fileName = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA).format(new Date());
		voicePath = ToolUtil.getVOICECachePath(getActivity()) + fileName + ".amr";
		mRecorder.start(ToolUtil.getVOICECachePath(getActivity()), fileName);
	}
			
	//停止录音
	private void stopRecorder() {
		if (mRecorder == null) return;
		animationDrawable.stop();
		recordLayout.setVisibility(View.GONE);
		mRecorder.stop();
		if ((mRecorderStartTime = System.currentTimeMillis() - mRecorderStartTime) < 350) {
			ToastUtil.showShort(getActivity(), "录音时间太短");
			return;
		}
		dealMessage(MessageType.VOICE);
	}
	
	//异步刷新历史记录
	private class AsyncQuery extends AsyncTask<Void, Void, List<Map<String, String>>> {
			
		@Override
		protected List<Map<String, String>> doInBackground(Void... params) {
			return mChatOffline.selectChatMsg(rosterInfo.get(DBHelper.R_ROSTER), mMsgList.size());
		}

		@Override
		protected void onPostExecute(List<Map<String, String>> result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			mMsgList.addAll(result);
			Collections.sort(mMsgList, new Comparator<Map<String, String>>() {
				@Override
				public int compare(Map<String, String> arg0,
						Map<String, String> arg1) {
					// TODO Auto-generated method stub
					return arg0.get(DBHelper.C_MSG_TIME).compareTo(arg1.get(DBHelper.C_MSG_TIME));
				}
			});
			mAdapter.notifyDataSetChanged();
			mPullListView.stopRefresh();
			if (result.size() != 0 && mMsgList.size() <= 15)
				mPullListView.setSelection(mPullListView.getBottom());
		}
	}
		
	/* 压缩图片 */
	private class AsyncBitmapTask extends AsyncTask<String, Void, String> {
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			super.onPreExecute();
			if (mDialog == null) {
				mDialog = ProgressDialog.show(getActivity(), null, "正在处理图片...", false);
				mDialog.setCancelable(false);
			}
			mDialog.show();
		}

		@Override
		protected String doInBackground(String... params) {
			// TODO Auto-generated method stub
			String path = params[0];
			return BitmapHelper.compressBitmap(getActivity(), path, 128, 128, true);
		}

		@Override
		protected void onPostExecute(String result) {
			// TODO Auto-generated method stub
			super.onPostExecute(result);
			if (mDialog != null && mDialog.isShowing()) mDialog.dismiss();
			if (result == null || result.equals("")) {
				ToastUtil.showShort(getActivity(), "图片处理失败");
				return;
			}
			picPath = result;
			dealMessage(MessageType.PICTURE);
		}
	}
}

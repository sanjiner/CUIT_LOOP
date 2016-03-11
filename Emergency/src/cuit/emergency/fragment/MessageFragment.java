package cuit.emergency.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import cuit.emergency.R;
import cuit.emergency.adapter.MessageAdapter;
import cuit.emergency.tool.ChatOffline;
import cuit.emergency.tool.DBHelper;
import cuit.emergency.util.Constants;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import cc.util.android.core.BaseFragment;
import cc.util.android.core.FragmentInfo;
import cc.util.android.core.FragmentInfoActivity;
import cc.util.android.core.SharedPreferenceUtil;
import cc.util.android.viewInject.ResInject;
import cc.util.android.viewInject.ViewInjectUtil;
import cc.util.android.viewInject.ViewListenerInject;
import cc.util.android.viewInject.ViewListenerType;
import cc.util.java.core.ConcreteObservable;
import cc.util.java.core.Observer;

public class MessageFragment extends BaseFragment implements OnRefreshListener, Observer {

	@ResInject(R.id.message_swiperefresh)
	SwipeRefreshLayout mSwipeRefreshLayout;
	@ResInject(R.id.message_listview)
	ListView mListView;
	
	private List<Map<String, String>> mallList;
	private MessageAdapter mAdapter;

	private ChatOffline mChatOffline;
	private Handler mHandler = new Handler();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ConcreteObservable.newInstance().registerObserver(this);
		mChatOffline = new ChatOffline(getActivity());
		mallList = new ArrayList<Map<String,String>>();
		mAdapter = new MessageAdapter(getActivity(), mallList);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_message, container, false);
		ViewInjectUtil.inject(this, view);
		mSwipeRefreshLayout.setOnRefreshListener(this);
		mSwipeRefreshLayout.setRefreshing(true);
		return view;
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		mListView.setAdapter(mAdapter);
		selectMsgAndnotify();
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (!hidden){
			mActionBar.setTitle("消息");
			mActionBar.hideRightView();
		}
	}
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ConcreteObservable.newInstance().unRegisterObserver(this);
	}
	
	@ViewListenerInject(value = {R.id.message_listview}, type = ViewListenerType.OnItemClick)
	public void onItemClick(final AdapterView<?> parent, View view, final int position,
			long id) {
		HashMap<String, String> map = (HashMap<String, String>) mallList.get(position);
		Bundle bundle = new Bundle();
		FragmentInfo info = null;
		if (map.get(DBHelper.C_MSG_TYPE).equals(Constants.MSG_TYPE_CHAT)) {
			
			if(map.get(DBHelper.C_MSG).length()>=10)
			{
			if(map.get(DBHelper.C_MSG).subSequence(0, 10).equals("@&+%zROPEN")){
			
				ChatFragment.Video=true;
				bundle.putSerializable(ChatFragment.ROSTER, map);
				info = new FragmentInfo(ChatFragment.class, bundle);
			}
			else{
				bundle.putSerializable(ChatFragment.ROSTER, map);
				info = new FragmentInfo(ChatFragment.class, bundle);
			}
			}
			else{
			bundle.putSerializable(ChatFragment.ROSTER, map);
			info = new FragmentInfo(ChatFragment.class, bundle);
			}
		} else {
			bundle.putString(MultiChatFragment.ROOM_ID, map.get(DBHelper.C_CHAT_ROSTER));
			bundle.putString(MultiChatFragment.ROOM_NAME, map.get(DBHelper.C_CHAT_NICKNAME));
			info = new FragmentInfo(MultiChatFragment.class, bundle);
		}
		FragmentInfoActivity.startFragment(MessageFragment.this, info);
	}
	
	@ViewListenerInject(value = {R.id.message_listview}, type = ViewListenerType.OnItemLongClick)
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, final int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		new AlertDialog.Builder(getActivity())
		.setItems(new String[]{"删除"}, new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				// TODO Auto-generated method stub
				if (which == 0) {
					mallList.remove(arg2);
					String message_roster = "";
					for (int i = 0; i < mallList.size(); i++) {
						message_roster += i > 0 ? ","+mallList.get(i).get(DBHelper.C_CHAT_ROSTER) : 
							mallList.get(i).get(DBHelper.C_CHAT_ROSTER);
					}
					SharedPreferenceUtil.setPrefString(getActivity(), Constants.MESSAGE_ROSTER, message_roster);
					mAdapter.notifyDataSetChanged();
					dialog.dismiss();
				}
			}
		}).show();
		return false;
	}

	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		selectMsgAndnotify();
	}

	@Override
	public void update(Object... objs) {
		// TODO Auto-generated method stub
		selectMsgAndnotify();
	}
	
	//获取数据并刷新
	private void selectMsgAndnotify() {
		mallList.clear();
		mallList.addAll(mChatOffline.selectUnReadedMsg());
		mHandler.post(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				mAdapter.notifyDataSetChanged();
				mSwipeRefreshLayout.setRefreshing(false);
			}
		});
	}
}

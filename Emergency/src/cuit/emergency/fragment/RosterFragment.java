package cuit.emergency.fragment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.SimpleExpandableListAdapter;
import android.widget.TextView;
import cc.util.android.core.BaseFragment;
import cc.util.android.core.FragmentInfo;
import cc.util.android.core.FragmentInfoActivity;
import cc.util.android.view.InnerExpandableListView;
import cc.util.android.view.InnerListView;
import cc.util.android.viewInject.ResInject;
import cc.util.android.viewInject.ViewInjectUtil;
import cc.util.android.viewInject.ViewListenerInject;
import cc.util.android.viewInject.ViewListenerType;
import cc.util.java.core.ConcreteObservable;
import cc.util.java.core.Observer;
import cuit.emergency.R;
import cuit.emergency.adapter.RosterAdapter;
import cuit.emergency.adapter.RosterRoomAdapter;
import cuit.emergency.tool.DBHelper;
import cuit.emergency.tool.RoomOffline;
import cuit.emergency.tool.RosterOffline;
import cuit.emergency.tool.SmackerHelper;
import cuit.emergency.util.Constants;

public class RosterFragment extends BaseFragment implements OnRefreshListener, Observer {
	
	private int FLAG = 0;  //0:联系人 1:群组
	private Map<Integer,Integer> show;
	private List<String> SecondGroup;
	private int count;

	@ResInject(R.id.roster_swiperefresh)
	SwipeRefreshLayout mSwipeRefreshLayout;
	/*@ResInject(R.id.roster_expandedlistview)
	public static InnerExpandableListView mInnerExpandableListView;*/
	
	private Map<String, List<Map<String, String>>> mAllGroupRoster;  //记录到查询的所有联系人
	private List<String> mAllGroups1;   //所有一级分组
	private List<List<Map<String, String>>> mAllChildren1; //所有一级分组的联系人
	private List<Integer> mAllGroupOnline1;   //所有一级在线人数
	
	private List<String> mAllGroups2;   //所有二级分组
	public  List<List<Map<String, String>>> mAllChildren2; //所有二级分组的联系人
	private List<Integer> mAllGroupOnline2;   //所有二级在线人数
	//public static RosterAdapter mAdapter;
	
	@ResInject(R.id.roster_listview)
	InnerListView mInnerListView;
	private List<Map<String, String>> mAllRoomItems;  //所有群分组
	private RosterRoomAdapter mRoomAdapter;
	
	private RosterOffline mRosterOffline;
	private RoomOffline mRoomOffline;
	
	private final Handler mHandler = new Handler();
/*	private EditText mAddRoom;
	private AlertDialog.Builder builder;
	private AlertDialog mDialog = null;
	private View dialogView;*/
	@ResInject(R.id.parent)
	private LinearLayout pARELayout;
	
	private Map<String,List<List<Map<String,String>>>> mgroupmap;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ConcreteObservable.newInstance().registerObserver(this);
		
		/*dialogView = LayoutInflater.from(getActivity()).inflate(R.layout.memedit, null);
		mAddRoom = (EditText) dialogView.findViewById(R.id.dialogview);*/
		show = new HashMap<Integer, Integer>();
		SecondGroup = new ArrayList<String>();
		mgroupmap = new HashMap<String, List<List<Map<String,String>>>>();
		//分组联系人
		mRosterOffline = new RosterOffline(getActivity());
		mAllGroups1 = new ArrayList<String>();
		mAllGroupOnline1 = new ArrayList<Integer>();
		mAllChildren1 = new ArrayList<List<Map<String,String>>>();
		
		mAllGroups2 = new ArrayList<String>();
		mAllGroupOnline2 = new ArrayList<Integer>();
		mAllChildren2 = new ArrayList<List<Map<String,String>>>();
		//mAdapter = new RosterAdapter(getActivity(), mAllGroups2, mAllGroupOnline2, mAllChildren2);
		
		//群组
		mRoomOffline = new RoomOffline(getActivity());
		mAllRoomItems = new ArrayList<Map<String, String>>();
		mAllRoomItems.addAll(mRoomOffline.selectAll());
		mRoomAdapter = new RosterRoomAdapter(getActivity(), mAllRoomItems);
		
		//createDialog();
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_roster, container, false);
		ViewInjectUtil.inject(this, view);
		/*mActionBar.addRightResAction(R.drawable.selector_room_add, new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				//mDialog.show();
				FragmentInfo info = new FragmentInfo(InviteFragment.class, null);
				FragmentInfoActivity.startFragment(RosterFragment.this, info);
			}
			
		});*/
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mSwipeRefreshLayout.setOnRefreshListener(this);
		mSwipeRefreshLayout.setRefreshing(true);
		//mInnerExpandableListView.setAdapter(mAdapter);
		
		mInnerListView.setAdapter(mRoomAdapter);

		selectAndnotify();
		fetchFriendFromServer();
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (!hidden && FLAG == 0) {
			mActionBar.setTitle("联系人");
			//mActionBar.hideRightView();
		}
		if (!hidden && FLAG == 1) {
			mActionBar.setTitle("群组");
			//mActionBar.showRightView();
		}
	}
	
	//创建Dialog
		/*public void createDialog() {
			builder = new AlertDialog.Builder(getActivity());
			builder.setView(dialogView);
			builder.setTitle("添加群组");
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
					String roomName = "";
					roomName = mAddRoom.getText() + "";
					if(roomName.equals("")){
						roomName = " ";
					}
					
					MultiUserChat muc = null;  
				    try {  
				        // 创建一个MultiUserChat  
				        muc = new MultiUserChat(SmackerHelper.newInstance(getActivity()).getmXMPPConnection(), roomName + "@conference."  
				                + SmackerHelper.newInstance(getActivity()).getmXMPPConnection().getServiceName());  
				        // 创建聊天室  
				        muc.create(roomName);  
				        // 获得聊天室的配置表单  
				        Form form = muc.getConfigurationForm();  
				        // 根据原始表单创建一个要提交的新表单。  
				        Form submitForm = form.createAnswerForm();  
				        // 向要提交的表单添加默认答复  
				        for (Iterator<FormField> fields = form.getFields(); fields  
				                .hasNext();) {  
				            FormField field = (FormField) fields.next();  
				            if (!FormField.TYPE_HIDDEN.equals(field.getType())  
				                    && field.getVariable() != null) {  
				                // 设置默认值作为答复  
				                submitForm.setDefaultAnswer(field.getVariable());  
				            }  
				        }  
				        // 设置聊天室的新拥有者  
				        List<String> owners = new ArrayList<String>();  
				        owners.add("admin@win-m48s4rvvs39");// 用户JID  
				        submitForm.setAnswer("muc#roomconfig_roomowners", owners);  
				        // 设置聊天室是持久聊天室，即将要被保存下来  
				        submitForm.setAnswer("muc#roomconfig_persistentroom", true);  
				        // 房间仅对成员开放  
				        submitForm.setAnswer("muc#roomconfig_membersonly", true);  
				        // 允许占有者邀请其他人  
				        submitForm.setAnswer("muc#roomconfig_allowinvites", true);  
				        if (!password.equals("")) {  
				            // 进入是否需要密码  
				            submitForm.setAnswer("muc#roomconfig_passwordprotectedroom",  
				                    true);  
				            // 设置进入密码  
				            submitForm.setAnswer("muc#roomconfig_roomsecret", password);  
				        }  
				        // 能够发现占有者真实 JID 的角色  
				        //submitForm.setAnswer("muc#roomconfig_whois", "anyone");  
				        //列出目录中的房间
				        submitForm.setAnswer("muc#roomconfig_publicroom", true);
				        // 登录房间对话  
				        submitForm.setAnswer("muc#roomconfig_enablelogging", false);  
				        // 仅允许注册的昵称登录  
				        submitForm.setAnswer("x-muc#roomconfig_reservednick", false);  
				        // 允许使用者修改昵称  
				        submitForm.setAnswer("x-muc#roomconfig_canchangenick", true);  
				        // 允许用户注册房间  
				        submitForm.setAnswer("x-muc#roomconfig_registration", true);  
				        //允许占有者更改主题
				        submitForm.setAnswer("muc#roomconfig_changesubject", true);
				        // 发送已完成的表单（有默认值）到服务器来配置聊天室  
				        muc.sendConfigurationForm(submitForm);
				        muc.invite(SmackerHelper.newInstance(getActivity()).getmXMPPConnection().getUser(), "邀请进入房间的消息");
				    } catch (XMPPException e) {  
				        e.printStackTrace();  
				    }  
				    onRefresh();
				}
			});
			builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

				@Override
				public void onClick(DialogInterface arg0, int arg1) {
					// TODO Auto-generated method stub
				}
			});
			mDialog = builder.create();
		}*/
	
	@Override
	public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ConcreteObservable.newInstance().unRegisterObserver(this);
	}
	
	@ViewListenerInject(value = {R.id.roster_radiogroup}, type = ViewListenerType.OnCheckedChange)
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		switch (checkedId) {
		case R.id.roster_radio_friend:
			FLAG = 0;
			pARELayout.setVisibility(View.VISIBLE);
			mInnerListView.setVisibility(View.GONE);
			mActionBar.setTitle("联系人");
			//mActionBar.hideRightView();
			break;
		case R.id.roster_radio_group:
			FLAG = 1;
			pARELayout.setVisibility(View.GONE);
			mInnerListView.setVisibility(View.VISIBLE);
			mActionBar.setTitle("群组");
			//mActionBar.showRightView();
			break;
		}
	}
	
	/*@ViewListenerInject(value = {R.id.roster_expandedlistview}, type = ViewListenerType.OnChildClick)
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		Bundle bundle = new Bundle();
		bundle.putSerializable(ChatFragment.ROSTER, (HashMap<String, String>)mAllChildren1.get(groupPosition).get(childPosition));
		FragmentInfo info = new FragmentInfo(ChatFragment.class, bundle);
		FragmentInfoActivity.startFragment(RosterFragment.this, info);
		return true;
	}*/
	
	@ViewListenerInject(value = {R.id.roster_listview}, type = ViewListenerType.OnItemClick)
	public void onItemClick(final AdapterView<?> parent, View view, final int position,
			long id) {
		@SuppressWarnings("unchecked")
		Map<String, String> map = (Map<String, String>) parent.getAdapter().getItem(position);
		Bundle bundle = new Bundle();
		bundle.putString(MultiChatFragment.ROOM_NAME, map.get(DBHelper.ROOM_NAME));
		bundle.putString(MultiChatFragment.ROOM_ID, map.get(DBHelper.ROOM_ID));
		FragmentInfo info = new FragmentInfo(MultiChatFragment.class, bundle);
		FragmentInfoActivity.startFragment(RosterFragment.this, info);
	}
	
	@Override
	public void onRefresh() {
		// TODO Auto-generated method stub
		if (FLAG == 0) {
			fetchFriendFromServer();
		} else {
			SmackerHelper.newInstance(getActivity()).fetchRoomItem();
		}
	}
	
	//服务器获取联系人
	private void fetchFriendFromServer() {
		SmackerHelper.newInstance(getActivity()).fetchFriends();
		mHandler.postDelayed(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				selectAndnotify();
				mSwipeRefreshLayout.setRefreshing(false);
			}
		}, 2 * 1000);
	}

	//查询联系人并通知界面改变
	private void selectAndnotify() {
		mAllGroupRoster = mRosterOffline.selectAll();
		mAllGroupOnline1.clear();
		mAllGroups1.clear();
		mAllChildren1.clear();
		
		mAllGroupOnline2.clear();
		mAllGroups2.clear();
		mAllChildren2.clear();
		for (Entry<String, List<Map<String, String>>> entry : mAllGroupRoster.entrySet()) {
			
			if(entry.getKey().substring(1, 2).equals("0")){
				int online = 0;
				for (Map<String, String> map : entry.getValue()) {
					online += map.get(DBHelper.R_MODE).equals(Constants.AVAILABLE) ? 1 : 0;
				}
				mAllGroupOnline1.add(online);
				mAllGroups1.add(entry.getKey());
				mAllChildren1.add(entry.getValue());
			}
			if(!entry.getKey().substring(1, 2).equals("0")){
				int online = 0;
				for (Map<String, String> map : entry.getValue()) {
					online += map.get(DBHelper.R_MODE).equals(Constants.AVAILABLE) ? 1 : 0;
				}
				mAllGroupOnline2.add(online);
				mAllGroups2.add(entry.getKey());
				mAllChildren2.add(entry.getValue());
				List<List<Map<String, String>>> map = new ArrayList<List<Map<String,String>>>();
				map.add(entry.getValue());
				mgroupmap.put(entry.getKey(), map);
			}
		}
		if(pARELayout.getChildCount() == 0){
			for(int i = 0; i < mAllGroups1.size(); i++){
				LinearLayout lLayout=(LinearLayout) View.inflate(getActivity().getApplicationContext(), R.layout.coom, null);
				LinearLayout mGroup1childLayout = (LinearLayout) lLayout.findViewById(R.id.see);
				show.put(i, View.GONE);
				TextView textNameTextView=(TextView) lLayout.findViewById(R.id.textName);
				TextView groupOnline = (TextView) lLayout.findViewById(R.id.roster_group_online_count);
				groupOnline.setText(mAllGroupOnline1.get(i) + "/" + mAllChildren1.get(i).size());
				textNameTextView.setText(mAllGroups1.get(i).substring(2));
				textNameTextView.setOnClickListener(new MyListener(lLayout,i));
				for(int j = 0; j < mAllChildren1.get(i).size(); j++){
					RelativeLayout mGro = (RelativeLayout) View.inflate(getActivity().getApplicationContext(), R.layout.fragment_roster_child_item, null);
					TextView mGroup1Child = (TextView) mGro.findViewById(R.id.roster_child_name);
					//ImageView child_icon = (ImageView) mGro.findViewById(R.id.roster_child_icon);
					ImageView child_mode_icon = (ImageView) mGro.findViewById(R.id.roster_child_mode_icon);
					TextView roster_child_mode = (TextView) mGro.findViewById(R.id.roster_child_mode);

					child_mode_icon.setImageResource(mAllChildren1.get(i).get(j).get(DBHelper.R_MODE)
							.equals(Constants.AVAILABLE) ? R.drawable.status_online : R.drawable.status_leave);
					roster_child_mode.setText(mAllChildren1.get(i).get(j).get(DBHelper.R_MODE)
					.equals(Constants.AVAILABLE) ? "[在线]" : "[离线]");
					
					mGroup1Child.setText(mAllChildren1.get(i).get(j).get(DBHelper.R_NICKNAME));
					mGro.setOnClickListener(new MyListener2(mAllChildren1.get(i).get(j), this));
					mGroup1childLayout.addView(mGro);
				}
				for(int k = 0; k < mAllGroups2.size(); k++){
					if(mAllGroups2.get(k).substring(0, 1).equals(mAllGroups1.get(i).substring(0, 1))){
						final InnerExpandableListView el1 = new InnerExpandableListView(getActivity(), null);
						el1.setIndicatorBounds(0, 0);
						el1.setTag(i + "-" + k + "");
						el1.setOnGroupExpandListener(new OnGroupExpandListener() {
							
							@Override
							public void onGroupExpand(int arg0) {
								// TODO Auto-generated method stub
								SecondGroup.add(el1.getTag().toString());
							}
						});
						el1.setOnGroupCollapseListener(new OnGroupCollapseListener() {
							
							@Override
							public void onGroupCollapse(int arg0) {
								// TODO Auto-generated method stub
								SecondGroup.remove(el1.getTag().toString());
							}
						});
						RosterAdapter mAdapter = new RosterAdapter(getActivity(), mAllGroups2.get(k), mAllGroupOnline2.get(k), mgroupmap.get(mAllGroups2.get(k)),this);
						el1.setAdapter(mAdapter);
						mGroup1childLayout.addView(el1);
					}
				}
				pARELayout.addView(lLayout);
			}
			count = pARELayout.getChildCount();
		}
		if(!(pARELayout.getChildCount() == 0)){
			pARELayout.removeAllViews();
			for(int i = 0; i < mAllGroups1.size(); i++){
				LinearLayout lLayout=(LinearLayout) View.inflate(getActivity().getApplicationContext(), R.layout.coom, null);
				LinearLayout mGroup1childLayout = (LinearLayout) lLayout.findViewById(R.id.see);
				if(count<=i){
					show.put(i, View.GONE);
				}
				mGroup1childLayout.setVisibility(show.get(i));
				TextView textNameTextView=(TextView) lLayout.findViewById(R.id.textName);
				TextView groupOnline = (TextView) lLayout.findViewById(R.id.roster_group_online_count);
				groupOnline.setText(mAllGroupOnline1.get(i) + "/" + mAllChildren1.get(i).size());
				textNameTextView.setText(mAllGroups1.get(i).substring(2));
				textNameTextView.setOnClickListener(new MyListener(lLayout,i));
				for(int j = 0; j < mAllChildren1.get(i).size(); j++){
					RelativeLayout mGro = (RelativeLayout) View.inflate(getActivity().getApplicationContext(), R.layout.fragment_roster_child_item, null);
					TextView mGroup1Child = (TextView) mGro.findViewById(R.id.roster_child_name);
					ImageView child_mode_icon = (ImageView) mGro.findViewById(R.id.roster_child_mode_icon);
					TextView roster_child_mode = (TextView) mGro.findViewById(R.id.roster_child_mode);

					child_mode_icon.setImageResource(mAllChildren1.get(i).get(j).get(DBHelper.R_MODE)
							.equals(Constants.AVAILABLE) ? R.drawable.status_online : R.drawable.status_leave);
					roster_child_mode.setText(mAllChildren1.get(i).get(j).get(DBHelper.R_MODE)
					.equals(Constants.AVAILABLE) ? "[在线]" : "[离线]");
					
					mGroup1Child.setText(mAllChildren1.get(i).get(j).get(DBHelper.R_NICKNAME));
					mGro.setOnClickListener(new MyListener2(mAllChildren1.get(i).get(j), this));
					mGroup1childLayout.addView(mGro);
				}
				for(int k = 0; k < mAllGroups2.size(); k++){
					if(mAllGroups2.get(k).substring(0, 1).equals(mAllGroups1.get(i).substring(0, 1))){
						final InnerExpandableListView el1 = new InnerExpandableListView(getActivity(), null);
						el1.setIndicatorBounds(0, 0);
						el1.setTag(i + "-" + k + "");
						el1.setOnGroupExpandListener(new OnGroupExpandListener() {
							
							@Override
							public void onGroupExpand(int arg0) {
								// TODO Auto-generated method stub
								if(SecondGroup.contains(el1.getTag().toString())){
									
								}else{
									SecondGroup.add(el1.getTag().toString());
								}
							}
						});
						el1.setOnGroupCollapseListener(new OnGroupCollapseListener() {
							
							@Override
							public void onGroupCollapse(int arg0) {
								// TODO Auto-generated method stub
								if(!SecondGroup.isEmpty()){
									SecondGroup.remove(el1.getTag().toString());
								}
							}
						});
						RosterAdapter mAdapter = new RosterAdapter(getActivity(), mAllGroups2.get(k), mAllGroupOnline2.get(k), mgroupmap.get(mAllGroups2.get(k)),this);
						el1.setAdapter(mAdapter);
						mGroup1childLayout.addView(el1);
						if (SecondGroup.contains(el1.getTag().toString())) {
							el1.expandGroup(0);
						}
					}
				}
				pARELayout.addView(lLayout);
			}
			count = pARELayout.getChildCount();
		}
		
	}

	@Override
	public void update(Object... objs) {
		// TODO Auto-generated method stub
		if (objs[0].equals(Constants.FRIEND_STATUS)) {
			selectAndnotify();
		} else if (objs[0].equals(Constants.FETCH_ROOM_ENDED)) {
			mSwipeRefreshLayout.setRefreshing(false);
			mAllRoomItems.clear();
			mAllRoomItems.addAll(mRoomOffline.selectAll());
			mRoomAdapter.notifyDataSetChanged();
		}
	}

	class MyListener implements OnClickListener {
		private boolean zhan = true;
		private int i;
		LinearLayout lLayout = null;

		public MyListener(LinearLayout view,int i) {
			this.lLayout = view;
			this.i = i;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if (zhan == false) {
				View view = lLayout.findViewById(R.id.see);
				view.setVisibility(View.GONE);
				show.put(this.i, View.GONE);
				zhan = true;
			} else {
				View view = lLayout.findViewById(R.id.see);
				view.setVisibility(View.VISIBLE);
				show.put(this.i, View.VISIBLE);
				zhan = false;
			}
		}
	}
	class MyListener2 implements OnClickListener {
		private Map<String, String> map;
		private BaseFragment base;

		public MyListener2(Map<String, String> map, BaseFragment base) {
			this.map = map;
			this.base = base;
		}

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			Bundle bundle = new Bundle();
			bundle.putSerializable(ChatFragment.ROSTER, (HashMap<String, String>)map);
			FragmentInfo info = new FragmentInfo(ChatFragment.class, bundle);
			FragmentInfoActivity.startFragment(base, info);
		}
	}
}

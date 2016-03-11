package cuit.emergency.fragment;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.Form;
import org.jivesoftware.smackx.FormField;
import org.jivesoftware.smackx.muc.MultiUserChat;

import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v4.widget.SwipeRefreshLayout.OnRefreshListener;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ExpandableListView;
import cc.util.android.core.BaseFragment;
import cc.util.android.view.InnerExpandableListView;
import cc.util.android.view.InnerListView;
import cc.util.android.viewInject.ResInject;
import cc.util.android.viewInject.ViewInjectUtil;
import cc.util.android.viewInject.ViewListenerInject;
import cc.util.android.viewInject.ViewListenerType;
import cc.util.java.core.ConcreteObservable;
import cc.util.java.core.Observer;
import cuit.emergency.R;
import cuit.emergency.adapter.InviteAdapter;
import cuit.emergency.adapter.InviteAdapter.ChildViewHolder;
import cuit.emergency.tool.DBHelper;
import cuit.emergency.tool.RosterOffline;
import cuit.emergency.tool.SmackerHelper;
import cuit.emergency.util.Constants;
import cuit.emergency.util.T;

public class InviteFragment extends BaseFragment implements OnRefreshListener, Observer{
	@ResInject(R.id.invite_swiperefresh) SwipeRefreshLayout mSwipeRefreshLayout;
	@ResInject(R.id.invite_expandedlistview) InnerExpandableListView mInnerExpandableListView;
	@ResInject(R.id.invite_listview) InnerListView mInnerListView;
	@ResInject(R.id.room_name) EditText mAddRoom;
	
	private RosterOffline mRosterOffline;
	private Map<String, List<Map<String, String>>> mAllGroupRoster;  //记录到查询的所有联系人
	private List<String> mAllGroups;   //所有分组
	private List<List<Map<String, String>>> mAllChildren; //所有分组的联系人
	private List<Integer> mAllGroupOnline;   //所有在线人数
	private InviteAdapter mAdapter;
	
	private List<String> jIDs;
	private List<String> owners; 
	
	private final Handler mHandler = new Handler();
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		ConcreteObservable.newInstance().registerObserver(this);

		mRosterOffline = new RosterOffline(getActivity());
		mAllGroups = new ArrayList<String>();
		mAllGroupOnline = new ArrayList<Integer>();
		mAllChildren = new ArrayList<List<Map<String,String>>>();
		mAdapter = new InviteAdapter(getActivity(), mAllGroups, mAllGroupOnline, mAllChildren);
		jIDs = new ArrayList<String>();
		owners = new ArrayList<String>();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.invite, container, false);
		ViewInjectUtil.inject(this, view);
		jIDs.clear();
		mActionBar.showLeftView();
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mSwipeRefreshLayout.setOnRefreshListener(this);
		mSwipeRefreshLayout.setRefreshing(true);
		mInnerExpandableListView.setAdapter(mAdapter);

		selectAndnotify();
		fetchFriendFromServer();
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (!hidden) {
			mActionBar.setLeftViewResourceId(R.drawable.selector_back);
			mActionBar.setTitle("创建群组");
			mActionBar.addRightResAction(R.drawable.selector_ok, new OnClickListener(){

				@Override
				public void onClick(View arg0) {
					// TODO Auto-generated method stub
					if(jIDs.isEmpty() && mAddRoom.getText().toString().trim().equals("")){
						T.showShort(getActivity(), "请填写相关信息");
					}else if(jIDs.isEmpty()){
						T.showShort(getActivity(), "请选择要邀请的好友");
					}else if(mAddRoom.getText().toString().trim().equals("")){
						T.showShort(getActivity(), "请输入创建的群组名称");
						mAddRoom.setText("");
					}
					else{
						joinMultiUserChat(jIDs);
					}
				}
			});
		}
	}
	
	/**
	 * 加入房间
	 * 
	 * @param Jid
	 *            用户JID
	 * @param muc
	 *            多人聊天
	 */
	private void joinMultiUserChat(List<String> Jids) {
		owners.clear();
		owners.add(SmackerHelper.newInstance(getActivity()).getmXMPPConnection().getUser());
		for(int i = 0; i < Jids.size(); i++){
			owners.add(Jids.get(i));
		}
		String roomName = "";
		roomName = mAddRoom.getText() + "";
		
		MultiUserChat muc = null;  
	    try {  
	        // 创建一个MultiUserChat  
	        muc = new MultiUserChat(SmackerHelper.newInstance(getActivity()).getmXMPPConnection(), roomName + "@conference.win-m48s4rvvs39");  
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
	        // 设置聊天室的拥有者  
	        submitForm.setAnswer("muc#roomconfig_roomowners", owners);  
	        // 设置聊天室是持久聊天室，即将要被保存下来  
	        submitForm.setAnswer("muc#roomconfig_persistentroom", true);  
	        // 房间仅对成员开放  
	        submitForm.setAnswer("muc#roomconfig_membersonly", true);  
	        // 允许占有者邀请其他人  
	        submitForm.setAnswer("muc#roomconfig_allowinvites", true);  
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
	        T.showShort(getActivity(), "创建成功");
	        getActivity().finish();
	    } catch (XMPPException e) {  
	        e.printStackTrace();  
	        T.showShort(getActivity(), "创建失败");
	    }  
	}

	@Override
		public void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		ConcreteObservable.newInstance().unRegisterObserver(this);
	}
	
	@ViewListenerInject(value = {R.id.invite_expandedlistview}, type = ViewListenerType.OnChildClick)
		public boolean onChildClick(ExpandableListView parent, View child, int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		InviteAdapter.ChildViewHolder childViewHolder = (ChildViewHolder) child.getTag();
		mAllChildren.get(groupPosition).get(childPosition).put("state",!childViewHolder.checkbox.isChecked()+"");
		childViewHolder.checkbox.toggle();   
		if(childViewHolder.checkbox.isChecked()){
			jIDs.add(mAllChildren.get(groupPosition).get(childPosition).get(DBHelper.C_CHAT_ROSTER));
		}else{
			jIDs.remove(mAllChildren.get(groupPosition).get(childPosition).get(DBHelper.C_CHAT_ROSTER));
		}
		return true;
	}

		/**
		 * 服务器获取联系人
		 * */
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

		/**
		 * 查询联系人并通知界面改变
		 * */
		private void selectAndnotify() {
			mAllGroupRoster = mRosterOffline.selectAll();
			mAllGroupOnline.clear();
			mAllGroups.clear();
			mAllChildren.clear();
			for (Entry<String, List<Map<String, String>>> entry : mAllGroupRoster.entrySet()) {
				int online = 0;
				for (Map<String, String> map : entry.getValue()) {
					online += map.get(DBHelper.R_MODE).equals(Constants.AVAILABLE) ? 1 : 0;
				}
				mAllGroupOnline.add(online);
				mAllGroups.add(entry.getKey());
				mAllChildren.add(entry.getValue());
			}
			mAdapter.notifyDataSetChanged();
		}
		
		@Override
		public void update(Object... objs) {
			// TODO Auto-generated method stub
			if (objs[0].equals(Constants.FRIEND_STATUS)) {
				selectAndnotify();
			} else if (objs[0].equals(Constants.FETCH_ROOM_ENDED)) {
				mSwipeRefreshLayout.setRefreshing(false);
			}
		}

		@Override
		public void onRefresh() {
			// TODO Auto-generated method stub
				fetchFriendFromServer();
		}

}

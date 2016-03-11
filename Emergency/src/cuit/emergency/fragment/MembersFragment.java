package cuit.emergency.fragment;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.jivesoftware.smack.XMPPConnection;
import org.jivesoftware.smack.XMPPException;
import org.jivesoftware.smackx.muc.Affiliate;
import org.jivesoftware.smackx.muc.MultiUserChat;
import org.jivesoftware.smackx.packet.VCard;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import cc.util.android.core.BaseFragment;
import cc.util.android.viewInject.ResInject;
import cc.util.android.viewInject.ViewInjectUtil;
import cuit.emergency.R;
import cuit.emergency.adapter.MemberAdapter;
import cuit.emergency.tool.SmackerHelper;

public class MembersFragment extends BaseFragment{
	@ResInject(R.id.member_listView) ListView mListView;
	
	private MemberAdapter mRoomMemberAdapter;
	
	private String roomID;
	private String mJID;
	private MultiUserChat muc;
	private List<String> listUser;
	private List<Affiliate> mOwners;
	private XMPPConnection mConnection;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		
		mOwners = new ArrayList<Affiliate>();
		listUser = new ArrayList<String>();
		mConnection = SmackerHelper.newInstance(getActivity()).getmXMPPConnection();
		mRoomMemberAdapter = new MemberAdapter(getActivity(), listUser);
		roomID = getArguments().getString(MultiChatFragment.ROOM_ID);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.member, container, false);
		ViewInjectUtil.inject(this, view);
		muc = new MultiUserChat(mConnection, roomID);
		mListView.setAdapter(mRoomMemberAdapter);
		findMulitUser(muc);
		mRoomMemberAdapter.notifyDataSetChanged();
		return view;
	}

	  /**
	   * 查询会议室成员名字
	   * @param muc
	   */
	  private List<String> findMulitUser(MultiUserChat muc){
		Collection<Affiliate> owners = null;
		VCard vCard = new VCard();
		mOwners.clear();
	    listUser.clear();

	    try {
	    	owners = muc.getOwners();	
	    	mOwners.addAll(owners);
	    	for (int i = 0; i < mOwners.size(); i++) {
				mJID = mOwners.get(i).getJid();
				vCard.load(mConnection, mJID);
				listUser.add(vCard.getNickName());
			}
		} catch (XMPPException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return listUser;
	}
	    
	  
	  @Override
		public void onHiddenChanged(boolean hidden) {
			// TODO Auto-generated method stub
			super.onHiddenChanged(hidden);
			if (!hidden) {
				mActionBar.setTitle("房间成员");
				mActionBar.hideRightView();
			}
		}
}

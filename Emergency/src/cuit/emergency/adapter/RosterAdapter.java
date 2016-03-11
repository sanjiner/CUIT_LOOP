package cuit.emergency.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cc.util.android.core.BaseFragment;
import cc.util.android.core.FragmentInfo;
import cc.util.android.core.FragmentInfoActivity;
import cc.util.android.viewInject.ResInject;
import cc.util.android.viewInject.ViewInjectUtil;
import cuit.emergency.R;
import cuit.emergency.fragment.ChatFragment;
import cuit.emergency.tool.DBHelper;
import cuit.emergency.util.Constants;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class RosterAdapter extends BaseExpandableListAdapter {

	private Context mContext;
	private String mAllGroups;
	private List<List<Map<String, String>>> mAllChildren;
	private Integer mAllGroupOnline;   //所有在线人数
	private BaseFragment base;

	public RosterAdapter(Context context, String group, Integer groupOnline, List<List<Map<String, String>>> children, BaseFragment base) {
		this.mContext = context;
		this.mAllGroups = group;
		this.mAllGroupOnline = groupOnline;
		this.mAllChildren = children;
		this.base = base;
	}
	
	@Override
	public Object getChild(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return mAllChildren.get(groupPosition).get(childPosition);
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		// TODO Auto-generated method stub
		return childPosition;
	}

	@Override
	public View getChildView(final int groupPosition, final int childPosition, boolean isLastChild, View convertView,
			ViewGroup parent) {
		// TODO Auto-generated method stub
		ChildViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = (View)View.inflate(mContext, R.layout.fragment_roster_child_item2, null);
			viewHolder = new ChildViewHolder();
			ViewInjectUtil.inject(viewHolder, convertView);
            convertView.setTag(viewHolder);
		} else {
			viewHolder = (ChildViewHolder) convertView.getTag();
		}
		viewHolder.nameText.setText(mAllChildren.get(groupPosition).get(childPosition).get(DBHelper.R_NICKNAME));
		viewHolder.modeText.setText(mAllChildren.get(groupPosition).get(childPosition).get(DBHelper.R_MODE)
				.equals(Constants.AVAILABLE) ? "[在线]" : "[离线]");
		viewHolder.modeIcon.setImageResource(mAllChildren.get(groupPosition).get(childPosition).get(DBHelper.R_MODE)
				.equals(Constants.AVAILABLE) ? R.drawable.status_online : R.drawable.status_leave);
		convertView.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				Bundle bundle = new Bundle();
				bundle.putSerializable(ChatFragment.ROSTER, (HashMap<String, String>)mAllChildren.get(groupPosition).get(childPosition));
				FragmentInfo info = new FragmentInfo(ChatFragment.class, bundle);
				FragmentInfoActivity.startFragment(base, info);
			}
		});
		return convertView;
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		// TODO Auto-generated method stub
		return mAllChildren.get(groupPosition).size();
	}

	@Override
	public Object getGroup(int groupPosition) {
		// TODO Auto-generated method stub
		return mAllGroups;
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return 1;
	}

	@Override
	public long getGroupId(int groupPosition) {
		// TODO Auto-generated method stub
		return groupPosition;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpandable, View convertView, ViewGroup parent){
		// TODO Auto-generated method stub
		GroupHolder groupHolder = null;
		if(convertView == null){
			groupHolder = new GroupHolder();
			convertView = (View)View.inflate(mContext, R.layout.fragment_roster_group_item2, null);
			ViewInjectUtil.inject(groupHolder, convertView);
			convertView.setTag(groupHolder);
		} else {
			groupHolder = (GroupHolder)convertView.getTag();
		}
		if(isExpandable){
			groupHolder.indicator.setBackgroundResource(R.drawable.indicator_expanded);
		} else {
			groupHolder.indicator.setBackgroundResource(R.drawable.indicator_unexpanded);
		}
		groupHolder.groupName.setText(mAllGroups.substring(2));
		groupHolder.onOffLine.setText(mAllGroupOnline+ 
				"/" + getChildrenCount(groupPosition));
		return convertView;
	}

	@Override
	public boolean hasStableIds() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) {
		// TODO Auto-generated method stub
		return true;
	}
	
	static class GroupHolder{
		@ResInject(R.id.roster_group_indicator2)
		ImageView indicator;
		@ResInject(R.id.roster_group_name2)
		TextView groupName;
		@ResInject(R.id.roster_group_online_count2)
		TextView onOffLine;
	}
	
	static class ChildViewHolder {
		@ResInject(R.id.roster_child_icon2)
		ImageView icon;
		@ResInject(R.id.roster_child_mode_icon2)
		ImageView modeIcon;
		@ResInject(R.id.roster_child_name2)
		TextView nameText;
		@ResInject(R.id.roster_child_mode2)
		TextView modeText;
		
	}

}

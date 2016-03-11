package cuit.emergency.adapter;

import java.util.List;
import java.util.Map;

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;
import cc.util.android.viewInject.ResInject;
import cc.util.android.viewInject.ViewInjectUtil;
import cuit.emergency.R;
import cuit.emergency.tool.DBHelper;
import cuit.emergency.util.Constants;

public class InviteAdapter extends BaseExpandableListAdapter{
	
	private Context mContext;
	private List<String> mAllGroups;
	private List<List<Map<String, String>>> mAllChildren;
	private List<Integer> mAllGroupOnline;   //所有在线人数

	public InviteAdapter(Context context, List<String> group, List<Integer> groupOnline, List<List<Map<String, String>>> children) {
		this.mContext = context;
		this.mAllGroups = group;
		this.mAllGroupOnline = groupOnline;
		this.mAllChildren = children;
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
		Log.e("convertView","convertView:"+convertView+"groupPos:"+groupPosition+"childPos:"+childPosition);
        if (convertView == null) {
            convertView = (View)View.inflate(mContext, R.layout.fragment_invite_child_item, null);
			viewHolder = new ChildViewHolder();
			ViewInjectUtil.inject(viewHolder, convertView);
            convertView.setTag(viewHolder);
		} else {
			viewHolder = (ChildViewHolder) convertView.getTag();
		}
        if((mAllChildren.get(groupPosition).get(childPosition).get("state") == null)){
        	viewHolder.checkbox.setChecked(false);
        }else if(mAllChildren.get(groupPosition).get(childPosition).get("state").equals(true+"")){
        	viewHolder.checkbox.setChecked(true);
        }else{
        	viewHolder.checkbox.setChecked(false);
        }
		viewHolder.nameText.setText(mAllChildren.get(groupPosition).get(childPosition).get(DBHelper.R_NICKNAME));
		viewHolder.modeText.setText(mAllChildren.get(groupPosition).get(childPosition).get(DBHelper.R_MODE)
				.equals(Constants.AVAILABLE) ? "[在线]" : "[离线]");
		viewHolder.modeIcon.setImageResource(mAllChildren.get(groupPosition).get(childPosition).get(DBHelper.R_MODE)
				.equals(Constants.AVAILABLE) ? R.drawable.status_online : R.drawable.status_leave);
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
		return mAllGroups.get(groupPosition);
	}

	@Override
	public int getGroupCount() {
		// TODO Auto-generated method stub
		return mAllGroups.size();
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
			convertView = (View)View.inflate(mContext, R.layout.fragment_roster_group_item, null);
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
		groupHolder.groupName.setText(mAllGroups.get(groupPosition).toString().substring(2, mAllGroups.get(groupPosition).length()));
		groupHolder.onOffLine.setText(mAllGroupOnline.get(groupPosition) + 
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
		@ResInject(R.id.roster_group_indicator)
		ImageView indicator;
		@ResInject(R.id.roster_group_name)
		TextView groupName;
		@ResInject(R.id.roster_group_online_count)
		TextView onOffLine;
	}
	
	public  class ChildViewHolder {
		@ResInject(R.id.invite_child_icon)
		ImageView icon;
		@ResInject(R.id.invite_child_mode_icon)
		ImageView modeIcon;
		@ResInject(R.id.invite_child_name)
		TextView nameText;
		@ResInject(R.id.invite_child_mode)
		TextView modeText;
		@ResInject(R.id.invite_checkbox)
		public CheckBox checkbox;
		
	}
}

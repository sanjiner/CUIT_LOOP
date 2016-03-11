package cuit.emergency.adapter;

import java.util.List;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cc.util.android.viewInject.ResInject;
import cc.util.android.viewInject.ViewInjectUtil;
import cuit.emergency.R;

public class MemberAdapter extends BaseAdapter{
	private Context mContext;
	private List<String> memberName;

	public MemberAdapter(Context context, List<String> membername){
		this.mContext = context;
		this.memberName = membername;
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return memberName.size();
	}

	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return memberName.get(arg0);
	}

	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}

	@Override
	public View getView(int pos, View convertView, ViewGroup arg2) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = (View)View.inflate(mContext, R.layout.fragment_roster_child_item, null);
			viewHolder = new ViewHolder();
			ViewInjectUtil.inject(viewHolder, convertView);
            convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.nameText.setText(memberName.get(pos));
		return convertView;
	}
	
	class ViewHolder{
		@ResInject(R.id.roster_child_icon)
		ImageView icon;
		@ResInject(R.id.roster_child_mode_icon)
		ImageView modeIcon;
		@ResInject(R.id.roster_child_name)
		TextView nameText;
		@ResInject(R.id.roster_child_mode)
		TextView modeText;
	}

}

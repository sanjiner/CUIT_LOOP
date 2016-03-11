package cuit.emergency.adapter;

import java.util.List;
import java.util.Map;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import cc.util.android.viewInject.ResInject;
import cc.util.android.viewInject.ViewInjectUtil;
import cuit.emergency.R;
import cuit.emergency.tool.DBHelper;

public class RosterRoomAdapter extends BaseAdapter {

	private Context mContext;
	private List<Map<String, String>> mAllList;
	public RosterRoomAdapter(Context context, List<Map<String, String>> list) {
		this.mContext = context;
		mAllList = list;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mAllList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mAllList.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
        if (convertView == null) {
            convertView = (View)View.inflate(mContext, R.layout.fragment_roster_room_item, null);
			viewHolder = new ViewHolder();
			ViewInjectUtil.inject(viewHolder, convertView);
            convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.nameText.setText(mAllList.get(position).get(DBHelper.ROOM_NAME));
		return convertView;
	}

	static class ViewHolder {
		@ResInject(R.id.roster_room_name)
		TextView nameText;
	}
}

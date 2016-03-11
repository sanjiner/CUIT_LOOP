package cuit.emergency.adapter;

import java.util.List;
import java.util.Map;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import cc.util.android.viewInject.ResInject;
import cc.util.android.viewInject.ViewInjectUtil;
import cuit.emergency.R;
import cuit.emergency.tool.DBHelper;
import cuit.emergency.util.ToolUtil;

public class MessageAdapter extends BaseAdapter {

	private Context mContext;
	private List<Map<String, String>> mallList;

	public MessageAdapter(Context context, List<Map<String, String>> data) {
		this.mContext = context;
		this.mallList = data;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mallList.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return mallList.get(position);
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
			convertView = (View) LayoutInflater.from(mContext).inflate(R.layout.fragment_message_item, parent, false);
			viewHolder = new ViewHolder();
			ViewInjectUtil.inject(viewHolder, convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		int count = Integer.parseInt(mallList.get(position).get(DBHelper.C_MSG_COUNT));
		viewHolder.countText.setText(count+"");
		viewHolder.countText.setVisibility(count > 0 ? View.VISIBLE : View.INVISIBLE);
		viewHolder.uNameText.setText(mallList.get(position).get(DBHelper.C_CHAT_NICKNAME));
		String message = mallList.get(position).get(DBHelper.C_MSG);
		if (ToolUtil.strIsFile(message)) viewHolder.latestedText.setText("图片消息");
		else if (ToolUtil.strIsVoice(message)) viewHolder.latestedText.setText("语音消息");
		else 
			{	
				if(message.length()>=10){
				if(message.substring(0,10).equals("@&+%zAOPEN"))
				{
					message="您的视频消息";
				}
				else if(message.substring(0,10).equals("@&+%zACLSE"))
				{
					message="视频被取消啦"; 
				}
				else if(message.substring(0,10).equals("@&+%zROPEN"))
				{
					message="您的视频消息";
				}
				else if(message.substring(0,10).equals("@&+%zRCLSE"))
				{
					message="视频被取消啦";
				}
				}
				viewHolder.latestedText.setText(message);
			}
		return convertView;
	}

	static class ViewHolder {
		@ResInject(R.id.icon)
		ImageView vcardImgV;
		@ResInject(R.id.countIcon)
		TextView countText;
		@ResInject(R.id.name)
		TextView uNameText;
		@ResInject(R.id.lasted_message)
		TextView latestedText;
	}

}

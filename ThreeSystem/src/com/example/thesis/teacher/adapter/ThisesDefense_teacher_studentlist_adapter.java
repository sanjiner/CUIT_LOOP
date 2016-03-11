package com.example.thesis.teacher.adapter;

import java.util.List;
import java.util.Map;
import com.exam.ThreeSystem.R;
import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ThisesDefense_teacher_studentlist_adapter extends BaseAdapter {

	public Context context ;
	public  List<Map<String, String>> data;
	
	public ThisesDefense_teacher_studentlist_adapter(Context context, List<Map<String, String>> data)
	{
		this.context = context;
		this.data = data;
	}
	
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		Holder holder;
		if(convertView==null){
			holder = new Holder();
			convertView = View.inflate(context, R.layout.thesisdefensee_listview_lterm, null);
			holder.thesisdefense_listview_item_text=(TextView) convertView.findViewById(R.id.thesisdefense_listview_item_text);
			convertView.setTag(holder);
			
		}
		else 
		{
			holder = (Holder) convertView.getTag();
		}
		
		holder.thesisdefense_listview_item_text.setText(data.get(position).get("studentName"));
		return convertView;
	}
	private class Holder {
		TextView thesisdefense_listview_item_text;
		
	}
}

package com.example.PCenter.adapter;

import java.util.List;
import java.util.Map;

import com.exam.ThreeSystem.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class HomeModuleStudentScoreDetailAdapter extends BaseAdapter{
	private List<Map<String, String>> data;
	private Context context;
	public  HomeModuleStudentScoreDetailAdapter(Context context, List<Map<String, String>> data) {
		this.context = context;
		this.data = data;
	}
	@Override
	public int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Holder holder;
		if (convertView == null) {
			
			holder = new Holder();
			convertView = View.inflate(context, R.layout.home_module_sutdent_score_detail_item, null);
			holder.time = (TextView) convertView.findViewById(R.id.Other_person_score_detail_time);
			holder.score = (TextView) convertView.findViewById(R.id.Other_person_score_detail_score);			
			convertView.setTag(holder);
			
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.time.setText(data.get(position).get("addTime"));
		holder.score.setText(data.get(position).get("score"));		
		return convertView;
	}
	
	private class Holder {
		TextView time;
		TextView score;
	}

}

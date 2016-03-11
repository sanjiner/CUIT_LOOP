package com.example.process.fragments;

import java.util.List;
import java.util.Map;

import com.exam.ThreeSystem.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class YuekaoDetailAdapterProcess extends BaseAdapter{

	private Context context;
	private List<Map<String, String>> data;
	private class Holder
	{
		TextView StartTime;
		TextView EndTime;
		TextView ExamRoom;
		TextView TotalNum;
		TextView LastNum;
	}
	public YuekaoDetailAdapterProcess(Context context, List<Map<String, String>> data)
	{
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
		if(convertView == null)
		{
			holder = new Holder();
			convertView = View.inflate(context, R.layout.process_yuekao_detailitem, null);
			holder.StartTime = (TextView) convertView.findViewById(R.id.yuekao_startTime);
			holder.EndTime = (TextView) convertView.findViewById(R.id.yuekao_endTime);
			holder.ExamRoom = (TextView) convertView.findViewById(R.id.yuekao_examRoom);
			holder.TotalNum = (TextView) convertView.findViewById(R.id.yuekao_LastNum);
			holder.LastNum = (TextView) convertView.findViewById(R.id.yuekao_TotalNum);
			convertView.setTag(holder);
		}
		else
		{
			holder = (Holder) convertView.getTag();
		}
		holder.StartTime.setText(data.get(position).get("StarTime"));
		holder.LastNum.setText(data.get(position).get("LastNum"));
		holder.ExamRoom.setText(data.get(position).get("ExamRoom"));
		holder.LastNum.setText(data.get(position).get("LastNum"));
		holder.TotalNum.setText("/" + data.get(position).get("TotalNum"));
		return convertView;
	}

}

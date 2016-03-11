package com.example.thesis.student.activity;

import java.util.List;
import java.util.Map;

import com.exam.ThreeSystem.R;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class Thesis_StudentHome_GuideContentAdapter extends BaseAdapter{
	private List<Map<String, String>> data;
	private Thesis_StudentHome_GuideContentActivity context;
	private Holder holder;
	private class Holder {
		TextView GuideContent;
		TextView GuideDate;
		TextView TeacherSign;
	}
	Thesis_StudentHome_GuideContentAdapter(Activity mActivity, List<Map<String, String>> data){
		this.context = (Thesis_StudentHome_GuideContentActivity) mActivity;
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

		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(context, R.layout.thesis_item_studenthome_guidecontent, null);
			holder.GuideContent = (TextView) convertView
					.findViewById(R.id.Thesis_StuHome_GuideContent_item_TextViw);
			holder.GuideDate = (TextView) convertView
					.findViewById(R.id.Thesis_StuHome_GuideContent_item_time);
			holder.TeacherSign = (TextView) convertView
					.findViewById(R.id.Thesis_StuHome_GuideContent_item_sign);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.GuideContent.setText(data.get(position).get("GuideContent"));
		holder.GuideDate.setText(data.get(position).get("RecordTime"));
		if(!data.get(position).get("TeacherSign").equals("")){
			holder.TeacherSign.setText(data.get(position).get("TeacherSign"));
		}
		return convertView;
	}
	

}

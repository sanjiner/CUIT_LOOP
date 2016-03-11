package com.example.PCenter.adapter;

import java.util.List;
import java.util.Map;

import com.exam.ThreeSystem.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
//教师打分 更填写评语   ，，，，
public class Homework_tea_class_activity extends BaseAdapter {
	private List<Map<String, String>> data;
	private Context context;
	public Homework_tea_class_activity(Context context, List<Map<String, String>> data) {
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
			convertView = View.inflate(context, R.layout.activity_homework_review_details, null);
			holder.WorkName=(TextView) convertView.findViewById(R.id.homeworkDetail_title);
			holder.WorkScore=(TextView) convertView.findViewById(R.id.stu_WorkScore);
			holder.WorkContent=(TextView) convertView.findViewById(R.id.stu_title);
			holder.WorkMemo=(TextView) convertView.findViewById(R.id.stu_memo);
			holder.StuAnswer=(TextView) convertView.findViewById(R.id.stu_daan);
			holder.uploadTime=(TextView) convertView.findViewById(R.id.stu_updatetime);
			holder.StuNumber = (TextView) convertView
					.findViewById(R.id.tv_more_class_id);
			holder.StuName = (TextView) convertView
					.findViewById(R.id.tv_more_class_name);
			holder.DeadTime = (TextView) convertView
					.findViewById(R.id.stu_outoftime);
			
			holder.StuNumber.setText(data.get(position).get("StuNumber"));
			holder.StuName.setText(data.get(position).get("StuName"));
			holder.DeadTime.setText(data.get(position).get("DeadTime"));
			holder.WorkName.setText(data.get(position).get("WorkName"));
			holder.WorkScore.setText(data.get(position).get("WorkScore"));
			holder.WorkContent.setText(data.get(position).get("WorkContent"));
			holder.WorkMemo.setText(data.get(position).get("WorkMemo"));
			holder.uploadTime.setText(data.get(position).get("uploadTime"));
			holder.StuAnswer.setText(data.get(position).get("StuAnswer"));

			
			
		}
		else 
		{
			holder = (Holder) convertView.getTag();
		}
		
		
		return convertView;
	}
	
	private class Holder {
		
		TextView StuName;
		TextView StuNumber;
		TextView DeadTime;
		TextView WorkName;
		TextView WorkScore;
		TextView WorkContent;
		TextView WorkMemo;
		TextView StuAnswer;
		TextView uploadTime;
		
		
	}

}



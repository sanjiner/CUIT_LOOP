package com.example.PCenter.adapter;

import java.util.List;
import java.util.Map;

import com.exam.ThreeSystem.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.Animation.AnimationListener;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

public class RecordScoreDetail_Tea_Adapter extends BaseAdapter{
	private List<Map<String, String>> data;
	private Context context;
	private String str, number;
	private TextView TvNo;
	
	public  RecordScoreDetail_Tea_Adapter(Context context, List<Map<String, String>> data, String str ,String number) {
		this.context = context;
		this.data = data;
		this.str = str;
		this.number = number;
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
	public View getView(final int position,  View convertView, ViewGroup parent) {
		View v = LayoutInflater.from(context).inflate(R.layout.activity_record_students_score_stu_detail, null);
		final Holder holder;
		if (convertView == null) {
			holder = new Holder();
			convertView = View.inflate(context, R.layout.record_score_detail_item, null);
			holder.time = (TextView) convertView.findViewById(R.id.Record_score_detail_time);
			holder.score = (TextView) convertView.findViewById(R.id.Record_score_detail_score);	
			holder.del = (Button) convertView.findViewById(R.id.record_detail_del);	
			TvNo = (TextView)v.findViewById(R.id.record_no);
			convertView.setTag(holder);
		} else {
			holder = (Holder) convertView.getTag();
		}
		holder.time.setText(data.get(position).get("date"));
		if(!data.get(position).get("score").equals("0")){
			holder.score.setText(data.get(position).get("score") + "分");
		}else{
			if(data.get(position).get("scoreGrade").equals("YES")){
				holder.score.setText("已到");
			}else{
				holder.score.setText(data.get(position).get("scoreGrade")+"级" );
			}
		}
		holder.del.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onRightItemClick(v, position);
                    holder.del.setVisibility(View.INVISIBLE);
                    Animation animation=AnimationUtils.loadAnimation(context, R.anim.photo_dialog_out_anim);
                    View view = View.inflate(context, R.layout.record_score_detail_item, null);
                    animation.setAnimationListener(new AnimationListener() {
            			@Override
            			public void onAnimationStart(Animation animation) {
            			}
            			@Override
            			public void onAnimationRepeat(Animation animation) {
            				
            			}
            			@Override
            			public void onAnimationEnd(Animation animation) {
            			}
            		});
                    view.startAnimation(animation);
                }
            }
        });
		return convertView;
		
	}
	
	private class Holder {
		TextView time;
		TextView score;
		Button del;
	}
	private onRightItemClickListener mListener = null;
	public void setOnRightItemClickListener(onRightItemClickListener listener){
    	mListener = listener;
    	
    }
	public interface onRightItemClickListener {
        void onRightItemClick(View v, int position);
    }
}

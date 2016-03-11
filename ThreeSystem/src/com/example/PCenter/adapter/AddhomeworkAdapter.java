package com.example.PCenter.adapter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.exam.ThreeSystem.R;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

public class AddhomeworkAdapter extends BaseAdapter {
	private List<Map<String, String>> data;
	private Context context;
	private static HashMap<Integer,Boolean> isSelected; 
	public  static List<Map<String, String>> List;
	Map<String, String> map = new HashMap<String, String>();
	 public static HashMap<Integer, Boolean> state = new HashMap<Integer, Boolean>();
	public AddhomeworkAdapter(Context context, List<Map<String, String>> data) {
		this.context = context;
		this.data = data;
		isSelected = new HashMap<Integer, Boolean>(); 
		
		initDate();
	}

	 
	public  int getCount() {
		return data.size();
	}

	@Override
	public Object getItem(int position) {
		return data.get(position);
	}
	private void initDate(){  
        for(int i=0; i<data.size();i++) {  
            getIsSelected().put(i,false);  
        }  
    } 
	@Override
	public long getItemId(int position) {
		return position;
	}
	
	
	public static HashMap<Integer,Boolean> getIsSelected() {  
        return isSelected;  
    } 
	public static void setIsSelected(HashMap<Integer,Boolean> isSelected) {  
		AddhomeworkAdapter.isSelected = isSelected;  
    }
	

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		Holder holder;
		
		
		if (convertView == null) {
			
			holder = new Holder();
			convertView = View.inflate(context, R.layout.addhomework_adapter_layout, null);
			holder.TeachingClassName = (TextView) convertView.findViewById(R.id.tvTitle);
			holder.addHomework_className = (CheckBox) convertView.findViewById(R.id.addHomework_className);	
			convertView.setTag(holder);
			
		} else {
			holder = (Holder) convertView.getTag();
		}
		
//		CheckBox check = (CheckBox) convertView.findViewById(R.id.addHomework_className);

		holder.addHomework_className.setOnCheckedChangeListener(new OnCheckedChangeListener() {
			@Override
			public void onCheckedChanged(CompoundButton buttonView,
			boolean isChecked) {
			// TODO Auto-generated method stub
			if (isChecked) {
				
				state.put(position, isChecked);
			} else {
				
				state.remove(position);
			}
			}
			});
		holder.addHomework_className.setChecked((state.get(position) == null ? false : true));
		holder.TeachingClassName.setText(data.get(position).get("TeachingClassName"));	
//		holder.addHomework_className.setChecked(getIsSelected().get(position));
		
		
		return convertView;
	}
	
	private class Holder {
		
		TextView TeachingClassName;
		CheckBox addHomework_className;
	}
	
}

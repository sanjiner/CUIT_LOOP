package com.example.PCenter.adapter;

import java.util.List;  

import android.content.Context;  
import android.view.View;  
import android.view.ViewGroup;  
import android.widget.BaseAdapter;  
import android.widget.TextView;

public class LvAdapter extends BaseAdapter {
	private List<String> list;  
    private Context context;  
  
    public LvAdapter(List<String> list, Context context) {  
        this.list = list;  
        this.context = context;  
    }
	@Override
	public int getCount() {
		// TODO 自动生成的方法存根
		 return list.size();  
	}

	@Override
	public Object getItem(int position) {
		// TODO 自动生成的方法存根
		 return list.get(position);  
		 }

	@Override
	public long getItemId(int position) {
		// TODO 自动生成的方法存根
		return position; 
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO 自动生成的方法存根
		return null;
	}

}

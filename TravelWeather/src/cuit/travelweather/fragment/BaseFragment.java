package cuit.travelweather.fragment;

import cuit.travelweather.util.Constant;
import cuit.travelweather.util.SkinManager;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class BaseFragment extends Fragment {

	private View view;
	private LayoutInflater infater;
	private int id;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		initView();
		Log.d("TAG", "f"+Constant.temperature_night0);
		setView(inflater, container);
		initUI();
		return view;	
	}
	
	public void initUI() {
		
	}
	
	public void initView() {
		id = getID(getActivity(), getLayoutName());	
	}

	public void setView(LayoutInflater inflater, ViewGroup container) {
		view = inflater.inflate(id,container, false);
	}

	public String getLayoutName() {
		  return "main";	  
	}


	private int getID(Context context, String layoutName) {
		return SkinManager.getResourceId(context, layoutName, null, false);
	}
}

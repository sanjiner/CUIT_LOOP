package cuit.travelweather.fragment;

import cuit.travelweather.R;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/*
 * [更多]页面，用户相关设置
 */
public class MoreFragment extends Fragment {

	private View view;
	private SharedPreferences sp;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		view =  inflater.inflate(R.layout.main_more, container, false);
		TextView tv1 = (TextView) view.findViewById(R.id.more_title);
		TextView tv2 = (TextView) view.findViewById(R.id.more_infocenter);
		TextView tv3 = (TextView) view.findViewById(R.id.more_usersetting);
		TextView tv4 = (TextView) view.findViewById(R.id.more_sharemanage);
		TextView tv5 = (TextView) view.findViewById(R.id.more_feature);
		TextView tv6 = (TextView) view.findViewById(R.id.more_recommend);
		TextView tv7 = (TextView) view.findViewById(R.id.more_disclaimer);
		TextView tv8 = (TextView) view.findViewById(R.id.more_about);
		TextView tv9 = (TextView) view.findViewById(R.id.main_more_logout);
		sp= getActivity().getSharedPreferences("test", Activity.MODE_PRIVATE); 
		 String size = sp.getString("affairinfo_fontsize", "2");
		if(size.equals("1")){
			tv1.setTextSize(10);
			tv2.setTextSize(10);
			tv3.setTextSize(10);
			tv4.setTextSize(10);
			tv5.setTextSize(10);
			tv6.setTextSize(10);
			tv7.setTextSize(10);
			tv8.setTextSize(10);
			tv9.setTextSize(10);
		}else if(size.equals("2")){
		}else{
			tv1.setTextSize(30);
			tv2.setTextSize(30);
			tv3.setTextSize(30);
			tv4.setTextSize(30);
			tv5.setTextSize(30);
			tv6.setTextSize(30);
			tv7.setTextSize(30);
			tv8.setTextSize(30);
			tv9.setTextSize(30);
		}

		
		return view;
	}

}

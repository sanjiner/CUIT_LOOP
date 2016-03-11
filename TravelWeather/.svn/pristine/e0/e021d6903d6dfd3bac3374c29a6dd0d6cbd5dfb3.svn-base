package cuit.travelweather.fragment;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import cuit.travelweather.R;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.MyHttpClient;
import cuit.travelweather.util.Split;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/*
 * [天气-生活]页面，显示[生活指数]和[气压与头痛和钓鱼的关系]
 */
public class LifeFragment extends Fragment {
	private View view;
	private LayoutInflater inflater;

	private TextView index_uv;
	private TextView index_tr;
	private TextView index_co;
	private SharedPreferences sp;

	private JSONObject jsonObject;
	private TextView index_cl;
	private TextView index_cy;
	private TextView index_ss;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;// 初始化inflater，用于ViewPager添加页面
		view = inflater.inflate(R.layout.life_lifeindex, container, false);
		init();
		return view;
	}

	private void init() {
		
		sp = getActivity().getSharedPreferences("User_SP", getActivity().MODE_PRIVATE);
		////
		TextView tv1 = (TextView) view.findViewById(R.id.tv4);
//		TextView tv2 = (TextView) view.findViewById(R.id.tv2);
		TextView tv3 = (TextView) view.findViewById(R.id.tv41);
		TextView tv4 = (TextView) view.findViewById(R.id.tv5);
//		TextView tv5 = (TextView) view.findViewById(R.id.tv42);
//		TextView tv6 = (TextView) view.findViewById(R.id.tv43);
//		TextView tv7 = (TextView) view.findViewById(R.id.tv44);
//		
		
		/////
//		index_uv = (TextView) view.findViewById(R.id.index_uv);
		
		index_cy = (TextView) view.findViewById(R.id.index_cy);
		index_ss = (TextView) view.findViewById(R.id.index_ss);
		index_cl = (TextView) view.findViewById(R.id.index_cl);
		
		sp = getActivity().getSharedPreferences("test", Activity.MODE_PRIVATE); 
		 String size = sp.getString("affairinfo_fontsize", "2");
		if(size.equals("1")){
			tv1.setTextSize(10);
			tv3.setTextSize(10);
			tv4.setTextSize(10);
		}else if(size.equals("2")){
		}else{
			tv1.setTextSize(30);
			tv3.setTextSize(30);
			tv4.setTextSize(30);
		}

	
		getData();
	}



	/*
	 * 自定义ViewPager适配器
	 */
	class MyViewPagerAdapter extends PagerAdapter {
		private List<View> mListViews;

		public MyViewPagerAdapter(List<View> mListViews) {
			this.mListViews = mListViews;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView(mListViews.get(position));
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			container.addView(mListViews.get(position), 0);
			getData();
			return mListViews.get(position);
		}

		@Override
		public int getCount() {
			return mListViews.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

	}

	private void getData() {
		new AsyncTask<Void, Void, Void>() {
			@Override
			protected Void doInBackground(Void... param) {
				// TODO Auto-generated method stub
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				try {
					params.add(new BasicNameValuePair("cityNum", URLEncoder
							.encode("101270101", "UTF-8")));
//					params.add(new BasicNameValuePair("cityNum", URLEncoder
//							.encode(Constant.flag1,"UTF-8")));
				} catch (UnsupportedEncodingException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				jsonObject = MyHttpClient.getJson(getActivity(),
						Constant.getWeatherIndex, params);

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				super.onPostExecute(result);
				try {
					JSONArray jsonArray = jsonObject.getJSONArray("i");
					ArrayList<String> map;
					for (int i = 0; i < 3; i++) {
						map = new ArrayList<String>();
						JSONObject json = jsonArray.getJSONObject(i);
						if(i==0){
							index_cl.setText(json.getString("i5"));//穿衣tr
						}
						if(i==1){
							index_ss.setText(json.getString("i5"));//晨练
								}
						if(i==2){
							index_cy.setText(json.getString("i5"));//舒适
								}
					}
				

				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}.execute();
	}
	
}

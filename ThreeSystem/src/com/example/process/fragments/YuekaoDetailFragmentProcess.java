package com.example.process.fragments;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.Listener;
import com.android.volley.plus.URLTools;
import com.android.volley.plus.VolleyErrorHelper;
import com.android.volley.toolbox.StringRequest;
import com.exam.ThreeSystem.R;
import com.example.PCenter.Constant;
import com.example.PCenter.fragments.BaseFragment;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Paint.Join;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class YuekaoDetailFragmentProcess extends BaseFragment{
	private TextView day;
	private ListView lv_Detail;
	private List<Map<String, String>> Detail;
	private String mExamPlanBh;
	private SharedPreferences sp;
	private View view;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		 view = inflater.inflate(R.layout.activity_process_yuekao_detail, container, false);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		sp = getActivity().getSharedPreferences(Constant.USERINFO_SP, Context.MODE_PRIVATE);
		initView(view);
		getExamplan();
		
	}
	private void getDetail() {
		Map<String, String> params = new HashMap<String, String>();
		params.put("ExamPlanBh", mExamPlanBh);
		String url = URLTools.buildURL(Constant.PROCESS_BASUURL + "GetAppoinList", params);
		StringRequest request = new StringRequest(url, successDetail(),
				new Response.ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(
						mActivity,
						VolleyErrorHelper
						.getErrorType(error),
						Toast.LENGTH_LONG).show();
			}
		});
		executeRequest(request);
	}

	private Listener<String> successDetail() {
		return new Listener<String>() {

			@Override
			public void onResponse(String response) {
				if(!response.isEmpty())
				{
					try {
						JSONObject JsonObject = new JSONObject(response);
						if(JsonObject.getString("Success").equals("true"))
						{
							JSONArray array = JsonObject.getJSONArray("AppoinListInfo");
							JSONObject dayObject = array.getJSONObject(0);
							day.setText(dayObject.getString("DayTime"));
							
							JSONObject arrayObject = array.getJSONObject(0);
							JSONArray detail_list = arrayObject.getJSONArray("DayAppointInfo");
							Detail = new ArrayList<Map<String,String>>(); 
							for(int i = 0; i< detail_list.length(); i++)
							{
								JSONObject one = detail_list.getJSONObject(i);
								Map<String, String> map = new HashMap<String, String>();
								map.put("ExamRoom", one.getString("ExamRoom"));
								map.put("StarTime", one.getString("StarTime"));
								map.put("EndTime", one.getString("EndTime"));
								map.put("LastNum", one.getString("LastNum"));
								map.put("TotalNum", one.getString("TotalNum"));
								Detail.add(map);
							}
							if(Detail.size() != 0){
								lv_Detail.setAdapter(new YuekaoDetailAdapterProcess(getActivity(), Detail));
							}
						}
						
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				}
			}
		};
	}

	private void getExamplan() {
		String stuNo = sp.getString(Constant.SPFIELD.NAME, "");
		Map<String, String> params = new HashMap<String, String>();
		params.put("StuNumber", stuNo);
		String url = URLTools.buildURL(Constant.PROCESS_BASUURL + "GetExamplan", params);
		StringRequest request = new StringRequest(url, success(),
				new Response.ErrorListener(){
			@Override
			public void onErrorResponse(VolleyError error) {
				Toast.makeText(
						mActivity,
						VolleyErrorHelper
						.getErrorType(error),
						Toast.LENGTH_LONG).show();
			}
		});
		executeRequest(request);
	}
	private Listener<String> success() {
		return new Listener<String>() {

			@Override
			public void onResponse(String response) {
				if(!response.isEmpty())
				{
					JSONObject JsonObject;
					try {
						JsonObject = new JSONObject(response);
						if(JsonObject.getString("Success").equals("true"))
						{
							JSONArray JsonArray = JsonObject.getJSONArray("ExamPlanList");
							JSONObject one = JsonArray.getJSONObject(0);
							mExamPlanBh = one.getString("ExamPlanBh");
							getDetail();
						}
					} catch (JSONException e) {
						e.printStackTrace();
					}
					
				}
			}
		};
	}
	private void initView(View view) {
		day = (TextView) view.findViewById(R.id.yuekao_day);
		lv_Detail = (ListView) view.findViewById(R.id.yuekaoDetail_listView);

	}
}

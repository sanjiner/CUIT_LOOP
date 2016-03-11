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
import cuit.travelweather.activity.AreaChooseActivity;
import cuit.travelweather.activity.MainActivity;
import cuit.travelweather.activity.TravelLineMakedActivity;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.MyHttpClient;
import cuit.travelweather.util.Split;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ContextMenu.ContextMenuInfo;
import android.view.View.OnClickListener;
import android.view.View.OnCreateContextMenuListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.SimpleAdapter;

/*
 * [出行]页面
 */
public class TravelFragment extends Fragment {

	private View view;
	private View pageLineMaked;
	private View pageLineNew;

	private SimpleAdapter adapter;;

	private JSONObject jsonObject;
	private JSONObject jsonObject2;
	private RadioGroup radioGroup;
	private ListView listView;
	private LinearLayout line_rl_from;
	private LinearLayout line_rl_to;
	private LinearLayout line_rl_node1;
	private LinearLayout line_rl_node2;
	private LinearLayout line_rl_node3;

	private TextView line_tv_from;
	private TextView line_tv_to;
	private TextView line_tv_node1;
	private TextView line_tv_node2;
	private TextView line_tv_node3;
	private TextView line_tv_clear;
	private Button add_button;
	private LayoutInflater inflater;
	private int status;
	private int status2;
	private String routeid;

	private SharedPreferences sp;
	private Editor editor;
	String nodes;
	private static final int SHOW_PROCESS = 1;
	private static final int DISMISS_PROCESS = 0;
	private static ProgressDialog pdialog;
	private static Handler handler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_PROCESS:
				pdialog.show();
				break;
			case DISMISS_PROCESS:
				pdialog.dismiss();
				break;
			}
		}

	};

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		this.inflater = inflater;
		view = inflater.inflate(R.layout.main_travel, container, false);
		init();
		return view;
	}

	/*
	 * 初始化view
	 */
	@SuppressWarnings("static-access")
	private void init() {

		sp = getActivity().getSharedPreferences("User_SP",
				getActivity().MODE_PRIVATE);
		editor = sp.edit();

		pageLineMaked = view.findViewById(R.id.travel_page_line_maked);
		pageLineNew = view.findViewById(R.id.travel_page_line_new);
		listView = (ListView) view.findViewById(R.id.travle_list);
		radioGroup = (RadioGroup) view.findViewById(R.id.travel_tabs);
		line_tv_from = (TextView) view.findViewById(R.id.line_tv_from);
		line_tv_to = (TextView) view.findViewById(R.id.line_tv_to);
		line_tv_node1 = (TextView) view.findViewById(R.id.line_tv_node1);
		line_tv_node2 = (TextView) view.findViewById(R.id.line_tv_node2);
		line_tv_node3 = (TextView) view.findViewById(R.id.line_tv_node3);
		line_tv_clear = (TextView) view.findViewById(R.id.line_tv_clear);
		line_rl_from = (LinearLayout) view.findViewById(R.id.line_rl_from);
		line_rl_to = (LinearLayout) view.findViewById(R.id.line_rl_to);
		line_rl_node1 = (LinearLayout) view.findViewById(R.id.line_rl_node1);
		line_rl_node2 = (LinearLayout) view.findViewById(R.id.line_rl_node2);
		line_rl_node3 = (LinearLayout) view.findViewById(R.id.line_rl_node3);
		add_button = (Button) view.findViewById(R.id.add_button);

		radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

			@Override
			public void onCheckedChanged(RadioGroup arg0, int arg1) {
				// TODO Auto-generated method stub
				switch (arg1) {
				case R.id.travel_line_maked:
					pageLineNew.setVisibility(View.INVISIBLE);
					pageLineMaked.setVisibility(View.VISIBLE);
					break;
				case R.id.travel_line_new:
					pageLineMaked.setVisibility(View.INVISIBLE);
					pageLineNew.setVisibility(View.VISIBLE);
					break;
				}
			}
		});

		listView.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				@SuppressWarnings("unchecked")
				HashMap<String, Object> map = (HashMap<String, Object>) parent
						.getItemAtPosition(position);
				String routeid = (String) map.get("routeid");
				Intent intent = new Intent();
				intent.putExtra("routeid", routeid);
				intent.setClass(getActivity(), TravelLineMakedActivity.class);
				getActivity().startActivity(intent);

			}
		});

		line_rl_from.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), AreaChooseActivity.class);
				intent.putExtra("listType",
						AreaChooseActivity.LIST_TYPE_PROVINCE);
				intent.putExtra("backActivityCode", 100);
				getActivity().startActivityForResult(intent, 100);
			}
		});

		line_rl_to.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), AreaChooseActivity.class);
				intent.putExtra("listType",
						AreaChooseActivity.LIST_TYPE_PROVINCE);
				intent.putExtra("backActivityCode", 101);
				getActivity().startActivityForResult(intent, 101);
			}
		});

		line_rl_node1.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent();
				intent.setClass(getActivity(), AreaChooseActivity.class);
				intent.putExtra("listType",
						AreaChooseActivity.LIST_TYPE_PROVINCE);
				intent.putExtra("backActivityCode", 102);
				getActivity().startActivityForResult(intent, 102);
			}
		});

		line_rl_node2.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!line_tv_node1.getText().toString().trim().equals("")) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), AreaChooseActivity.class);
					intent.putExtra("listType",
							AreaChooseActivity.LIST_TYPE_PROVINCE);
					intent.putExtra("backActivityCode", 103);
					getActivity().startActivityForResult(intent, 103);
				} else {
					Toast.makeText(getActivity(), "请先填写途径点一", Toast.LENGTH_LONG)
							.show();
				}
			}
		});

		line_rl_node3.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (!line_tv_node2.getText().toString().trim().equals("")) {
					Intent intent = new Intent();
					intent.setClass(getActivity(), AreaChooseActivity.class);
					intent.putExtra("listType",
							AreaChooseActivity.LIST_TYPE_PROVINCE);
					intent.putExtra("backActivityCode", 104);
					getActivity().startActivityForResult(intent, 104);
				} else {
					Toast.makeText(getActivity(), "请先填写途径点二", Toast.LENGTH_LONG)
							.show();
				}

			}
		});

		add_button.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				setRouteData();
			}
		});

		line_tv_clear.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				line_tv_from.setText("");
				line_tv_to.setText("");
				line_tv_node1.setText("");
				line_tv_node2.setText("");
				line_tv_node3.setText("");
			}
		});

		listView.setOnItemLongClickListener(new OnItemLongClickListener() {

			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View view,
					int position, long id) {
				// TODO Auto-generated method stub
				HashMap<String, Object> map = (HashMap<String, Object>) parent
						.getItemAtPosition(position);
				routeid = (String) map.get("routeid");
				return false;
			}
		});
		listView.setOnCreateContextMenuListener(new OnCreateContextMenuListener() {

			@Override
			public void onCreateContextMenu(ContextMenu menu, View v,
					ContextMenuInfo menuInfo) {
				// TODO Auto-generated method stub
				menu.setHeaderTitle("是否要删除？");
				menu.add(0, 5, 0, "是");
				menu.add(0, 6, 0, "否");
			}
		});

	}

	private void getRouteData() {
		pdialog = new ProgressDialog(getActivity());
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("正在加载路况中……");
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... param) {
				// TODO Auto-generated method stub
				handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
				ArrayList<HashMap<String, Object>> data = new ArrayList<HashMap<String, Object>>();
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				try {
					params.add(new BasicNameValuePair("userID", URLEncoder
							.encode(sp.getString("userId",
									"40282b8d4324a89e014324a8a06d0001"),
									"UTF-8")));
				} catch (UnsupportedEncodingException e) {
					System.out.print(e.getMessage());
				}

				adapter = getAdapter(data, Constant.getLines, params);

				return null;
			}

			@Override
			protected void onPreExecute() {
				// TODO Auto-generated method stub
				super.onPreExecute();
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));
				listView.setAdapter(adapter);
			}

		}.execute();

	}

	private SimpleAdapter getAdapter(ArrayList<HashMap<String, Object>> data,
			String method, List<NameValuePair> params) {

		JSONObject jsonObject = MyHttpClient.getJson(getActivity(), method,
				params);
		try {
			status = jsonObject.getInt("status");
			if (status == 1) {
				JSONArray jsonArray = jsonObject.getJSONArray("RouteList");
				HashMap<String, Object> map = null;
				for (int i = 0; i < jsonArray.length(); i++) {
					JSONObject routeJson = jsonArray.getJSONObject(i);
					map = new HashMap<String, Object>();
					String tui = "";
					map.put("routeid", routeJson.getString("routeid"));
					JSONArray nodesArray = routeJson.getJSONArray("nodes");
					for (int j = 0; j < nodesArray.length(); j++) {
						JSONObject nodesJson = nodesArray.getJSONObject(j);
						if (j == 0) {
							map.put("start",
									Split.cut2(nodesJson.getString("addr")));
						}
						if (j == nodesArray.length() - 1) {
							map.put("end",
									Split.cut2(nodesJson.getString("addr")));
						}
						tui = tui + " "
								+ Split.cut2(nodesJson.getString("addr"));
						if (j == nodesArray.length() - 2 && j != 0) {
							map.put("node", tui);
						}
					}
					data.add(i, map);
				}

				adapter = new SimpleAdapter(getActivity(), data,
						R.layout.travle_list_itme, new String[] { "start",
								"end", "node" }, new int[] { R.id.line_start,
								R.id.line_end, R.id.line_nodes });
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return adapter;
	}

	private void setRouteData() {

		String start = line_tv_from.getText().toString().trim();
		String end = line_tv_to.getText().toString().trim();
		String node1 = line_tv_node1.getText().toString().trim();
		String node2 = line_tv_node2.getText().toString().trim();
		String node3 = line_tv_node3.getText().toString().trim();

		if (start.equals("") || end.equals("")) {
			Toast.makeText(getActivity(), "目的地和出发地为必填地址", Toast.LENGTH_SHORT)
					.show();
			return;
		} else {
			if (node1.equals("") && node2.equals("") && node3.equals("")) {
				nodes = "[{\"addr\":" + "\"" + start
						+ "\",\"lng\":\"123\",\"lat\":\"123\"}" + ",{\"addr\":"
						+ "\"" + end + "\",\"lng\":\"123\",\"lat\":\"123\"}"
						+ "]";
			} else {
				if (node2.equals("") && node3.equals("")) {
					nodes = "[{\"addr\":" + "\"" + start
							+ "\",\"lng\":\"123\",\"lat\":\"123\"}"
							+ ",{\"addr\":" + "\"" + node1
							+ "\",\"lng\":\"123\",\"lat\":\"123\"}"
							+ ",{\"addr\":" + "\"" + end
							+ "\",\"lng\":\"123\",\"lat\":\"123\"}" + "]";
				} else {
					if (node3.equals("")) {
						nodes = "[{\"addr\":" + "\"" + start
								+ "\",\"lng\":\"123\",\"lat\":\"123\"}"
								+ ",{\"addr\":" + "\"" + node1
								+ "\",\"lng\":\"123\",\"lat\":\"123\"}"
								+ ",{\"addr\":" + "\"" + node2
								+ "\",\"lng\":\"123\",\"lat\":\"123\"}"
								+ ",{\"addr\":" + "\"" + end
								+ "\",\"lng\":\"123\",\"lat\":\"123\"}" + "]";
					} else if (node3 != null) {
						nodes = "[{\"addr\":" + "\"" + start
								+ "\",\"lng\":\"123\",\"lat\":\"123\"}"
								+ ",{\"addr\":" + "\"" + node1
								+ "\",\"lng\":\"123\",\"lat\":\"123\"}"
								+ ",{\"addr\":" + "\"" + node2
								+ "\",\"lng\":\"123\",\"lat\":\"123\"}"
								+ ",{\"addr\":" + "\"" + node3
								+ "\",\"lng\":\"123\",\"lat\":\"123\"}"
								+ ",{\"addr\":" + "\"" + end
								+ "\",\"lng\":\"123\",\"lat\":\"123\"}" + "]";
					}
				}
			}
		}

		new AsyncTask<Void, Void, JSONObject>() {

			@Override
			protected JSONObject doInBackground(Void... param) {
				// TODO Auto-generated method stub
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				try {
					params.add(new BasicNameValuePair("userID", URLEncoder
							.encode(sp.getString("userId",
									"40282b8d4324a89e014324a8a06d0001"),
									"UTF-8")));
					params.add(new BasicNameValuePair("nodes", URLEncoder
							.encode(nodes, "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					System.out.print(e.getMessage());
				}

				return MyHttpClient.getJson(getActivity(),
						Constant.routeLinesDefine, params);
			}

			@Override
			protected void onPostExecute(JSONObject result) {
				// TODO Auto-generated method stub
				try {
					status = result.getInt("status");
					if (result.getInt("status") == 1) {
						Toast a = Toast.makeText(getActivity(), "定制路线成功",
								Toast.LENGTH_SHORT);
						a.show();
						radioGroup.check(R.id.travel_line_maked);
						pageLineNew.setVisibility(View.INVISIBLE);
						pageLineMaked.setVisibility(View.VISIBLE);
						getRouteData();
					} else {
						Toast a = Toast.makeText(getActivity(), "定制路线失败",
								Toast.LENGTH_SHORT);
						a.show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				super.onPostExecute(result);
			}
		}.execute();

	}

	@Override
	public void setUserVisibleHint(boolean isVisibleToUser) {
		// TODO Auto-generated method stub
		super.setUserVisibleHint(isVisibleToUser);
		if (isVisibleToUser) {
			getRouteData();
		}
	}

	// 长按菜单响应函数
	@Override
	public boolean onContextItemSelected(MenuItem item) {
		// setTitle("点击了长按菜单里面的第"+item.getItemId()+"个项目");
		if (item.getItemId() == 5) {
			deleteCity();
		}
		return super.onContextItemSelected(item);
	}

	private void deleteCity() {
		// TODO Auto-generated method stub
		pdialog = new ProgressDialog(getActivity());
		pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
		pdialog.setCancelable(false);
		pdialog.setMessage("正在删除中……");
		new AsyncTask<Void, Void, Void>() {

			@Override
			protected Void doInBackground(Void... param) {
				// TODO Auto-generated method stub
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				try {
					params.add(new BasicNameValuePair("userID", URLEncoder
							.encode(sp.getString("userId",
									"40282b8d4324a89e014324a8a06d0001"),
									"UTF-8")));
					params.add(new BasicNameValuePair("routeID", URLEncoder
							.encode(routeid, "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					System.out.print(e.getMessage());
				}

				jsonObject2 = MyHttpClient.getJson(getActivity(),
						Constant.routeLinesDelete, params);

				return null;
			}

			@Override
			protected void onPostExecute(Void result) {
				// TODO Auto-generated method stub
				try {
					status2 = jsonObject2.getInt("status");
					if (status2 == 1) {
						Toast.makeText(getActivity(), "删除成功",
								Toast.LENGTH_SHORT).show();
						
						getRouteData();
					} else {
						Toast.makeText(getActivity(), "删除出错",
								Toast.LENGTH_SHORT).show();
					}
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}

		}.execute();

	}

}

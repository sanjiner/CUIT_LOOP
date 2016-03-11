package cuit.travelweather.activity;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import cuit.travelweather.R;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.MyHttpClient;

@SuppressLint("NewApi")
public class MyDialogFragment extends DialogFragment {
	public EditText maxtep = null;
	public EditText mintep = null;
	public TextView tv = null;
	private SharedPreferences sp;
	private static final int SHOW_PROCESS = 1;
	private static final int DISMISS_PROCESS = 0;
	String[] months_big = { "1", "3", "5", "7", "8", "10", "12" };
	String[] months_little = { "4", "6", "9", "11" };
	List list_big = Arrays.asList(months_big);
	List list_little = Arrays.asList(months_little);
	private Context context = getActivity();
	List<NameValuePair> param = new ArrayList<NameValuePair>();

	public static MyDialogFragment newInstance(String fragmentNumber) {
		MyDialogFragment newInstance = new MyDialogFragment();
		Bundle args = new Bundle();
		args.putString("fragnum", fragmentNumber);
		newInstance.setArguments(args);
		return newInstance;
	}

	// 获得明天日期
	private String getTomoData() {
		Calendar calendar = Calendar.getInstance();
		int year = calendar.get(Calendar.YEAR);
		int month = calendar.get(Calendar.MONTH) + 1;
		int day = calendar.get(Calendar.DATE);
		if (day == 30) {
			if (list_big.contains(String.valueOf(month))) {
				day = 31;
			}
			if (list_little.contains(String.valueOf(month))) {
				day = 1;
				if (month == 12) {
					year++;
					month = 1;
				} else {
					month++;
				}

			}
		} else if (day == 31) {
			day = 1;
			if (month == 12) {
				year++;
				month = 1;
			} else {
				month++;
			}

		} else {
			day++;
		}
		String data = year + "-" + month + "-" + day;
		return data;
	}

	@Override
	public Dialog onCreateDialog(Bundle savedInstanceState) {
		LayoutInflater li = (LayoutInflater) LayoutInflater.from(getActivity());
		final View edit = li.inflate(R.layout.edit_dialog, null);
		tv = (TextView) edit.findViewById(R.id.data_tv);
		tv.setText(getTomoData());
		AlertDialog.Builder dialog4 = new AlertDialog.Builder(getActivity());
		dialog4.setIcon(android.R.drawable.btn_star).setTitle("我猜天气")
				.setView(edit);
		dialog4.setPositiveButton("确定", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				maxtep = (EditText) edit.findViewById(R.id.txt_maxtep);
				mintep = (EditText) edit.findViewById(R.id.txt_mintep);
				String max = maxtep.getText().toString();
				String min = mintep.getText().toString();
				if (max.equals("") || max.trim() == null && min.equals("")
						|| min.trim() == null) {
					Toast tot = Toast.makeText(getActivity(), "请输入数字!",
							Toast.LENGTH_LONG);
					tot.show();
					return;
				}
				int maxi = Integer.parseInt(max);
				int mini = Integer.parseInt(min);
				if (maxi <= mini) {
					Toast tt = Toast.makeText(getActivity(), "输入格式非法!",
							Toast.LENGTH_LONG);
					tt.show();
					return;
				}
				sp = getActivity().getSharedPreferences("User_SP",
						getActivity().MODE_PRIVATE);
				String userID = sp.getString("userName", "");
				try {
					param.add(new BasicNameValuePair("userID", URLEncoder
							.encode(userID, "UTF-8")));
					param.add(new BasicNameValuePair("highestTemperature",
							URLEncoder.encode(max, "UTF-8")));
					param.add(new BasicNameValuePair("lowestTemperature",
							URLEncoder.encode(min, "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					System.out.print(e.getMessage());
				}
				new Thread() {
					@Override
					public void run() {
						JSONObject jsonObject = MyHttpClient.getJson(
								getActivity(), Constant.guessWeather, param);
					}
				}.start();

				Toast toast = Toast.makeText(getActivity(), "提交成功!",
						Toast.LENGTH_LONG);

				toast.show();
				return;
			}
		}).setNegativeButton("取消", null);

		return dialog4.create();
	}
}

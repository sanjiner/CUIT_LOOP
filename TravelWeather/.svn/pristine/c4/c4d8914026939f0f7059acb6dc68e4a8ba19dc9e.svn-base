package com.dcs.test.Activity;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import cuit.travelweather.R;
import android.app.Activity;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.LinearLayout.LayoutParams;
public class CalendarGridViewAdapter extends BaseAdapter {
	private Calendar calStartDate = Calendar.getInstance();// 当前显示的日历
	private Calendar calSelected = Calendar.getInstance(); // 选择的日历
	private int countsReceived;
	private Date[] listReceived;
	private int iMonthViewCurrentYearF = 0;

	public void setSelectedDate(Calendar cal) {
		calSelected = cal;
	}

	private Calendar calToday = Calendar.getInstance(); // 今日
	private int iMonthViewCurrentMonth = 0; // 当前视图月

	private void UpdateStartDateForMonth() {
		calStartDate.set(Calendar.DATE, 1); // 设置成当月第一天
		iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);// 得到当前日历显示的月
		int iDay = 0;
		int iFirstDayOfWeek = Calendar.MONDAY;
		int iStartDay = iFirstDayOfWeek;
		if (iStartDay == Calendar.MONDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
			if (iDay < 0)
				iDay = 6;
		}
		if (iStartDay == Calendar.SUNDAY) {
			iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
			if (iDay < 0)
				iDay = 6;
		}
		calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);
		calStartDate.add(Calendar.DAY_OF_MONTH, -1);// 周日第一位

	}

	ArrayList<java.util.Date> titles;

	private ArrayList<java.util.Date> getDates() {
		UpdateStartDateForMonth();
		ArrayList<java.util.Date> alArrayList = new ArrayList<java.util.Date>();
		for (int i = 1; i <= 42; i++) {
			alArrayList.add(calStartDate.getTime());
			calStartDate.add(Calendar.DAY_OF_MONTH, 1);
		}
		return alArrayList;
	}
	private Activity activity;
	Resources resources;
	public CalendarGridViewAdapter(Activity a, Calendar cal, Date list[],
			int in, int iMonthViewCurrentYear) {
		calStartDate = cal;
		activity = a;
		resources = activity.getResources();
		titles = getDates();
		countsReceived = in;
		listReceived = list;
		iMonthViewCurrentYearF = iMonthViewCurrentYear;
	}
	public CalendarGridViewAdapter(Activity a) {
		activity = a;
		resources = activity.getResources();
	}
	@Override
	public int getCount() {
		return titles.size();
	}
	@Override
	public Object getItem(int position) {
		return titles.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		LinearLayout iv = new LinearLayout(activity);
		iv.setId(position + 5000);
		LinearLayout imageLayout = new LinearLayout(activity);
		imageLayout.setOrientation(0);
		iv.setGravity(Gravity.CENTER);
		iv.setOrientation(1);
		iv.setBackgroundColor(resources.getColor(R.color.seagreen));

		Date myDate = (Date) getItem(position);
		Calendar calCalendar = Calendar.getInstance();
		calCalendar.setTime(myDate);
		final int iMonth = calCalendar.get(Calendar.MONTH);// iMonth保存的是什么东西呢。Calendar.MONTH的
		final int iDay = calCalendar.get(Calendar.DAY_OF_WEEK);
		// 判断周六周日
		iv.setBackgroundColor(resources.getColor(R.color.white));
		if (iDay == 7) {
			// 周六
			iv.setBackgroundColor(resources.getColor(R.color.text_6));
		} else if (iDay == 1) {
			// 周日
			iv.setBackgroundColor(resources.getColor(R.color.text_7));
		} else {
		}
		// 判断周六周日结束
		TextView txtToDay = new TextView(activity);// 日本老黄历
		txtToDay.setGravity(Gravity.CENTER_HORIZONTAL);
		txtToDay.setTextSize(9);
		// 设置背景颜色结束
		// 日期开始
		TextView txtDay = new TextView(activity);// 日期
		txtDay.setGravity(Gravity.CENTER_HORIZONTAL);
		if (equalsDate(calToday.getTime(), myDate)) {
			// 当前日期
			iv.setBackgroundColor(resources.getColor(R.color.event_center));
			txtToDay.setText("TODAY!");
		}
		// 设置背景颜色
		if (equalsDate(calSelected.getTime(), myDate)) {
			iv.setBackgroundColor(resources.getColor(R.color.selection));
		} else if (equalsDate(calToday.getTime(), myDate)) {
			// 当前日期
			iv.setBackgroundColor(resources.getColor(R.color.calendar_zhe_day));
		}
		TextView t = new TextView(activity);// 猜中的
		for (int i = 0; i < countsReceived; i++) {
			if (equalsDate(listReceived[i], myDate)) {
				iv.setBackgroundColor(resources.getColor(R.color.green));
			}
		}
		if (iMonth == iMonthViewCurrentMonth) {
		}
		// 判断是否是当前月
		if (iMonth == iMonthViewCurrentMonth) {
			txtToDay.setTextColor(resources.getColor(R.color.ToDayText));
			txtDay.setTextColor(resources.getColor(R.color.red));
		} else {
			txtDay.setTextColor(resources.getColor(R.color.noMonth));// noMonth显示本页之中其他的颜色
			txtToDay.setTextColor(resources.getColor(R.color.noMonth));// 显示本页之中其他的颜色
		}
		int day = myDate.getDate(); // 日期
		txtDay.setText(String.valueOf(day));
		txtDay.setId(position + 500);
		iv.setTag(myDate);// myDate中保存是整个时间
		LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		iv.addView(txtDay, lp);
		LinearLayout.LayoutParams lp1 = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		iv.addView(txtToDay, lp1);
		LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams(
				LayoutParams.FILL_PARENT, LayoutParams.WRAP_CONTENT);
		iv.addView(t, lp2);
		return iv;
	}

	@Override
	public void notifyDataSetChanged() {
		super.notifyDataSetChanged();
	}

	private Boolean equalsDate(Date date1, Date date2) {
		if (date1.getYear() == date2.getYear()
				&& date1.getMonth() == date2.getMonth()
				&& date1.getDate() == date2.getDate()) {
			return true;
		} else {
			return false;
		}
	}
}
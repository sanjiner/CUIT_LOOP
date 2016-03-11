package edu.cuit.common.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateFormat {
	public static Timestamp getTimestampTime(Date date){
		Timestamp CreateDate = Timestamp.valueOf(getStrTime(date,1));
		return CreateDate;
	}
	public static Date strToDate(String strDate){
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		Date date = null;
		try {
			date = format.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	public static String getStrTime(Date date,int type)
	{
		String t = null;
		SimpleDateFormat format = null;
		switch (type)
		{
			case 0:
				format = new SimpleDateFormat("yyyyMMddHHmmss");
				break;
			case 1:
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				break;
			case 2:
				format = new SimpleDateFormat("yyyy年MM月dd日 HH:mm:ss");
				break;
			case 3:
				format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分ss秒");
				break;
			case 4:
				format = new SimpleDateFormat("yyyy-MM-dd");
				break;
			case 5:
				format = new SimpleDateFormat("yyyy-M-d HH:mm:ss");
				break;
			case 6:
				format = new SimpleDateFormat("yyyyMMdd");
				break;
			case 7:
				format = new SimpleDateFormat("yyyy-MM");
				break;
			case 8:
				format = new SimpleDateFormat("HH:mm:ss");
				break;
			default:
				format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				break;
		}
		t = format.format(date);
		return t;
	}
}

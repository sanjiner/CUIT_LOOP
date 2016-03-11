package cuit.travelweather.util;

public class Split {
	static String[] strarray;
	static String[] strarray2; // 进行二次分割所需要的函数；
	/**
	 * 返回后2个城市
	 */
	public static String cut(String str) {
		strarray = new String[] {};
		strarray = str.split(",", 2);
		return strarray[1];
	}

	/**
	 * 返回第3个城市
	 */
	public static String cut2(String str) {
		strarray = new String[] {};
		strarray = str.split(",");
		return strarray[2];
	}

	/**
	 * 返回第1个城市
	 */
	public static String cut_r1(String str) {
		strarray = new String[] {};
		strarray = str.split(",");
		return strarray[0];
	}

	/**
	 * 返回第2个城市
	 */
	public static String cut_r2(String str) {
		strarray = new String[] {};
		strarray = str.split(",");
		return strarray[1];
	}

	/**
	 * 返回全部城市
	 */
	public static String cut_r_all(String str) {
		strarray = new String[] {};
		strarray = str.split(",");
		return strarray[0] + strarray[1] + strarray[2];
	}
	
	/**
	 * 返回最高气温
	 */
	public static String cutTemp_rTop(String str) {
		strarray = new String[] {};
		strarray2 = new String[] {};
		strarray = str.split("~");
		strarray2 = strarray[0].split("℃");
		return strarray2[0];
	}
	/**
	 * 返回最低气温
	 */
	public static String cutTemp_rLow(String str) {
		strarray = new String[] {};
		strarray2 = new String[] {};
		strarray = str.split("~");
		strarray2 = strarray[1].split("℃");
		return strarray2[0];
	}
	
	/**
	 * 返回城市 分隔符“；”
	 */
	public static String cutCity(String str) {
		strarray = new String[] {};
		strarray = str.split(":");
		return strarray[0];
	}
}

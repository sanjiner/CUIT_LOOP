package cuit.travelweather.util;

public class Split {
	static String[] strarray;
	static String[] strarray2; // ���ж��ηָ�����Ҫ�ĺ�����
	/**
	 * ���غ�2������
	 */
	public static String cut(String str) {
		strarray = new String[] {};
		strarray = str.split(",", 2);
		return strarray[1];
	}

	/**
	 * ���ص�3������
	 */
	public static String cut2(String str) {
		strarray = new String[] {};
		strarray = str.split(",");
		return strarray[2];
	}

	/**
	 * ���ص�1������
	 */
	public static String cut_r1(String str) {
		strarray = new String[] {};
		strarray = str.split(",");
		return strarray[0];
	}

	/**
	 * ���ص�2������
	 */
	public static String cut_r2(String str) {
		strarray = new String[] {};
		strarray = str.split(",");
		return strarray[1];
	}

	/**
	 * ����ȫ������
	 */
	public static String cut_r_all(String str) {
		strarray = new String[] {};
		strarray = str.split(",");
		return strarray[0] + strarray[1] + strarray[2];
	}
	
	/**
	 * �����������
	 */
	public static String cutTemp_rTop(String str) {
		strarray = new String[] {};
		strarray2 = new String[] {};
		strarray = str.split("~");
		strarray2 = strarray[0].split("��");
		return strarray2[0];
	}
	/**
	 * �����������
	 */
	public static String cutTemp_rLow(String str) {
		strarray = new String[] {};
		strarray2 = new String[] {};
		strarray = str.split("~");
		strarray2 = strarray[1].split("��");
		return strarray2[0];
	}
	
	/**
	 * ���س��� �ָ���������
	 */
	public static String cutCity(String str) {
		strarray = new String[] {};
		strarray = str.split(":");
		return strarray[0];
	}
}

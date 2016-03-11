package cc.util.java.core;

import java.util.Locale;

/**
 * @author wangcccong
 * @version 1.140122
 * create at: 14-02-13
 */
public class CoreUtil {
	
	public static String toMD5(String instr) {
		String strMD5 = null;
		char hexDigits[] = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
			md.update(instr.getBytes());
			byte tmp[] = md.digest();
			char str[] = new char[16 * 2];
			int k = 0;
			for (int i = 0; i < 16; i++) {
				byte byte0 = tmp[i];
				str[k++] = hexDigits[byte0 >>> 4 & 0xf]; 
				str[k++] = hexDigits[byte0 & 0xf];
			}
			strMD5 = new String(str).toUpperCase(Locale.CHINA);
		} catch (Exception e) {}
		return strMD5;
		
	}
}

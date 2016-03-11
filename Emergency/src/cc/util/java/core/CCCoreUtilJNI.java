package cc.util.java.core;

/**
 * 暂不使用
 * @author wangcong
 */
public class CCCoreUtilJNI {
	
	public native static void registerNatives();
	
	static {
		try {
			System.loadLibrary("ccutil");
		} catch (Exception e) {
			// TODO: handle exception
			System.out.print(e.getMessage());
		}
	}
}

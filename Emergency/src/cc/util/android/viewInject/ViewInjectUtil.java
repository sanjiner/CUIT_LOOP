package cc.util.android.viewInject;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import cc.util.java.core.ConcurrentKKVMap;
import android.app.Activity;
import android.util.Log;
import android.view.View;

/**
 * @author wangcccong
 * @version 1.140122
 * create at: 2014-01-14
 * update at: 2014-03-17
 */
public class ViewInjectUtil {

	private ViewInjectUtil() {}

	/**
	 * injcet in activity
	 * @param t T extends Activity
	 */
	public static <T extends Activity> void inject(T t) {
		injectObject(t, new ResFinder<T>(t));
	}
	
	/**
	 * inject in View
	 * @param t T extends View
	 */
	public static <T extends View> void inject(T t) {
		injectObject(t, new ResFinder<T>(t));
	}
	
	/**
	 * injcet in obj whitch doesn't belong to t
	 * @param obj
	 * @param t T extends Activity
	 */
	public static <T extends Activity> void inject(Object obj, T t) {
		injectObject(obj, new ResFinder<T>(t));
	}
	
	/**
	 * injcet in obj whitch doesn't belong to t
	 * @param obj
	 * @param t T extends View
	 */
	public static <T extends View> void inject(Object obj, T t) {
		injectObject(obj, new ResFinder<T>(t));
	}
	
	private static void injectObject(Object obj, ResFinder<?> finder) {
		Field[] fields = obj.getClass().getDeclaredFields();
		if (fields != null && fields.length > 0) {
			for (Field field : fields) {
				ResInject viewInject = field.getAnnotation(ResInject.class);
				if (viewInject != null && viewInject.type() == ResType.View) {
					try {
						View view = finder.findViewById(viewInject.value(), viewInject.pId());
						if (view != null) {
							field.setAccessible(true);
							field.set(obj, view);
						}
					} catch (Exception e) {
						// TODO: handle exception
						Log.e("injectView", e.getMessage());
					}
				} else if (viewInject != null && viewInject.type() != ResType.View){
					try {
						Object res = finder.loadRes(viewInject.type(), viewInject.value());
						if (res != null) {
							field.setAccessible(true);
							field.set(obj, res);
						}
					} catch (Exception e) {
						// TODO: handle exception
						Log.e("injectRes", e.getMessage());
					}
				}
				
			}
		}
		
		Method[] methods = obj.getClass().getDeclaredMethods();
		if (methods != null && methods.length > 0) {
			ConcurrentKKVMap<Integer, String, Method> kkvMap = new ConcurrentKKVMap<Integer, String, Method>();
			for (Method method : methods) {
				ViewListenerInject viewListenerInject = method.getAnnotation(ViewListenerInject.class);
				if (viewListenerInject == null) continue;
				method.setAccessible(true);
				int[] values = viewListenerInject.value();
				for (int i = 0; i < values.length; i++) {
					kkvMap.put(values[i], viewListenerInject.type().name(), method);
				}
			}
			ViewListener.setAllListener(obj, finder, kkvMap);
		}
	}
}

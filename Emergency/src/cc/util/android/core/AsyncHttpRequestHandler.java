package cc.util.android.core;

/**
 * 异步请求反馈类，根据需要重写方法
 * @author wangcccong
 * @version 1.1406
 * <br>create at: Tue, 26 Aug. 2014
 */
public class AsyncHttpRequestHandler {

	/**
	 * 请求取消后调用此方法（钩子方法，具体需要用户去实现）
	 */
	protected void onCancel(){
		//do nothing 添加具体实现
	}
	
	/**
	 * 请求正在进行，需要了解当前获取数据进度调用此方法（钩子方法，具体需要用户去实现）
	 * @param totalLength
	 * @param currentLength
	 */
	protected void onProgress(final long totalLength, final long currentLength){
		//do nothing 添加具体实现
	}
}
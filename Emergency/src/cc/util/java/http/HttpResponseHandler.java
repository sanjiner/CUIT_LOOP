package cc.util.java.http;

/**
 * 请求响应类，根据需要重写方法
 * @author wangcccong
 * @version 1.1406
 * create at: Wed, 18 Jun. 2014
 * update at: Mon, 03 Sep. 2014
 * 新增：超时方法
 * <br>update at: Fri, 19 Sep. 2014
 *  &nbsp;&nbsp修改获取数据成功和失败新增返回状态,为后续数据缓存和断点续传做准备
 */
public class HttpResponseHandler {

	/**
	 * 请求完全成功之后调用此方法（钩子方法，具体需要用户去实现）
	 * @param response
	 */
	protected void onSuccess(final HttpResponse response, final byte[] bts){
		//do nothing 添加具体实现
	}

	/**
	 * 请求超时（钩子方法，具体需要用户实现）
	 */
	protected void onTimeOut() {
		//do nothing 添加具体实现
	}
	
	/**
	 * 请求错误后调用此方法（钩子方法，具体需要用户去实现）
	 * @param response
	 */
	protected void onError(final int statusCode, final byte[] bts){
		//do nothing 添加具体实现
	}
	
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

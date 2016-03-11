package cc.util.android.core;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

import android.os.Handler;
import cc.util.java.http.DefaultHttpClient;
import cc.util.java.http.HttpParams;
import cc.util.java.http.HttpRequest;
import cc.util.java.http.HttpRequestHandler;
import cc.util.java.http.HttpResponse;
import cc.util.java.http.HttpResponseHandler;

/**
 * 异步HTTP访问客户端，初始化客户端发起http请求; 编码方式、cookie、userAgent等
 * <br>支持（POST/GET/PUT/HEAD/DELETE）方式访问服务器端，默认支持gzip， deflate压缩，
 * 支持多文件上传，支持取消操作，支持下载进度 
 * 注：一个AsyncHttpClient只能发送一个请求， 如果需要发送第二个请求需要重新new一个AsyncHttpClient，
 * 如有必要在重新发送请求前将之前的数据请求取消(调用cancel()方法)
 * @author wangcccong
 * @version 1.1406
 * <br>create at： Tue, 26 Aug. 2014
 * <br>update at: Thur, 28 Aug. 2014
 * 新增：上传进度与取消
 * <br>update at: Tue, 02 Sep. 2014
 * 新增：请求超时方法
 * <br>update at: Fri, 19 Sep. 2014
 *  &nbsp;&nbsp修改获取数据成功和失败新增返回状态,为后续数据缓存和断点续传做准备
 */

public class AsyncHttpClient {
	
	/** 默认请求时长 */
	private final static int DEFAULT_CON_TIMEOUT = 10 * 1000;
	/** 默认获取数据时长 */
	private final static int DEFAULT_SO_TIMEOUT = 20 * 1000;
	
	/** 请求时长 */
	int connectionTimeOut = DEFAULT_CON_TIMEOUT;
	int soTimeOut = DEFAULT_SO_TIMEOUT;
	
	private final DefaultHttpClient mClient;
	private final Handler mHandler;
	
	private HttpRequest mRequest;
	private HttpResponse mResponse;
	//异步请求
	private FutureTask<HttpResponse> mFutureTask;
	private final ExecutorService mService;

	public AsyncHttpClient() {
		this.mClient = new DefaultHttpClient();
		mHandler = new Handler();
		mService = Executors.newSingleThreadExecutor();
	}

	/**
	 * post请求方式（无需知道上传进度)
	 * @param uri
	 * @param params {@link cc.util.java.http.HttpParams}
	 * @param handler {@link cc.util.android.core.AsyncHttpResponseHandler}
	 */
	public void post(String uri, HttpParams params, final AsyncHttpResponseHandler resHandler) {
		mRequest = mClient.post(uri, params);
		doResponse(null, resHandler);
	}
	
	/**
	 * post请求方式（需知道上传进度)
	 * @param uri
	 * @param params {@link cc.util.java.http.HttpParams}
	 * @param handler {@link cc.util.android.core.AsyncHttpResponseHandler}
	 */
	public void post(String uri, HttpParams params, final AsyncHttpRequestHandler reqHandler, final AsyncHttpResponseHandler resHandler) {
		mRequest = mClient.post(uri, params);
		doResponse(reqHandler, resHandler);
	}

	/**
	 * put请求方式（无需知道上传进度)
	 * @param uri
	 * @param params {@link cc.util.java.http.HttpParams}
	 * @param handler {@link cc.util.android.core.AsyncHttpResponseHandler}
	 */
	public void put(String uri, HttpParams params, final AsyncHttpResponseHandler resHandler) {
		mRequest = mClient.put(uri, params);
		doResponse(null, resHandler);
	}
	
	/**
	 * put请求方式（需知道上传进度)
	 * @param uri
	 * @param params {@link cc.util.java.http.HttpParams}
	 * @param handler {@link cc.util.android.core.AsyncHttpResponseHandler}
	 */
	public void put(String uri, HttpParams params, final AsyncHttpRequestHandler reqHandler, final AsyncHttpResponseHandler resHandler) {
		mRequest = mClient.put(uri, params);
		doResponse(reqHandler, resHandler);
	}

	/**
	 * delete请求方式（无需知道上传进度)
	 * @param uri
	 * @param params {@link cc.util.java.http.HttpParams}
	 * @param handler {@link cc.util.android.core.AsyncHttpResponseHandler}
	 */
	public void delete(String uri, HttpParams params, AsyncHttpResponseHandler resHandler) {
		mRequest = mClient.delete(uri, params);
		doResponse(null, resHandler);
	}
	
	/**
	 * delete请求方式（需知道上传进度)
	 * @param uri
	 * @param params {@link cc.util.java.http.HttpParams}
	 * @param handler {@link cc.util.android.core.AsyncHttpResponseHandler}
	 */
	public void delete(String uri, HttpParams params, final AsyncHttpRequestHandler reqHandler, AsyncHttpResponseHandler resHandler) {
		mRequest = mClient.delete(uri, params);
		doResponse(reqHandler, resHandler);
	}

	/**
	 * get请求方式  
	 * @param uri
	 * @param params {@link cc.util.java.http.HttpParams}
	 * @param handler {@link cc.util.android.core.AsyncHttpResponseHandler}
	 */
	public void get(String uri, Map<String, String> params, AsyncHttpResponseHandler resHandler) {
		mRequest = mClient.get(uri, params);
		doResponse(null, resHandler);
	}

	/**
	 * get请求方式
	 * @param uri
	 * @param params {@link cc.util.java.http.HttpParams}
	 * @param handler {@link cc.util.android.core.AsyncHttpResponseHandler}
	 */
	public void get(String uri, String[] names, String[] values, AsyncHttpResponseHandler handler) {
		int length = names.length > values.length ? values.length : names.length;
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < length; i++) {
			map.put(names[i], values[i]);
		}
		get(uri, map, handler);
	}
	
	/**
	 * head请求方式
	 * @param uri
	 * @param params 
	 * @param handler {@link cc.util.android.core.AsyncHttpResponseHandler}
	 */
	public void head(String uri, Map<String, String> params, AsyncHttpResponseHandler handler) {
		mRequest = mClient.head(uri, params);
		doResponse(null, handler);
	}
	
	/**
	 * head请求方式
	 * @param uri
	 * @param names
	 * @param values
	 * @param handler {@link cc.util.android.core.AsyncHttpResponseHandler}
	 */
	public void head(String uri, String[] names, String[] values, AsyncHttpResponseHandler handler) {
		int length = names.length > values.length ? values.length : names.length;
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < length; i++) {
			map.put(names[i], values[i]);
		}
		head(uri, map, handler);
	}
	
	/**
	 * 处理请求连接后的数据
	 * @param response
	 * @param handler
	 * @throws IOException
	 */
	private void doResponse(final AsyncHttpRequestHandler reqHandler, final AsyncHttpResponseHandler resHandler) {
		mFutureTask = new FutureTask<HttpResponse>(new Callable<HttpResponse>() {
			@Override
			public HttpResponse call() throws Exception {
				// TODO Auto-generated method stub
				try {
					mResponse = mRequest.execute(new HttpRequestHandler() {
						@Override
						protected void onCancel() {
							// TODO Auto-generated method stub
							super.onCancel();
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									if(reqHandler != null) reqHandler.onCancel();
								}
							});
						}
						
						@Override
						protected void onProgress(final long totalLength,
								final long currentLength) {
							// TODO Auto-generated method stub
							super.onProgress(totalLength, currentLength);
							mHandler.post(new Runnable() {
								@Override
								public void run() {
									// TODO Auto-generated method stub
									if (reqHandler != null) reqHandler.onProgress(totalLength, currentLength);
								}
							});
						}
					});
					
					if (mResponse != null) {
						mResponse.read(new HttpResponseHandler() {
							@Override
							protected void onSuccess(final HttpResponse response, final byte[] bts) {
								// TODO Auto-generated method stub
								super.onSuccess(response, bts);
								mHandler.post(new Runnable() {
									@Override
									public void run() {
										// TODO Auto-generated method stub
										if (resHandler != null) resHandler.onSuccess(response, bts);
									}
								});
							}
							
							@Override
							protected void onError(final int statusCode, final byte[] bts) {
								// TODO Auto-generated method stub
								super.onError(statusCode, bts);
								mHandler.post(new Runnable() {
									@Override
									public void run() {
										// TODO Auto-generated method stub
										if (resHandler != null ) resHandler.onError(statusCode, bts);;
									}
								});
							}
							
							@Override
							protected void onTimeOut() {
								// TODO Auto-generated method stub
								super.onTimeOut();
								mHandler.post(new Runnable() {
									@Override
									public void run() {
										// TODO Auto-generated method stub
										if (resHandler != null) resHandler.onTimeOut();
									}
								});
							}
							
							@Override
							protected void onCancel() {
								// TODO Auto-generated method stub
								super.onCancel();
								mHandler.post(new Runnable() {
									@Override
									public void run() {
										// TODO Auto-generated method stub
										if (resHandler != null) resHandler.onCancel();
									}
								});
							}
							
							@Override
							protected void onProgress(final long totalLength, final long currentLength) {
								// TODO Auto-generated method stub
								super.onProgress(totalLength, currentLength);
								mHandler.post(new Runnable() {
									@Override
									public void run() {
										// TODO Auto-generated method stub
										if (resHandler != null) resHandler.onProgress(totalLength, currentLength);;
									}
								});
							}
						});
					} else {
						mHandler.post(new Runnable() {
							@Override
							public void run() {
								// TODO Auto-generated method stub
								if (resHandler != null) resHandler.onError(400, "请求语法错误".getBytes());
							}
						});
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				return mResponse;
			}
		});
		if (!mService.isShutdown()) mService.submit(mFutureTask);
	}
	
	/**
	 * 取消请求
	 */
	public void cancel() {
		//调用取消请求方法
		if (mRequest != null) mRequest.cancel();
		if (mResponse != null) mResponse.cancel();
		
		//取消正在进行的任务
		if (mFutureTask != null) mFutureTask.cancel(true);
		mService.shutdownNow();
	}

	/**
	 * 设置编码方式，默认采用UTF-8
	 * @param charset
	 */
	public AsyncHttpClient setCharset(String charset) {
		mClient.setCharset(charset);
		return this;
	}
	
	/**
	 * 设置UserAgent
	 * @param userAgent
	 */
	public AsyncHttpClient setUserAgent(String userAgent) {
		mClient.setUserAgent(userAgent);
		return this;
	}
	
	public AsyncHttpClient setHost(String host) {
		mClient.setHost(host);
		return this;
	}
	
	/** 设置连接时长，默认时间为 10 * 1000 */
	public AsyncHttpClient setConnectionTimeOut(int connectionTimeOut) {
		mClient.setConnectionTimeOut(connectionTimeOut);
		return this;
	}
	
	/** 设置读取数据时长，默认时间为 20 * 1000 */
	public AsyncHttpClient setSoTimeOut(int soTimeOut) {
		mClient.setSoTimeOut(soTimeOut);
		return this;
	}
	
	/**
	 * 设置请求数据范围，格式如
	 * <br>从某个位置开始到结尾: bytes=1024-
	 * <br>从某个位置到某个位置: bytes=1024-2048
	 * <br>同时指定几个range: bytes=512-1024,2048-4096
	 * @param range
	 * @return
	 */
	public AsyncHttpClient setRange(String range) {
		mClient.setRange(range);
		return this;
	}
}

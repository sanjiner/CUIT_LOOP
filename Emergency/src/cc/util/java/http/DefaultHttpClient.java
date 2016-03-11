package cc.util.java.http;

import java.util.HashMap;
import java.util.Map;

/**
 * 同步HTTP访问客户端，初始化客户端发起http请求; 编码方式、cookie、userAgent等
 * <br>支持（POST/GET/PUT/HEAD/DELETE）方式访问服务器端，默认支持gzip， deflate压缩，
 * 支持多文件上传，支持取消操作，支持下载进度，支持断点续传
 * @author wangcccong
 * @version 1.1406
 * create at： Fri, 13 Jun 2014
 * update at: Thur, 26 Aug 2014
 * 新增：上传进度与取消
 */

public class DefaultHttpClient extends HttpClient {

	public DefaultHttpClient() {
		super();
	}

	@Override
	public HttpRequest post(String uri, HttpParams params) {
		// TODO Auto-generated method stub
		return new HttpRequest(this, HttpMethod.HTTP_POST, uri, params);
	}

	@Override
	public HttpRequest put(String uri, HttpParams params) {
		// TODO Auto-generated method stub
		return new HttpRequest(this, HttpMethod.HTTP_PUT, uri, params);
	}

	@Override
	public HttpRequest delete(String uri, HttpParams params) {
		// TODO Auto-generated method stub
		return new HttpRequest(this, HttpMethod.HTTP_DELETE, uri, params);
	}

	@Override
	public HttpRequest get(String uri, Map<String, String> params) {
		// TODO Auto-generated method stub
		return new HttpRequest(this, HttpMethod.HTTP_GET, uri, params);
	}

	@Override
	public HttpRequest get(String uri, String[] names, String[] values) {
		// TODO Auto-generated method stub
		if (names == null || values == null) 
			throw new NullPointerException("键值对不能为空");
		int length = names.length > values.length ? values.length : names.length;
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < length; i++) {
			map.put(names[i], values[i]);
		}
		return new HttpRequest(this, HttpMethod.HTTP_GET, uri, map);
	}

	@Override
	public HttpRequest head(String uri, Map<String, String> params) {
		// TODO Auto-generated method stub
		return new HttpRequest(this, HttpMethod.HTTP_HEAD, uri, params);
	}

	@Override
	public HttpRequest head(String uri, String[] names, String[] values) {
		// TODO Auto-generated method stub
		if (names == null || values == null) 
			throw new NullPointerException("键值对不能为空");
		int length = names.length > values.length ? values.length : names.length;
		Map<String, String> map = new HashMap<String, String>();
		for (int i = 0; i < length; i++) {
			map.put(names[i], values[i]);
		}
		return new HttpRequest(this, HttpMethod.HTTP_HEAD, uri, map);
	}

}

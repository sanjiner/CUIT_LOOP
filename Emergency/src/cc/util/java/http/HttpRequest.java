package cc.util.java.http;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.zip.GZIPInputStream;
import java.util.zip.Inflater;
import java.util.zip.InflaterInputStream;

import static cc.util.java.http.HttpMethod.*;

/**
 * 数据请求类， 无需直接调用， 使用 {@link cc.util.java.http.DefaultHttpClient}
 * @author wangcccong
 * @version 1.1406
 * create at: Fri, 16 Jun 2014
 * update at: Thur, 17 Sep 2014
 * 修改：获取数据压缩和未压缩情况
 * <br>update at: Tue, 23 Sep. 2014
 * &nbsp;&nbsp;新增断点续传功能
 */
public class HttpRequest {

	/** 当前请求方式 */
	private HttpMethod method;
	private String uri;
	private HttpClient mClient;

	/** 请求头 */
	private Map<String, List<String>> headers;
	
	/** GET/PUT请求参数  */
	private Map<String, String> params;

	/** POST/PUT/DELETE 的请求参数 */
	private HttpParams httpParams;
	
	public volatile boolean isCancel = false;
	
	HttpRequest(HttpClient client, HttpMethod method, String uri, HttpParams params) {
		this.mClient = client;
		this.method = method;
		this.uri = uri;
		this.httpParams = params;
	}
	
	HttpRequest(HttpClient client, HttpMethod method, String uri, Map<String, String> params) {
		this.mClient = client;
		this.method = method;
		this.uri = uri;
		this.params = params;
	}
	
	/**
	 * 设置请求头
	 * @param headers
	 * @return
	 */
	public HttpRequest headers(Map<String, List<String>> headers) {
		this.headers = headers;
		return this;
	}
	
	/**
	 * 设置请求头 ，请求头的name 和 value 不能为空
	 * @param name
	 * @param value
	 * @return
	 */
	public HttpRequest header(String name, String value) {
		if (name == null || value == null) {
			throw new IllegalArgumentException("Header name or value cannot be null");
		}
		if (headers == null) headers = new HashMap<String, List<String>>();
		List<String> values = headers.get(name);
		if (values == null) {
			values = new ArrayList<String>();
			headers.put(name, values);
		}
		values.add(value);
		return this;
	}
	
	/**
	 * get 或者 head 请求方式设置需要携带的参数
	 * @param params
	 * @return
	 */
	public HttpRequest params(Map<String, String> params) {
		this.params = params;
		return this;
	}
	
	/**
	 * get 或者 head 请求方式, 通过数组方式设置需要携带的参数
	 * @param keys
	 * @param values
	 * @return
	 */
	public HttpRequest params(String[] keys, String[] values) {
		if (keys.length != values.length) {
			throw new IllegalArgumentException("keys size must equal values size");
		}
		if (params == null) params = new HashMap<String, String>();
		for (int i = 0; i < keys.length; i++) {
			params.put(keys[i], values[i]);
		}
		return this;
	}
	
	/**
	 * get 或者 head 请求方式,设置请求的参数
	 * @param key
	 * @param value
	 * @return
	 */
	public HttpRequest param(String key, String value) {
		if (key == null) {
			throw new NullPointerException("key cannot be null");
		}
		if (params == null) params = new HashMap<String, String>();
		params.put(key, value);
		return this;
	}
	
	/**
	 * 取消上传
	 */
	public void cancel() {
		isCancel = true;
	}
	
	/**
	 * 执行http访问，带有反馈
	 * @return
	 * @throws HttpClientException
	 */
	public HttpResponse execute(HttpRequestHandler handler) {
		HttpURLConnection conn = null;
		InputStream uncloseableInputStream = null;
		HttpResponse response = null;
		try {
			final StringBuilder buf = new StringBuilder(256);
			if (params != null && !params.isEmpty()) {
				if ((HTTP_GET.equals(method) || HTTP_HEAD.equals(method)) && !uri.endsWith("?")) {
					buf.append('?');
				}
				int paramIndex = 0;
				for (Entry<String, String> entry : params.entrySet()) {
					if (paramIndex != 0) buf.append('&');
					buf.append(URLEncoder.encode(entry.getKey(), mClient.getCharset()))
						.append('=').append(URLEncoder.encode(entry.getValue(), mClient.getCharset()));
					paramIndex ++;
				}
			}
			
			byte[] content = new byte[0];
			if (httpParams != null && (HTTP_POST.equals(method) || HTTP_PUT.equals(method) || HTTP_DELETE.equals(method))) {
				try {
					content = httpParams.toBytes(mClient.getCharset());
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			} else {
				uri += buf;
			}
			System.out.println(uri);

			conn = (HttpURLConnection) new URL(uri).openConnection();
			if (headers != null && !headers.isEmpty()) {
				for (final Entry<String, List<String>> entry : headers.entrySet()) {
					final List<String> values = entry.getValue();
					if (values != null) {
						final String name = entry.getKey();
						for (final String value : values) {
							conn.addRequestProperty(name, value);
						}
					}
				}
			}

			conn.setConnectTimeout(mClient.getConnectionTimeOut());
			conn.setReadTimeout(mClient.getSoTimeOut());
			conn.setRequestMethod(method.toString());
			conn.setRequestProperty("Accept-Charset", mClient.getCharset());
			conn.setRequestProperty("Connection", "keep-alive");
			conn.setRequestProperty("User-Agent", mClient.getUserAgent());
			conn.setRequestProperty("Accept-Encoding", "gzip,deflate");
			//断点续传
			if (mClient.getRange() != null) conn.setRequestProperty("Range", mClient.getRange());
			if (httpParams != null && (HTTP_POST.equals(method) || HTTP_PUT.equals(method) || HTTP_DELETE.equals(method))) {
				if (content != null) {
					conn.setDoOutput(true);
					conn.setRequestProperty("Content-Length", content.length+"");
					if (httpParams.getContentType().equals("application/x-www-form-urlencoded"))
						conn.setRequestProperty("Content-Type", httpParams.getContentType()+";charset="+mClient.getCharset());
					else conn.setRequestProperty("Content-Type", httpParams.getContentType());
					final OutputStream os = conn.getOutputStream();
					ByteArrayInputStream bais = new ByteArrayInputStream(content);
					byte[] bts = new byte[1024 < bais.available() ?  1024 : bais.available()];
					int length = 0;
					int totoalLength = content.length, currentLength = 0;
					while ((length = bais.read(bts)) != -1) {
						if (isCancel) {
							if (handler != null) handler.onCancel();
			        		bais.close();
			        		return null;
			        	}
			        	currentLength += length;
			        	if (handler != null) handler.onProgress(totoalLength, currentLength);
			        	os.write(bts);
			        	bts = new byte[1024 < bais.available() ? 1024 : bais.available()];
					}
					os.flush();
					bais.close();
				}
			}
			conn.connect();
			final int statusCode = conn.getResponseCode();
			final Map<String, List<String>> headerFields = conn.getHeaderFields();
			final Map<String, String> inMemoryCookies = mClient.getCookie();
			if (statusCode >= 400) {
				uncloseableInputStream = getErrorStream(conn);
			} else {
				uncloseableInputStream = getInputStream(conn);
			}
			response = new HttpResponse(statusCode, null, uncloseableInputStream, headerFields, inMemoryCookies);
		} catch (SocketTimeoutException e) {
			response = new HttpResponse(-1, e, null, null, null);
		} catch (IOException e) {
			// TODO: handle exception
			response = new HttpResponse(400, e, null, null, null);
		}
		return response;
	}
	
	/**
	 *  
	 * @param connection
	 * @return
	 */
	private static InputStream getErrorStream(HttpURLConnection conn) throws IOException {
		final List<String> contentEncodingValues = conn.getHeaderFields().get("Content-Encoding") == null ?
				conn.getHeaderFields().get("content-encoding") : conn.getHeaderFields().get("Content-Encoding");
		if (contentEncodingValues != null) {
			for (final String contentEncoding : contentEncodingValues) {
				if (contentEncoding != null) {
					if (contentEncoding.contains("gzip")) {
						return new GZIPInputStream(conn.getErrorStream());
					}
					if (contentEncoding.contains("deflate")) {
						return new InflaterInputStream(conn.getErrorStream(), new Inflater(true));
					}
				}
			}
		}
		return conn.getErrorStream();
	}
	
	/**
	 * 获取指定连接的输入流,支持gzip 和deflate 压缩
	 * @param connection
	 * @return
	 * @throws HttpClientException
	 */
	private InputStream getInputStream(HttpURLConnection conn) throws IOException {
		final List<String> contentEncodingValues = conn.getHeaderFields().get("Content-Encoding") == null ?
				conn.getHeaderFields().get("content-encoding") : conn.getHeaderFields().get("Content-Encoding");
		if (contentEncodingValues != null) {
			for (final String contentEncoding : contentEncodingValues) {
				if (contentEncoding.contentEquals("gzip")) {
					return new GZIPInputStream(conn.getInputStream());
				}
				if (contentEncoding.contains("deflate")) {
					return new InflaterInputStream(conn.getInputStream(), new Inflater(true));
				}
			}
		}
		return conn.getInputStream();
	}
}

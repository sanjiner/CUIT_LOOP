package cc.util.java.http;

import java.util.HashMap;
import java.util.Map;

/**
 * HTTP访问客户端，初始化客户端发起http请求; 编码方式、cookie、userAgent等
 * <br>支持（POST/GET/PUT/HEAD/DELETE）方式访问服务器端，默认支持gzip， deflate压缩，
 * 支持多文件上传，支持取消操作，支持下载进度，支持断点续传功能
 * @author wangcccong
 * @version 1.1406
 * create at： Fri, 13 Jun 2014
 * update at: Thur, 28 Aug 2014
 * 新增：上传进度与取消
 */
public abstract class HttpClient {

	/** 默认请求时长 */
	private final static int DEFAULT_CON_TIMEOUT = 10 * 1000;
	/** 默认获取数据时长 */
	private final static int DEFAULT_SO_TIMEOUT = 20 * 1000;
	
	public final static String DEFAULT_CHARSET = "UTF-8";

	/** 编码 */
	protected String charset;
	/** 用户识别 */
	protected String userAgent;
	/** cookie */
	protected Map<String, String> cookie;
	/** 主机 */
	protected String host;
	/** 请求数据range */
	protected String range;
	/** 端口号 */
	protected short port;
	
	/** 请求时长 */
	protected int connectionTimeOut = DEFAULT_CON_TIMEOUT;
	/** 获取数据时长 */
	protected int soTimeOut = DEFAULT_SO_TIMEOUT;
	
	protected HttpClient()	{
		charset = DEFAULT_CHARSET;
		userAgent = "ccutil_framework_create_by_wangcong:Android";
		port = 80;
		cookie = new HashMap<String, String>();
	}
	
	/**
	 * Post 方式访问
	 * @param uri 访问地址
	 * @param params {@link cc.util.HttpParams}  post 数据
	 * @return {@link cc.util.http.HttpRequest}
	 */
	public abstract HttpRequest post(String uri, HttpParams params);
	
	/**
	 * Put 方式访问
	 * @param uri 访问地址
	 * @param params {@link cc.util.HttpParams}  put 数据
	 * @return {@link cc.util.http.HttpRequest}
	 */
	public abstract HttpRequest put(String uri, HttpParams params);
	
	/**
	 * Delete 方式访问
	 * @param uri 访问地址
	 * @param params {@link cc.util.HttpParams}  delete 数据
	 * @return {@link cc.util.http.HttpRequest}
	 */
	public abstract HttpRequest delete(String uri, HttpParams params);

	/**
	 * get 访问方式
	 * @param uri url请求头，不包含请求参数
	 * @param params
	 * @return
	 */
	public abstract HttpRequest get(String uri, Map<String, String> params);
	
	/**
	 * get 访问方式
	 * @param uri url请求头，不包含请求参数
	 * @param names 请求参数名称
	 * @param values 请求参数值
	 * @return
	 */
	public abstract HttpRequest get(String uri, String[] names, String[] values);
	
	/**
	 * head 访问方式
	 * @param uri url请求头，不包含请求参数
	 * @param params
	 * @return
	 */
	public abstract HttpRequest head(String uri, Map<String, String> params);
	
	/**
	 * head 访问方式
	 * @param uri url请求头，不包含请求参数
	 * @param names 请求参数名称
	 * @param values 请求参数值
	 * @return
	 */
	public abstract HttpRequest head(String uri, String[] names, String[] values);
	
	/**
	 * 设置编码方式，默认采用UTF-8
	 * @param charset
	 */
	public void setCharset(String charset) {
		this.charset = charset;
	}
	
	public String getCharset() {
		return charset;
	}
	
	/**
	 * 获取User-Agent相关信息
	 * @return
	 */
	public String getUserAgent() {
		return userAgent;
	}
	
	public void setUserAgent(String userAgent) {
		this.userAgent = userAgent;
	}
	
	/**
	 * 获取cookie 相关信息
	 * @return
	 */
	public Map<String, String> getCookie() {
		return cookie;
	}
	
	public void setCookie(Map<String, String> cookie) {
		this.cookie = cookie;
	}
	
	/** 获取host */
	public String getHost() {
		return host;
	}
	
	public void setHost(String host) {
		this.host = host;
	}
	
	/**
	 * 获取访问端口, 默认为80端口 
	 * @return
	 */
	public short getPort() {
		return port;
	}
	
	public void setPort(short port) {
		this.port = port;
	}
	
	/** 设置连接时长，默认时间为 10 * 1000 */
	public void setConnectionTimeOut(int connectionTimeOut) {
		this.connectionTimeOut = connectionTimeOut;
	}
	
	public int getConnectionTimeOut() {
		return this.connectionTimeOut;
	}
	
	/** 设置读取数据时长，默认时间为 10 * 1000 */
	public void setSoTimeOut(int soTimeOut) {
		this.soTimeOut = soTimeOut;
	}
	
	public int getSoTimeOut() {
		return this.soTimeOut;
	}
	
	/**
	 * 格式如
	 * <br>从某个位置开始到结尾: bytes=1024-
	 * <br>从某个位置到某个位置: bytes=1024-2048
	 * <br>同时指定几个range: bytes=512-1024,2048-4096
	 * @param range
	 */
	public void setRange(String range) {
		this.range = range;
	}
	
	public String getRange() {
		return range;
	}
}

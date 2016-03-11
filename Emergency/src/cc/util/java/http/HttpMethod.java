package cc.util.java.http;

/**
 * 支持的HTTP请求方法
 * @author wangcccong
 * @version 1.1406
 * create at： Fri, 13 Jun 2014
 */

public enum HttpMethod {
	
	HTTP_GET("GET"),
	HTTP_POST("POST"),
	HTTP_PUT("PUT"),
	HTTP_DELETE("DELETE"),
	HTTP_HEAD("HEAD");
	
	private String value;
	HttpMethod(String value) {
		this.value = value;
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return value;
	}
}

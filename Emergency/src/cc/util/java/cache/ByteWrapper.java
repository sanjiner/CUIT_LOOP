package cc.util.java.cache;

import java.io.Serializable;

/**
 * 封装字节数据, 与 {@link cc.util.cache.NetChacheManager}使用
 * @author wangcccong
 * @version 1.1406
 * <br> create at: Tues, 10 Jun 2014
 */
public class ByteWrapper implements Serializable {

	private final static long serialVersionUID = 1L;

	/**
	 * 从网络获取到的数据
	 */
	private byte[] data;
	/**
	 * 数据长度
	 */
	int contentLength;
	
	/**
	 * 数据上一次被修改的时间
	 */
	private long lastModified;
	
	/**
	 * 文件的Etag值，客户端不需要关心此值，只需在发送http请求时
	 * 将该值写入http头中即可
	 */
	private String ETag;

	public ByteWrapper(byte[] data, long lastModified, String Etag) {
		this.data = data;
		this.lastModified = lastModified;
		this.ETag = Etag;
	}
	
	public byte[] getData() {
		return data;
	}
	public void setData(byte[] data) {
		this.data = data;
	}

	public long getLastModified() {
		return lastModified;
	}
	public void setLastModified(long lastModified) {
		this.lastModified = lastModified;
	}

	public String getETag() {
		return ETag;
	}
	
	public void setETag(String eTag) {
		this.ETag = eTag;
	}
	
	public int getContentLength() {
		return data == null ? 0 : data.length;
	}
}

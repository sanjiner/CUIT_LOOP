package cc.util.java.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;

/**
 * 封装缓存数据（软引用）{@link cc.util.ByteWrapper.NetByteWrapper} 
 * with {@link java.lang.ref.SoftReference}, 用于JVM内存吃紧回收
 * @author wangcccong
 * @version 1.1406
 * <br> create at: Tues, 10 Jun. 2014
 */
public class ByteSoftReference extends SoftReference<ByteWrapper> {
	
	private String key = "";
	private long length = 0;

	public ByteSoftReference(String key, ByteWrapper arg0) {
		this(key, arg0, null);
	}

	public ByteSoftReference(String key, ByteWrapper arg0,
			ReferenceQueue<? super ByteWrapper> arg1) {
		super(arg0, arg1);
		// TODO Auto-generated constructor stub
		this.key = key;
		this.length = arg0.getContentLength();
	}
	
	public String getKey() {
		return key;
	}
	
	public long getLength() {
		return length;
	}

}

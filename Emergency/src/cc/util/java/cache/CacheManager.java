package cc.util.java.cache;

import java.lang.ref.ReferenceQueue;
import java.lang.ref.SoftReference;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/**
 * 采用LRU 算法缓存数据， 可自动检测对象是否已被虚拟机回收，并且重新计算当前缓存大小，清除缓存中无用的键值对象(即已经被虚拟机回收但未从缓存清除的数据)；
 * 默认内存缓存大小为: 4 * 1024 * 1024 可通过通过setMaxCacheSize重新设置缓存大小，可手动清空内存缓存，并回收对象，支持内存缓存和磁盘缓存方式
 * <br> 通过 {@link cc.util.ByteWrapper.NetByteWrapper} 支持HTTP缓存 （注：详细参考cc.util.org.http包，该功能暂未实现）
 * @author wangcccong
 * @version 1.1406
 * <br> create at: Tue, 10 Jun 2014
 * <br> update at: Thur, 17 Sep 2014
 * <br> 修改：异步存放图片到外部存储卡
 */
public class CacheManager {

	/** max cache size */
	private long MAX_CACHE_SIZE = 4 * 1024 * 1024;
	private long cacheSize = 0; 

	private static CacheManager instance = null;
	
	private final ReferenceQueue<ByteWrapper> referenceQueue;
	private final LinkedHashMap<String, ByteSoftReference> cacheMap;
	
	private final ExecutorService cacheService;
	private CacheManager(){
		cacheService = Executors.newCachedThreadPool();
		referenceQueue = new ReferenceQueue<ByteWrapper>();
		cacheMap = new LinkedHashMap<String, ByteSoftReference>(16, 0.75f, true) {

			private static final long serialVersionUID = -8378285623387632829L;
			@Override
			protected boolean removeEldestEntry(
					java.util.Map.Entry<String, ByteSoftReference> eldest) {
				// TODO Auto-generated method stub
				boolean shouldRemove = cacheSize > MAX_CACHE_SIZE;
				if (shouldRemove) {
					clearRecycledObject();
					System.gc();
				}
				return shouldRemove;
			}
		};
	}
	
	/** singleton model */
	public static synchronized CacheManager newInstance(){
		if (instance == null) {
			instance = new CacheManager();
		}
		return instance;
	}
	
	/**
	 * reset the memory cache size
	 * @param cacheSize
	 */
	public void setMaxCacheSize(long cacheSize) {
		this.MAX_CACHE_SIZE = cacheSize;
	}
	
	/**
	 * 获取当前内存缓存大小
	 * @return
	 */
	public long getCacheSize() {
		return cacheSize;
	}
	
	/**
	 * 将数据缓存至内存， 如果http返回的数据<b>不支持</b>缓存则采用此方法
	 * @param key
	 * @param value
	 */
	public void cacheInMemory(String key, byte[] value) {
		this.cacheInMemory(key, value, 0, null);
	}
	
	/**
	 * 将数据缓存至内存， 如果http返回的数据<b>支持</b>缓存则采用此方法
	 * @param key
	 * @param value
	 * @param lastModified
	 */
	public void cacheInMemory(String key, byte[] value, long lastModified) {
		this.cacheInMemory(key, value, lastModified, null);
	}
	
	/**
	 * 将数据缓存至内存， 如果http返回的数据<b>支持</b>缓存则采用此方法
	 * @param key
	 * @param value
	 * @param Etags
	 */
	public void cacheInMemory(String key, byte[] value, String Etags) {
		this.cacheInMemory(key, value, 0, Etags);
	}
	
	/**
	 * 将数据缓存至内存， 如果http返回的数据<b>支持</b>缓存则采用此方法
	 * @param key
	 * @param value
	 * @param lastModified
	 * @param Etags
	 */
	private void cacheInMemory(String key, byte[] value, long lastModified, String Etags) {
		if (key == null) throw new NullPointerException("key must not be null");
		ByteWrapper wrapper = new ByteWrapper(value, lastModified, Etags);
		ByteSoftReference byteRef = new ByteSoftReference(key, wrapper, referenceQueue);
		cacheMap.put(key, byteRef);
		value = null;
		wrapper = null;
	}
	
	/**
	 * 缓存至磁盘, 默认不首先缓存到内存
	 * @param key
	 * @param value
	 * @param path
	 */
	public void cacheInDisk(String key, byte[] value, String path) {
		cacheInDisk(key, value, path, false);
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @param path
	 * @param cacheInMemory
	 */
	public void cacheInDisk(String key, byte[] value, String path, boolean cacheInMemory) {
		this.cacheInDisk(key, value, 0, null, path, cacheInMemory);
	}
	
	/**
	 * 
	 * @param key
	 * @param value
	 * @param lastModified
	 * @param Etags
	 * @param path
	 * @param cacheInMemory
	 */
	private void cacheInDisk(final String key, final byte[] value, final long lastModified,
			final String Etags, final String path, final boolean cacheInMemory) {
		if (cacheInMemory) cacheInMemory(key, value, lastModified, Etags);
		//修改：之前为同步（有可能图片太大或同一时间缓存太多，造成UI卡死）
		final FutureTask<Void> futureTask = new FutureTask<Void>(new Callable<Void>() {

			@Override
			public Void call() throws Exception {
				// TODO Auto-generated method stub
				try {
					File file = new File(path);
					if (!file.exists()) file.createNewFile();
					FileOutputStream fos = new FileOutputStream(file);
					ObjectOutputStream oos = new ObjectOutputStream(fos);
					ByteWrapper wrapper = new ByteWrapper(value, lastModified, Etags);
					oos.writeObject(wrapper);
					fos.close();
					oos.close();
				} catch (Exception e) {
						// TODO: handle exception
					e.printStackTrace();
				}
				return null;
			}
		});
		if (!cacheService.isShutdown()) cacheService.submit(futureTask);
	}
	
	/**
	 * get {@link cc.util.ByteWrapper.NetByteWrapper} from memory according to key
	 * @param key
	 * @return {@link cc.util.ByteWrapper.NetByteWrapper}
	 */
	public ByteWrapper getFromMemory(String key) {
		SoftReference<ByteWrapper> softReference = cacheMap.get(key);
		return softReference != null ? softReference.get() : null;
	}
	
	/**
	 * get byte[] from memory according to key
	 * @param context
	 * @param key
	 * @return
	 */
	public byte[] getByteFromMemory(String key) {
		ByteWrapper wrapper = getFromMemory(key);
		return wrapper != null ? wrapper.getData() : null;
	}
	
	/**
	 * 从磁盘获取数据
	 * @param path
	 * @return {@link cc.util.ByteWrapper.NetByteWrapper}
	 */
	public ByteWrapper getFromDisk(String path) {
		try {
			//修改：如果路径不存在则直接返回
			File file = new File(path);
			if (!file.exists()) return null;
			FileInputStream fis = new FileInputStream(file);
			ObjectInputStream ois = new ObjectInputStream(fis);
			ByteWrapper wrapper = (ByteWrapper) ois.readObject();
			fis.close();
			ois.close();
			return wrapper;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			return null;
		}
	}
	
	/**
	 * 
	 * @param path
	 * @return
	 */
	public byte[] getByteFromDisk(String path) {
		ByteWrapper wrapper = getFromDisk(path);
		return wrapper == null ? null : wrapper.getData();
	}
	
	/**
	 * calculate the size of the cache memory
	 */
	private void clearRecycledObject() {
		ByteSoftReference ref = null;
		while ((ref = (ByteSoftReference) referenceQueue.poll()) != null) {
			cacheMap.remove(ref.getKey());
		}
		cacheSize = 0;
		Iterator<String> keys = cacheMap.keySet().iterator();
		while (keys.hasNext()) {
			cacheSize += cacheMap.get(keys.next()).getLength();
		}
	}
	
	/**
	 * clear the memory cache
	 */
	public void clearCache() {
		cacheMap.clear();
		System.gc();
		System.runFinalization();
	}
	
}

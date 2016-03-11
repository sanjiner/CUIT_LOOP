package cc.util.android.image;

import java.io.ByteArrayOutputStream;
import java.lang.ref.SoftReference;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;
import cc.util.java.cache.CacheManager;
import cc.util.java.core.CoreUtil;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Bitmap.CompressFormat;
import android.os.Handler;

/**
 * 图片加载控制类，使用Builder创建一个ImageLoader， 根据需要设置是否需要缓存到本地，设置是否缩放，设置最大加载数据数量（这个对ListView加
 * 载图片时有帮助.例如：ListView滑动加载后，因为网络原因当前页图片未加载出来，而用户又滑动到另一页，现在可以根据设置最大加载项解决，如果当用户
 * 当前加载数量大于设置的最大加载图片数量，则会自动取消最早添加的任务,减少不必要的网络请求）
 * @author wangcccong
 * @version 1.14
 * crated at: 2014-03-25
 * update at: 2014-06-30
 * <br>修改：增加自动取消过早加载的图片功能,为ListView滑动加载图片时提供更好的控制
 */
public class ImageLoader {
	private ExecutorService mLoadService;
	private final Handler mHandler;
	private final LinkedHashMap<String, FutureTask<ImageInfoWrapper>> futureTaskCache;
	private final ThreadFactory factory; 
	private final ImageLoaderController.ImageLoaderParams params;

	private ImageLoader(final ImageLoaderController.ImageLoaderParams params){
		mHandler = new Handler();
		this.params = params;
		futureTaskCache = new LinkedHashMap<String, FutureTask<ImageInfoWrapper>>(16, .75f, true) {
			private static final long serialVersionUID = 1L;
			@Override
			protected boolean removeEldestEntry(
					java.util.Map.Entry<String, FutureTask<ImageInfoWrapper>> eldest) {
				// TODO Auto-generated method stub
				//如果任务数量大于最大加载数，则取消最早的任务
				if (size() > params.maxTasks)
					eldest.getValue().cancel(true);
				return size() > params.maxTasks;
			}
		};
		factory = new ThreadFactory() {
			private AtomicInteger index = new AtomicInteger(1);
			
			@Override
			public Thread newThread(Runnable r) {
				// TODO Auto-generated method stub
				return new Thread(r, "ImageLoader--Thread:"+index.getAndIncrement());
			}
		};
		mLoadService = Executors.newFixedThreadPool(10, factory);
	}
	
	/**
	 * 图片加载控制类
	 * @author wangcccong
	 * @version 1.14
	 * crated at: 2014-03-25
	 */
	private static class ImageLoaderController {
		static class ImageLoaderParams {
			Context context;            //设置上下文（主要是为了图片保存至本地）
			AsyncListener listener;     //加载图片完成后监听
			int reqOutW = 0;            //图片输出宽度
			int reqOutH = 0;            //图片输出高度
			int maxTasks = 10;          //最大加载任务数，默认为10
		}
		ImageLoaderParams PARAMS;
		private ImageLoaderController() {
			PARAMS = new ImageLoaderParams();
		}
		
		ImageLoader buildByParams() {
			return new ImageLoader(PARAMS);
		}
	}
	
	public static class Builder {
		static ImageLoaderController controller;
		public Builder() {
			controller = new ImageLoaderController();
		}
		
		public Builder(Context context) {
			this();
			controller.PARAMS.context = context;
		}
		
		/**
		 * 设置异步回调，回调方法中可以直接操作UI相关逻辑
		 * @param listener
		 * @return
		 */
		public Builder setAsyncListener(AsyncListener listener) {
			controller.PARAMS.listener = listener;
			return this;
		}
		
		/**
		 * 设置图片输出的宽、高
		 * @param reqW
		 * @param reqH
		 * @return
		 */
		public Builder setOutWH(int reqW, int reqH) {
			controller.PARAMS.reqOutW = reqW;
			controller.PARAMS.reqOutH = reqH;
			return this;
		}
		
		/**
		 * 设置最大任务加载数量，默认为10条
		 * @param size
		 * @return
		 */
		public Builder setMaxTasks(int size) {
			controller.PARAMS.maxTasks = size;
			return this;
		}
		
		/**
		 * 构建一个Imageloader
		 * @return
		 */
		public ImageLoader build() {
			ImageLoader imageLoader = controller.buildByParams();
			return imageLoader;
		}
		
		/**
		 * 构建并加载图片
		 * @param url
		 * @param tag
		 */
		public void load(final String url, final Object tag) {
			ImageLoader imageLoader = build();
			imageLoader.loadImage(url, tag);
		}
	}
	
	/**
	 * 取消数据加载
	 * @param now 如果为true 则立即取消所有任务，否则拒绝新添加任务，再等待已经提交的任务完成后取消
	 */
	public void cancel(boolean now) {
		if (now) mLoadService.shutdownNow();
		else mLoadService.shutdown();
	}
	
	/**
	 * 如果ImageLoader 调用了cacel方法，需要重置线程池才能重新添加任务
	 */
	public void reset() {
		mLoadService = Executors.newFixedThreadPool(10, factory);
	}
	
	/**
	 * 加载图片
	 * @param url
	 * @param tag 需要和图片一起返回的tag
	 */
	public void loadImage(final String url, final Object tag) {
		
		//如果需要缓存图片到sdcard则获取到缓存路径
		final String imgPath = params.context == null ?
				null : BitmapHelper.getImageCacheDir(params.context) + CoreUtil.toMD5(url);

		final FutureTask<ImageInfoWrapper> futureTask = new FutureTask<ImageInfoWrapper>(new Callable<ImageInfoWrapper>() {
			@Override
			public ImageInfoWrapper call() throws Exception {
				// TODO Auto-generated method stub
				if (url == null) return null;
				boolean isLoadFromLocal = true;
				final ImageInfo info = new ImageInfo();
				info.setTag(tag);
				//首先从缓存中获取图片
				byte[] bts = params.context == null ? CacheManager.newInstance().getByteFromMemory(url)
						: CacheManager.newInstance().getByteFromDisk(imgPath);
				//修改了这里：之前的bitmap是全局变量
				Bitmap bitmap = bts == null ? null : BitmapFactory.decodeByteArray(bts, 0, bts.length);
				if (bitmap == null) {
					isLoadFromLocal = false;
					URL conUrl = new URL(url);
					HttpURLConnection connection = (HttpURLConnection) conUrl.openConnection();
					connection.setConnectTimeout(10 * 1000);
					connection.setReadTimeout(30 * 1000);
					/** 首先将图片读入 , 注意此时刻不应该直接调用BitmapFactory.decodeStream ;
					 * 当图片过大时会出现OutOfMemoryException, 应先将数据读入，然后通过压缩得到图片对象(以上所述已在压缩中实现)
					 */
					bitmap = BitmapHelper.compressBitmap(connection.getInputStream(), params.reqOutW, params.reqOutH);
				}
				SoftReference<Bitmap> soft = new SoftReference<Bitmap>(bitmap);
				info.setBitmap(soft);
				bitmap = null;

				ImageInfoWrapper wrapper = new ImageInfoWrapper();
				wrapper.imageInfo = info;
				wrapper.isLoadFromLocal = isLoadFromLocal;
				return wrapper;
			}
		}) {
			@Override
			protected void done() {
				// TODO Auto-generated method stub
				super.done();
				try {
					if (isCancelled()) return;
					final ImageInfoWrapper wrapper = get();
					final ImageInfo info = wrapper.imageInfo;
					if (info.getBitmap() == null) return;
					mHandler.post(new Runnable() {
						@Override
						public void run() {
							// TODO Auto-generated method stub
							params.listener.done(info);
						}
					});
					if (wrapper.isLoadFromLocal) return;
					ByteArrayOutputStream os = new ByteArrayOutputStream();
					info.getBitmap().compress(CompressFormat.PNG, 100, os);
					if (params.context == null) {
						CacheManager.newInstance().cacheInMemory(url, os.toByteArray());
					} else {
						CacheManager.newInstance().cacheInDisk(url, os.toByteArray(), imgPath, true);
					}
					os.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
			}
		};
		if (!mLoadService.isShutdown()) {
			mLoadService.execute(futureTask);
			futureTaskCache.put(url, futureTask);
		}
	}

	/**
	 * 封装图片信息和是否从本地加载
	 * @version 1.14
	 * crated at: 2014-03-25
	 */
	private static class ImageInfoWrapper {
		ImageInfo imageInfo;
		boolean isLoadFromLocal;
	}

}
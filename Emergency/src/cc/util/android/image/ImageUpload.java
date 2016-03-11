package cc.util.android.image;

import java.io.File;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.apache.http.HttpResponse;
import org.apache.http.HttpVersion;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.FileEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.params.HttpParams;
import org.apache.http.params.HttpProtocolParams;
import org.apache.http.util.EntityUtils;

import android.content.Context;
import android.graphics.BitmapFactory.Options;
import android.os.Handler;
import android.util.Log;

/**
 * A class for helping upload bitmap and do get the orientation of the bitmap, compress bitmap etc.
 * @author wangcccong
 * @version 1.140122
 * crated at: 2014-03-22
 */
public class ImageUpload {

	/* 上传图片*/
	private final static int CONN_TIME = 10 * 1000;
	private final static int SO_TIME_ONE = 15 * 1000;  //上传单张图片
	private final static int SO_TIME = 60 * 1000;      //上传多张图片
	
	public final static String FAIL = "上传失败";
	public final static String SUCCESS = "上传成功";

	private Context mContext;
	private final ExecutorService mExecutorService;
	private final Handler mHandler;
	
	private final ThreadFactory mFactory = new ThreadFactory() {
		private AtomicInteger count = new AtomicInteger(0);

		@Override
		public Thread newThread(Runnable r) {
			// TODO Auto-generated method stub
			return new Thread(r, "ImageUpload_Thread-->"+count.getAndIncrement());
		}
	};

	public ImageUpload(Context context) {
		this.mContext = context;
		mExecutorService = Executors.newFixedThreadPool(5, mFactory);
		mHandler = new Handler();
	}

	/**
	 * 上传图片：根据所提供的压缩宽高进行压缩后传输
	 * @param url  访问地址
	 * @param path 图片本地路径
	 * @param rqsW 需要压缩的宽
	 * @param rqsH 需要压缩的高
	 * @param listener 图片上传成功后监听处理（UI线程）
	 */
	public final void compressUpload(String url, String path, int rqsW, int rqsH, AsyncListener listener) {
		String[] tempStr = {path};
		upload(url, tempStr, rqsW, rqsH, listener);
	}
	
	/**
	 * 上传图片（上传多张）：根据所提供的压缩宽高进行压缩后传输
	 * @param url  访问地址
	 * @param path 图片本地路径
	 * @param rqsW 需要压缩的宽
	 * @param rqsH 需要压缩的高
	 * @param listener 图片上传成功后监听处理（UI线程）
	 */
	/*public final void compressUpload(String url, String path[], int rqsW, int rqsH, OnImageUploadListener listener) {
		upload(url, path, rqsW, rqsH, listener);
	}*/
	
	/**
	 * 简单压缩图片上传：在压缩图片时 根据图片像素压缩
	 * <br/> pixls > 480 * 800 则压缩为 480 * 800
	 * <br/> 320 * 480 < pixls <= 480 * 800 则压缩为  320 * 480
	 * <br/> pixls <= 320 * 480 则不压缩传输
	 * @param url 访问地址
	 * @param path 图片本地路径
	 * @param listener 图片上传成功后监听处理（UI线程）
	 */
	public final void simpleCompressUpload(String url, String path, AsyncListener listener) {
		String[] tempStr = {path};
		upload(url, tempStr, 0, 0, listener);
	}
	
	/**
	 * 简单压缩图片上传（同时上传多张图片）：在压缩图片时 根据图片像素压缩
	 * <br/> pixls > 480 * 800 则压缩为 480 * 800
	 * <br/> 320 * 480 < pixls <= 480 * 800 则压缩为  320 * 480
	 * <br/> pixls <= 320 * 480 则不压缩传输
	 * @param url 访问地址
	 * @param path 图片本地路径
	 * @param listener 图片上传成功后监听处理（UI线程）
	 */
	/*public final void simpleCompressUpload(String url, String path[], OnImageUploadListener listener) {
		upload(url, path, 0, 0, listener);
	}*/
	
	private final void upload(final String url, final String paths[], final int rqsW, 
			final int rqsH, final AsyncListener mListener) {
		final FutureTask<HttpResponse> futureTask = new FutureTask<HttpResponse>(new Callable<HttpResponse>() {

			@Override
			public HttpResponse call() throws Exception {
				// TODO Auto-generated method stub
				HttpPost post = new HttpPost(url);
				HttpParams params = new BasicHttpParams();
				HttpConnectionParams.setConnectionTimeout(params, CONN_TIME);
				HttpConnectionParams.setConnectionTimeout(params, paths.length == 1 ? SO_TIME_ONE : SO_TIME);
				HttpProtocolParams.setVersion(params, HttpVersion.HTTP_1_1);
				HttpClient client = new DefaultHttpClient(params);
				try {
					//由于接口原因，暂不支持多张上传
					//MultipartEntity mpEntity = new MultipartEntity();
					for (String path : paths) {
						Options options = BitmapHelper.getBitmapOptions(path);
						int size = options.outHeight * options.outWidth;
						File file = null;
						//如果没有提供 需要压缩的宽高则按照以下规则压缩图片
						if (rqsH == 0 || rqsW == 0) {
							Log.e("size--->", options.outWidth +"----" + options.outHeight);
							if (size >= 480 * 800) {
								file = new File(BitmapHelper.compressBitmap(mContext, path,
										options.outHeight > options.outWidth ? 480 : 800, options.outHeight > options.outWidth ? 800 : 480, true));
							} else if (size > 320 * 480 && size < 480 * 800) {
								file = new File(BitmapHelper.compressBitmap(mContext, path,
										options.outHeight > options.outWidth ? 320 : 480, options.outHeight > options.outWidth ? 480 : 320, true));
							} else {
								file = new File(path);
							}
						} else {
							file = new File(BitmapHelper.compressBitmap(mContext, path, rqsW, rqsH, true));
						}
						FileEntity entity = new FileEntity(file, "binary/octet-stream");
						entity.setContentEncoding("binary/octet-stream");
						post.setEntity(entity);
					}
					return client.execute(post);
				} catch (Exception e) {
					// TODO: handle exception
					Log.e("ImageUpload-->", "上传图片错误");
				}
				return null;
			}
		}) {
			@Override
			protected void done() {
				// TODO Auto-generated method stub
				super.done();
				try {
					HttpResponse response = get();
					if (response != null) {
						Log.e("response", EntityUtils.toString(response.getEntity()));
						if (response.getStatusLine().getStatusCode() == 200) {
							doInUI(mListener, SUCCESS);
						} else {
							doInUI(mListener, FAIL);
						}
					} else {
						doInUI(mListener, FAIL);
					}
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} 
			}
		};
		if (!mExecutorService.isShutdown()) {
			mExecutorService.execute(futureTask);
		}
	}
	
	/** 
	 * a private method, help user deal logic in UI Thread.
	 * @param listener
	 * @param state
	 */
	private void doInUI(final AsyncListener listener, final String state) {
		mHandler.post(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				listener.done(state);
			}
		});
	}
}
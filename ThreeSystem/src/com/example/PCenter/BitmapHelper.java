package com.example.PCenter;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.math.BigDecimal;
import java.math.RoundingMode;



import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff.Mode;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.media.ExifInterface;
import android.util.Log;

/**
 * A class for helping deal the bitmap,
 *  like: get the orientation of the bitmap, compress bitmap etc.
 * @author wangcccong
 * @version 1.140122
 * crated at: 2014-03-22
 * update at: 2014-06-26
 */
public class BitmapHelper {

	/**
	 * get the orientation of the bitmap {@link android.media.ExifInterface}
	 * @param path
	 * @return
	 */
	public final static int getDegress(String path) {
		int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
            case ExifInterface.ORIENTATION_ROTATE_90:
                degree = 90;
                break;
            case ExifInterface.ORIENTATION_ROTATE_180:
                degree = 180;
                break;
            case ExifInterface.ORIENTATION_ROTATE_270:
                degree = 270;
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
	}
	
	/**
	 * rotate the bitmap 
	 * @param bitmap
	 * @param degress
	 * @return
	 */
	public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress); 
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }
	
	/**
	 * caculate the bitmap sampleSize
	 * @param path
	 * @return
	 */
	public final static int caculateInSampleSize(BitmapFactory.Options options, int rqsW, int rqsH) {
		final int height = options.outHeight;
	    final int width = options.outWidth;
	    int inSampleSize = 1;
	    if (rqsW == 0 || rqsH == 0) return 1;
	    if (height > rqsH || width > rqsW) {
	    	final int heightRatio = Math.round((float) height/ (float) rqsH);
	    	final int widthRatio = Math.round((float) width / (float) rqsW);
	    	inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
	    }
	    return inSampleSize;
	}
	
	/**
	 * 压缩制定路径的图片，并得到图片对象
	 * @param context
	 * @param path bitmap source path
	 * @return Bitmap {@link android.graphics.Bitmap}
	 */
	public final static Bitmap compressBitmap(String path, int rqsW, int rqsH) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inDither = false;
		options.inPurgeable = true;
		options.inTempStorage = new byte[12 * 1024];
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(path, options);
		options.inSampleSize = caculateInSampleSize(options, rqsW, rqsH);
		options.inJustDecodeBounds = false;
		File file = new File(path);
		FileInputStream fs = null;
		try {
			fs = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Bitmap bmp = null;
		if (fs != null)
			try {
				bmp = BitmapFactory.decodeFileDescriptor(fs.getFD(), null,
						options);
			} catch (IOException e) {
				e.printStackTrace();
			} finally {
				if (fs != null) {
					try {
						fs.close();
					} catch (IOException e) {
						e.printStackTrace();
					}
				}
			}
		return bmp;
	}
	
	/**
	 * 压缩制定路径图片，并将其保存在缓存目录中，通过isDelSrc判定是否删除源文件，并获取到缓存后的图片路径
	 * @param context
	 * @param srcPath
	 * @param rqsW
	 * @param rqsH
	 * @param isDelSrc
	 * @return
	 */
	public final static String compressBitmap(Context context, String srcPath, int rqsW, int rqsH, boolean isDelSrc) {
		Bitmap bitmap = compressBitmap(srcPath, rqsW, rqsH);
		File srcFile = new File(srcPath);
		String desPath = getImageCacheDir(context) + srcFile.getName();
		int degree = getDegress(srcPath);
		try {
			if (degree != 0) bitmap = rotateBitmap(bitmap, degree);
			File file = new File(desPath);
			FileOutputStream  fos = new FileOutputStream(file);
			bitmap.compress(Bitmap.CompressFormat.PNG, 30, fos);
			fos.close();
			if (isDelSrc) srcFile.deleteOnExit();
		} catch (Exception e) {
			// TODO: handle exception
			Log.e("BitmapHelper-->compressBitmap", e.getMessage()+"");
		}
		return desPath;
	}
	
	/**
	 * 此方法过期，该方法可能造成OutOfMemoryException，使用不含isAdjust参数的方法
	 * @param is
	 * @param reqsW
	 * @param reqsH
	 * @param isAdjust
	 * @return
	 */
	@Deprecated
	public final static Bitmap compressBitmap(InputStream is, int reqsW, int reqsH, boolean isAdjust) {
		Bitmap bitmap = BitmapFactory.decodeStream(is);
		return compressBitmap(bitmap, reqsW, reqsH, isAdjust);
	}
	
	/**
	 * 压缩某个输入流中的图片，可以解决网络输入流压缩问题，并得到图片对象
	 * @param context
	 * @param path bitmap source path
	 * @return Bitmap {@link android.graphics.Bitmap}
	 */
	public final static Bitmap compressBitmap(InputStream is, int reqsW, int reqsH) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			byte[] bts = new byte[1024];
			int len = 0;
			while ((len = is.read(bts)) != -1) {
				baos.write(bts, 0, len);
				baos.flush();
			}
			byte[] bytes = baos.toByteArray();
			Bitmap bitmap = compressBitmap(bytes, reqsW, reqsH);
			is.close();
			baos.close();
			return bitmap;
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			Log.e("BitmapHelper--inputstream", e.getMessage()+"");
			return null;
		}
	}
	
	/**
	 * 压缩制定byte[]图片，并得到压缩后的图像
	 * @param bts
	 * @param reqsW
	 * @param reqsH
	 * @return
	 */
	public final static Bitmap compressBitmap(byte[] bts, int reqsW, int reqsH) {
		final Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeByteArray(bts, 0, bts.length, options);
		options.inSampleSize = caculateInSampleSize(options, reqsW, reqsH);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeByteArray(bts, 0, bts.length, options);
	}
	
	/**
	 * 此方法已过期，该方法可能造成OutOfMemoryException，使用不含isAdjust参数的方法
	 * @param bitmap
	 * @param reqsW
	 * @param reqsH
	 * @return
	 */
	@Deprecated
	public final static Bitmap compressBitmap(Bitmap bitmap, int reqsW, int reqsH, boolean isAdjust) {
		if (bitmap == null || reqsW == 0 || reqsH == 0) return bitmap;
		if (bitmap.getWidth() > reqsW || bitmap.getHeight() > reqsH) {
			float scaleX = new BigDecimal(reqsW).divide(new BigDecimal(bitmap.getWidth()), 4, RoundingMode.DOWN).floatValue();
			float scaleY = new BigDecimal(reqsH).divide(new BigDecimal(bitmap.getHeight()), 4, RoundingMode.DOWN).floatValue();
			if (isAdjust) {
				scaleX = scaleX < scaleY ? scaleX : scaleY;
				scaleY = scaleX;
			}
			Matrix matrix = new Matrix();
			matrix.postScale(scaleX, scaleY);
			bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);
		}
		return bitmap;
	}
	
	/**
	 * 压缩已存在的图片对象，并返回压缩后的图片
	 * @param bitmap
	 * @param reqsW
	 * @param reqsH
	 * @return
	 */
	public final static Bitmap compressBitmap(Bitmap bitmap, int reqsW, int reqsH) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.PNG, 100, baos);
			byte[] bts = baos.toByteArray();
			Bitmap res = compressBitmap(bts, reqsW, reqsH);
			baos.close();
			return res;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return bitmap;
		}
	}
	
	/**
	 * 此方法过期，该方法可能造成OutOfMemoryException，使用不含isAdjust参数的方法
	 * get bitmap form resource dictory, and then compress bitmap according to reqsW and reqsH
	 * @param res {@link android.content.res.Resources}
	 * @param resID
	 * @param reqsW
	 * @param reqsH
	 * @return
	 */
	@Deprecated
	public final static Bitmap compressBitmap(Resources res, int resID, int reqsW, int reqsH, boolean isAdjust) {
		Bitmap bitmap = BitmapFactory.decodeResource(res, resID);
		return compressBitmap(bitmap, reqsW, reqsH, isAdjust);
	}
	
	/**
	 * 压缩资源图片，并返回图片对象
	 * @param res {@link android.content.res.Resources}
	 * @param resID
	 * @param reqsW
	 * @param reqsH
	 * @return
	 */
	public final static Bitmap compressBitmap(Resources res, int resID, int reqsW, int reqsH) {
		final Options options = new Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeResource(res, resID, options);
		options.inSampleSize = caculateInSampleSize(options, reqsW, reqsH);
		options.inJustDecodeBounds = false;
		return BitmapFactory.decodeResource(res, resID, options);
	}
	
	
	
	/**
	 * 基于质量的压缩算法， 此方法未 解决压缩后图像失真问题
	 * <br> 可先调用比例压缩适当压缩图片后，再调用此方法可解决上述问题
	 * @param bts
	 * @param maxBytes 压缩后的图像最大大小 单位为byte
	 * @return
	 */
	public final static Bitmap compressBitmap(Bitmap bitmap, long maxBytes) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			bitmap.compress(CompressFormat.PNG, 100, baos);
			int options = 90;
			while (baos.toByteArray().length > maxBytes) {
				baos.reset();
				bitmap.compress(CompressFormat.PNG, options, baos);
				options -= 10;
			}
			byte[] bts = baos.toByteArray();
			Bitmap bmp = BitmapFactory.decodeByteArray(bts, 0, bts.length);
			baos.close();
			return bmp;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}
	
//	public final static Bitmap compressBitmap(InputStream is, long maxBytes) {
//		try {
//			ByteArrayOutputStream baos = new ByteArrayOutputStream();
//			byte[] bts = new byte[1024];
//			while (is.read(bts) != -1) baos.write(bts, 0, bts.length);
//			is.close();
//			int options = 100;
//			while (baos.toByteArray().length > maxBytes) {
//				
//			}
//		} catch (Exception e) {
//			// TODO: handle exception
//		}
//	}
	
	/**
	 * 得到制定路径图片的options
	 * @param srcPath
	 * @return Options {@link android.graphics.BitmapFactory.Options}
	 */
	public final static Options getBitmapOptions(String srcPath) {
		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		BitmapFactory.decodeFile(srcPath, options);
		return options;
	}
	
	/**
	 * 获取图片缓存路径
	 * @param context
	 * @return
	 */
	private static String getImageCacheDir(Context context) {
		String dir = FileHelper.getCacheDir(context) + "Image" + File.separator;
		File file = new File(dir);
		if (!file.exists()) file.mkdirs();
		return dir;
	}
	
	
	public static Bitmap getRoundedCornerBitmap(Bitmap bitmap,float roundPx){
		Bitmap output = Bitmap.createBitmap(bitmap.getWidth(),bitmap.getHeight(),Config.ARGB_8888);
		Canvas canvas = new Canvas(output);
		final int color = 0xff424242;
		final Paint paint = new Paint();
		final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
		final RectF rectF = new RectF(rect);
		paint.setAntiAlias(true);
		canvas.drawARGB(0, 0, 0, 0);
		paint.setColor(color);
		canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
		paint.setXfermode(new PorterDuffXfermode(Mode.SRC_IN));
		canvas.drawBitmap(bitmap, rect, rect, paint);
		//bitmap.recycle();
		return output;
	}
	public static  Bitmap comp(Bitmap image) {  
	       
	    ByteArrayOutputStream baos = new ByteArrayOutputStream();         
	    image.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
	    if( baos.toByteArray().length / 1024>800) {//判断如果图片大于1M,进行压缩避免在生成图片（BitmapFactory.decodeStream）时溢出    
	        baos.reset();//重置baos即清空baos  
	        image.compress(Bitmap.CompressFormat.JPEG, 50, baos);//这里压缩50%，把压缩后的数据存放到baos中  
	    }  
	    ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());  
	    BitmapFactory.Options newOpts = new BitmapFactory.Options();  
	    //开始读入图片，此时把options.inJustDecodeBounds 设回true了  
	    newOpts.inJustDecodeBounds = true;  
	    Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
	    newOpts.inJustDecodeBounds = false;  
	    int w = newOpts.outWidth;  
	    int h = newOpts.outHeight;  
	    //现在主流手机比较多是800*480分辨率，所以高和宽我们设置为  
	    float hh = 800f;//这里设置高度为800f  
	    float ww = 480f;//这里设置宽度为480f 
//	    float hh = 1920f;//这里设置高度为800f  
//	    float ww = 1080f;//这里设置宽度为480f
	    //缩放比。由于是固定比例缩放，只用高或者宽其中一个数据进行计算即可  
	    int be = 1;//be=1表示不缩放  
	    if (w > h && w > ww) {//如果宽度大的话根据宽度固定大小缩放  
	        be = (int) (newOpts.outWidth / ww);  
	    } else if (w < h && h > hh) {//如果高度高的话根据宽度固定大小缩放  
	        be = (int) (newOpts.outHeight / hh);  
	    }  
	    if (be <= 0)  
	        be = 1;  
	    newOpts.inSampleSize = be;//设置缩放比例  
	    //重新读入图片，注意此时已经把options.inJustDecodeBounds 设回false了  
	    isBm = new ByteArrayInputStream(baos.toByteArray());  
	    bitmap = BitmapFactory.decodeStream(isBm, null, newOpts);  
	    return compressImage(bitmap);//压缩好比例大小后再进行质量压缩  
	}
	public static  Bitmap compressImage(Bitmap image) {  
		   
        ByteArrayOutputStream baos = new ByteArrayOutputStream();  
        image.compress(Bitmap.CompressFormat.JPEG, 80, baos);//质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中  
        int options = 100;  
        while ( baos.toByteArray().length / 1024>100) {  //循环判断如果压缩后图片是否大于100kb,大于继续压缩         
            baos.reset();//重置baos即清空baos  
            image.compress(Bitmap.CompressFormat.JPEG, options, baos);//这里压缩options%，把压缩后的数据存放到baos中  
            options -= 10;//每次都减少10  
        }  
        ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());//把压缩后的数据baos存放到ByteArrayInputStream中  
        Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);//把ByteArrayInputStream数据生成图片  
        return bitmap;  
    }
}

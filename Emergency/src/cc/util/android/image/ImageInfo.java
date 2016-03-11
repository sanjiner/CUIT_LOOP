package cc.util.android.image;

import java.lang.ref.SoftReference;

import android.graphics.Bitmap;

/**
 * @author wangcccong
 * @version 1.140122
 * crated at:14-02-12
 */
public class ImageInfo {

	private SoftReference<Bitmap> softBitmap;  //图片信息
	private Object tag;                        //与图片一起附带的tag(可以是图片索引，或者其他需要传递的object)
	
	public Bitmap getBitmap() {
		return softBitmap.get();
	}

	public void setBitmap(SoftReference<Bitmap> softReference) {
		this.softBitmap = softReference;
	}

	public Object getTag() {
		return tag;
	}

	public void setTag(Object tag) {
		this.tag = tag;
	}
	
}

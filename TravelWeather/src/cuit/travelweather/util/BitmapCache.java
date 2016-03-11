package cuit.travelweather.util;

import java.lang.ref.SoftReference;
import java.util.HashMap;

import android.graphics.Bitmap;

public class BitmapCache {

	static private BitmapCache cache;
	// »Ì“˝”√
	private HashMap<String, SoftReference<Bitmap>> imageCache;

	public BitmapCache() {
		imageCache = new HashMap<String, SoftReference<Bitmap>>();
	}

	public static BitmapCache getInstance() {

		if (cache == null) {
			cache = new BitmapCache();
		}

		return cache;

	}

	public Bitmap getBitmap(String key) {

		if (imageCache.containsKey(key)) {

			SoftReference<Bitmap> reference = imageCache.get(key);
			Bitmap bitmap = reference.get();

			if (bitmap != null) {
				return bitmap;
			}
		}

		return null;

	}

	public void putSoftReference(Bitmap bitmap, String key) {

		imageCache.put(key, new SoftReference<Bitmap>(bitmap));

	}

}
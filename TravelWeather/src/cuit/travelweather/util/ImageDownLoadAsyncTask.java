package cuit.travelweather.util;

import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

public class ImageDownLoadAsyncTask extends AsyncTask<Void, Void, Bitmap> {

	private String imagePath;
	private ImageView imageView;
	private Context context;
	private AssetManager assetManager;
	private int Image_width;// 显示图片的宽度
	private final String file = "images/";
	private LinearLayout progressbar;
	private TextView loadtext;

	public ImageDownLoadAsyncTask(Context context, String imgePath,
			ImageView imageView, int Image_width) {
		this.context = context;
		this.imagePath = imgePath;
		this.Image_width = Image_width;
		this.imageView = imageView;
		assetManager = this.context.getAssets();
	}

	public void setLoadtext(TextView loadtext) {
		this.loadtext = loadtext;
	}

	public void setProgressbar(LinearLayout progressbar) {
		this.progressbar = progressbar;
	}

	@Override
	protected Bitmap doInBackground(Void... params) {
		// TODO Auto-generated method stub
		try {
			BitmapFactory.Options options = new BitmapFactory.Options();
			options.inJustDecodeBounds = false;// 这里只返回bitmap的大小
			InputStream inputStream = assetManager.open(file + imagePath);
			Bitmap bitmap = BitmapFactory.decodeStream(inputStream, null,
					options);
			return bitmap;
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	protected void onPostExecute(Bitmap result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);

		if (result != null) {
			LayoutParams layoutParams = imageView.getLayoutParams();
			int height = result.getHeight();// 获取图片的高度.
			int width = result.getWidth();// 获取图片的宽度
			layoutParams.height = (height * Image_width) / width;

			imageView.setLayoutParams(layoutParams);
			imageView.setImageBitmap(result);
		}

		if (progressbar.isShown() || loadtext.isShown()) {
			progressbar.setVisibility(View.GONE);
			loadtext.setVisibility(View.GONE);
		}

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();

		if (!loadtext.isShown()) {
			loadtext.setVisibility(View.VISIBLE);
		}

	}

}

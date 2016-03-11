package cuit.emergency.fragment;

import java.lang.ref.WeakReference;

import cuit.emergency.R;
import cuit.emergency.app.ScaleImageView;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import cc.util.android.core.BaseFragment;
import cc.util.android.core.ToastUtil;
import cc.util.android.viewInject.ResInject;
import cc.util.android.viewInject.ViewInjectUtil;

public class ScanPictureFragment extends BaseFragment {

	public static final String IMAGEPATH = "imgPath";
	public static final String IMAGEBYTES = "imgBytes";
	
	@ResInject(R.id.scanpicture_imgLinear)
	LinearLayout mLinearLayout;
	
	private WeakReference<Bitmap> refBitmap;
	private Bitmap bitmap;
	public Handler mHandler = new Handler(new Handler.Callback() {
		
		@Override
		public boolean handleMessage(Message msg) {
			// TODO Auto-generated method stub
			switch (msg.what) {
			case -1:
				ToastUtil.showShort(getActivity(), "图片未存储，无法获取");
				break;
			case 0:
				ScaleImageView viewArea = new ScaleImageView(getActivity(), refBitmap.get());
				mLinearLayout.removeAllViews();
				LayoutParams params = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT);
				mLinearLayout.addView(viewArea, params);
			default:
				break;
			}
			return true;
		}
	});
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		View view = inflater.inflate(R.layout.fragment_scan_picture, container, false);
		ViewInjectUtil.inject(this, view);
		return view;
	}
	
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		mHandler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				initView();
			}
		}, 200);
	}
	
	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
	}
	
	@Override
	public void onHiddenChanged(boolean hidden) {
		// TODO Auto-generated method stub
		super.onHiddenChanged(hidden);
		if (!hidden) {
			mActionBar.setTitle("查看大图");
			mActionBar.showLeftView();
		}
	}
	
	private void initView() {
		// TODO Auto-generated method stub
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					bitmap = BitmapFactory.decodeFile(getArguments().getString(IMAGEPATH));
					if (bitmap == null) {
						byte[] bts = getArguments().getByteArray(IMAGEBYTES);
						bitmap = BitmapFactory.decodeByteArray(bts, 0, bts.length);
					}
					if(bitmap == null) {
						mHandler.sendEmptyMessage(-1);
					}else {
						refBitmap = new WeakReference<Bitmap>(bitmap);
						mHandler.sendEmptyMessage(0);
					}
				} catch (Exception e) {
					// TODO: handle exception
					mHandler.sendEmptyMessage(-1);
				}
				
			}
		}).start();
		
	}
}

package cuit.emergency;

import java.lang.ref.WeakReference;
import cc.util.android.core.AbsActivity;
import cc.util.android.viewInject.ResInject;
import cc.util.android.viewInject.ViewInjectUtil;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.Animation.AnimationListener;
import android.widget.ImageView;

public class LoadingActivity extends AbsActivity {
	
	@ResInject(R.id.loading_image)
	ImageView mImageView;
	
	Drawable drawable;
	WeakReference<Drawable> wfReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, 
        		WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_loading);
        ViewInjectUtil.inject(this);
    }
    
    @SuppressWarnings("deprecation")
	@Override
    protected void onResume() {
    	// TODO Auto-generated method stub
    	super.onResume();
    	AlphaAnimation animation = new AlphaAnimation(1.0f, 0.5f);
    	animation.setDuration(4 * 1000);
    	animation.setAnimationListener(new AnimationListener() {
			
			@Override
			public void onAnimationStart(Animation animation) {
				// TODO Auto-generated method stub
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onAnimationEnd(Animation animation) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(LoadingActivity.this, LoginActivity.class);
				intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
				startActivity(intent);
				finish();
			}
		});
    	Bitmap bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.bg_loading);
    	drawable = new BitmapDrawable(getResources(), bitmap);
    	wfReference = new WeakReference<Drawable>(drawable);
    	bitmap = null;
    	mImageView.setBackgroundDrawable(wfReference.get());
    	mImageView.setAnimation(animation);
    }
    
    @Override
    protected void onDestroy() {
    	// TODO Auto-generated method stub
    	super.onDestroy();
    	wfReference.clear();
    }

}

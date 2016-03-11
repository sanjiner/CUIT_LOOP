package cuit.travelweather.activity;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.widget.RadioGroup;
import cuit.travelweather.R;
import cuit.travelweather.fragment.PictureFragment;



public class PictureTemp extends BaseAct{
	private PictureFragment pictureFragment;

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_picture);
		
//	 pictureFragment = (PictureFragment) getSupportFragmentManager().findFragmentById(R.id.PictureFragment);
		pictureFragment.isVisable = true;
	 refreshPictures();
	
}
	public void refreshPictures() {
		if (pictureFragment.isVisable) {
			switch (pictureFragment.radioGroup.getCheckedRadioButtonId()) {
			case R.id.picture_btn_hot:
				pictureFragment
						.initLoad(pictureFragment.CONSTANT_PICTURE_BTN_HOT);
				break;
			case R.id.picture_btn_eat:
				pictureFragment
						.initLoad(pictureFragment.CONSTANT_PICTURE_BTN_EAT);
				break;
			case R.id.picture_btn_hotel:
				pictureFragment
						.initLoad(pictureFragment.CONSTANT_PICTURE_BTN_HOTEL);
				break;
			case R.id.picture_btn_road:
				pictureFragment
						.initLoad(pictureFragment.CONSTANT_PICTURE_BTN_ROAD);
				break;
			case R.id.picture_btn_shop:
				pictureFragment
						.initLoad(pictureFragment.CONSTANT_PICTURE_BTN_SHOP);
				break;
			default:
				pictureFragment
				.initLoad(pictureFragment.CONSTANT_PICTURE_BTN_HOT);
			}
		}
	}
	
}

package cuit.travelweather.activity;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentActivity;
import android.telephony.TelephonyManager;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.RelativeLayout;
import android.widget.TabHost;
import android.widget.TabWidget;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cuit.travelweather.R;
import cuit.travelweather.fragment.PictureFragment;
import cuit.travelweather.fragment.PlaceFragment;
import cuit.travelweather.fragment.TravelFragment;
import cuit.travelweather.fragment.WeatherFragment;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.MyFont;
import cuit.travelweather.util.MyHttpClient;
// import android.provider.DocumentsContract;

/*
 * 软件主界面Activity
 */
public class MainActivity extends BaseAct {
	private static final int DISMISS_PROCESS = 0;
	private static final int SHOW_PROCESS = 1;
	private TabHost tabHost;
	private boolean picLoaded = false;
	private boolean placeLoaded = false;
	private int status;
	private Context context = MainActivity.this;
	private SharedPreferences sp,sp1;
	private Editor editor;
	protected String[] jsonStr;
	private TextView line_tv_from;
	private TextView line_tv_to;
	private TextView line_tv_node1;
	private TextView line_tv_node2;
	private TextView line_tv_node3;
	private TravelFragment travelFragment;
	private WeatherFragment weatherFragment;
	private PictureFragment pictureFragment;
	private PlaceFragment placefragment;
	public boolean isExit;

	public Handler mHandler = new Handler() {

		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			super.handleMessage(msg);
			isExit = false;
		}

	};
	private int strContentString;
	private PictureFragment mWeixin;
	private String a="abc";

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);


		initTheme();
		ShareSDK.initSDK(this);
		init();
		getTel();
		MyFont.setTypeface(this, new int[] { R.id.travel_line_maked,
				R.id.travel_line_new, R.id.more_title, R.id.picture_btn_hot,
				R.id.picture_btn_eat, R.id.picture_btn_hotel,
				R.id.picture_btn_road, R.id.picture_btn_shop });     
	
		
		
	}
	
//	private void setDefaultFragment()  
//    {  
//		FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction  fragmentTransaction = fragmentManager.beginTransaction();
//        PictureFragment newfragment =new PictureFragment();
//        fragmentTransaction.replace(R.layout.tabcontent,newfragment ).commit();
//    } 
//	

	@Override
	protected void onRestart() {
		super.onRestart();
		initTheme();
	}
	
	@SuppressLint("NewApi")
	public void initTheme(){
		if(getSharedPreferences("Theme_SP", MODE_PRIVATE).getString("theme_type", "jianzhi").equals("qinghuaci")){
			//main
			RadioGroup main_tabs = (RadioGroup) findViewById(R.id.main_tabs);
			main_tabs.setBackgroundResource(R.drawable.main_tab_bg_qhc);
			
			RadioButton weatheRadioButton = (RadioButton) findViewById(R.id.main_tabs_weather);
			weatheRadioButton.setTextColor(this.getResources().getColor(R.color.checked_white_blue));
			weatheRadioButton.setBackgroundResource(R.drawable.main_tabbtn_bg_qhc);
			weatheRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, this.getResources().getDrawable(R.drawable.main_weather_qhc), null, null);
			
			RadioButton travelRadioButton = (RadioButton) findViewById(R.id.main_tabs_travel);
			travelRadioButton.setTextColor(this.getResources().getColor(R.color.checked_white_blue));
			travelRadioButton.setBackgroundResource(R.drawable.main_tabbtn_bg_qhc);
			travelRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, this.getResources().getDrawable(R.drawable.main_travel_qhc), null, null);
			
			RadioButton placeRadioButton = (RadioButton) findViewById(R.id.main_tabs_place);
			placeRadioButton.setTextColor(this.getResources().getColor(R.color.checked_white_blue));
			placeRadioButton.setBackgroundResource(R.drawable.main_tabbtn_bg_qhc);
			placeRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, this.getResources().getDrawable(R.drawable.main_place_qhc), null, null);
			
			RadioButton pictureRadioButton = (RadioButton) findViewById(R.id.main_tabs_picture);
			pictureRadioButton.setTextColor(this.getResources().getColor(R.color.checked_white_blue));
			pictureRadioButton.setBackgroundResource(R.drawable.main_tabbtn_bg_qhc);
			pictureRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, this.getResources().getDrawable(R.drawable.main_picture_qhc), null, null);
			
			RadioButton moreRadioButton = (RadioButton) findViewById(R.id.main_tabs_more);
			moreRadioButton.setTextColor(this.getResources().getColor(R.color.checked_white_blue));
			moreRadioButton.setBackgroundResource(R.drawable.main_tabbtn_bg_qhc);
			moreRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, this.getResources().getDrawable(R.drawable.main_more_qhc), null, null);
		
			//main_travel
			LinearLayout main_travel_linear = (LinearLayout) findViewById(R.id.main_travel);
			main_travel_linear.setBackgroundResource(R.drawable.login_bg_qhc);
			
			RelativeLayout main_travel_relative = (RelativeLayout) findViewById(R.id.main_travel_relative);
			main_travel_relative.setBackgroundResource(R.drawable.weather_top_bg_qhc);
			
			RadioButton travel_line_maked = (RadioButton) findViewById(R.id.travel_line_maked);
			travel_line_maked.setBackgroundResource(R.drawable.pic_top_btn_qhc);
			travel_line_maked.setTextColor(this.getResources().getColor(R.color.checked_yellow_blue));
			
			RadioButton travel_line_new = (RadioButton) findViewById(R.id.travel_line_new);
			travel_line_new.setBackgroundResource(R.drawable.pic_top_btn_qhc);
			travel_line_new.setTextColor(this.getResources().getColor(R.color.checked_yellow_blue));
			
			TextView node_tip_tv = (TextView) findViewById(R.id.tv_node_tip);
			node_tip_tv.setTextColor(this.getResources().getColor(R.color.theme_bule));
			
			Button addButton = (Button) findViewById(R.id.add_button);
			addButton.setBackgroundResource(R.drawable.btn_theme1_qhc);
			addButton.setTextColor(this.getResources().getColor(R.color.theme_bule));
			
			TextView line_clear_tv = (TextView) findViewById(R.id.line_tv_clear);
			line_clear_tv.setTextColor(this.getResources().getColor(R.color.theme_bule));
			
			ImageButton list_map_IB = (ImageButton) findViewById(R.id.iv_list_map);
			list_map_IB.setBackgroundResource(R.drawable.list_map_qhc);
			
			TabWidget tabWidget = (TabWidget) findViewById(android.R.id.tabs);
			tabWidget.setBackgroundResource(R.drawable.weather_top_bg_qhc);
			
			//main_palce
			RelativeLayout main_place = (RelativeLayout) findViewById(R.id.main_place);
			main_place.setBackgroundResource(R.drawable.login_bg_qhc);
			
			//main_picture
			RelativeLayout main_picture = (RelativeLayout) findViewById(R.id.main_picture);
			main_picture.setBackgroundResource(R.drawable.login_bg_qhc);
			
			RadioGroup picture_topbar = (RadioGroup) findViewById(R.id.picture_topbar);
			picture_topbar.setBackgroundResource(R.drawable.weather_top_bg_qhc);
			
			RadioButton picture_btn_hot = (RadioButton) findViewById(R.id.picture_btn_hot);
			picture_btn_hot.setTextColor(this.getResources().getColor(R.color.checked_yellow_blue));
			picture_btn_hot.setBackgroundResource(R.drawable.pic_top_btn_qhc);
			
			RadioButton picture_btn_eat = (RadioButton) findViewById(R.id.picture_btn_eat);
			picture_btn_eat.setTextColor(this.getResources().getColor(R.color.checked_yellow_blue));
			picture_btn_eat.setBackgroundResource(R.drawable.pic_top_btn_qhc);
			
			RadioButton picture_btn_hotel = (RadioButton) findViewById(R.id.picture_btn_hotel);
			picture_btn_hotel.setTextColor(this.getResources().getColor(R.color.checked_yellow_blue));
			picture_btn_hotel.setBackgroundResource(R.drawable.pic_top_btn_qhc);
			
			RadioButton picture_btn_road = (RadioButton) findViewById(R.id.picture_btn_road);
			picture_btn_road.setTextColor(this.getResources().getColor(R.color.checked_yellow_blue));
			picture_btn_road.setBackgroundResource(R.drawable.pic_top_btn_qhc);
			
			RadioButton picture_btn_shop = (RadioButton) findViewById(R.id.picture_btn_shop);
			picture_btn_shop.setTextColor(this.getResources().getColor(R.color.checked_yellow_blue));
			picture_btn_shop.setBackgroundResource(R.drawable.pic_top_btn_qhc);
			
			//main_more
			RelativeLayout main_more = (RelativeLayout) findViewById(R.id.main_more);
			main_more.setBackgroundResource(R.drawable.login_bg_qhc);
			
			TextView more_title_tv = (TextView) findViewById(R.id.more_title);
			more_title_tv.setTextColor(this.getResources().getColor(R.color.theme_bule));
			more_title_tv.setBackgroundResource(R.drawable.travel_top_bg_qhc);
			
			TextView more_infocenter = (TextView) findViewById(R.id.more_infocenter);
			more_infocenter.setBackgroundResource(R.drawable.ic_preference_first_qhc);
			
			TextView more_usersetting = (TextView) findViewById(R.id.more_usersetting);
			more_usersetting.setBackgroundResource(R.drawable.ic_preference_last_qhc);
			
			TextView more_sharemanage = (TextView) findViewById(R.id.more_sharemanage);
			more_sharemanage.setBackgroundResource(R.drawable.ic_preference_first_qhc);
			
			TextView more_feature = (TextView) findViewById(R.id.more_feature);
			more_feature.setBackgroundResource(R.drawable.ic_preference_mid_qhc);
			
			TextView more_recommend = (TextView) findViewById(R.id.more_recommend);
			more_recommend.setBackgroundResource(R.drawable.ic_preference_last_qhc);
			
			TextView more_disclaimer = (TextView) findViewById(R.id.more_disclaimer);
			more_disclaimer.setBackgroundResource(R.drawable.ic_preference_first_qhc);
			
			TextView more_about = (TextView) findViewById(R.id.more_about);
			more_about.setBackgroundResource(R.drawable.ic_preference_last_qhc);
			
			Button main_more_logout = (Button) findViewById(R.id.main_more_logout);
			main_more_logout.setBackgroundResource(R.drawable.logout_btn_qhc);
		}
		else if(getSharedPreferences("Theme_SP", MODE_PRIVATE).getString("theme_type", "jianzhi").equals("jianzhi"))
		{
			RadioGroup main_tabs = (RadioGroup) findViewById(R.id.main_tabs);
			main_tabs.setBackgroundResource(R.drawable.main_tab_bg);
			
			RadioButton weatheRadioButton = (RadioButton) findViewById(R.id.main_tabs_weather);
			weatheRadioButton.setTextColor(this.getResources().getColor(R.color.checked_white_red));
			weatheRadioButton.setBackgroundResource(R.drawable.main_tabbtn_bg);
			weatheRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, this.getResources().getDrawable(R.drawable.main_weather), null, null);
			
			RadioButton travelRadioButton = (RadioButton) findViewById(R.id.main_tabs_travel);
			travelRadioButton.setTextColor(this.getResources().getColor(R.color.checked_white_red));
			travelRadioButton.setBackgroundResource(R.drawable.main_tabbtn_bg);
			travelRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, this.getResources().getDrawable(R.drawable.main_travel), null, null);
			
			RadioButton placeRadioButton = (RadioButton) findViewById(R.id.main_tabs_place);
			placeRadioButton.setTextColor(this.getResources().getColor(R.color.checked_white_red));
			placeRadioButton.setBackgroundResource(R.drawable.main_tabbtn_bg);
			placeRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, this.getResources().getDrawable(R.drawable.main_place), null, null);
			
			RadioButton pictureRadioButton = (RadioButton) findViewById(R.id.main_tabs_picture);
			pictureRadioButton.setTextColor(this.getResources().getColor(R.color.checked_white_red));
			pictureRadioButton.setBackgroundResource(R.drawable.main_tabbtn_bg);
			pictureRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, this.getResources().getDrawable(R.drawable.main_picture), null, null);
			
			RadioButton moreRadioButton = (RadioButton) findViewById(R.id.main_tabs_more);
			moreRadioButton.setTextColor(this.getResources().getColor(R.color.checked_white_red));
			moreRadioButton.setBackgroundResource(R.drawable.main_tabbtn_bg);
			moreRadioButton.setCompoundDrawablesRelativeWithIntrinsicBounds(null, this.getResources().getDrawable(R.drawable.main_more), null, null);
		
			//main_travel
			LinearLayout main_travel_linear = (LinearLayout) findViewById(R.id.main_travel);
			main_travel_linear.setBackgroundResource(R.drawable.login_bg);
			
			RelativeLayout main_travel_relative = (RelativeLayout) findViewById(R.id.main_travel_relative);
			main_travel_relative.setBackgroundResource(R.drawable.weather_top_bg);
			
			RadioButton travel_line_maked = (RadioButton) findViewById(R.id.travel_line_maked);
			travel_line_maked.setBackgroundResource(R.drawable.pic_top_btn);
			travel_line_maked.setTextColor(this.getResources().getColor(R.color.checked_yellow_red));
			
			RadioButton travel_line_new = (RadioButton) findViewById(R.id.travel_line_new);
			travel_line_new.setBackgroundResource(R.drawable.pic_top_btn);
			travel_line_new.setTextColor(this.getResources().getColor(R.color.checked_yellow_red));
			
			TextView node_tip_tv = (TextView) findViewById(R.id.tv_node_tip);
			node_tip_tv.setTextColor(this.getResources().getColor(R.color.theme1_red));
			
			Button addButton = (Button) findViewById(R.id.add_button);
			addButton.setBackgroundResource(R.drawable.btn_theme1);
			addButton.setTextColor(this.getResources().getColor(R.color.theme1_red));
			
			TextView line_clear_tv = (TextView) findViewById(R.id.line_tv_clear);
			line_clear_tv.setTextColor(this.getResources().getColor(R.color.theme1_red));
			
			ImageButton list_map_IB = (ImageButton) findViewById(R.id.iv_list_map);
			list_map_IB.setBackgroundResource(R.drawable.list_map);
			
			TabWidget tabWidget = (TabWidget) findViewById(android.R.id.tabs);
			tabWidget.setBackgroundResource(R.drawable.weather_top_bg);
			
			//main_palce
			RelativeLayout main_place = (RelativeLayout) findViewById(R.id.main_place);
			main_place.setBackgroundResource(R.drawable.login_bg);
			
			//main_picture
			RelativeLayout main_picture = (RelativeLayout) findViewById(R.id.main_picture);
			main_picture.setBackgroundResource(R.drawable.login_bg);
			
			RadioGroup picture_topbar = (RadioGroup) findViewById(R.id.picture_topbar);
			picture_topbar.setBackgroundResource(R.drawable.weather_top_bg);
			
			RadioButton picture_btn_hot = (RadioButton) findViewById(R.id.picture_btn_hot);
			picture_btn_hot.setTextColor(this.getResources().getColor(R.color.checked_yellow_red));
			picture_btn_hot.setBackgroundResource(R.drawable.pic_top_btn);
			
			RadioButton picture_btn_eat = (RadioButton) findViewById(R.id.picture_btn_eat);
			picture_btn_eat.setTextColor(this.getResources().getColor(R.color.checked_yellow_red));
			picture_btn_eat.setBackgroundResource(R.drawable.pic_top_btn);
			
			RadioButton picture_btn_hotel = (RadioButton) findViewById(R.id.picture_btn_hotel);
			picture_btn_hotel.setTextColor(this.getResources().getColor(R.color.checked_yellow_red));
			picture_btn_hotel.setBackgroundResource(R.drawable.pic_top_btn);
			
			RadioButton picture_btn_road = (RadioButton) findViewById(R.id.picture_btn_road);
			picture_btn_road.setTextColor(this.getResources().getColor(R.color.checked_yellow_red));
			picture_btn_road.setBackgroundResource(R.drawable.pic_top_btn);
			
			RadioButton picture_btn_shop = (RadioButton) findViewById(R.id.picture_btn_shop);
			picture_btn_shop.setTextColor(this.getResources().getColor(R.color.checked_yellow_red));
			picture_btn_shop.setBackgroundResource(R.drawable.pic_top_btn);
			
			//main_more
			RelativeLayout main_more = (RelativeLayout) findViewById(R.id.main_more);
			main_more.setBackgroundResource(R.drawable.login_bg);
			
			TextView more_title_tv = (TextView) findViewById(R.id.more_title);
			more_title_tv.setTextColor(this.getResources().getColor(R.color.theme_bule));
			more_title_tv.setBackgroundResource(R.drawable.travel_top_bg);
			
			TextView more_infocenter = (TextView) findViewById(R.id.more_infocenter);
			more_infocenter.setBackgroundResource(R.drawable.ic_preference_first);
			
			TextView more_usersetting = (TextView) findViewById(R.id.more_usersetting);
			more_usersetting.setBackgroundResource(R.drawable.ic_preference_last);
			
			TextView more_sharemanage = (TextView) findViewById(R.id.more_sharemanage);
			more_sharemanage.setBackgroundResource(R.drawable.ic_preference_first);
			
			TextView more_feature = (TextView) findViewById(R.id.more_feature);
			more_feature.setBackgroundResource(R.drawable.ic_preference_mid);
			
			TextView more_recommend = (TextView) findViewById(R.id.more_recommend);
			more_recommend.setBackgroundResource(R.drawable.ic_preference_last);
			
			TextView more_disclaimer = (TextView) findViewById(R.id.more_disclaimer);
			more_disclaimer.setBackgroundResource(R.drawable.ic_preference_first);
			
			TextView more_about = (TextView) findViewById(R.id.more_about);
			more_about.setBackgroundResource(R.drawable.ic_preference_last);
			
			Button main_more_logout = (Button) findViewById(R.id.main_more_logout);
			main_more_logout.setBackgroundResource(R.drawable.logout_btn);
		}
		
	}



	/******************************** added by l ********************************/
	public static final int MENU_CAMP_PIC = 2;
	public static final int MENU_SEL_PIC = 3;
	public static final int MENU_SHARE = 4;
	public static final int MENU_ADDSHIP = 5;
	public static final int MENU_ORDER_BY_HOT = 0;
	public static final int MENU_ORDER_BY_DISTANCE = 1;

	public static int orderBy = MENU_ORDER_BY_HOT;

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		super.onCreateOptionsMenu(menu);

		menu.add(Menu.NONE, MENU_CAMP_PIC, MENU_CAMP_PIC, "拍照");
		menu.add(Menu.NONE, MENU_SEL_PIC, MENU_SEL_PIC, "选择照片");
		menu.add(Menu.NONE, MENU_SHARE, MENU_SHARE, "分享");
		menu.add(Menu.NONE,MENU_ADDSHIP,MENU_ADDSHIP,"新增景区");
		// if (pictureFragment.isVisable) {
		menu.add(Menu.FIRST, MENU_ORDER_BY_HOT, MENU_ORDER_BY_HOT, "根据热度排序图片");
		menu.add(Menu.FIRST, MENU_ORDER_BY_DISTANCE, MENU_ORDER_BY_DISTANCE,
				"根据距离排序图片");
		// }
		menu.findItem(MENU_ORDER_BY_HOT).setChecked(true);
		return true;

	}

	@Override
	public boolean onPrepareOptionsMenu(Menu menu) {
		MenuItem campPicItem = menu.findItem(MENU_CAMP_PIC);
		MenuItem selPicItem = menu.findItem(MENU_SEL_PIC);
		MenuItem hotItem = menu.findItem(MENU_ORDER_BY_HOT);
		MenuItem distanceItem = menu.findItem(MENU_ORDER_BY_DISTANCE);
		MenuItem additem = menu.findItem(MENU_ADDSHIP);
		MenuItem shareitem = menu.findItem(MENU_SHARE);
		

		MenuItem[] allMenuItems = { campPicItem, selPicItem, hotItem,
				distanceItem };
		
		MenuItem[] addMenuItems = { additem};
		
		switch (orderBy) {
		case MENU_ORDER_BY_HOT:
			distanceItem.setChecked(false);
			hotItem.setChecked(true);
			break;
		case MENU_ORDER_BY_DISTANCE:
			distanceItem.setChecked(true);
			hotItem.setChecked(false);
			break;
		}

		for (MenuItem item : allMenuItems) {
			item.setVisible(pictureFragment.isVisable);
		}
		for (MenuItem item : addMenuItems) {
			item.setVisible(placefragment.isVisible());
		}
		
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item) {
		switch (item.getItemId()) {
		case MENU_CAMP_PIC:
			takePhoto();
			break;
		case MENU_SEL_PIC:
			pickPhoto();
			break;
		case MENU_SHARE:
			share();
			break;
		case MENU_ORDER_BY_HOT:
			orderBy = MENU_ORDER_BY_HOT;
			refreshPictures();
			break;
		case MENU_ORDER_BY_DISTANCE:
			orderBy = MENU_ORDER_BY_DISTANCE;
			refreshPictures();
			break;
		case MENU_ADDSHIP:
			addScenic();
		default:

		}
		return super.onMenuItemSelected(featureId, item);
	}

	private void addScenic() {
		Intent it = new Intent().setClass(MainActivity.this,
				NewScenicActivity.class);
		startActivity(it);
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

	/*********************************/
	/***
	 * 使用照相机拍照获取图片
	 */
	public static final int SELECT_PIC_BY_TAKE_PHOTO = 1;
	/***
	 * 使用相册中的图片
	 */
	public static final int SELECT_PIC_BY_PICK_PHOTO = 2;

	/***
	 * 从Intent获取图片路径的KEY
	 */
	public static final String KEY_PHOTO_PATH = "photo_path";

	/*********************************/
	String imgPath;
	private TextView tv_node_tip;
	private TextView to;
	private TextView chufa;
	private TextView tu1;
	private TextView tu2;
	private TextView tu3;
	private Button add_button;
	private TextView line_tv_clear;
	private String deviceid;
	private String tel;
	private String temp;

	public Uri setImageUri() {
		// Store image in dcim
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/DCIM/Camera/", "image" + new Date().getTime() + ".jpg");
		Uri imgUri = Uri.fromFile(file);
		this.imgPath = file.getAbsolutePath();
		return imgUri;
	}

	public String getImagePath() {
		return imgPath;
	}

	/**
	 * 拍照获取图片
	 */
	private void takePhoto() {
		// 执行拍照前，应该先判断SD卡是否存在
		String SDState = Environment.getExternalStorageState();
		if (SDState.equals(Environment.MEDIA_MOUNTED)) {

			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// "android.media.action.IMAGE_CAPTURE"
			// File out = new File(Environment.getExternalStorageDirectory(),
			// new SimpleDateFormat(
			// "yyyyMMddHHmmss").format(new Date()) + ".jpg");
			// imgPath = out.getAbsolutePath();
			// Uri mUri = Uri.fromFile(out);

			/***
			 * 需要说明一下，以下操作使用照相机拍照，拍照后的图片会存放在相册中的 这里使用的这种方式有一个好处就是获取的图片是拍照后的原图
			 * 如果不实用ContentValues存放照片路径的话，拍照后获取的图片为缩略图不清晰
			 */
			ContentValues values = new ContentValues();
			Uri mUri = this.getContentResolver().insert(
					MediaStore.Images.Media.EXTERNAL_CONTENT_URI, values);
			imgPath = getRealPathFromURI(mUri);
			intent.putExtra(MediaStore.EXTRA_OUTPUT, mUri);
			//
			// intent.putExtra(android.provider.MediaStore.EXTRA_OUTPUT,
			// setImageUri());
			/** ----------------- */
			startActivityForResult(intent, SELECT_PIC_BY_TAKE_PHOTO);
		} else {
			Toast.makeText(this, "内存卡不存在", Toast.LENGTH_LONG).show();
		}
	}

	/***
	 * 从相册中取图片
	 */
	private void pickPhoto() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "选择要上传的照片："),
				SELECT_PIC_BY_PICK_PHOTO);
	}

	/**
	 * 选择图片后，获取图片的路径
	 * 
	 * @param requestCode
	 * @param data
	 */
	private void doPhoto(int requestCode, Intent data) {
		Uri photoUri = null;
		if (requestCode == SELECT_PIC_BY_PICK_PHOTO) {
			if (data == null) {
				Toast.makeText(this, "没有选择图片文件", Toast.LENGTH_LONG).show();
				return;
			}

			// if(data.hasExtra("data")){
			// Bitmap thumbnail = data.getParcelableExtra("data");
			// //得到bitmap后的操作
			// }

			photoUri = data.getData();

			if (photoUri == null) {
				Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
				return;
			}
		}

		String picPath = null;
		switch (requestCode) {

		case SELECT_PIC_BY_TAKE_PHOTO:
			picPath = getImagePath();
			break;
		case SELECT_PIC_BY_PICK_PHOTO:
			picPath = getRealPathFromURI(photoUri);
			// picPath = getAbsolutePath(photoUri);
			break;

		}

		System.out.println("picPath>>> " + picPath);
		if (picPath != null
				&& (picPath.endsWith(".png") || picPath.endsWith(".PNG")
						|| picPath.endsWith(".jpg") || picPath.endsWith(".JPG"))) {
			Intent mIntent = new Intent();
			mIntent.putExtra(KEY_PHOTO_PATH, picPath);
			mIntent.setClass(context, PictureUploadActivity.class);
			startActivity(mIntent);

		} else {
			Toast.makeText(this, "选择图片文件不正确", Toast.LENGTH_SHORT).show();
		}
	}
	// 把uri转换为文件路径
	// 从MediaStore的uri中获得文件名和路径
	private String getRealPathFromURI(Uri contentUri) {
		if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.JELLY_BEAN_MR2) {
			// return getPath(context, contentUri);
		}
		String[] proj = { MediaStore.Images.Media.DATA };
		Cursor cursor = managedQuery(contentUri, proj, null, null, null);
		int column_index = cursor.getColumnIndexOrThrow(proj[0]);
		cursor.moveToFirst();
		return cursor.getString(column_index);
	}

	/******************************** added by l ********************************/

	/*
	 * 初始化TabHost和导航按钮RadioButton
	 */
	private void init() {
		sp = context.getSharedPreferences("User_SP", MODE_PRIVATE);
		editor = sp.edit();

		line_tv_from = (TextView) findViewById(R.id.line_tv_from);
		line_tv_to = (TextView) findViewById(R.id.line_tv_to);
		line_tv_node1 = (TextView) findViewById(R.id.line_tv_node1);
		line_tv_node2 = (TextView) findViewById(R.id.line_tv_node2);
		line_tv_node3 = (TextView) findViewById(R.id.line_tv_node3);
		tv_node_tip = (TextView) findViewById(R.id.tv_node_tip);
		to = (TextView) findViewById(R.id.to);
		chufa = (TextView) findViewById(R.id.chufa);
		tu1 = (TextView) findViewById(R.id.tu1);
		tu2 = (TextView) findViewById(R.id.tu2);
		tu3 = (TextView) findViewById(R.id.tu3);
		add_button = (Button) findViewById(R.id.add_button);
		line_tv_clear = (TextView) findViewById(R.id.line_tv_clear); 

		 sp= getSharedPreferences("test", Activity.MODE_PRIVATE); 
				 String size = sp.getString("affairinfo_fontsize", "2");
				if(size.equals("1")){
					line_tv_from.setTextSize(10);
					line_tv_to.setTextSize(10);
					line_tv_node1.setTextSize(10);
					line_tv_node2.setTextSize(10);
					line_tv_node3.setTextSize(10);
					tv_node_tip.setTextSize(10);
					to.setTextSize(10);
					chufa.setTextSize(10);
					tu1.setTextSize(10);
					tu2.setTextSize(10);
					tu3.setTextSize(10);
					add_button.setTextSize(10);
					line_tv_clear.setTextSize(10);
				}else if(size.equals("2")){
//					tv1.setTextSize(20);
				}else{
					line_tv_from.setTextSize(30);
					line_tv_to.setTextSize(30);
					line_tv_node1.setTextSize(30);
					line_tv_node2.setTextSize(30);
					line_tv_node3.setTextSize(30);
					tv_node_tip.setTextSize(30);
					to.setTextSize(30);
					chufa.setTextSize(30);
					tu1.setTextSize(30);
					tu2.setTextSize(30);
					tu3.setTextSize(30);
					add_button.setTextSize(30);
					line_tv_clear.setTextSize(30);
				}
				
				
				

		travelFragment = (TravelFragment) getSupportFragmentManager()
				.findFragmentById(R.id.TravelFragment);
		weatherFragment = (WeatherFragment) getSupportFragmentManager()
				.findFragmentById(R.id.WeatherFragment);

		pictureFragment = (PictureFragment) getSupportFragmentManager()
				.findFragmentById(R.id.PictureFragment);
        placefragment = (PlaceFragment) getSupportFragmentManager().findFragmentById(R.id.PlaceFragment);
		
		tabHost = (TabHost) findViewById(android.R.id.tabhost);
		tabHost.setup();
	       
	       
		tabHost.addTab(tabHost.newTabSpec("weather").setIndicator("weather")
				.setContent(R.id.WeatherFragment));
		tabHost.addTab(tabHost.newTabSpec("travel").setIndicator("travel")
				.setContent(R.id.TravelFragment));
		tabHost.addTab(tabHost.newTabSpec("place").setIndicator("place")
				.setContent(R.id.PlaceFragment));
		tabHost.addTab(tabHost.newTabSpec("picture").setIndicator("picture")
				.setContent(R.id.PictureFragment));/////////////////////////////////////////////////////////////////
		tabHost.addTab(tabHost.newTabSpec("more").setIndicator("more")
				.setContent(R.id.MoreFragment));
		tabHost.setCurrentTab(0);
//		try
//	       {
//	           Field current = tabHost.getClass().getDeclaredField("mCurrentTab");
//	           current.setAccessible(true);
//	           current.setInt(tabHost, 0);
//	       }catch (Exception e){
//	           e.printStackTrace();
//	       }
	       
		if(Constant.raod==1){	

//			FragmentManager fragmentManager = getSupportFragmentManager();
//			PictureFragment fragment = new PictureFragment();
//			FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//			fragmentTransaction.add(android.R.id.tabcontent, fragment);			 
//			fragmentTransaction.commit();
			
			final RadioButton pic2 = (RadioButton) findViewById(R.id.main_tabs_picture);
			final RadioButton pic1 = (RadioButton) findViewById(R.id.picture_btn_road);
//			tabHost.setCurrentTabByTag("picture");
			tabHost.setCurrentTab(3);
			pic2.setChecked(true);
			pictureFragment.shakeListener.setRecoding(false);
			pictureFragment.shakeListener.start();
			pictureFragment.isVisable = true;
			if (!picLoaded) {
				pic1.setChecked(true);
				picLoaded = true;//把热门景区设置成默认
			}
			refreshPictures();
			
			//mCurrentTab恢复到－1状态
		     
		}
		
//		  try
//	       {
//	           Field current = tabHost.getClass().getDeclaredField("mCurrentTab");
//	           current.setAccessible(true);
//	           current.set(tabHost, -1);
//	       }catch (Exception e){
//	           e.printStackTrace();
//	       }
		
		final RadioButton pic = (RadioButton) findViewById(R.id.picture_btn_hot);
		final CheckBox place = (CheckBox) findViewById(R.id.place_switch);
		RadioGroup radioGroup = (RadioGroup) this.findViewById(R.id.main_tabs);
		
     
//		if(Constant.raod==1){
			radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				
				@Override
				public void onCheckedChanged(RadioGroup group, int checkedId) {
					switch (checkedId) {
					
					case R.id.main_tabs_weather:
						
						tabHost.setCurrentTabByTag("weather");
						// weatherFragment.setUserVisibleHint(true);
						pictureFragment.shakeListener.stop();
						System.out
								.println("执行了MainActivity的 pictureFragment.isVisable = false");
						pictureFragment.isVisable = false;
						// isPictureFragmentSelected=false;
						break;
					case R.id.main_tabs_travel:
						if (sp.getString("userName", "").equals("")) {
//							Toast.makeText(context, "登录体验更多", Toast.LENGTH_LONG)
//									.show();
							tabHost.setCurrentTabByTag("travel");
							travelFragment.setUserVisibleHint(true);
							pictureFragment.shakeListener.stop();
							pictureFragment.isVisable = false;
						} else {
							tabHost.setCurrentTabByTag("travel");
							travelFragment.setUserVisibleHint(true);
							pictureFragment.shakeListener.stop();
							pictureFragment.isVisable = false;
						}
						break;
					case R.id.main_tabs_place:
						tabHost.setCurrentTabByTag("place");
						pictureFragment.shakeListener.stop();
						pictureFragment.isVisable = false;
						if (!placeLoaded) {
							place.setChecked(false);
							placeLoaded = true;
						}
						break;
					case R.id.main_tabs_picture:
						if (sp.getString("userName", "").equals("")) {
//							Toast.makeText(context, "登录体验更多", Toast.LENGTH_LONG)
//									.show();
							tabHost.setCurrentTabByTag("picture");
							// isPictureFragmentSelected=true;
							pictureFragment.shakeListener.setRecoding(false);
							pictureFragment.shakeListener.start();
							pictureFragment.isVisable = true;

							if (!picLoaded) {
								pic.setChecked(true);
								picLoaded = true;//把热门景区设置成默认
							}
						} else {
							tabHost.setCurrentTabByTag("picture");
							pictureFragment.shakeListener.setRecoding(false);
							pictureFragment.shakeListener.start();
							pictureFragment.isVisable = true;

							if (!picLoaded) {
								pic.setChecked(true);
								picLoaded = true;
							}
						}

						break;
					case R.id.main_tabs_more:

						if (sp.getString("userName", "").equals("")) {
//							Toast.makeText(context, "登录体验更多", Toast.LENGTH_LONG)
//									.show();
							tabHost.setCurrentTabByTag("more");
							pictureFragment.shakeListener.stop();
							pictureFragment.isVisable = false;
						} else {
							tabHost.setCurrentTabByTag("more");
							pictureFragment.shakeListener.stop();
							pictureFragment.isVisable = false;
						}
						break;
					default:
//						tabHost.setCurrentTabByTag("weather");
//
//						pictureFragment.shakeListener.stop();
//						pictureFragment.isVisable = false;
						tabHost.setCurrentTabByTag("picture");
						pictureFragment.shakeListener.setRecoding(false);
						pictureFragment.shakeListener.start();
						pictureFragment.isVisable = true;

						if (!picLoaded) {
							pic.setChecked(true);
							picLoaded = true;
						}
					


					}
				}
			});
		}

//		else
//		{
//			radioGroup.setOnCheckedChangeListener(new OnCheckedChangeListener() {
//
//				@Override
//				public void onCheckedChanged(RadioGroup group, int checkedId) {
//					switch (checkedId) {
//					case R.id.main_tabs_weather:
//						tabHost.setCurrentTabByTag("weather");
//						// weatherFragment.setUserVisibleHint(true);
//						pictureFragment.shakeListener.stop();
//						System.out
//								.println("执行了MainActivity的 pictureFragment.isVisable = false");
//						pictureFragment.isVisable = false;
//						// isPictureFragmentSelected=false;
//						break;
//					case R.id.main_tabs_travel:
//						if (sp.getString("userName", "").equals("")) {
////							Toast.makeText(context, "登录体验更多", Toast.LENGTH_LONG)
////									.show();
//							tabHost.setCurrentTabByTag("travel");
//							travelFragment.setUserVisibleHint(true);
//							pictureFragment.shakeListener.stop();
//							pictureFragment.isVisable = false;
//						} else {
//							tabHost.setCurrentTabByTag("travel");
//							travelFragment.setUserVisibleHint(true);
//							pictureFragment.shakeListener.stop();
//							pictureFragment.isVisable = false;
//						}
//						break;
//					case R.id.main_tabs_place:
//						tabHost.setCurrentTabByTag("place");
//						pictureFragment.shakeListener.stop();
//						pictureFragment.isVisable = false;
//						if (!placeLoaded) {
//							place.setChecked(false);
//							placeLoaded = true;
//						}
//						break;
//					case R.id.main_tabs_picture:
//						if (sp.getString("userName", "").equals("")) {
////							Toast.makeText(context, "登录体验更多", Toast.LENGTH_LONG)
////									.show();
//							tabHost.setCurrentTabByTag("picture");
//							// isPictureFragmentSelected=true;
//							pictureFragment.shakeListener.setRecoding(false);
//							pictureFragment.shakeListener.start();
//							pictureFragment.isVisable = true;
//
//							if (!picLoaded) {
//								pic.setChecked(true);
//								picLoaded = true;//把热门景区设置成默认
//							}
//						} else {
//							tabHost.setCurrentTabByTag("picture");
//							pictureFragment.shakeListener.setRecoding(false);
//							pictureFragment.shakeListener.start();
//							pictureFragment.isVisable = true;
//
//							if (!picLoaded) {
//								pic.setChecked(true);
//								picLoaded = true;
//							}
//						}
//
//						break;
//					case R.id.main_tabs_more:
//
//						if (sp.getString("userName", "").equals("")) {
////							Toast.makeText(context, "登录体验更多", Toast.LENGTH_LONG)
////									.show();
//							tabHost.setCurrentTabByTag("more");
//							pictureFragment.shakeListener.stop();
//							pictureFragment.isVisable = false;
//						} else {
//							tabHost.setCurrentTabByTag("more");
//							pictureFragment.shakeListener.stop();
//							pictureFragment.isVisable = false;
//						}
//						break;
//					default:
//						tabHost.setCurrentTabByTag("weather");
//
//						pictureFragment.shakeListener.stop();
//						pictureFragment.isVisable = false;
//
//					}
//				}
//			});
//		}
//	}
		
		

	// public static boolean isPictureFragmentSelected =false;

	public void place_detail(View v) {
		startActivity(new Intent(MainActivity.this, PlaceDetailActivity.class));
	}

	// 注销
	public void logout(View v) {
		// getSharedPreferences("USER", 0).edit().clear().commit();
		editor.putString("userName", "");
		editor.commit();
		startActivity(new Intent(MainActivity.this, LoginActivity.class));
		MainActivity.this.finish();

	}

	/*
	 * 个人中心
	 */
	public void more_infocenter(View v) {
		sp1 = context.getSharedPreferences("User_SP", MODE_PRIVATE);
		String a = sp1.getString("userName", "");
		System.out.println("点击时有没有名字："+a);
		if (sp1.getString("userName", "").equals("")) {
			Toast.makeText(context, "该内容需要登陆后查看", Toast.LENGTH_LONG).show();
			startActivity(new Intent(MainActivity.this, LoginActivity.class));
		}else{
			Intent intent = new Intent(MainActivity.this, MoreActivity.class);
			intent.putExtra("LAYOUT", R.layout.more_infocenter);
			startActivity(intent);
		}
	}

	/*
	 * 用户设置
	 */
	public void more_usersetting(View v) {
		Intent intent = new Intent(MainActivity.this, MoreActivity.class);
		intent.putExtra("LAYOUT", R.layout.more_usersetting);
		startActivity(intent);
	}

	/*
	 * 分享账号管理
	 */
	public void more_sharemanage(View v) {
		// Intent intent = new Intent(MainActivity.this, MoreActivity.class);
		// intent.putExtra("LAYOUT", R.layout.more_sharemanage);
		// startActivity(intent);
		share();
	}

	/*
	 * 特色功能
	 */
	public void more_feature(View v) {
		Intent intent = new Intent(MainActivity.this, MoreActivity.class);
		intent.putExtra("LAYOUT", R.layout.more_feature);
		startActivity(intent);
	}

	/*
	 * 精品软件推荐
	 */
	public void more_recommend(View v) {
		Intent intent = new Intent(MainActivity.this, MoreActivity.class);
		intent.putExtra("LAYOUT", R.layout.more_recommend);
		startActivity(intent);
	}

	/*
	 * 免责声明
	 */
	public void more_disclaimer(View v) {
		Intent intent = new Intent(MainActivity.this, MoreActivity.class);
		intent.putExtra("LAYOUT", R.layout.more_disclaimer);
		startActivity(intent);
	}

	/*
	 * 关于我们
	 */
	public void more_about(View v) {
		Intent intent = new Intent(MainActivity.this, MoreActivity.class);
		intent.putExtra("LAYOUT", R.layout.more_aboutus);
		startActivity(intent);
	}

	private void share() {
		OnekeyShare oks = new OnekeyShare();
		// 分享时Notification的图标和文字
		oks.setNotification(R.drawable.ic_launcher,
				getString(R.string.app_name));
		// address是接收人地址，仅在信息和邮件使用
		oks.setAddress("12345678901");
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle(getString(R.string.share));
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl("http://sharesdk.cn");
		// text是分享文本，所有平台都需要这个字段
		oks.setText(getString(R.string.share_content));
		// imagePath是图片的本地路径，Linked-In 以外的平台都支持此参数
		// 设置自定义的外部回调
		oks.setCallback(new OneKeyShareCallback());
		oks.show(this, null);
	}

	// 天气-添加城市
	public void weather_visit(View v) {
		sp1 = context.getSharedPreferences("User_SP", MODE_PRIVATE);
		String a = sp1.getString("userName", "");
		System.out.println("点击时有没有名字："+a);
		if (sp1.getString("userName", "").equals("")) {
			Toast.makeText(context, "该内容需要登陆后查看", Toast.LENGTH_LONG).show();
			startActivity(new Intent(MainActivity.this, LoginActivity.class));
		}else{
			Intent intent = new Intent();
			intent.setClass(context, MoreInfocenterActivity.class);
			intent.putExtra("LAYOUT", R.layout.infocenter_city_management);
			intent.putExtra("flag", 123);
			startActivity(intent);
		}
	}

	// 天气-添加城市
	public void weather_add(View v) {
			Intent intent = new Intent();
			intent.setClass(context, MoreInfocenterActivity.class);
			intent.putExtra("LAYOUT", R.layout.infocenter_city_management);
			intent.putExtra("flag", 123);
			startActivity(intent);
	}

	// 天气-我来猜天气
	public void guess_weather(View v) {
		sp1 = context.getSharedPreferences("User_SP", MODE_PRIVATE);
		String a = sp1.getString("userName", "");
		System.out.println("点击时有没有名字："+a);
		if (sp1.getString("userName", "").equals("")) {
			Toast.makeText(context, "该内容需要登陆后查看", Toast.LENGTH_LONG).show();
			startActivity(new Intent(MainActivity.this, LoginActivity.class));
		} else {
			Intent it = new Intent(MainActivity.this, GuessWeatherActivity.class);
			startActivity(it);
		}
	}

	// 路线-添加途经点
	public void add_node(View v) {
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, AddView.class);
		startActivity(intent);
	}

	// 返回--添加返回
	public void onback(View view) {
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, AddView.class);
		startActivity(intent);
	}

	// 路线-添加路线确定按钮
	public void add_button(View v) {
		Toast.makeText(this, "功能正在开发中", Toast.LENGTH_SHORT).show();
	}

	// 地点-推荐景点跳转页面
	public void addr_1(View v) {
		showPlace("167");
	}

	public void addr_2(View v) {
		showPlace("159");
	}

	public void addr_3(View v) {
		showPlace("157");
	}

	public void addr_4(View v) {
		showPlace("156");
	}

	public void addr_5(View v) {
		showPlace("154");
	}

	public void addr_6(View v) {
		showPlace("170");
	}

	public void addr_7(View v) {
		showPlace("169");
	}

	public void addr_8(View v) {
		showPlace("166");///////////////////////////////////	id
	}

	public void showPlace(String addr) {
//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
		
		Intent intent = new Intent();
		intent.setClass(MainActivity.this, PrePlaceDetailActivity.class);
		intent.putExtra("addr", addr);
		startActivity(intent);
	}

	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == 100) {
			if (data.getStringExtra("area") != null) {
				line_tv_from.setText(data.getStringExtra("area"));
			}
		} else if (resultCode == 101) {
			if (data.getStringExtra("area") != null) {
				line_tv_to.setText(data.getStringExtra("area"));
			}
		} else if (resultCode == 102) {
			if (data.getStringExtra("area") != null) {
				line_tv_node1.setText(data.getStringExtra("area"));
			}
		} else if (resultCode == 103) {
			if (data.getStringExtra("area") != null) {
				line_tv_node2.setText(data.getStringExtra("area"));
			}
		} else if (resultCode == 104) {
			if (data.getStringExtra("area") != null) {
				line_tv_node3.setText(data.getStringExtra("area"));
			}
		} else if (resultCode == Activity.RESULT_OK) {
			// TODO

			doPhoto(requestCode, data);

		}

		super.onActivityResult(requestCode, resultCode, data);
	}

	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		if (keyCode == KeyEvent.KEYCODE_BACK) {
			exit();
			return false;
		} else {
			return super.onKeyDown(keyCode, event);
		}

	}

	public void exit() {
		// TODO Auto-generated method stub
		if (!isExit) {
			isExit = true;
			Toast.makeText(getApplicationContext(), "再按一次退出程序",
					Toast.LENGTH_SHORT).show();
			mHandler.sendEmptyMessageDelayed(0, 2000);
		} else {
			Intent intent = new Intent(Intent.ACTION_MAIN);
			intent.addCategory(Intent.CATEGORY_HOME);
			startActivity(intent);
			System.exit(0);
		}
	}

	
	
	
	public void getLogin(){
		new AsyncTask<Void, Void, Void>() {

			private int status_login;
			@Override
			protected Void doInBackground(Void... param) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				mHandler.sendMessage(mHandler.obtainMessage(SHOW_PROCESS));
				try {
					params.add(new BasicNameValuePair("userName", URLEncoder
							.encode(temp, "UTF-8")));
					params.add(new BasicNameValuePair("password", URLEncoder
							.encode(temp, "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					System.out.print(e.getMessage());
				}

				JSONObject jsonObject = MyHttpClient.getJson(context,Constant.login, params);

				try {
					status_login = jsonObject.getInt("status");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				if (status_login == 1) {
					try {
						JSONObject jsonObj = jsonObject
								.getJSONObject("message");
						JSONArray jsonArray = jsonObj.getJSONArray("userinfo");
						JSONObject jo = jsonArray.getJSONObject(0);
						jsonStr = new String[] { jo.getString("userId"),
								jo.getString("userName"),
								jo.getString("userPassword"),
								jo.getString("userEmail"),
								jo.getString("registerTime"),
								jo.getString("userCoin") };
						sp = context.getSharedPreferences("User_SP",
								MODE_PRIVATE);
						editor.putString("userId", jsonStr[0]);
						editor.putString("userName", jsonStr[1]);
						editor.putString("userPassword", tel);
						editor.putString("userEmail", jsonStr[3]);
						editor.putString("registerTime", jsonStr[4]);
						editor.putString("userCoin", jsonStr[5]);
						editor.putString("Sum", jsonObject.getString("sum"));
						editor.putString("YourNum",jsonObject.getString("yourNum"));
						editor.commit();
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
				return null;
			}
			@Override
			protected void onPostExecute(Void result) {
				mHandler.sendMessage(mHandler.obtainMessage(DISMISS_PROCESS));

				switch (status_login) {
				case 1:
					break;
				case -1:
//					register();
					break;
				case -2:
					break;
				case -3:
					Toast a3 = Toast.makeText(MainActivity.this, "系统错误",
							Toast.LENGTH_SHORT);
					a3.show();
					break;
				case 0:
					Toast a0 = Toast.makeText(MainActivity.this, "网络连接错误",
							Toast.LENGTH_SHORT);
					a0.show();
					break;
				}
			}
		}.execute();
	
	}

	
	
	
	private void getTel() {
		// TODO Auto-generated method stub
//		SIMCardInfo siminfo = new SIMCardInfo(MainActivity.this); 
//		tel=siminfo.getNativePhoneNumber();
		
		//创建电话管理
		 
		Build bd = new Build();  
		String model = bd.MODEL;  
		TelephonyManager tm = (TelephonyManager) this.getSystemService(Context.TELEPHONY_SERVICE);
         deviceid = tm.getDeviceId();
        tel = tm.getLine1Number();//手机号码
//        String imei = tm.getSimSerialNumber();
//        String imsi = tm.getSubscriberId(); 
//        Toast a2 = Toast.makeText(MainActivity.this,"手机号："+ tel,
//				Toast.LENGTH_SHORT);
//		a2.show();
        if (tel!=null && !tel.equals(""))
        {
        	temp = tel;
        }
        else
        {
        	temp = "123";
        }
        //注册
        new AsyncTask<Void, Void, Void>() {

			private int status_register;
			private String message;
			@Override
			protected Void doInBackground(Void... param) {
				List<NameValuePair> params = new ArrayList<NameValuePair>();
				mHandler.sendMessage(mHandler.obtainMessage(SHOW_PROCESS));

				try {
					params.add(new BasicNameValuePair("userName", URLEncoder
							.encode(temp, "UTF-8")));
					params.add(new BasicNameValuePair("password", URLEncoder
							.encode(temp, "UTF-8")));
					params.add(new BasicNameValuePair("Email", URLEncoder
							.encode("123@qq.com", "UTF-8")));
				} catch (UnsupportedEncodingException e) {
					System.out.print(e.getMessage());
				}

				JSONObject jsonObject = MyHttpClient.getJson(context,
						Constant.regist, params);

				try {
					status_register = jsonObject.getInt("status");
					message = jsonObject.getString("message");
				} catch (JSONException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}

				return null;
			}
			
			@Override
			protected void onPostExecute(Void result) {
				mHandler.sendMessage(mHandler.obtainMessage(DISMISS_PROCESS));

				switch (status_register) {
				case 1:
					//注册成功
					getLogin();
					break;
				case -1:
					//已被注册
					getLogin();
					break;
				case 0:
					Toast a2 = Toast.makeText(MainActivity.this, "网络错误",
							Toast.LENGTH_SHORT);
					a2.show();
				}
			}
		}.execute();
	
	}
	
	
	
}

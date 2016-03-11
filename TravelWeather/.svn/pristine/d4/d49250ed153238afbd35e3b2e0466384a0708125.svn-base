package cuit.travelweather.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;
import cuit.travelweather.R;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.MyFont;
import cuit.travelweather.zxing.activity.CaptureActivity;

/*
 * 更多具体页面Activity
 */
public class MoreActivity extends BaseAct {

	private int layout;
	private SharedPreferences sp;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		if(Constant.Falgqing!=2){
			layout = getIntent().getIntExtra("LAYOUT", R.layout.start);
		}
		if(Constant.Falgqing==2){
			layout = getIntent().getIntExtra("LAYOUT", R.layout.start_qing);
		}
		setContentView(layout);
		MyFont.setTypeface(this, new int[] { R.id.more_title });
		
		
		if (layout == R.layout.more_infocenter){
			if(getSharedPreferences("Theme_SP", MODE_PRIVATE).getString("theme_type", "jianzhi").equals("qinghuaci")){
				RelativeLayout more_infocenter = (RelativeLayout) findViewById(R.id.more_infocenter);
				more_infocenter.setBackgroundResource(R.drawable.login_bg_qhc);
				
				TextView more_title = (TextView) findViewById(R.id.more_title);
				more_title.setTextColor(this.getResources().getColor(R.color.theme_bule));
				more_title.setBackgroundResource(R.drawable.travel_top_bg_qhc);
				
				TextView more_text_first = (TextView) findViewById(R.id.more_text_first);
				more_text_first.setBackgroundResource(R.drawable.ic_preference_first_qhc);
				
				TextView more_text_mid = (TextView) findViewById(R.id.more_text_mid);
				more_text_mid.setBackgroundResource(R.drawable.ic_preference_mid_qhc);
				
				TextView more_text_last = (TextView) findViewById(R.id.more_text_last);
				more_text_last.setBackgroundResource(R.drawable.ic_preference_last_qhc);
				
				TextView more_pinglun = (TextView) findViewById(R.id.more_pinglun);
				more_pinglun.setBackgroundResource(R.drawable.ic_preference_first_qhc);
				
				TextView more_scancode = (TextView) findViewById(R.id.more_scancode);
				more_scancode.setBackgroundResource(R.drawable.ic_preference_last_qhc);
				
				TextView infocenter_coin = (TextView) findViewById(R.id.infocenter_coin);
				infocenter_coin.setBackgroundResource(R.drawable.ic_preference_first_qhc);
				
				TextView infocenter_coin_rank = (TextView) findViewById(R.id.infocenter_coin_rank);
				infocenter_coin_rank.setBackgroundResource(R.drawable.ic_preference_mid_qhc);
				
				TextView more_guize = (TextView) findViewById(R.id.more_guize);
				more_guize.setBackgroundResource(R.drawable.ic_preference_last_qhc);
			}
			TextView infocenter_coin;
			TextView infocenter_coin_rank;
			SharedPreferences sp;
			sp = MoreActivity.this
					.getSharedPreferences("User_SP", MODE_PRIVATE);
			infocenter_coin = (TextView) findViewById(R.id.infocenter_coin);
			infocenter_coin_rank = (TextView) findViewById(R.id.infocenter_coin_rank);
			infocenter_coin.setText("目前积分：" + sp.getString("userCoin", "0"));
			infocenter_coin_rank.setText("目前排名：" + sp.getString("YourNum", "1"));
			
			TextView tv1 = (TextView)findViewById(R.id.more_title);
			TextView tv2 = (TextView)findViewById(R.id.more_text_first);
			TextView tv3 = (TextView)findViewById(R.id.more_text_mid);
			TextView tv4 = (TextView)findViewById(R.id.more_text_last);
			TextView tv5 = (TextView)findViewById(R.id.more_pinglun);
			TextView tv6 = (TextView)findViewById(R.id.more_scancode);
			TextView tv7 = (TextView)findViewById(R.id.infocenter_decoding_result);
			TextView tv8 = (TextView)findViewById(R.id.infocenter_coin);
			TextView tv9 = (TextView)findViewById(R.id.infocenter_coin_rank);
			TextView tv10 = (TextView)findViewById(R.id.more_guize);
			sp= getSharedPreferences("test", Activity.MODE_PRIVATE); 
			 String size = sp.getString("affairinfo_fontsize", "2");
			if(size.equals("1")){
				tv1.setTextSize(10);
				tv2.setTextSize(10);
				tv3.setTextSize(10);
				tv4.setTextSize(10);
				tv5.setTextSize(10);
				tv6.setTextSize(10);
				tv7.setTextSize(10);
				tv8.setTextSize(10);
				tv9.setTextSize(10);
				tv10.setTextSize(10);
			}else if(size.equals("2")){
			}else{
				tv1.setTextSize(30);
				tv2.setTextSize(30);
				tv3.setTextSize(30);
				tv4.setTextSize(30);
				tv5.setTextSize(30);
				tv6.setTextSize(30);
				tv7.setTextSize(30);
				tv8.setTextSize(30);
				tv9.setTextSize(30);
				tv10.setTextSize(30);
			}
			

		}
		
		if (layout == R.layout.more_usersetting) {
			if(getSharedPreferences("Theme_SP", MODE_PRIVATE).getString("theme_type", "jianzhi").equals("qinghuaci")){
				RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.usersetting);
				relativeLayout.setBackgroundResource(R.drawable.login_bg_qhc);
				
				TextView textView = (TextView) findViewById(R.id.more_title);
				textView.setBackgroundResource(R.drawable.travel_top_bg_qhc);
				textView.setTextColor(this.getResources().getColor(R.color.theme_bule));
				
				Button clearButton = (Button) findViewById(R.id.clearbutton);
				clearButton.setBackgroundResource(R.drawable.logout_btn_qhc);
				
				TextView a1 = (TextView) findViewById(R.id.a1);
				TextView a2 = (TextView) findViewById(R.id.a2);
				TextView a3 = (TextView) findViewById(R.id.a3);
				TextView clearbutton = (TextView) findViewById(R.id.clearbutton);
				sp= getSharedPreferences("test", Activity.MODE_PRIVATE); 
				 String size = sp.getString("affairinfo_fontsize", "2");
				if(size.equals("1")){
					a1.setTextSize(10);
					a2.setTextSize(10);
					a3.setTextSize(10);
					clearbutton.setTextSize(10);
				}else if(size.equals("2")){
				}else{
					a1.setTextSize(30);
					a2.setTextSize(30);
					a3.setTextSize(30);
					clearbutton.setTextSize(30);
				}
				
			}
		}
		
		if (layout == R.layout.more_disclaimer) {
			TextView shen1 = (TextView)findViewById(R.id.shen1);
			TextView shen2 = (TextView)findViewById(R.id.shen2);
			TextView shen3 = (TextView)findViewById(R.id.shen3);
			TextView shen4 = (TextView)findViewById(R.id.shen4);
			TextView shen5 = (TextView)findViewById(R.id.shen5);
			TextView shen6 = (TextView)findViewById(R.id.shen6);
			sp = getSharedPreferences("test", Activity.MODE_PRIVATE); 
			 String size1 = sp.getString("affairinfo_fontsize", "2");
			if(size1.equals("1")){
				shen1.setTextSize(10);
				shen2.setTextSize(10);
				shen3.setTextSize(10);
				shen4.setTextSize(10);
				shen5.setTextSize(10);
				shen6.setTextSize(10);
			}else if(size1.equals("2")){
			}else{
				shen1.setTextSize(30);
				shen2.setTextSize(30);
				shen3.setTextSize(30);
				shen4.setTextSize(30);
				shen5.setTextSize(30);
				shen6.setTextSize(30);
			}
			
		}
		if (layout == R.layout.more_aboutus) {
			
			TextView tv11 = (TextView)findViewById(R.id.guan1);
			TextView tv12 = (TextView)findViewById(R.id.guan2);
			TextView tv13 = (TextView)findViewById(R.id.guan3);
			TextView tv14 = (TextView)findViewById(R.id.guan4);
			TextView tv15 = (TextView)findViewById(R.id.guan5);
			TextView tv16 = (TextView)findViewById(R.id.guan6);
			sp = getSharedPreferences("test", Activity.MODE_PRIVATE); 
			 String size1 = sp.getString("affairinfo_fontsize", "2");
			if(size1.equals("1")){
				tv11.setTextSize(10);
				tv12.setTextSize(10);
				tv13.setTextSize(10);
				tv14.setTextSize(10);
				tv15.setTextSize(10);
				tv16.setTextSize(10);
			}else if(size1.equals("2")){
			}else{
				tv11.setTextSize(30);
				tv12.setTextSize(30);
				tv13.setTextSize(30);
				tv14.setTextSize(30);
				tv15.setTextSize(30);
				tv16.setTextSize(30);
			}
			
		}
		if (layout == R.layout.more_feature) {
			if(getSharedPreferences("Theme_SP", MODE_PRIVATE).getString("theme_type", "jianzhi").equals("qinghuaci")){
				RelativeLayout more_feature = (RelativeLayout) findViewById(R.id.more_feature);
				more_feature.setBackgroundResource(R.drawable.login_bg_qhc);
				
				TextView more_title = (TextView) findViewById(R.id.more_title);
				more_title.setTextColor(this.getResources().getColor(R.color.theme_bule));
				more_title.setBackgroundResource(R.drawable.travel_top_bg_qhc);
				
				TextView feature_weather_history = (TextView) findViewById(R.id.feature_weather_history);
				feature_weather_history.setTextColor(this.getResources().getColor(R.color.theme_bule));
				feature_weather_history.setBackgroundResource(R.drawable.ic_preference_first_qhc);
				
				RelativeLayout date_relative = (RelativeLayout) findViewById(R.id.date_relative);
				date_relative.setBackgroundResource(R.drawable.ic_preference_mid_qhc);
				
				TextView feature_label_date = (TextView) findViewById(R.id.feature_label_date);
				feature_label_date.setTextColor(this.getResources().getColor(R.color.theme_bule));
				
				EditText feature_editText_date = (EditText) findViewById(R.id.feature_editText_date);
				feature_editText_date.setBackgroundResource(R.drawable.login_edit_qhc);
				
				ImageButton datepicker = (ImageButton) findViewById(R.id.datepicker);
				datepicker.setBackgroundResource(R.drawable.datepicker_qhc);
				
				LinearLayout city_linear = (LinearLayout) findViewById(R.id.city_linear);
				city_linear.setBackgroundResource(R.drawable.ic_preference_last_qhc);
				
				TextView feature_label_city = (TextView) findViewById(R.id.feature_label_city);
				feature_label_city.setTextColor(this.getResources().getColor(R.color.theme_bule));
				
				EditText feature_editText_city = (EditText) findViewById(R.id.feature_editText_city);
				feature_editText_city.setBackgroundResource(R.drawable.login_edit_qhc);
				
				Button feature_confirm = (Button) findViewById(R.id.feature_confirm);
				feature_confirm.setBackgroundResource(R.drawable.btn_theme1_qhc);
				feature_confirm.setTextColor(this.getResources().getColor(R.color.theme_bule));
				
				TextView feature_expect = (TextView) findViewById(R.id.feature_expect);
				feature_expect.setBackgroundResource(R.drawable.feature_expect_bg_qhc);
				
				
				 sp= getSharedPreferences("test", Activity.MODE_PRIVATE); 
				 String size = sp.getString("affairinfo_fontsize", "2");
				if(size.equals("1")){
					more_title.setTextSize(10);
					feature_weather_history.setTextSize(10);
					feature_label_date.setTextSize(10);
					feature_label_city.setTextSize(10);
					feature_confirm.setTextSize(10);
					feature_expect.setTextSize(10);
				}else if(size.equals("2")){
//					tv1.setTextSize(20);
				}else{
					more_title.setTextSize(30);
					feature_weather_history.setTextSize(30);
					feature_label_date.setTextSize(30);
					feature_label_city.setTextSize(30);
					feature_confirm.setTextSize(30);
					feature_expect.setTextSize(30);
				}
				
			}
			MyFont.setTypeface(this, new int[] { R.id.feature_weather_history,
					R.id.feature_label_date, R.id.feature_label_city,
					R.id.feature_confirm });
		}
	}
	
	

	/*
	 * 个人中心
	 */
	// 关注城市管理
	public void cityManagement(View v) {
		Intent intent = new Intent();
		intent.setClass(MoreActivity.this, MoreInfocenterActivity.class);
		intent.putExtra("LAYOUT", R.layout.infocenter_city_management);
		MoreActivity.this.startActivity(intent);
		MoreActivity.this.finish();
	}

	// 关注路线管理
	public void lineManagement(View v) {
		Intent intent = new Intent();
		intent.setClass(MoreActivity.this, MoreInfocenterActivity.class);
		intent.putExtra("LAYOUT", R.layout.infocenter_route);
		MoreActivity.this.startActivity(intent);
		MoreActivity.this.finish();
	}

	// 订阅信息管理
	public void informationManagement(View v) {
		Intent intent = new Intent();
		intent.setClass(MoreActivity.this, MoreInfocenterActivity.class);
		intent.putExtra("LAYOUT", R.layout.infocenter_push);
		MoreActivity.this.startActivity(intent);
		MoreActivity.this.finish();
	}

	// 竞猜天气管理
	public void weatherGuessManagement(View v) {
		Intent it = new Intent(MoreActivity.this, GuessWeatherActivity.class);
		startActivity(it);

	}
	
	// 天气评论查看
	public void weatherCommet(View v) {
		Intent it = new Intent(MoreActivity.this, CommentDetailActivity.class);
		startActivity(it);
	}

	// 查看积分
	public void coin_rank_rule(View v) {
		Intent intent = new Intent();
		intent.setClass(MoreActivity.this, MoreInfocenterActivity.class);
		intent.putExtra("LAYOUT", R.layout.infocenter_coin);//积分规则
		MoreActivity.this.startActivity(intent);
		MoreActivity.this.finish();
	}
	public void coin_rank(View v) {
		Intent intent = new Intent();
		intent.setClass(MoreActivity.this, MoreInfocenterActivity.class);
		intent.putExtra("LAYOUT", R.layout.infocenter_coin_rank);//积分榜
		MoreActivity.this.startActivity(intent);
		MoreActivity.this.finish();
	}

	// 用户设置
	// 更换皮肤
	public void change_theme(View v) {
		SharedPreferences themeSP = getSharedPreferences("Theme_SP", MODE_PRIVATE);
		String theme_type = themeSP.getString("theme_type", "jianzhi");
		SharedPreferences.Editor editor = themeSP.edit();
		
		RelativeLayout relativeLayout = (RelativeLayout) findViewById(R.id.usersetting);
		Button clearButton = (Button) findViewById(R.id.clearbutton);
		TextView textView = (TextView) findViewById(R.id.more_title);
		
		if(theme_type.equals("jianzhi")){
			editor.putString("theme_type", "qinghuaci");
			relativeLayout.setBackgroundResource(R.drawable.login_bg_qhc);
			textView.setBackgroundResource(R.drawable.travel_top_bg_qhc);
			textView.setTextColor(this.getResources().getColor(R.color.theme_bule));
			clearButton.setBackgroundResource(R.drawable.logout_btn_qhc);
		}else if(theme_type.equals("qinghuaci")){
			editor.putString("theme_type", "jianzhi");
			relativeLayout.setBackgroundResource(R.drawable.login_bg);
			textView.setBackgroundResource(R.drawable.travel_top_bg);
			textView.setTextColor(this.getResources().getColor(R.color.theme1_red));
			clearButton.setBackgroundResource(R.drawable.logout_btn);
		}
		editor.commit();
	}

	// 更换字体大小
	public void change_size(View v) {
//		Toast.makeText(this, "功能正在开发中", Toast.LENGTH_SHORT).show();
		Intent intent = new Intent(MoreActivity.this, ChangeSize.class);
		startActivity(intent);
	}

	// 清除缓存
	public void clear(View v) {
		Toast.makeText(this, "功能正在开发中", Toast.LENGTH_SHORT).show();
	}

	// 特色功能 确定
	public void feature_do(View v) {
		Toast.makeText(this, "功能正在开发中", Toast.LENGTH_SHORT).show();
	}

	// 二维码扫描
	public void scanCode(View v) {
		Intent openCameraIntent = new Intent(MoreActivity.this,
				CaptureActivity.class);
		startActivityForResult(openCameraIntent, 0);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		// 处理扫描结果（在界面上显示）
		if (resultCode == RESULT_OK) {
			Bundle bundle = data.getExtras();
			String scanResult = bundle.getString("result");
			TextView result = (TextView) findViewById(R.id.infocenter_decoding_result);
			result.setText(scanResult);
		}
	}
}

package cuit.travelweather.activity;


import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import cuit.travelweather.R;
import cuit.travelweather.util.Constant;



public class AddView extends BaseAct implements OnClickListener{
     private Button button;
     private EditText et_name;
     private EditText  et_address;
     private EditText  et_phone;
	private SharedPreferences sp;
    
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.add_window);
		if(getSharedPreferences("Theme_SP", MODE_PRIVATE).getString("theme_type", "jianzhi").equals("qinghuaci")){
			
		}
		
		button = (Button)findViewById(R.id.botton_ensure);
		et_address = (EditText)findViewById(R.id.et_address);
		et_phone = (EditText)findViewById(R.id.et_phone);
		et_name = (EditText)findViewById(R.id.et_name);
		
        TextView tv1 = (TextView)findViewById(R.id.tv_ID);
        TextView tv2 = (TextView)findViewById(R.id.tv_address);
        TextView tv3 = (TextView)findViewById(R.id.tv_phone_id);
        TextView tv4 = (TextView)findViewById(R.id.tv_picture_ID);
		
		 sp= getSharedPreferences("test", Activity.MODE_PRIVATE); 
		 String size = sp.getString("affairinfo_fontsize", "2");
		if(size.equals("1")){
		   tv1.setTextSize(10);
		   tv2.setTextSize(10);
		   tv3.setTextSize(10);
		   tv4.setTextSize(10);
		}else if(size.equals("2")){
//			tv1.setTextSize(20);
		}else{
			tv1.setTextSize(30);
			   tv2.setTextSize(30);
			   tv3.setTextSize(30);
			   tv4.setTextSize(30);
		}
		
	}
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.botton_ensure)
		{
			Intent intent = new Intent();
			String uname = et_name.getText().toString();
			String uaddress = et_address.getText().toString();
			String uphone = et_phone.getText().toString();
			intent.putExtra("uname", uname);
			intent.putExtra("uaddress", uaddress);
			intent.putExtra("uphone", uphone);
			intent.setClass(AddView.this, MainActivity.class);
			startActivity(intent);
			
		}
		
	}
	public  void onBack(View view)
	{
		Intent intent = new Intent();
		intent.setClass(AddView.this, MainActivity.class);
		startActivity(intent);
		
	}
}
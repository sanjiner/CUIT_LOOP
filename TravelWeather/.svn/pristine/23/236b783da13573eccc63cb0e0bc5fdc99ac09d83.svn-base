package cuit.travelweather.activity;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;
import cuit.travelweather.R;
import cuit.travelweather.util.Constant;

public class ChangeSize extends BaseAct{

	private SharedPreferences sp;
	private SharedPreferences.Editor  editor;
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.more_size);
		sp=PreferenceManager.getDefaultSharedPreferences(this);
		Button size_small = (Button)findViewById(R.id.size_small);
		Button size_middle = (Button)findViewById(R.id.size_middle);
		Button size_large = (Button)findViewById(R.id.size_large);
		size_small.setOnClickListener(smallonclick);
		size_middle.setOnClickListener(middleonclick);
		size_large.setOnClickListener(largeonclick);
		sp= getSharedPreferences("test", Activity.MODE_PRIVATE); 
		editor = sp.edit();

	}
	private Button.OnClickListener smallonclick = new Button.OnClickListener()
    {
        @SuppressLint("NewApi")
        @Override
        public void onClick(View arg0) 
        {
//    		sp.getString("affairinfo_fontsize", "1");
//    		sp.edit().putString("affairinfo_fontsize", "1");
        	editor.putString("affairinfo_fontsize", "1"); 
        	editor.commit(); 
        	Constant.keysize=1;
        	ShowHint("已切换到小号字体，请重启app使用");
        }
    };
    private Button.OnClickListener middleonclick = new Button.OnClickListener()
    {
        @SuppressLint("NewApi")
        @Override
        public void onClick(View arg0) 
        {
//    		sp.putString("affairinfo_fontsize", "2");
        	editor.putString("affairinfo_fontsize", "2");
        	editor.commit(); 
        	Constant.keysize=2;
        	ShowHint("已切换到大中字体，请重启app使用");
        }
    };
    private Button.OnClickListener largeonclick = new Button.OnClickListener()
    {
        @SuppressLint("NewApi")
        @Override
        public void onClick(View arg0) 
        {
//    		sp.getString("affairinfo_fontsize", "3");
        	editor.putString("affairinfo_fontsize", "3");
        	Constant.keysize=3;
        	editor.commit(); 
        	ShowHint("已切换到大号字体，请重启app使用");
        	
        }
    };
    
    public void ShowHint(String message) {
    	Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
    }
}

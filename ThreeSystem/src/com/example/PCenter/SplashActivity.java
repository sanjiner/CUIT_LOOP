package com.example.PCenter;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.util.Log;
 

//判断是否第一次运行该程序
public class SplashActivity extends Activity {
 
    @Override
    protected void onCreate(Bundle savedInstanceState) {
    	int mFirst;
        super.onCreate(savedInstanceState);
        SharedPreferences sharedPreferences = this.getSharedPreferences("share", MODE_PRIVATE); 
        boolean isFirstRun = sharedPreferences.getBoolean("isFirstRun", true);
        Editor editor = sharedPreferences.edit(); 
        if(isFirstRun) 
        { 
        	Log.d("debug", "第一次运行"); 
        	editor.putBoolean("isFirstRun", false);
        	editor.commit(); 
        	mFirst=1;
        }
        else { 
        	Log.d("debug", "非首次运行");
        	mFirst=0;
        } 

        handleMessage( mFirst);
    }
    
    public void handleMessage (int mFirst)
    {
    	switch(mFirst)
    	{
    	case 1:
    		
    		Intent mIntent = new Intent();
    		mIntent.setClass(SplashActivity.this, WhatsnewPagesA.class);
    		startActivity(mIntent);
    	    finish();		
    		break;
    	case 0:
    		
    		mIntent=new Intent();
    		mIntent.setClass(SplashActivity.this, Login.class);
    		startActivity(mIntent);
    	    finish();
    		break;
    	}
    }
   
}
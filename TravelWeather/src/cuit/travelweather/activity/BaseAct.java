/** 
 * @��Ŀ���ƣ�TravelWeather   
 * @�ļ�����BaseAct.java    
 * @�汾��Ϣ��
 * @���ڣ�2015��4��20��    
 * @Copyright 2015 www.517na.com Inc. All rights reserved.         
 */
package cuit.travelweather.activity;

import com.umeng.analytics.MobclickAgent;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**    
 *     
 * @��Ŀ���ƣ�TravelWeather    
 * @�����ƣ�BaseAct    
 * @��������    
 * @�����ˣ�zhaonan    
 * @����ʱ�䣺2015��4��20�� ����5:40:23    
 * @�޸��ˣ�zhaonan    
 * @�޸�ʱ�䣺2015��4��20�� ����5:40:23    
 * @�޸ı�ע��    
 * @version     
 *     
 */
public class BaseAct extends FragmentActivity{
    
    Context context;
    
    @Override
    protected void onCreate(Bundle arg0) {
        super.onCreate(arg0);
        context = this;
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
    
    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}

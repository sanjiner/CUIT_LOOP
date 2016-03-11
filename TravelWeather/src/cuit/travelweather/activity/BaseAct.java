/** 
 * @项目名称：TravelWeather   
 * @文件名：BaseAct.java    
 * @版本信息：
 * @日期：2015年4月20日    
 * @Copyright 2015 www.517na.com Inc. All rights reserved.         
 */
package cuit.travelweather.activity;

import com.umeng.analytics.MobclickAgent;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;

/**    
 *     
 * @项目名称：TravelWeather    
 * @类名称：BaseAct    
 * @类描述：    
 * @创建人：zhaonan    
 * @创建时间：2015年4月20日 下午5:40:23    
 * @修改人：zhaonan    
 * @修改时间：2015年4月20日 下午5:40:23    
 * @修改备注：    
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

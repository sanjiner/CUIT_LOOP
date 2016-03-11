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

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.Display;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RadioGroup;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.mapapi.BMapManager;
import com.baidu.mapapi.map.Geometry;
import com.baidu.mapapi.map.Graphic;
import com.baidu.mapapi.map.GraphicsOverlay;
import com.baidu.mapapi.map.MKEvent;
import com.baidu.mapapi.map.MKMapViewListener;
import com.baidu.mapapi.map.MapController;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.RouteOverlay;
import com.baidu.mapapi.map.Symbol;
import com.baidu.mapapi.search.MKAddrInfo;
import com.baidu.mapapi.search.MKBusLineResult;
import com.baidu.mapapi.search.MKDrivingRouteResult;
import com.baidu.mapapi.search.MKPlanNode;
import com.baidu.mapapi.search.MKPoiResult;
import com.baidu.mapapi.search.MKSearch;
import com.baidu.mapapi.search.MKSearchListener;
import com.baidu.mapapi.search.MKShareUrlResult;
import com.baidu.mapapi.search.MKSuggestionResult;
import com.baidu.mapapi.search.MKTransitRouteResult;
import com.baidu.mapapi.search.MKWalkingRouteResult;
import com.baidu.mapapi.search.MKWpNode;
import com.baidu.platform.comapi.basestruct.GeoPoint;

import cuit.travelweather.R;
import cuit.travelweather.fragment.PictureFragment;
import cuit.travelweather.util.Constant;
import cuit.travelweather.util.MyFont;
import cuit.travelweather.util.MyHttpClient;
import cuit.travelweather.util.Split;

@TargetApi(Build.VERSION_CODES.JELLY_BEAN)
public class TravelMapActivity extends BaseAct {
	
	public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
	public static final String KEY_PHOTO_PATH = "photo_path";
	private Context context = TravelMapActivity.this;
	private SharedPreferences sp;
	private Editor editor;

    
    MapView mMapView = null;// 地图View
    
    MKSearch mSearch = null;// 搜索模块，也可去掉地图模块独立使用
    
    private String str[] = null;
    
    private JpushApplication application;
    
    private JSONArray jsonArray;
    
    private RadioGroup travel_tabs;
    
    private int jsonArrayLenth = 0;
    
    private JSONObject jsonStr;
    
    // 控制tv的出现和消失
    private static final int LOAD = 1;
    
    private static final int UNLOAD = 2;
    
    private int intStart = UNLOAD;
    
    private int intEnd = UNLOAD;
    
    private int intNode1 = UNLOAD;
    
    private int intNode2 = UNLOAD;
    
    private int intNode3 = UNLOAD;
    
    private TextView tvStart;
    
    private TextView tvEnd;
    
    private TextView tvNode1;
    
    private TextView tvNode2;
    
    private TextView tvNode3;
    
    private Button b_start;
    
    private Button b_end;
    
    private Button b_node1;
    
    private Button b_node2;
    
    private Button b_node3;
    private Button turn1,turn2,turn3,turn4;////////////////////////////////////////////四个方向
    
    private String[] strNode = new String[] { "", "", "", "" };
    
    private static final int SHOW_PROCESS = 1;
    
    private static final int DISMISS_PROCESS = 0;
    
    private static ProgressDialog pdialog;
    
    private ImageButton mIBtnReresh;

	protected MapView.LayoutParams screenLP1;
	private String imgPath;
    
    private static Handler handler = new Handler() {
        
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SHOW_PROCESS:
                    pdialog.show();
                    break;
                case DISMISS_PROCESS:
                    pdialog.dismiss();
                    break;
            }
        }
        
    };
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        application = (JpushApplication) this.getApplication();
        application.mBMapManager = new BMapManager(this);
        application.mBMapManager.init(JpushApplication.strKey, new JpushApplication.MyGeneralListener());
        if (application.mBMapManager == null) {
            application.mBMapManager = new BMapManager(this);
            application.mBMapManager.init(JpushApplication.strKey, new JpushApplication.MyGeneralListener());
        }
        
        setContentView(R.layout.travel_map);
        MyFont.setTypeface(this, new int[] { R.id.travel_rbtn_forecast,
                                            R.id.travel_rbtn_current,
                                            R.id.travel_rbtn_forecast_sos,
                                            R.id.travel_rbtn_roadcondition });
        mMapView = (MapView) findViewById(R.id.bmapView);
        mMapView.setBuiltInZoomControls(true);
        // 设置启用内置的缩放控件
        MapController mMapController = mMapView.getController();
        // 得到mMapView的控制权,可以用它控制和驱动平移和缩放
        
        jsonInit();
        
        tvStart = new TextView(TravelMapActivity.this);
        tvEnd = new TextView(TravelMapActivity.this);
        tvNode1 = new TextView(TravelMapActivity.this);
        tvNode2 = new TextView(TravelMapActivity.this);
        tvNode3 = new TextView(TravelMapActivity.this);
        b_start = new Button(TravelMapActivity.this);
        ////////
        turn1 = new Button(TravelMapActivity.this);
        turn2 = new Button(TravelMapActivity.this);
        turn3 = new Button(TravelMapActivity.this);
        turn4 = new Button(TravelMapActivity.this);
        //////////////////
        b_end = new Button(TravelMapActivity.this);
        b_node1 = new Button(TravelMapActivity.this);
        b_node2 = new Button(TravelMapActivity.this);
        b_node3 = new Button(TravelMapActivity.this);
        
        GeoPoint point = new GeoPoint((int) (30.585 * 1E6), (int) (103.995 * 1E6));
        // 用给定的经纬度构造一个GeoPoint，单位是微度 (度 * 1E6)
        mMapController.setCenter(point);// 设置地图中心点
        mMapController.setZoom(12);// 设置地图zoom级别
        mMapView.getController().enableClick(true);
        travel_tabs = (RadioGroup) findViewById(R.id.travel_tabs);
        mIBtnReresh = (ImageButton) findViewById(R.id.travel_map_ibtn_refresh);
        mSearch = new MKSearch();
        
        mSearch.init(application.mBMapManager, new MKSearchListener() {
            
            @Override
            public void onGetWalkingRouteResult(MKWalkingRouteResult arg0, int arg1) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onGetTransitRouteResult(MKTransitRouteResult arg0, int arg1) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onGetSuggestionResult(MKSuggestionResult arg0, int arg1) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onGetShareUrlResult(MKShareUrlResult arg0, int arg1, int arg2) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onGetPoiResult(MKPoiResult arg0, int arg1, int arg2) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onGetPoiDetailSearchResult(int arg0, int arg1) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onGetDrivingRouteResult(MKDrivingRouteResult res, int error) {
                // TODO Auto-generated method stub
                if (error == MKEvent.ERROR_ROUTE_ADDR) {
                    // 遍历所有地址
                    // ArrayList<MKPoiInfo> stPois =
                    // res.getAddrResult().mStartPoiList;
                    // ArrayList<MKPoiInfo> enPois =
                    // res.getAddrResult().mEndPoiList;
                    // ArrayList<MKCityListInfo> stCities =
                    // res.getAddrResult().mStartCityList;
                    // ArrayList<MKCityListInfo> enCities =
                    // res.getAddrResult().mEndCityList;
                    return;
                }
                // 错误号可参考MKEvent中的定义
                if (error != 0 || res == null) {
                    Toast.makeText(TravelMapActivity.this, "抱歉，未找到结果", Toast.LENGTH_SHORT).show();
                    return;
                }
                // 清除其他图层
                if (mMapView.getOverlays() != null) {
                    mMapView.getOverlays().clear();
                }
                RouteOverlay routeOverlay = new RouteOverlay(TravelMapActivity.this, mMapView); // 此处仅展示一个方案作为示例
                routeOverlay.setData(res.getPlan(0).getRoute(0));
                mMapView.getOverlays().add(routeOverlay);
                //TODO
                //mMapView.refresh(); // //////////////////////////////////////////////////////////////////////////s
                // 自定义按钮
                int bg = 0xf0bed742;
                int tc = 0xfffeeeed;
                float btn_tx = 14;
                // 起点和 终点的button
                MapView.LayoutParams l_start = null;
                MapView.LayoutParams l_end = null;
                //////////////////////////////////////////////////////////////////////////////////////////////////
                l_start = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
                                                   MapView.LayoutParams.WRAP_CONTENT,
                                                   res.getStart().pt,
                                                   0);
                l_end = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT, MapView.LayoutParams.WRAP_CONTENT, res.getEnd().pt, 0);
                b_start.setText("1");
                /////初始化
                b_start.setTextColor(tc);
                b_start.setTextSize(btn_tx);
                b_start.setBackgroundColor(bg);
                b_end.setText("1");
                b_end.setTextColor(tc);
                b_end.setTextSize(btn_tx);
                b_end.setBackgroundColor(bg);
                // 途经点1
                
                MapView.LayoutParams l_node1 = null;
                b_node1.setTextColor(tc);
                b_node1.setTextSize(btn_tx);
                b_node1.setBackgroundColor(bg);
                // 途经点2
                
                MapView.LayoutParams l_node2 = null;
                b_node2.setTextColor(tc);
                b_node2.setTextSize(btn_tx);
                b_node2.setBackgroundColor(bg);
                // 途经点3
                
                MapView.LayoutParams l_node3 = null;
                b_node3.setTextColor(tc);
                b_node3.setTextSize(btn_tx);
                b_node3.setBackgroundColor(bg);
                // 自定义textview
                int tv_color = 0xffff4500;
                int tv_bcolor = 0xfffffff0;
                float tv_size = 14;
                // 起点和终点的textview
                MapView.LayoutParams layoutStart = null;
                MapView.LayoutParams layoutEnd = null;
                tvStart.setBackgroundColor(tv_bcolor);
                tvEnd.setBackgroundColor(tv_bcolor);
                tvStart.setTextColor(tv_color);
                tvEnd.setTextColor(tv_color);
                tvEnd.setTextSize(tv_size);
                tvStart.setTextSize(tv_size);
                layoutStart = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
                                                       MapView.LayoutParams.WRAP_CONTENT,
                                                       res.getStart().pt,
                                                       MapView.LayoutParams.BOTTOM_CENTER);
                layoutEnd = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
                                                     MapView.LayoutParams.WRAP_CONTENT,
                                                     res.getEnd().pt,
                                                     MapView.LayoutParams.BOTTOM_CENTER);
                if (tvStart.length() > 0) {
                    mMapView.removeView(tvStart);
                    mMapView.removeView(tvEnd);
                }
                tvStart.setVisibility(View.INVISIBLE);
                tvStart.setText(res.getStart().name + "\n" + str[0]);
                tvEnd.setVisibility(View.INVISIBLE);
                tvEnd.setText(res.getEnd().name + "\n" + str[1]);
                // 途经点1 的文字
                
                MapView.LayoutParams layoutNode1 = null;
                tvNode1.setBackgroundColor(tv_bcolor);
                tvNode1.setTextColor(tv_color);
                tvNode1.setTextSize(tv_size);
                tvNode1.setVisibility(View.INVISIBLE);
                // 途经点2的文字
                
                MapView.LayoutParams layoutNode2 = null;
                tvNode2.setBackgroundColor(tv_bcolor);
                tvNode2.setTextColor(tv_color);
                tvNode2.setTextSize(tv_size);
                tvNode2.setVisibility(View.INVISIBLE);
                // 途经点3 的文字
                MapView.LayoutParams layoutNode3 = null;
                tvNode3.setBackgroundColor(tv_bcolor);
                tvNode3.setTextColor(tv_color);
                tvNode3.setTextSize(tv_size);
                tvNode3.setVisibility(View.INVISIBLE);
                
                if (travel_tabs.getCheckedRadioButtonId() == R.id.travel_rbtn_forecast) {
                    
                    switch (jsonArrayLenth) {
                        case 5:
                            if (b_node3.length() > 0) {
                                mMapView.removeView(b_node3);
                            }
                            if (tvNode3.length() > 0) {
                                mMapView.removeView(tvNode3);
                            }
                            b_node3.setText(res.getWpNode().get(2).name);
                            l_node3 = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
                                                               MapView.LayoutParams.WRAP_CONTENT,
                                                               res.getWpNode().get(2).pt,
                                                               0);
                            mMapView.addView(b_node3, l_node3);
                            tvNode3.setText(res.getWpNode().get(2).name + "\n" + str[3]);
                            layoutNode3 = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
                                                                   MapView.LayoutParams.WRAP_CONTENT,
                                                                   res.getWpNode().get(2).pt,
                                                                   MapView.LayoutParams.BOTTOM_CENTER);
                            mMapView.addView(tvNode3, layoutNode3);
                            b_node3.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    if (intNode3 == UNLOAD) {
                                        intNode3 = LOAD;
                                        tvNode3.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        intNode3 = UNLOAD;
                                        tvNode3.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                        case 4:
                            if (tvNode2.length() > 0) {
                                mMapView.removeView(tvNode2);
                            }
                            if (b_node2.length() > 0) {
                                mMapView.removeView(b_node2);
                            }
                            b_node2.setText(res.getWpNode().get(1).name);
                            l_node2 = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
                                                               MapView.LayoutParams.WRAP_CONTENT,
                                                               res.getWpNode().get(1).pt,
                                                               0);
                            mMapView.addView(b_node2, l_node2);
                            tvNode2.setText(res.getWpNode().get(1).name + "\n" + str[3]);
                            layoutNode2 = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
                                                                   MapView.LayoutParams.WRAP_CONTENT,
                                                                   res.getWpNode().get(1).pt,
                                                                   MapView.LayoutParams.BOTTOM_CENTER);
                            mMapView.addView(tvNode2, layoutNode2);
                            b_node2.setOnClickListener(new OnClickListener() {
                                
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    if (intNode2 == UNLOAD) {
                                        intNode2 = LOAD;
                                        tvNode2.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        intNode2 = UNLOAD;
                                        tvNode2.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                        case 3:
                            if (b_node1.length() > 0) {
                                mMapView.removeView(b_node1);
                            }
                            if (tvNode1.length() > 0) {
                                mMapView.removeView(tvNode1);
                            }
                            b_node1.setText(res.getWpNode().get(0).name);
                            l_node1 = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
                                                               MapView.LayoutParams.WRAP_CONTENT,
                                                               res.getWpNode().get(0).pt,
                                                               0);
                            layoutNode1 = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
                                                                   MapView.LayoutParams.WRAP_CONTENT,
                                                                   res.getWpNode().get(0).pt,
                                                                   MapView.LayoutParams.BOTTOM_CENTER);
                            tvNode1.setText(res.getWpNode().get(0).name + "\n" + str[2]);
                            mMapView.addView(b_node1, l_node1);
                            mMapView.addView(tvNode1, layoutNode1);
                            b_node1.setOnClickListener(new OnClickListener() {
                                
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    if (intNode1 == UNLOAD) {
                                        intNode1 = LOAD;
                                        tvNode1.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        intNode1 = UNLOAD;
                                        tvNode1.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                            
                        case 2:
                            if (b_start.length() > 0) {
                                mMapView.removeView(b_start);
                                mMapView.removeView(b_end);
                            }
                            if (tvNode3.length() > 0) {
                                mMapView.removeView(tvNode3);
                            }
                            b_start.setText(res.getStart().name);
                            b_end.setText(res.getEnd().name);
                            mMapView.addView(b_start, l_start);
                            mMapView.addView(b_end, l_end);
                            mMapView.addView(tvStart, layoutStart);//、、、、、、、、、、、、、、、、、、、、、、、、、、、显示
                            mMapView.addView(tvEnd, layoutEnd);
                            b_start.setOnClickListener(new OnClickListener() {
                                
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    // textOverlay.addText(textItemStart);
                                    
                                    if (intStart == UNLOAD) {
                                        intStart = LOAD;
                                        tvStart.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        intStart = UNLOAD;
                                        tvStart.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                            b_end.setOnClickListener(new OnClickListener() {
                                
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    if (intEnd == UNLOAD) {
                                        intEnd = LOAD;
                                        tvEnd.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        intEnd = UNLOAD;
                                        tvEnd.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                            break;
                        case 0:
                            Toast.makeText(TravelMapActivity.this, "暂无数据，此处为展示数据", Toast.LENGTH_SHORT).show();
                            if (b_start.length() > 0) {
                                mMapView.removeView(b_start);
                                mMapView.removeView(b_end);
                                mMapView.removeView(b_node1);
                            }
                            b_start.setText(res.getStart().name);
                            b_end.setText(res.getEnd().name);
                            mMapView.addView(b_start, l_start);
                            mMapView.addView(b_end, l_end);
                            mMapView.addView(tvStart, layoutStart);
                            mMapView.addView(tvEnd, layoutEnd);
                            b_start.setOnClickListener(new OnClickListener() {
                                
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    // textOverlay.addText(textItemStart);
                                    
                                    if (intStart == UNLOAD) {
                                        intStart = LOAD;
                                        tvStart.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        intStart = UNLOAD;
                                        tvStart.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                            b_end.setOnClickListener(new OnClickListener() {
                                
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    if (intEnd == UNLOAD) {
                                        intEnd = LOAD;
                                        tvEnd.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        intEnd = UNLOAD;
                                        tvEnd.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                            b_node1.setText(res.getWpNode().get(0).name);
                            l_node1 = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
                                                               MapView.LayoutParams.WRAP_CONTENT,
                                                               res.getWpNode().get(0).pt,
                                                               0);
                            layoutNode1 = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
                                                                   MapView.LayoutParams.WRAP_CONTENT,
                                                                   res.getWpNode().get(0).pt,
                                                                   MapView.LayoutParams.BOTTOM_CENTER);
                            tvNode1.setText(res.getWpNode().get(0).name + "\n" + str[2]);
                            
                            mMapView.addView(b_node1, l_node1);
                            mMapView.addView(tvNode1, layoutNode1);
                            b_node1.setOnClickListener(new OnClickListener() {
                                
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    if (intNode1 == UNLOAD) {
                                        intNode1 = LOAD;
                                        tvNode1.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        intNode1 = UNLOAD;
                                        tvNode1.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                            break;
                    }
                }
                
                if (travel_tabs.getCheckedRadioButtonId() == R.id.travel_rbtn_current) {
                    if (tvStart.length() > 0) {
                        mMapView.removeView(tvStart);
                        mMapView.removeView(tvEnd);
                    }
                    tvStart.setText(res.getStart().name + "\t" + "暂无");
                    tvEnd.setText(res.getEnd().name + "\t" + "暂无");
                    switch (jsonArrayLenth) {
                        case 5:
                            if (tvNode3.length() > 0) {
                                mMapView.removeView(tvNode3);
                            }
                            if (b_node3.length() > 0) {
                                mMapView.removeView(b_node3);
                            }
                            
                            b_node3.setText(res.getWpNode().get(2).name);
                            l_node3 = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
                                                               MapView.LayoutParams.WRAP_CONTENT,
                                                               res.getWpNode().get(2).pt,
                                                               0);
                            mMapView.addView(b_node3, l_node3);
                            tvNode3.setText(res.getWpNode().get(2).name + "\t" + "暂无");
                            layoutNode3 = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
                                                                   MapView.LayoutParams.WRAP_CONTENT,
                                                                   res.getWpNode().get(2).pt,
                                                                   MapView.LayoutParams.BOTTOM_CENTER);
                            mMapView.addView(tvNode3, layoutNode3);
                            b_node3.setOnClickListener(new OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    if (intNode3 == UNLOAD) {
                                        intNode3 = LOAD;
                                        tvNode3.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        intNode3 = UNLOAD;
                                        tvNode3.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                        case 4:
                            if (tvNode2.length() > 0) {
                                mMapView.removeView(tvNode2);
                            }
                            if (b_node2.length() > 0) {
                                mMapView.removeView(b_node2);
                            }
                            b_node2.setText(res.getWpNode().get(1).name);
                            l_node2 = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
                                                               MapView.LayoutParams.WRAP_CONTENT,
                                                               res.getWpNode().get(1).pt,
                                                               0);
                            mMapView.addView(b_node2, l_node2);
                            tvNode2.setText(res.getWpNode().get(1).name + "\t" + "暂无");
                            layoutNode2 = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
                                                                   MapView.LayoutParams.WRAP_CONTENT,
                                                                   res.getWpNode().get(1).pt,
                                                                   MapView.LayoutParams.BOTTOM_CENTER);
                            mMapView.addView(tvNode2, layoutNode2);
                            b_node2.setOnClickListener(new OnClickListener() {
                                
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    if (intNode2 == UNLOAD) {
                                        intNode2 = LOAD;
                                        tvNode2.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        intNode2 = UNLOAD;
                                        tvNode2.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                        case 3:
                            if (tvNode1.length() > 0) {
                                mMapView.removeView(tvNode1);
                            }
                            if (b_node1.length() > 0) {
                                mMapView.removeView(b_node1);
                            }
                            b_node1.setText(res.getWpNode().get(0).name);
                            l_node1 = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
                                                               MapView.LayoutParams.WRAP_CONTENT,
                                                               res.getWpNode().get(0).pt,
                                                               0);
                            layoutNode1 = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
                                                                   MapView.LayoutParams.WRAP_CONTENT,
                                                                   res.getWpNode().get(0).pt,
                                                                   MapView.LayoutParams.BOTTOM_CENTER);
                            tvNode1.setText(res.getWpNode().get(0).name + "\t" + "暂无");
                            mMapView.addView(b_node1, l_node1);
                            mMapView.addView(tvNode1, layoutNode1);
                            b_node1.setOnClickListener(new OnClickListener() {
                                
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    if (intNode1 == UNLOAD) {
                                        intNode1 = LOAD;
                                        tvNode1.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        intNode1 = UNLOAD;
                                        tvNode1.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                            
                        case 2:
                            if (b_start.length() > 0) {
                                mMapView.removeView(b_start);
                                mMapView.removeView(b_end);
                            }
                            b_start.setText(res.getStart().name);
                            
                            b_end.setText(res.getEnd().name);
                            mMapView.addView(b_start, l_start);
                            mMapView.addView(b_end, l_end);
                            mMapView.addView(tvStart, layoutStart);
                            mMapView.addView(tvEnd, layoutEnd);
                            b_start.setOnClickListener(new OnClickListener() {
                                
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    // textOverlay.addText(textItemStart);
                                    
                                    if (intStart == UNLOAD) {
                                        intStart = LOAD;
                                        tvStart.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        intStart = UNLOAD;
                                        tvStart.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                            b_end.setOnClickListener(new OnClickListener() {
                                
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    if (intEnd == UNLOAD) {
                                        intEnd = LOAD;
                                        tvEnd.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        intEnd = UNLOAD;
                                        tvEnd.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                            break;
                        case 0:
                            if (tvNode1.length() > 0) {
                                mMapView.removeView(tvNode1);
                            }
                            Toast.makeText(TravelMapActivity.this, "暂无数据，此处为展示数据", Toast.LENGTH_SHORT).show();
                            if (b_start.length() > 0) {
                                mMapView.removeView(b_start);
                                mMapView.removeView(b_end);
                                mMapView.removeView(b_node1);
                            }
                            b_start.setText(res.getStart().name);
                            b_end.setText(res.getEnd().name);
                            mMapView.addView(b_start, l_start);
                            mMapView.addView(b_end, l_end);
                            mMapView.addView(tvStart, layoutStart);
                            mMapView.addView(tvEnd, layoutEnd);
                            b_start.setOnClickListener(new OnClickListener() {
                                
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    // textOverlay.addText(textItemStart);
                                    
                                    if (intStart == UNLOAD) {
                                        intStart = LOAD;
                                        tvStart.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        intStart = UNLOAD;
                                        tvStart.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                            b_end.setOnClickListener(new OnClickListener() {
                                
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    if (intEnd == UNLOAD) {
                                        intEnd = LOAD;
                                        tvEnd.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        intEnd = UNLOAD;
                                        tvEnd.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                            b_node1.setText(res.getWpNode().get(0).name);
                            l_node1 = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
                                                               MapView.LayoutParams.WRAP_CONTENT,
                                                               res.getWpNode().get(0).pt,
                                                               0);
                            layoutNode1 = new MapView.LayoutParams(MapView.LayoutParams.WRAP_CONTENT,
                                                                   MapView.LayoutParams.WRAP_CONTENT,
                                                                   res.getWpNode().get(0).pt,
                                                                   MapView.LayoutParams.BOTTOM_CENTER);
                            tvNode1.setText(res.getWpNode().get(0).name + "\n" + str[2]);
                            mMapView.addView(b_node1, l_node1);
                            mMapView.addView(tvNode1, layoutNode1);
                            b_node1.setOnClickListener(new OnClickListener() {
                                
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    if (intNode1 == UNLOAD) {
                                        intNode1 = LOAD;
                                        tvNode1.setVisibility(View.VISIBLE);
                                    }
                                    else {
                                        intNode1 = UNLOAD;
                                        tvNode1.setVisibility(View.INVISIBLE);
                                    }
                                }
                            });
                            break;
                    }
                }
                
                if (travel_tabs.getCheckedRadioButtonId() == R.id.travel_rbtn_roadcondition) {
                    getRaodNumber();
                    if (strNode[0].equals("")) {
                        strNode[0] = "0";
                        strNode[1] = "0";
                        strNode[2] = "0";
                        strNode[3] = "0";
                    }
                    ////////////////////////////////////////////////////////////////
                  turn1.setText("1");
                  turn2.setText("1");
                  turn3.setText("1");
                  turn4.setText("1");
                  turn1.setTextColor(tc);
                  turn2.setTextColor(tc);
                  turn3.setTextColor(tc);
                  turn4.setTextColor(tc);
                  turn1.setTextSize(btn_tx);
                  turn2.setTextSize(btn_tx);
                  turn3.setTextSize(btn_tx);
                  turn4.setTextSize(btn_tx);
                  turn1.setBackgroundColor(bg);
                  turn2.setBackgroundColor(bg);
                  turn3.setBackgroundColor(bg);
                  turn4.setBackgroundColor(bg);
                  turn1.setOnClickListener(new OnClickListener() {
                      
                      private PictureFragment fragment;

					@Override
                      public void onClick(View v) {
                          // TODO Auto-generated method stub
                    	  //进入到图片的行
                    	  	Intent mIntent = new Intent();
              				mIntent.setClass(context, MainActivity.class);
              				Constant.raod = 1;
              				startActivity(mIntent);
              				finish();
//                    	  FragmentManager fragmentManager = getFragmentManager();
//                          FragmentTransaction fragmentTransaction = fragmentManager.
//                                  beginTransaction();
////						PictureFragment fragment = new PictureFragment();
//                          Fragment fragment = new Fragment();
//                       
//                          fragmentTransaction.add(R.id.main_picture,fragment);
//                           fragmentTransaction.addToBackStack("picture_btn_road");
////                          fragmentTransaction.commit();
//                    	  android.app.FragmentManager fm = getFragmentManager();  
//                          FragmentTransaction transaction = fm.beginTransaction();  
//                           fragment = new PictureFragment(); 
//                          transaction.replace(R.id.main_picture, fragment);  
//                          transaction.commit();  
                      }
                  
                  });
                  
                  turn2.setOnClickListener(new OnClickListener() {
                      
                      private PictureFragment fragment;

					@Override
                      public void onClick(View v) {
                          // TODO Auto-generated method stub
                    	  //进入到图片的行
                    	  	Intent mIntent = new Intent();
              				mIntent.setClass(context, MainActivity.class);
              				Constant.raod = 1;
              				startActivity(mIntent);
              				finish();
                      }
                  
                  });
 turn3.setOnClickListener(new OnClickListener() {
                      
                      private PictureFragment fragment;

					@Override
                      public void onClick(View v) {
                          // TODO Auto-generated method stub
                    	  //进入到图片的行
                    	  	Intent mIntent = new Intent();
              				mIntent.setClass(context, MainActivity.class);
              				Constant.raod = 1;
              				startActivity(mIntent);
              				finish();
                      }
                  
                  });
 turn4.setOnClickListener(new OnClickListener() {
     
     private PictureFragment fragment;

	@Override
     public void onClick(View v) {
         // TODO Auto-generated method stub
   	  //进入到图片的行
   	  	Intent mIntent = new Intent();
				mIntent.setClass(context, MainActivity.class);
				Constant.raod = 1;
				startActivity(mIntent);
				finish();
     }
 
 });
                  
                  //获取屏幕的高宽度
                Display display=getWindow().getWindowManager().getDefaultDisplay();
                int screenWidth = display.getWidth();
                int screenHeight = display.getHeight();
                
                int x1=(int) (0.75*screenWidth) ;
                int y1=(int) (0.25*screenHeight) ;
                MapView.LayoutParams mpParams1 = new MapView.LayoutParams(
                		MapView.LayoutParams.WRAP_CONTENT,
                		MapView.LayoutParams.WRAP_CONTENT, x1,y1,
                		MapView.LayoutParams.BOTTOM_CENTER);
                mMapView.addView(turn1, mpParams1);
                 
                 int x2=(int) (0.25*screenWidth) ;
                 int y2=(int) (0.25*screenHeight) ;		
                MapView.LayoutParams mpParams2 = new MapView.LayoutParams(
                		MapView.LayoutParams.WRAP_CONTENT,
                		MapView.LayoutParams.WRAP_CONTENT, x2,y2,
                		MapView.LayoutParams.BOTTOM_CENTER);
                mMapView.addView(turn2, mpParams2);
                		
                
                int x3=(int) (0.25*screenWidth) ;
                int y3=(int) (0.75*screenHeight) ;
                MapView.LayoutParams mpParams3 = new MapView.LayoutParams(
                		MapView.LayoutParams.WRAP_CONTENT,
                		MapView.LayoutParams.WRAP_CONTENT, x3,y3,
                		MapView.LayoutParams.BOTTOM_CENTER);
                mMapView.addView(turn3, mpParams3);
                
                
                int x4=(int) (0.75*screenWidth) ;
                int y4=(int) (0.75*screenHeight) ;
                MapView.LayoutParams mpParams4 = new MapView.LayoutParams(
                		MapView.LayoutParams.WRAP_CONTENT,
                		MapView.LayoutParams.WRAP_CONTENT, x4,y4,
                		MapView.LayoutParams.BOTTOM_CENTER);
                mMapView.addView(turn4, mpParams4);
//                
                ////////////////////////////////////////////////////////////////////////	
                  ////////////////////////////////////////////////////////////////
                    Geometry circleStart = new Geometry();//圆形
                    Geometry circleEnd = new Geometry();
                    Geometry circleNode1 = new Geometry();
                    Geometry circleNode2 = new Geometry();
                    Geometry circleNode3 = new Geometry();
                    // 移除文字
                    mMapView.removeView(tvStart);
                    mMapView.removeView(tvEnd);
                    // mMapView.refresh();
                    // 设置样式
                    Symbol circleSymbol = new Symbol();///绘制轨道
                    Symbol.Color circleColor = circleSymbol.new Color();
                    circleColor.red = 0;
                    circleColor.green = 0;
                    circleColor.blue = 255;
                    circleColor.alpha = 66;
                    circleSymbol.setSurface(circleColor, 1, 3);
                    GraphicsOverlay circleOverlay = new GraphicsOverlay(mMapView);
                    mMapView.getOverlays().add(circleOverlay);
                    circleStart.setCircle(res.getStart().pt, 80000);
                    circleEnd.setCircle(res.getEnd().pt, 80000);
                    Graphic circleS = new Graphic(circleStart, circleSymbol);//自定义圆形控件
                    Graphic circleE = new Graphic(circleEnd, circleSymbol);
                    circleOverlay.setData(circleS);//把起始的紫色圆形加到路线中，在这一步中加载出紫色范围
                    circleOverlay.setData(circleE);
                    // b_start.setText(strNode[0]);
                    b_end.setText(strNode[1]);
                    b_start.setOnClickListener(new OnClickListener() {
                        
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            Intent it = new Intent();
                            it.setClass(TravelMapActivity.this, RoadActivity.class);////////////////////////////////////////////////////////////////////
                            startActivity(it);
                        }
                    });
                    b_end.setOnClickListener(new OnClickListener() {
                        
                        @Override
                        public void onClick(View v) {
                            // TODO Auto-generated method stub
                            Intent it = new Intent();
                            it.setClass(TravelMapActivity.this, RoadActivity.class);
                            startActivity(it);
                        }
                    });
                    
                    switch (jsonArrayLenth) {
                        case 0:
                            mMapView.removeView(tvNode1);
                            b_node1.setText(strNode[2]);
                            b_node1.setOnClickListener(new OnClickListener() {
                                
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    Intent it = new Intent();
                                    it.setClass(TravelMapActivity.this, RoadActivity.class);
                                    startActivity(it);
                                }
                            });
                            circleNode1.setCircle(res.getWpNode().get(0).pt, 80000);
                            Graphic circleN1_1 = new Graphic(circleNode1, circleSymbol);
                            circleOverlay.setData(circleN1_1);
                            break;
                        case 5:
                            mMapView.removeView(tvNode3);
                            b_node3.setText(strNode[1]);
                            b_node3.setOnClickListener(new OnClickListener() {
                                
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    Intent it = new Intent();
                                    it.setClass(TravelMapActivity.this, RoadActivity.class);
                                    startActivity(it);
                                }
                            });
                            circleNode3.setCircle(res.getWpNode().get(2).pt, 80000);
                            Graphic circleN3 = new Graphic(circleNode3, circleSymbol);
                            circleOverlay.setData(circleN3);
                            
                        case 4:
                            mMapView.removeView(tvNode2);
                            b_node2.setText(strNode[3]);
                            circleNode2.setCircle(res.getWpNode().get(1).pt, 80000);
                            Graphic circleN2 = new Graphic(circleNode2, circleSymbol);
                            circleOverlay.setData(circleN2);
                            b_node2.setOnClickListener(new OnClickListener() {
                                
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    Intent it = new Intent();
                                    it.setClass(TravelMapActivity.this, RoadActivity.class);
                                    startActivity(it);
                                }
                            });
                        case 3:
                            mMapView.removeView(tvNode1);
                            b_node1.setText(strNode[2]);
                            circleNode1.setCircle(res.getWpNode().get(0).pt, 80000);
                            Graphic circleN1 = new Graphic(circleNode1, circleSymbol);
                            circleOverlay.setData(circleN1);
                            b_node1.setOnClickListener(new OnClickListener() {
                                
                                @Override
                                public void onClick(View v) {
                                    // TODO Auto-generated method stub
                                    Intent it = new Intent();
                                    it.setClass(TravelMapActivity.this, RoadActivity.class);
                                    startActivity(it);
                                }
                            });
                            break;
                        default:
                            break;
                    
                    }
                }
                
                // 执行刷新使生效
                mMapView.refresh();
                // 使用zoomToSpan()绽放地图，使路线能完全显示在地图上
                mMapView.getController().zoomToSpan(routeOverlay.getLatSpanE6(), routeOverlay.getLonSpanE6());
                // 移动地图到起点
                mMapView.getController().animateTo(res.getStart().pt);
                mMapView.getController().zoomToSpan(routeOverlay.getLatSpanE6(), routeOverlay.getLonSpanE6());
                // 移动地图到起点
                mMapView.getController().animateTo(res.getStart().pt);
                
            }
            
            @Override
            public void onGetBusDetailResult(MKBusLineResult res, int error) {
                // TODO Auto-generated method stub
                
            }
            
            @Override
            public void onGetAddrResult(MKAddrInfo res, int error) {
                // TODO Auto-generated method stub
                
            }
        });
        
        MKMapViewListener mMapViewListener = new MKMapViewListener() {
            
            @Override
            public void onMapMoveFinish() {
                // 此处可以实现地图移动完成事件的状态监听
                if (travel_tabs.getCheckedRadioButtonId() == R.id.travel_rbtn_roadcondition) {
                    getRaodNumber();
                }
                
            }
            
            @Override
            public void onMapLoadFinish() {
                // 地图初始化完成时，此回调被触发.
                //getRoutedata();
            }
            
            @Override
            public void onMapAnimationFinish() {
                /**
                 * 地图完成带动画的操作（如: animationTo()）后，此回调被触发
                 */
            }
            
            @Override
            public void onGetCurrentMap(Bitmap arg0) {
                // TODO Auto-generated method stub
                // 用MapView.getCurrentMap()发起截图后，在此处理截图结果.
            }
            
            @Override
            public void onClickMapPoi(MapPoi arg0) {
                // TODO Auto-generated method stub
                // 此处可实现点击到地图可点标注时的监听
                if (travel_tabs.getCheckedRadioButtonId() == R.id.travel_rbtn_roadcondition) {
                    getRaodNumber();
                }
            }
        };
        mMapView.regMapViewListener(application.mBMapManager, mMapViewListener);
        
        travel_tabs.setOnCheckedChangeListener(new OnCheckedChangeListener() {//////////////////////////
            
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.travel_rbtn_forecast:
                        if (jsonArrayLenth == 0) {
                            getTest();
                        }
                        else {
                            getRoutedata();
                        }
                        break;
                    case R.id.travel_rbtn_current:
                        if (jsonArrayLenth == 0) {
                            getTest();
                        }
                        else {
                            getRoutedata();
                        }
                         showD1();
                        break;
                    case R.id.travel_rbtn_forecast_sos:
                        showD2();
                        break;
                    case R.id.travel_rbtn_roadcondition:
                        if (jsonArrayLenth == 0) {
                            getTest();
                        }
                        else {
                            getRoutedata();
                        }
                        break;
                    default:
                        break;
                }
            }
        });
        mIBtnReresh.setOnClickListener(new OnClickListener() {
            
            @Override
            public void onClick(View v) {
                getRoutedata();
            }
        });
        new Handler().postDelayed(new Runnable() {
            
            @Override
            public void run() {
                getRoutedata();
            }
        }, 1500);
    }
    
    /**
     * 初始化json数据
     */
    private void jsonInit() {
        // TODO Auto-generated method stub
        application = (JpushApplication) TravelMapActivity.this.getApplication();
        JSONObject jsonObject = application.getJson4travle_forcast();
        try {
            jsonArray = jsonObject.getJSONArray("nodes_info");
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        if (jsonArray != null) {
            jsonArrayLenth = jsonArray.length() / 2;
            str = new String[jsonArrayLenth];
            // showD3();
        }
    }
    
    public void getRoutedata() {
        
        MKPlanNode stNode = new MKPlanNode();
        MKPlanNode enNode = new MKPlanNode();
        MKWpNode node1 = new MKWpNode();
        MKWpNode node2 = new MKWpNode();
        MKWpNode node3 = new MKWpNode();
        ArrayList<MKWpNode> wpNodes = new ArrayList<MKWpNode>();
        
        if (jsonArrayLenth == 2) {
            try {
                JSONObject jsonSt = jsonArray.getJSONObject(0);
                JSONObject jsonEn = jsonArray.getJSONObject(2);
                stNode.name = Split.cut2(jsonSt.getString("location"));
                enNode.name = Split.cut2(jsonEn.getString("location"));
                getForcase(0);
                getForcase(2);
                mSearch.drivingSearch(Split.cut_r2(jsonSt.getString("location")),
                                      stNode,
                                      Split.cut_r2(jsonEn.getString("location")),
                                      enNode);
            }
            catch (JSONException e) {
                e.printStackTrace();
            }
            
        }
        else if (jsonArrayLenth == 0) {
            showD3();
        }
        else {
            String start = "成都";
            String end = "重庆";
            for (int i = 0; i < jsonArray.length(); i = i + 2) {
                
                try {
                    JSONObject json = jsonArray.getJSONObject(i);
                    if (i == 0) {
                        stNode.name = Split.cut2(json.getString("location"));
                        start = Split.cut_r2(json.getString("location"));
                        getForcase(i);
                    }
                    else if (i == (jsonArray.length() - 2)) {
                        enNode.name = Split.cut2(json.getString("location"));
                        end = Split.cut_r2(json.getString("location"));
                        getForcase(i);
                        
                    }
                    else if (i != (jsonArray.length() - 2) && i == 2) {
                        node1.city = Split.cut_r2(json.getString("location"));
                        node1.name = Split.cut2(json.getString("location"));
                        wpNodes.add(0, node1);
                        getForcase(i);
                        
                    }
                    else if (i != (jsonArray.length() - 2) && i == 4) {
                        node2.city = Split.cut_r2(json.getString("location"));
                        node2.name = Split.cut2(json.getString("location"));
                        wpNodes.add(1, node2);
                        getForcase(i);
                        
                    }
                    else if (i != (jsonArray.length() - 2) && i == 6) {
                        node3.city = Split.cut_r2(json.getString("location"));
                        node3.name = Split.cut2(json.getString("location"));
                        wpNodes.add(2, node3);
                        getForcase(i);
                        
                    }
                }
                catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                mSearch.drivingSearch(start, stNode, end, enNode, wpNodes);
            }
        }
        
    }
    
    // 无数据测试代码
    private void getTest() {
        String start = "成都";
        String end = "重庆";
        MKPlanNode stNode = new MKPlanNode();
        MKPlanNode enNode = new MKPlanNode();
        MKWpNode node1 = new MKWpNode();
        ArrayList<MKWpNode> wpNodes = new ArrayList<MKWpNode>();
        stNode.name = "双流";
        enNode.name = "潼南";
        node1.city = "雅安";
        node1.name = "雨城区";
        wpNodes.add(0, node1);
        mSearch.drivingSearch(start, stNode, end, enNode, wpNodes);
    }
    
    @Override
    protected void onPause() {
        if (mMapView != null) {
            mMapView.onPause();
        }
        super.onPause();
    }
    
    @Override
    protected void onResume() {
        if (mMapView != null) {
            mMapView.onResume();
        }
        super.onResume();
    }
    
    @Override
    public void onDestroy() {
        if (mMapView != null) {
            mMapView.destroy();
        }
        super.onDestroy();
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(outState);
        mMapView.onSaveInstanceState(outState);
    }
    
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        mMapView.onRestoreInstanceState(savedInstanceState);
    }
    
    // xml绑定方法
    public void map_return(View v) {
        finish();
    }
    
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////上传图片
    public void map_add(View v) {
    	pickPhoto();
    }
    
    @Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

    	super.onActivityResult(requestCode, resultCode, data);
    	if (resultCode == Activity.RESULT_OK) {
			// TODO
			doPhoto(requestCode, data);
		}
		
	}
    
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

			photoUri = data.getData();

			if (photoUri == null) {
				Toast.makeText(this, "选择图片文件出错", Toast.LENGTH_LONG).show();
				return;
			}
		}

		String picPath = null;
		switch (requestCode) {

//		case SELECT_PIC_BY_TAKE_PHOTO:
//			picPath = getImagePath();
//			break;
		case SELECT_PIC_BY_PICK_PHOTO:
			picPath = getRealPathFromURI(photoUri);
			break;

		}

		if (picPath != null
				&& (picPath.endsWith(".png") || picPath.endsWith(".PNG")
						|| picPath.endsWith(".jpg") || picPath.endsWith(".JPG"))) {
			Intent mIntent = new Intent();
			mIntent.putExtra(KEY_PHOTO_PATH, picPath);
			mIntent.setClass(context, LinePicActivity.class);//
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


    //////////////////////////////////////////////////////////////////////////////////////////////////////
    
    private void getForcase(int i) {
        
        String day1;
        String day2;
        String day3;
        try {
            JSONObject J = jsonArray.getJSONObject(i + 1);
            JSONArray jsonContent = J.getJSONArray("content");
            JSONObject j1 = jsonContent.getJSONObject(0);
            JSONObject j2 = jsonContent.getJSONObject(1);
            JSONObject j3 = jsonContent.getJSONObject(2);
            day1 = "24小时\t" + j1.getString("weather_day")
                   + "\t"
                   + j1.getString("temperature_night")
                   + "℃~"
                   + j1.getString("temperature_day")
                   + "℃\n";
            day2 = "48小时\t" + j2.getString("weather_day")
                   + "\t"
                   + j2.getString("temperature_night")
                   + "℃~"
                   + j2.getString("temperature_day")
                   + "℃\n";
            day3 = "72小时\t" + j3.getString("weather_day")
                   + "\t"
                   + j3.getString("temperature_night")
                   + "℃~"
                   + j3.getString("temperature_day")
                   + "℃";
            str[i / 2] = day1 + day2 + day3;
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        
    }
    
    private String getLive(int i) {
        String re = "\n";
        JSONObject j = application.getJson4travle_live();
        try {
            JSONArray jArray = j.getJSONArray("nodes");
            JSONObject j1 = jArray.getJSONObject(i);
            re = "\n温度：" + j1.getString("temp") + "\t风级：" + j1.getString("WD") + j1.getString("WS") + "\t湿度：" + j1.getString("SD");
        }
        catch (JSONException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return re;
    }
    
    private void showD1() {
        // 创建builder
        AlertDialog.Builder builder = new AlertDialog.Builder(TravelMapActivity.this);
        builder.setTitle("驴友天气提示您~") // 标题
               .setIcon(R.drawable.ic_launcher)
               // icon
               .setCancelable(false)
               // 不响应back按钮
               .setMessage("暂无实况信息")
               // 对话框显示内容
               // 设置按钮
               .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       Toast.makeText(TravelMapActivity.this, "暂无实况信息", Toast.LENGTH_SHORT).show();
                   }
               });
        
        builder.create().show();
        mMapView.getOverlays().clear();
        
    }
    
    private void showD2() {
        // 创建builder
        AlertDialog.Builder builder = new AlertDialog.Builder(TravelMapActivity.this);
        builder.setTitle("驴友天气提示您~") // 标题
               .setIcon(R.drawable.ic_launcher)
               // icon
               .setCancelable(false)
               // 不响应back按钮
               .setMessage("暂无预警信息")
               // 对话框显示内容
               // 设置按钮
               .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       Toast.makeText(TravelMapActivity.this, "暂无预警信息", Toast.LENGTH_SHORT).show();
                   }
               });
        builder.create().show();
        mMapView.getOverlays().clear();
        
    }
    
    private void showD3() {
        // 创建builder
        AlertDialog.Builder builder = new AlertDialog.Builder(TravelMapActivity.this);
        builder.setTitle("驴友天气提示您~") // 标题
               .setIcon(R.drawable.ic_launcher)
               // icon
               .setCancelable(false)
               // 不响应back按钮
               .setMessage("暂无预报信息")
               // 对话框显示内容
               // 设置按钮
               .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       Toast.makeText(TravelMapActivity.this, "暂无预报信息", Toast.LENGTH_SHORT).show();
                   }
               });
        builder.create().show();
        // mMapView.getOverlays().clear();
    }
    //路况信息
    private void getRaodNumber() {
        GeoPoint centerPoint = mMapView.getMapCenter(); //
        final int tbSpan = mMapView.getLatitudeSpan(); // 维度范围 （从地图的上边缘到地图的下边缘）
                                                       // 3
        final int lrSpan = mMapView.getLongitudeSpan(); // 经度范围（从地图的左边缘到地图的右边缘）
                                                        // 4
        final int centerLatitude = centerPoint.getLatitudeE6(); // 1
        final int centerLongitude = centerPoint.getLongitudeE6(); // 2
        
        pdialog = new ProgressDialog(TravelMapActivity.this);
        pdialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        pdialog.setCancelable(false);
        pdialog.setMessage("正在获取路况信息……");
        new AsyncTask<Void, Void, Void>() {
            
            @Override
            protected Void doInBackground(Void... param) {
                List<NameValuePair> params = new ArrayList<NameValuePair>();
                handler.sendMessage(handler.obtainMessage(SHOW_PROCESS));
                
                try {
                    params.add(new BasicNameValuePair("centerLat", URLEncoder.encode(String.valueOf(centerLatitude), "UTF-8")));
                    params.add(new BasicNameValuePair("centerLon", URLEncoder.encode(String.valueOf(centerLongitude), "UTF-8")));
                    params.add(new BasicNameValuePair("latSpan", URLEncoder.encode(String.valueOf(tbSpan), "UTF-8")));
                    params.add(new BasicNameValuePair("lonSpan", URLEncoder.encode(String.valueOf(lrSpan), "UTF-8")));
                }
                catch (UnsupportedEncodingException e) {
                    System.out.print(e.getMessage());
                }
                jsonStr = MyHttpClient.getJson(TravelMapActivity.this, Constant.condition, params);
                return null;
            }
            
            @Override
            protected void onPostExecute(Void result) {
                handler.sendMessage(handler.obtainMessage(DISMISS_PROCESS));
                try {
                    strNode[0] = jsonStr.getString("num1");
                    strNode[1] = jsonStr.getString("num2");
                    strNode[2] = jsonStr.getString("num3");
                    strNode[3] = jsonStr.getString("num4");
                }
                catch (JSONException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                
            }
        }.execute();
        
        turn1.setText(strNode[0]);
        turn2.setText(strNode[1]);
        turn3.setText(strNode[2]);
        turn4.setText(strNode[3]);
    }
    
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // if (pictureFragment.isVisable) {
        super.onCreateOptionsMenu(menu);
        menu.add(Menu.NONE, 0, 0, "新增路况");
        return true;
    }
    
    @Override
    public boolean onMenuItemSelected(int featureId, MenuItem item) {
        switch (item.getItemId()) {
            case 0:
                Intent it = new Intent().setClass(TravelMapActivity.this, TravelNewRoadActivity.class);
                startActivity(it);
                break;
            default:
                
        }
        return super.onMenuItemSelected(featureId, item);
    }
}

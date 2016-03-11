package com.example.PCenter;

import java.io.File;

import com.exam.ThreeSystem.R;
import com.example.PCenter.MyWebChromeClient.OpenFileChooserCallBack;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.JsResult;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ProgressBar;

public class Homework_stu_detail_activity_finishing_Addpiconweb extends BaseActivity 
implements OnClickListener,MyWebChromeClient.OpenFileChooserCallBack
{
	private WebView mWebView;
	private Button backButton;
	private ProgressBar progressBar;
	private ValueCallback<Uri> mUploadMsg;
	private static final String TAG = "MyActivity";  
    private static final int REQUEST_CODE_PICK_IMAGE = 0;  
    private static final int REQUEST_CODE_IMAGE_CAPTURE = 1;
    private ValueCallback<Uri> mUploadFile;
    private static final int REQUEST_UPLOAD_FILE_CODE = 12343;
    private Intent mSourceIntent;
    private String realURL="";
//	private String UPloadURL="http://222.18.158.198:8016/PhoneAccess/StudentUploadPictures/List.aspx?";
	private String UPloadURL="http://222.18.158.220/PhoneAccess/StudentUploadPictures/List.aspx?";
	private String HomeworkID;
	private String TeachingClassID;
	private String WorkContent;
	private String StudentNum;
	private SharedPreferences sp;
	private String FILE = Constant.USERINFO_SP;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO 自动生成的方法存根
		super.onCreate(savedInstanceState);
		setContentView(R.layout.homework_tea_addhomework_onweb);
		init();
		getintent();
		sp = mActivity.getSharedPreferences(FILE, Context.MODE_PRIVATE);
		UPloadURL=sp.getString(Constant.SPFIELD.UPloadURL, "");
		WebSettings webSettings =   mWebView .getSettings();       
//		webSettings.setUseWideViewPort(true);//设置此属性，可任意比例缩放
		webSettings.setLoadWithOverviewMode(true);
		webSettings.setJavaScriptEnabled(true);  
		webSettings.setBuiltInZoomControls(true);
		webSettings.setSupportZoom(true);
		webSettings.setAllowFileAccess(true);
		webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
		webSettings.setLoadsImagesAutomatically(true);
		webSettings.setAllowFileAccess(true);
		fixDirPath();
		mWebView.loadUrl(realURL);
		
	}
	private void resetURL()
	{
		realURL=UPloadURL+"HomeworkID=" + HomeworkID + "&"
				+ "TeachingClassID=" + TeachingClassID
				+ "&" + "WorkContent=" + WorkContent + "&" + "StudentNum="
				+ StudentNum  ;
		System.out.println(realURL);
	}
	private void getintent()
	{
		Intent intent=getIntent();
		HomeworkID=intent.getStringExtra("HomeworkID");
		TeachingClassID=intent.getStringExtra("TeachingClassID");
		WorkContent=intent.getStringExtra("WorkContent");
		StudentNum=intent.getStringExtra("StudentNum");
		resetURL();
	}
	private void init()
	{
		GetViewObj();
		setlistener();
	}
	private void GetViewObj()
	{
		mWebView=(WebView) findViewById(R.id.webView);
		progressBar=(ProgressBar) findViewById(R.id.progressBar_web);
		mWebView.requestFocusFromTouch();
		backButton=(Button) findViewById(R.id.homework_Addhomework_web_back);
		
	}
	private void setlistener()
	{
		backButton.setOnClickListener(this);
		//setWebChromeClient();
		mWebView.setWebChromeClient(new MyWebChromeClient(this));
		mWebView.setWebViewClient(new WebViewClient(){
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO 自动生成的方法存根
				view.loadUrl(url);   
                return true;   
			}

						//网页加载开始时调用，显示加载提示旋转进度条
			            @Override
			            public void onPageStarted(WebView view, String url,Bitmap favicon) {
			                // TODO Auto-generated method stub
			                super.onPageStarted(view, url, favicon);
			                progressBar.setVisibility(android.view.View.VISIBLE);
//			                Toast.makeText(ElecHall.this, "onPageStarted", 2).show();
			            }

			//网页加载完成时调用，隐藏加载提示旋转进度条
			            @Override
			            public void onPageFinished(WebView view, String url) {
			                // TODO Auto-generated method stub
			                super.onPageFinished(view, url);
			                progressBar.setVisibility(android.view.View.GONE);
//			                Toast.makeText(ElecHall.this, "onPageFinished", 2).show();
			            }
			//网页加载失败时调用，隐藏加载提示旋转进度条
			            @Override
			            public void onReceivedError(WebView view, int errorCode,
			                    String description, String failingUrl) {
			                // TODO Auto-generated method stub
			                super.onReceivedError(view, errorCode, description, failingUrl);
			                progressBar.setVisibility(android.view.View.GONE);
//			                Toast.makeText(ElecHall.this, "onReceivedError", 2).show();
			            }
			            
			        });
		
	}

	@Override
	public void onClick(View view) {
		// TODO 自动生成的方法存根
		switch (view.getId()) {
		case R.id.homework_Addhomework_web_back:
			finish();
			break;

		default:
			break;
		}
	}
	
	@Override  
    public boolean onKeyDown(int keyCode, KeyEvent event) {   
        if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {   
        	mWebView.goBack(); //goBack()表示返回WebView的上一页面   
            return true;   
        }   
        else if((keyCode == KeyEvent.KEYCODE_BACK) && !mWebView.canGoBack())
        {
        	
        	finish();
        	 return false; 
        }
        else 
        {return false;}

         
    }

	
	
	 @Override  
	    public void onActivityResult(int requestCode, int resultCode, Intent data) {  
	        if (resultCode != Activity.RESULT_OK) {  
	            return;  
	        }  
	        switch (requestCode) {  
	            case REQUEST_CODE_IMAGE_CAPTURE:  
	            case REQUEST_CODE_PICK_IMAGE: {  
	                try {  
	                    if (mUploadMsg == null) {  
	                        return;  
	                    }  
	                    String sourcePath = ImageUtil.retrievePath(this, mSourceIntent, data);  
	                    if (TextUtils.isEmpty(sourcePath) || !new File(sourcePath).exists()) {  
	                        Log.w(TAG, "sourcePath empty or not exists.");  
	                        break;  
	                    }  
	                    Uri uri = Uri.fromFile(new File(sourcePath));  
	                    mUploadMsg.onReceiveValue(uri);  
	                } catch (Exception e) {  
	                    e.printStackTrace();  
	                }  
	                break;  
	            }  
	        }  
	    }  
	  
	    @Override  
	    public void openFileChooserCallBack(ValueCallback<Uri> uploadMsg, String acceptType) {  
	        mUploadMsg = uploadMsg;  
	        showOptions();  
	    }  
	  
	    public void showOptions() {  
	        AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);  
	        alertDialog.setOnCancelListener(new ReOnCancelListener());  
	        alertDialog.setTitle("选择");  
	        alertDialog.setItems(R.array.options, new DialogInterface.OnClickListener() {  
	                    @Override  
	                    public void onClick(DialogInterface dialog, int which) {  
	                        if (which == 0) {  
	                            mSourceIntent = ImageUtil.choosePicture();  
	                            startActivityForResult(mSourceIntent, REQUEST_CODE_PICK_IMAGE);  
	                        } else {  
	                            mSourceIntent = ImageUtil.takeBigPicture();  
	                            startActivityForResult(mSourceIntent, REQUEST_CODE_IMAGE_CAPTURE);  
	                        }  
	                    }  
	                }  
	        );  
	        alertDialog.show();  
	    }  
	  
	    private void fixDirPath() {  
	        String path = ImageUtil.getDirPath();  
	        File file = new File(path);  
	        if (!file.exists()) {  
	            file.mkdirs();  
	        }  
	    }  
	  
	    private class ReOnCancelListener implements DialogInterface.OnCancelListener {  
	  
	        @Override  
	        public void onCancel(DialogInterface dialogInterface) {  
	            if (mUploadMsg != null) {  
	                mUploadMsg.onReceiveValue(null);  
	                mUploadMsg = null;  
	            }  
	        }  
	    }
final class MyWebChromeClient extends WebChromeClient{
	    	
	    	private OpenFileChooserCallBack mOpenFileChooserCallBack;  
	    	  
	        public  MyWebChromeClient(OpenFileChooserCallBack openFileChooserCallBack) {  
	            mOpenFileChooserCallBack = openFileChooserCallBack;  
	        }  
	      
	        public boolean onJsAlert(WebView view,String url,        
	                String message,JsResult result) {        
			    if(message.equals("图片上传成功"))
			    {
			    	try {
						Thread.sleep(800);
					} catch (InterruptedException e) {
						// TODO 自动生成的 catch 块
						e.printStackTrace();
					}
			    	finally{
			    	/*Intent intent=new Intent(Homework_tea_addhomework_onweb.this,homework_tea_addhomework.class);
			    	intent.putExtra("success", "success");
			    	startActivity(intent);*/
			    		finish();
			    	}
			    	
			    }
			    result.confirm();        
			    return super.onJsConfirm(view,url,message, result);        
	    }

	    	//For Android 3.0+  
	        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {  
	            mOpenFileChooserCallBack.openFileChooserCallBack(uploadMsg, acceptType);  
	        }  
	      
	        // For Android < 3.0  
	        public void openFileChooser(ValueCallback<Uri> uploadMsg) {  
	            openFileChooser(uploadMsg, "");  
	        }  
	      
	        // For Android  > 4.1.1  
	        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {  
	            openFileChooser(uploadMsg, acceptType);  
	        }  
	      

	    }
}

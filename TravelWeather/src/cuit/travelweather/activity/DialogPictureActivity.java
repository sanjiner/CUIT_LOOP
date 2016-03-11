package cuit.travelweather.activity;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.provider.MediaStore;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import cn.sharesdk.onekeyshare.OnekeyShare;
import cuit.travelweather.R;
import cuit.travelweather.fragment.PictureFragment;

@SuppressLint("NewApi")
public class DialogPictureActivity extends BaseAct implements OnClickListener{  
  
	private LinearLayout layout01,layout02,layout03,layout04,layout05; 
	boolean bIsSelection = false;
		
	public static final int SELECT_PIC_BY_TAKE_PHOTO = 1;
	public static final int SELECT_PIC_BY_PICK_PHOTO = 2;
	public static final int MENU_ORDER_BY_HOT = 0;
	public static final int MENU_ORDER_BY_DISTANCE = 1;
//	public  int orderBy = MENU_ORDER_BY_HOT;
	String imgPath;
	private PictureFragment pictureFragment;
	public static final String KEY_PHOTO_PATH = "photo_path";
	
	private static ProgressDialog pdialog;     //进度条
	private static final int SHOW_PROCESS = 1;
	private static final int DISMISS_PROCESS = 0;
	public static int flag = -1;
	List<NameValuePair> param = new ArrayList<NameValuePair>();

	private SharedPreferences sp;

	String[] menu_toolbar_name_array;
	public Uri setImageUri() {
		// Store image in dcim
		File file = new File(Environment.getExternalStorageDirectory()
				+ "/DCIM/Camera/", "image" + new Date().getTime() + ".jpg");
		Uri imgUri = Uri.fromFile(file);
		this.imgPath = file.getAbsolutePath();
		return imgUri;
	}
    @Override  
    protected void onCreate(Bundle savedInstanceState) {  
        super.onCreate(savedInstanceState);
        pictureFragment = (PictureFragment) getSupportFragmentManager()
				.findFragmentById(R.id.PictureFragment);
//        pictureFragment =new MainActivity().pictureFragment;/
        setContentView(R.layout.activity_picture_dialog);  
        TextView tv1 = (TextView)findViewById(R.id.dia1);
        TextView tv2 = (TextView)findViewById(R.id.dia2);
        TextView tv3 = (TextView)findViewById(R.id.dia3);
        TextView tv4 = (TextView)findViewById(R.id.dia4);
        TextView tv5 = (TextView)findViewById(R.id.dia5);
        sp= getSharedPreferences("test", Activity.MODE_PRIVATE); 
		 String size = sp.getString("affairinfo_fontsize", "2");
		if(size.equals("1")){
			tv1.setTextSize(10);
			tv2.setTextSize(10);
			tv3.setTextSize(10);
			tv4.setTextSize(10);
			tv5.setTextSize(10);
		}else if(size.equals("2")){
		}else{
			tv1.setTextSize(30);
			tv2.setTextSize(30);
			tv3.setTextSize(30);
			tv4.setTextSize(30);
			tv5.setTextSize(30);
		}
        initView();  
    }  
       
    /** 
     * 初始化组件 
     */  
    private void initView(){  
        //得到布局组件对象并设置监听事件  
    
    	//getData();
        layout01 = (LinearLayout)findViewById(R.id.llayout01);  
        layout02 = (LinearLayout)findViewById(R.id.llayout02);  
        layout03 = (LinearLayout)findViewById(R.id.llayout03);  
        layout04 = (LinearLayout)findViewById(R.id.llayout04);
        layout05 = (LinearLayout)findViewById(R.id.llayout05);
        
        layout01.setOnClickListener(this);  
        layout02.setOnClickListener(this);  
        layout03.setOnClickListener(this);
        layout04.setOnClickListener(this);
        layout05.setOnClickListener(this);
    }
    
    


    @Override  
    public boolean onTouchEvent(MotionEvent event){  
        finish();  
        return true;  
    }  
           
    @Override  
    public void onClick(View v) {  
    	switch (v.getId()) {
    	case R.id.llayout01:
    		MainActivity.orderBy = MENU_ORDER_BY_HOT;
    		flag = 0;
    		finish();
    		break;
    	case R.id.llayout02:
    		MainActivity.orderBy = MENU_ORDER_BY_DISTANCE;
    		flag = 1;
    		finish();
    		break;
    	case R.id.llayout03:
    		takePhoto();
    		break;
    	case R.id.llayout04:
    		pickPhoto();
    		break;
    	case R.id.llayout05:
    		share();
    		break;
		default:
			break;
		}
               
    }  
	public String getImagePath() {
		return imgPath;
	}
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
	@Override
	public void onActivityResult(int requestCode, int resultCode, Intent data) {

		if (resultCode == 100) {
			if (data.getStringExtra("area") != null) {
			//	line_tv_from.setText(data.getStringExtra("area"));
			}
		} else if (resultCode == 101) {
			if (data.getStringExtra("area") != null) {
			//	line_tv_to.setText(data.getStringExtra("area"));
			}
		} else if (resultCode == 102) {
			if (data.getStringExtra("area") != null) {
			//	line_tv_node1.setText(data.getStringExtra("area"));
			}
		} else if (resultCode == 103) {
			if (data.getStringExtra("area") != null) {
			//	line_tv_node2.setText(data.getStringExtra("area"));
			}
		} else if (resultCode == 104) {
			if (data.getStringExtra("area") != null) {
			//	line_tv_node3.setText(data.getStringExtra("area"));
			}
		} else if (resultCode == Activity.RESULT_OK) {
			doPhoto(requestCode, data);
		}

		super.onActivityResult(requestCode, resultCode, data);
	}
    private void pickPhoto() {
		Intent intent = new Intent();
		intent.setType("image/*");
		intent.setAction(Intent.ACTION_GET_CONTENT);
		startActivityForResult(Intent.createChooser(intent, "选择要上传的照片："),
				SELECT_PIC_BY_PICK_PHOTO);
	}
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
}

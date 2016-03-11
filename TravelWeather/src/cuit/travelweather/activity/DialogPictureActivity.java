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
	
	private static ProgressDialog pdialog;     //������
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
     * ��ʼ����� 
     */  
    private void initView(){  
        //�õ���������������ü����¼�  
    
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
				Toast.makeText(this, "û��ѡ��ͼƬ�ļ�", Toast.LENGTH_LONG).show();
				return;
			}

			// if(data.hasExtra("data")){
			// Bitmap thumbnail = data.getParcelableExtra("data");
			// //�õ�bitmap��Ĳ���
			// }

			photoUri = data.getData();

			if (photoUri == null) {
				Toast.makeText(this, "ѡ��ͼƬ�ļ�����", Toast.LENGTH_LONG).show();
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
			Toast.makeText(this, "ѡ��ͼƬ�ļ�����ȷ", Toast.LENGTH_SHORT).show();
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
		startActivityForResult(Intent.createChooser(intent, "ѡ��Ҫ�ϴ�����Ƭ��"),
				SELECT_PIC_BY_PICK_PHOTO);
	}
    private void takePhoto() {
		// ִ������ǰ��Ӧ�����ж�SD���Ƿ����
		String SDState = Environment.getExternalStorageState();
		if (SDState.equals(Environment.MEDIA_MOUNTED)) {

			Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);// "android.media.action.IMAGE_CAPTURE"
			// File out = new File(Environment.getExternalStorageDirectory(),
			// new SimpleDateFormat(
			// "yyyyMMddHHmmss").format(new Date()) + ".jpg");
			// imgPath = out.getAbsolutePath();
			// Uri mUri = Uri.fromFile(out);

			/***
			 * ��Ҫ˵��һ�£����²���ʹ����������գ����պ��ͼƬ����������е� ����ʹ�õ����ַ�ʽ��һ���ô����ǻ�ȡ��ͼƬ�����պ��ԭͼ
			 * �����ʵ��ContentValues�����Ƭ·���Ļ������պ��ȡ��ͼƬΪ����ͼ������
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
			Toast.makeText(this, "�ڴ濨������", Toast.LENGTH_LONG).show();
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
		// ����ʱNotification��ͼ�������
		oks.setNotification(R.drawable.ic_launcher,
				getString(R.string.app_name));
		// address�ǽ����˵�ַ��������Ϣ���ʼ�ʹ��
		oks.setAddress("12345678901");
		// title���⣬ӡ��ʼǡ����䡢��Ϣ��΢�š���������QQ�ռ�ʹ��
		oks.setTitle(getString(R.string.share));
		// titleUrl�Ǳ�����������ӣ�������������QQ�ռ�ʹ��
		oks.setTitleUrl("http://sharesdk.cn");
		// text�Ƿ����ı�������ƽ̨����Ҫ����ֶ�
		oks.setText(getString(R.string.share_content));
		// imagePath��ͼƬ�ı���·����Linked-In �����ƽ̨��֧�ִ˲���
		// �����Զ�����ⲿ�ص�
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

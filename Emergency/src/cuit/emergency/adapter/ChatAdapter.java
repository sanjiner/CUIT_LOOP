package cuit.emergency.adapter;

import java.lang.ref.SoftReference;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import cc.util.android.core.BaseFragment;
import cc.util.android.core.FragmentInfo;
import cc.util.android.core.FragmentInfoActivity;
import cc.util.android.core.ToastUtil;
import cc.util.android.image.BitmapHelper;
import cc.util.android.viewInject.ResInject;
import cc.util.android.viewInject.ViewInjectUtil;
import cuit.emergency.R;
import cuit.emergency.fragment.ScanPictureFragment;
import cuit.emergency.tool.DBHelper;
import cuit.emergency.util.ToolUtil;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.AnimationDrawable;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.media.SoundPool;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ChatAdapter extends BaseAdapter implements OnClickListener {
	
	private List<Map<String, String>> mAllMsg;

	private AnimationDrawable animationDrawable;
	private MediaPlayer mPlayer;
	private ImageView mImageView;
	private SoundPool mSoundPool;
	private int mLoadId;
	private Context mContext;
	
	//用于异步加载图片
	private final LinkedHashMap<String, SoftReference<Bitmap>> linkedHashMap;
	private SimpleDateFormat dateFormat;
	private BaseFragment fragment;
	public void setFragment(BaseFragment fragment) {
		this.fragment = fragment;
	}
	
	public ChatAdapter(Context context, List<Map<String, String>> data) {
		//图片保存
		linkedHashMap = new LinkedHashMap<String, SoftReference<Bitmap>>(16, .75f, true) {
			private static final long serialVersionUID = 1L;
			@Override
			protected boolean removeEldestEntry(
					Entry<String, SoftReference<Bitmap>> eldest) {
				// TODO Auto-generated method stub
				return linkedHashMap.size() > 32;
			}
		};
		
		mContext = context;
		this.mAllMsg = data;
		dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.CHINA);
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return mAllMsg.size();
	}
	
	@Override
	public Object getItem(int arg0) {
		// TODO Auto-generated method stub
		return mAllMsg.get(arg0);
	}
	
	@Override
	public long getItemId(int arg0) {
		// TODO Auto-generated method stub
		return arg0;
	}
	
	@Override
	public int getItemViewType(int position) {
		// TODO Auto-generated method stub
		return Integer.parseInt(mAllMsg.get(position).get(DBHelper.C_MSG_FROM));
	}
	
	@Override
	public int getViewTypeCount() {
		// TODO Auto-generated method stub
		return 2;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub
		ViewHolder viewHolder = null;
		int type = getItemViewType(position);
		if (convertView == null ) {
			switch (type) {
			case 0:
				convertView = (View)View.inflate(mContext, R.layout.chat_left_item, null);
				break;
			default:
				convertView = (View)View.inflate(mContext, R.layout.chat_right_item, null);
				break;
			}
			viewHolder = new ViewHolder();
			ViewInjectUtil.inject(viewHolder, convertView);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		viewHolder.voice.setId(position);
		viewHolder.time.setText(mAllMsg.get(position).get(DBHelper.C_MSG_TIME).toString());
		String messageStr = mAllMsg.get(position).get(DBHelper.C_MSG);
		if (ToolUtil.strIsFile(messageStr)) {   //图片
			viewHolder.picture.setVisibility(View.VISIBLE);
			viewHolder.picture.setId(position);
			viewHolder.voice.setVisibility(View.GONE);
			viewHolder.duration.setVisibility(View.GONE);
			viewHolder.message.setVisibility(View.GONE);
			
			final String picPath = mAllMsg.get(position).get(DBHelper.C_MSG_PATH);
			viewHolder.picture.setTag(picPath);
			SoftReference<Bitmap> softReference = linkedHashMap.get(picPath);
			Bitmap bitmap = softReference == null ? null : softReference.get();
			if (bitmap == null) {
				bitmap = BitmapHelper.compressBitmap(picPath, 64, 32);
				softReference = new SoftReference<Bitmap>(bitmap);
				bitmap = null;
				linkedHashMap.put(picPath, softReference);
			}
			if (softReference.get() == null) {
				viewHolder.picture.setVisibility(View.GONE);
			} else {
				viewHolder.picture.setImageBitmap(softReference.get());
				viewHolder.picture.setOnClickListener(new OnClickListener() {
					@Override
					public void onClick(View v) {
						// TODO Auto-generated method stub
						Bundle bundle = new Bundle();
						bundle.putString(ScanPictureFragment.IMAGEPATH, v.getTag()+"");
						FragmentInfo info = new FragmentInfo(ScanPictureFragment.class, bundle);
						FragmentInfoActivity.startFragment(fragment, info);
					}
				});
			}
		} else if (ToolUtil.strIsVoice(messageStr)) {  //语音
			viewHolder.voice.setVisibility(View.VISIBLE);
			viewHolder.voice.setId(position);
			viewHolder.voice.setTag(type+"");
			viewHolder.duration.setVisibility(View.VISIBLE);
			viewHolder.picture.setVisibility(View.GONE);
			viewHolder.message.setVisibility(View.GONE);
			float time = (float) (Float.parseFloat(mAllMsg.get(position).get(DBHelper.C_MSG_DURA)) / 1000.0);
			LayoutParams params = viewHolder.voice.getLayoutParams();
			params.width = 40 + (int)time * 15;
			viewHolder.voice.setLayoutParams(params);
			viewHolder.voice.setOnClickListener(this);
			String timeStr = Math.round(time)+"";
			viewHolder.duration.setText(timeStr+"”");
		} else {
			
			viewHolder.message.setVisibility(View.VISIBLE);
			viewHolder.picture.setVisibility(View.GONE);
			viewHolder.voice.setVisibility(View.GONE);
			viewHolder.duration.setVisibility(View.GONE);
			if(messageStr.length()>=10)
			{
					if(messageStr.substring(0,10).equals("@&+%zAOPEN"))
					{
						messageStr="您的视频消息";
					}
					else if(messageStr.substring(0,10).equals("@&+%zACLSE"))
					{
						messageStr="视频被取消啦"; 
					}
					else if(messageStr.substring(0,10).equals("@&+%zROPEN"))
					{
						messageStr="您的视频消息";
					}
					else if(messageStr.substring(0,10).equals("@&+%zRCLSE"))
					{
						messageStr="视频被取消啦";
					}
			
			
			}
			viewHolder.message.setText(messageStr);
		}
		if (position == 0) {
			viewHolder.time.setVisibility(View.VISIBLE);
		} else {
			try {
				Date front = dateFormat.parse(mAllMsg.get(position-1).get(DBHelper.C_MSG_TIME));
				Date back = dateFormat.parse(mAllMsg.get(position).get(DBHelper.C_MSG_TIME).toString());
				if ((back.getTime() - front.getTime() > 20000 && ToolUtil.strIsVoice(messageStr))||
						(back.getTime() - front.getTime() > 12000 && !ToolUtil.strIsVoice(messageStr))) {
					viewHolder.time.setVisibility(View.VISIBLE);
				} else {
					viewHolder.time.setVisibility(View.GONE);
				}
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				viewHolder.time.setVisibility(View.GONE);
			}
		}
		return convertView;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (animationDrawable != null) {
			animationDrawable.stop();
		}
		mImageView = (ImageView)v;
		mImageView.setImageResource(v.getTag().toString().equals(DBHelper.C_MSG_FROM_OTHER+"")
				? R.drawable.voice_play_left : R.drawable.voice_play_right);
		animationDrawable = (AnimationDrawable) mImageView.getDrawable();
		animationDrawable.start();
		play();
	}
	
	//播放音频
	private void play() {
		try {
			//首先播放提示音
			if (mSoundPool == null) {
				mSoundPool = new SoundPool(1, AudioManager.STREAM_SYSTEM, 5);
				mLoadId = mSoundPool.load(mContext, R.raw.ptt_startrecord, 1);
			}
			mSoundPool.play(mLoadId, 1, 1, 0, 0, 1);
			if (mPlayer == null) mPlayer = new MediaPlayer();
			mPlayer.reset();
			mPlayer.setDataSource(mAllMsg.get(mImageView.getId()).get(DBHelper.C_MSG_PATH).toString());
			mPlayer.prepareAsync();
			mPlayer.setOnPreparedListener(new OnPreparedListener() {
				@Override
				public void onPrepared(MediaPlayer mp) {
					// TODO Auto-generated method stub
					mPlayer.start();
				}
			});
			mPlayer.setOnCompletionListener(new OnCompletionListener() {
				@Override
				public void onCompletion(MediaPlayer arg0) {
					// TODO Auto-generated method stub
					animationDrawable.stop();
				}
			});
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
			ToastUtil.showShort(mContext, "找不到音频路径");
		}
	}
	
	static class ViewHolder {
		@ResInject(R.id.chat_item_msg_time)
		TextView time;
		@ResInject(R.id.chat_item_msg_pic)
		ImageView picture;
		@ResInject(R.id.chat_item_msg_voice)
		ImageView voice;
		@ResInject(R.id.chat_item_msg_dura)
		TextView duration;
		@ResInject(R.id.chat_item_msg_msg)
		TextView message;
	}
}

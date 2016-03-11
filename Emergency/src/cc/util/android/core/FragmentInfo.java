package cc.util.android.core;

import android.os.Bundle;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * 封装一个Fragment的消息
 * @author wangcccong 
 * @version 1.140122
 * create at: 14-02-13
 * <br>update at: Mon, 14, Sep. 2014
 */
public class FragmentInfo implements Parcelable {

	public final static String INFO = "FragmentInfo";

	private Class<?> clazz;         //Fragment的Class
	private Bundle bundle;          //传递过来的参数

	public FragmentInfo(Class<?> clazz, Bundle bundle) {
		this.clazz = clazz;
		this.bundle = bundle;
	}

	public Class<?> getClazz() {
		return clazz;
	}

	public Bundle getBundle() {
		return bundle;
	}

	public final static Creator<FragmentInfo> CREATOR = new Parcelable.Creator<FragmentInfo>() {

		@Override
		public FragmentInfo createFromParcel(Parcel source) {
			// TODO Auto-generated method stub
			return new FragmentInfo((Class<?>)source.readSerializable(), source.readBundle());
		}

		@Override
		public FragmentInfo[] newArray(int size) {
			// TODO Auto-generated method stub
			return new FragmentInfo[size];
		}
	};

	@Override
	public int describeContents() {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public void writeToParcel(Parcel dest, int flags) {
		// TODO Auto-generated method stub
		dest.writeSerializable(clazz);
		dest.writeBundle(bundle);
	}

}
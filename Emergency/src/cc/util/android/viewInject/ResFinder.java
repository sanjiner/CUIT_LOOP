package cc.util.android.viewInject;

import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.XmlResourceParser;
import android.graphics.Movie;
import android.graphics.drawable.Drawable;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;

/**
 * @author wangcccong
 * @version 1.140122
 * create at: 2014-01-17
 * update at: 2014-02-27
 */
public class ResFinder<T> {

	private T mT;
	public ResFinder(T t) {
		this.mT = t;
	}
	
	public View findViewById(int id) {
		return mT instanceof Activity ? ((Activity)mT).findViewById(id) :
				( mT instanceof View ? ((View)mT).findViewById(id) : null);
	}
	
	public View findViewById(int id, int parentId) {
		View pView = null;
		if (parentId > 0)
			pView = this.findViewById(parentId);
		
		View view = null;
		if (pView != null)
			view = pView.findViewById(id);
		else
			view = this.findViewById(id);
		return view;
	}
	
	public Object loadRes(ResType type, int id) {
		if (id < 1) return null;
        switch (type) {
            case Animation:
                return getAnimation(getContext(), id);
            case Boolean:
                return getBoolean(getContext(), id);
            case Color:
                return getColor(getContext(), id);
            case ColorStateList:
                return getColorStateList(getContext(), id);
            case Dimension:
                return getDimension(getContext(), id);
            case DimensionPixelOffset:
                return getDimensionPixelOffset(getContext(), id);
            case DimensionPixelSize:
                return getDimensionPixelSize(getContext(), id);
            case Drawable:
                return getDrawable(getContext(), id);
            case Integer:
                return getInteger(getContext(), id);
            case IntArray:
                return getIntArray(getContext(), id);
            case Movie:
                return getMovie(getContext(), id);
            case String:
                return getString(getContext(), id);
            case StringArray:
                return getStringArray(getContext(), id);
            case Text:
                return getText(getContext(), id);
            case TextArray:
                return getTextArray(getContext(), id);
            case Xml:
                return getXml(getContext(), id);
            default:
                break;
        }
        return null;
	}
	
	public static Animation getAnimation(Context context, int id) {
        return AnimationUtils.loadAnimation(context, id);
    }

    public static boolean getBoolean(Context context, int id) {
        return context.getResources().getBoolean(id);
    }

    public static int getColor(Context context, int id) {
        return context.getResources().getColor(id);
    }

    public static ColorStateList getColorStateList(Context context, int id) {
        return context.getResources().getColorStateList(id);
    }

    public static float getDimension(Context context, int id) {
        return context.getResources().getDimension(id);
    }

    public static int getDimensionPixelOffset(Context context, int id) {
        return context.getResources().getDimensionPixelOffset(id);
    }

    public static int getDimensionPixelSize(Context context, int id) {
        return context.getResources().getDimensionPixelSize(id);
    }

    public static Drawable getDrawable(Context context, int id) {
        return context.getResources().getDrawable(id);
    }

    public static int getInteger(Context context, int id) {
        return context.getResources().getInteger(id);
    }

    public static int[] getIntArray(Context context, int id) {
        return context.getResources().getIntArray(id);
    }

    public static Movie getMovie(Context context, int id) {
        return context.getResources().getMovie(id);
    }

    public static String getString(Context context, int id) {
        return context.getResources().getString(id);
    }

    public static String[] getStringArray(Context context, int id) {
        return context.getResources().getStringArray(id);
    }

    public static CharSequence getText(Context context, int id) {
        return context.getResources().getText(id);
    }

    public static CharSequence[] getTextArray(Context context, int id) {
        return context.getResources().getTextArray(id);
    }

    public static XmlResourceParser getXml(Context context, int id) {
        return context.getResources().getXml(id);
    }
	
	public Context getContext() {
		return mT instanceof Activity ? (Activity)mT : ((View)mT).getContext();
	}
}

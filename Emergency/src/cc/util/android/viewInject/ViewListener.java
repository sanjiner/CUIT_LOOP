package cc.util.android.viewInject;

import java.lang.reflect.Method;
import java.util.Date;
import java.util.Map;

import cc.util.android.core.LogUtil;
import cc.util.android.view.CalendarView;
import cc.util.android.view.CalendarView.OnCalendarViewListener;
import cc.util.android.view.PullExpandedListView;
import cc.util.android.view.PullListView;
import cc.util.android.view.PullExpandedListView.OnPullExpandedleListViewListener;
import cc.util.android.view.PullListView.OnPullListViewListener;
import cc.util.java.core.ConcurrentKKVMap;
import android.annotation.SuppressLint;
import android.preference.Preference;
import android.preference.Preference.OnPreferenceChangeListener;
import android.preference.Preference.OnPreferenceClickListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnFocusChangeListener;
import android.view.View.OnKeyListener;
import android.view.View.OnLongClickListener;
import android.view.View.OnTouchListener;
import android.view.ViewTreeObserver.OnScrollChangedListener;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;
import android.widget.ExpandableListView.OnGroupClickListener;
import android.widget.ExpandableListView.OnGroupCollapseListener;
import android.widget.ExpandableListView.OnGroupExpandListener;
import android.widget.RadioGroup;
import android.widget.SeekBar;
import android.widget.SeekBar.OnSeekBarChangeListener;
import android.widget.TabHost.OnTabChangeListener;

/**
 * @author wangcccong
 * @version 1.140122
 * create at: 2014-01-24
 * update at: 2014-02-27
 * <br>update at: Mon, 15 Sep. 2014
 * &nbsp;&nbsp;新增SwipeRefreshLayout监听
 * <br>update at: Mon, 30 Sep. 2014
 * &nbsp;&nbsp;新增CalendarView监听 
 */
public class ViewListener implements OnClickListener, OnLongClickListener,  OnFocusChangeListener,
	OnKeyListener, OnTouchListener, OnItemClickListener, OnItemLongClickListener, OnChildClickListener,
	OnGroupClickListener, OnGroupCollapseListener, OnGroupExpandListener, OnCheckedChangeListener,
	android.widget.RadioGroup.OnCheckedChangeListener, OnPreferenceChangeListener, OnPreferenceClickListener,
	OnTabChangeListener, OnScrollChangedListener, OnScrollListener, OnItemSelectedListener, OnSeekBarChangeListener,
	OnPullListViewListener, OnCalendarViewListener, OnPullExpandedleListViewListener {
	
	private Object mObject;
	private Method[] mMethods;
	public ViewListener (Object obj, Method... methods) {
		this.mObject = obj;
		this.mMethods = methods;
	}

	/*---------------OnSeekBarChangeListener (start) ---------------*/
	@Override
	public void onProgressChanged(SeekBar seekBar, int progress,
			boolean fromUser) {
		// TODO Auto-generated method stub
		try {
			mMethods[0].invoke(mObject, seekBar, progress, fromUser);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException--OnProgressChanged"+seekBar.getId(), e.getMessage()+"");
		}
	}

	@Override
	public void onStartTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		if (mMethods.length < 2 || mMethods[1] == null) {
			LogUtil.e("ViewListenerException--OnStartTrackingTouch"+seekBar.getId(), "no startTrackingTouch");
			return;
		}
		try {
			mMethods[1].invoke(mObject, seekBar);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException--OnStartTrackingTouch"+seekBar.getId(), e.getMessage()+"");
		}
	}

	@Override
	public void onStopTrackingTouch(SeekBar seekBar) {
		// TODO Auto-generated method stub
		if (mMethods.length < 3 || mMethods[2] == null) {
			LogUtil.e("ViewListenerException--OnStopTrackingTouch"+seekBar.getId(), "no stopTrackingTouch");
			return;
		}
		try {
			mMethods[2].invoke(mObject, seekBar);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException--OnStopTrackingTouch"+seekBar.getId(), e.getMessage()+"");
		}
	}
	/*---------------OnSeekBarChangeListener (end) ---------------*/

	/*---------------OnItemSelectedListener (start) ---------------*/
	@Override
	public void onItemSelected(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		try {
			mMethods[0].invoke(mObject, arg0, arg1, arg2, arg3);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException--onItemSelected"+arg0.getId(), e.getMessage()+"");
		}
	}

	@Override
	public void onNothingSelected(AdapterView<?> arg0) {
		// TODO Auto-generated method stub
		if (mMethods.length < 2 || mMethods[1] == null) {
			LogUtil.e("ViewListenerException--onNothingSelected", "no onNothingSelected");
			return;
		}
		try {
			mMethods[1].invoke(mObject, arg0);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException--onNothingSelected"+arg0.getId(), e.getMessage()+"");
		}
	}
	/*---------------OnItemSelectedListener (end) ---------------*/

	/*---------------OnScrollListener (start) ---------------*/
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {
		// TODO Auto-generated method stub
		try {
			mMethods[0].invoke(mObject, view, firstVisibleItem, visibleItemCount, totalItemCount);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException--onScroll"+view.getId(), e.getMessage()+"");
		}
	}

	@Override
	public void onScrollStateChanged(AbsListView view, int scrollState) {
		// TODO Auto-generated method stub
		if (mMethods.length < 2 || mMethods[1] == null) {
			LogUtil.e("ViewListenerException--onScrollStateChanged", "no onScrollStateChanged");
			return;
		}
		try {
			mMethods[1].invoke(mObject, view, scrollState);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException--onScrollStateChanged"+view.getId(), e.getMessage()+"");
		}
	}
	/*---------------OnScrollListener (end) ---------------*/

	@Override
	public void onScrollChanged() {
		// TODO Auto-generated method stub
		try {
			mMethods[0].invoke(mObject);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException--onScrollChanged", e.getMessage()+"");
		}
	}

	@Override
	public void onTabChanged(String tabId) {
		// TODO Auto-generated method stub
		try {
			mMethods[0].invoke(mObject, tabId);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException--onTabChanged"+tabId, e.getMessage()+"");
		}
	}

	@Override
	public boolean onPreferenceClick(Preference preference) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean onPreferenceChange(Preference preference, Object newValue) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		// TODO Auto-generated method stub
		try {
			mMethods[0].invoke(mObject, group, checkedId);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException--onCheckedChanged--group"+group.getId(), e.getMessage()+"");
		}
	}

	@Override
	public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
		// TODO Auto-generated method stub
		try {
			mMethods[0].invoke(mObject, buttonView, isChecked);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException--onCheckedChanged--CommpoundButton"+buttonView.getId(), e.getMessage()+"");
		}
	}

	@Override
    public void onGroupCollapse(int i) {
        try {
            mMethods[0].invoke(mObject, i);
        } catch (Throwable e) {
        	LogUtil.e("ViewListenerException---onGroupCollapse---"+i, e.getMessage()+"");
        }
    }

    @Override
    public void onGroupExpand(int i) {
        try {
            mMethods[0].invoke(mObject, i);
        } catch (Throwable e) {
        	LogUtil.e("ViewListenerException---onGroupExpand---"+i, e.getMessage()+"");
        }
    }

	@Override
	public boolean onGroupClick(ExpandableListView parent, View v,
			int groupPosition, long id) {
		// TODO Auto-generated method stub
		try {
			return (Boolean) mMethods[0].invoke(mObject, parent, v, groupPosition, id);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException---onGroupClick---"+parent.getId(), e.getMessage()+"");
		}
		return false;
	}

	@Override
	public boolean onChildClick(ExpandableListView parent, View v,
			int groupPosition, int childPosition, long id) {
		// TODO Auto-generated method stub
		try {
			return (Boolean) mMethods[0].invoke(mObject, parent, v, groupPosition, childPosition, id);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException---onChildClick---"+parent.getId(), e.getMessage()+"");
		}
		return false;
	}

	@Override
	public boolean onItemLongClick(AdapterView<?> arg0, View arg1, int arg2,
			long arg3) {
		// TODO Auto-generated method stub
		try {
			return (Boolean) mMethods[0].invoke(mObject, arg0, arg1, arg2, arg3);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException---onItemLongClick---"+arg0.getId(), e.getMessage()+"");
		}
		return false;
	}

	@Override
	public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
		// TODO Auto-generated method stub
		try {
			mMethods[0].invoke(mObject, arg0, arg1, arg2, arg3);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException---onItemClick---"+arg0.getId(), e.getMessage()+"");
		}
	}

	@SuppressLint("ClickableViewAccessibility")
	@Override
	public boolean onTouch(View v, MotionEvent event) {
		// TODO Auto-generated method stub
		try {
			return (Boolean) mMethods[0].invoke(mObject, v, event);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException---onTouch---"+v.getId(), e.getMessage()+"");
		}
		return false;
	}

	@Override
	public boolean onKey(View v, int keyCode, KeyEvent event) {
		// TODO Auto-generated method stub
		try {
			return (Boolean) mMethods[0].invoke(mObject, v, keyCode, event);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException---onKey---"+v.getId(), e.getMessage()+"");
		}
		return false;
	}

	@Override
	public void onFocusChange(View v, boolean hasFocus) {
		// TODO Auto-generated method stub
		try {
			mMethods[0].invoke(mObject, v, hasFocus);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException---onFocusChange---"+v.getId(), e.getMessage()+"");
		}
	}

	@Override
	public boolean onLongClick(View v) {
		// TODO Auto-generated method stub
		try {
			return (Boolean) mMethods[0].invoke(mObject, v);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException---onLongClick---"+v.getId(), e.getMessage()+"");
		}
		return false;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		try {
			mMethods[0].invoke(mObject, v);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException---onClick---"+v.getId(), e.getMessage()+"");
		}
	}
	

	/* --------------- 自定义可刷新listview(start)-----------------*/
	@Override
	public void onPullRefresh(PullListView view) {
		// TODO Auto-generated method stub
		try {
			mMethods[0].invoke(mObject, view);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException---onClick---"+view.getId(), e.getMessage()+"");
		}
	}

	@Override
	public void onPullLoadMore(PullListView view) {
		// TODO Auto-generated method stub
		if (mMethods.length < 2 || mMethods[1] == null) {
			LogUtil.e("ViewListenerException--onLoadMore", "no onLoadMore");
			return;
		}
		try {
			mMethods[1].invoke(mObject, view);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException--onScrollStateChanged"+view.getId(), e.getMessage()+"");
		}
	}
	
	/* ------------- CalendarViewListener(start) ---------------- */
	
	@Override
	public void onPullExpandedRefresh(PullExpandedListView pullExpandedListView) {
		// TODO Auto-generated method stub
		try {
			mMethods[0].invoke(mObject, pullExpandedListView);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException---onPullExpandedRefresh---"+pullExpandedListView.getId(), e.getMessage()+"");
		}
	}
	
	@Override
	public void onPullExpandedLoadMore(PullExpandedListView pullExpandedListView) {
		// TODO Auto-generated method stub
		if (mMethods.length < 2 || mMethods[1] == null) {
			LogUtil.e("ViewListenerException--onExpandedLoadMore", "no onLoadMore");
			return;
		}
		try {
			mMethods[1].invoke(mObject, pullExpandedListView);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException--onPullExpandedLoadMore"+pullExpandedListView.getId(), e.getMessage()+"");
		}
	}
	
	@Override
	public void onCalendarSelected(CalendarView view, Date date) {
		// TODO Auto-generated method stub
		try {
			mMethods[0].invoke(mObject, view, date);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException---onCalendarSelected", "CalendarView---"+view.getId());
		}	
	}
	
	@Override
	public void onCalendarItemClick(CalendarView view, Date date) {
		// TODO Auto-generated method stub
		if (mMethods.length < 2 || mMethods[1] == null) {
			LogUtil.e("ViewListenerException--onCalendarItemClick", "no onCalendarItemClick");
			return;
		}
		try {
			mMethods[1].invoke(mObject, view, date);
		} catch (Exception e) {
			// TODO: handle exception
			LogUtil.e("ViewListenerException--onCalendarItemClick"+view.getId(), e.getMessage()+"");
		}	
	}
	/* ------------- CalendarViewListener(end) ---------------- */
	
	public static void setAllListener(Object obj, ResFinder<?> finder, ConcurrentKKVMap<Integer, String, Method> kkv) {
		for (int info : kkv.getFirstKeys()) {
			Map<String, Method> map = kkv.get(info);
			for (String name : map.keySet()) {
				try {
					View view = finder.findViewById(info);
					if (view == null) break;
					String setListenerName = "";
					Class<?> cls = null;
					ViewListener tempListener = new ViewListener(obj, map.get(name));
					if (name.equalsIgnoreCase(ViewListenerType.OnClick.name())) {
						setListenerName = "setOnClickListener";
						cls = OnClickListener.class;
					} else if (name.equalsIgnoreCase(ViewListenerType.OnLongClick.name())) {
						setListenerName = "setOnLongClickListener";
						cls = OnLongClickListener.class;
					} else if (name.equalsIgnoreCase(ViewListenerType.OnFocusChange.name())) {
						setListenerName = "setOnFocusChangeListener";
						cls = OnFocusChangeListener.class;
					} else if (name.equalsIgnoreCase(ViewListenerType.OnKey.name())) {
						setListenerName = "setOnKeyListener";
						cls = OnKeyListener.class;
					} else if (name.equalsIgnoreCase(ViewListenerType.OnTouch.name())) {
						setListenerName = "setOnTouchListener";
						cls = OnTouchListener.class;
					} else if (name.equalsIgnoreCase(ViewListenerType.OnItemClick.name())) {
						setListenerName = "setOnItemClickListener";
						cls = OnItemClickListener.class;
					} else if (name.equalsIgnoreCase(ViewListenerType.OnItemLongClick.name())) {
						setListenerName = "setOnItemLongClickListener";
						cls = OnItemLongClickListener.class;
					} else if (name.equalsIgnoreCase(ViewListenerType.OnChildClick.name())) {
						setListenerName = "setOnChildClickListener";
						cls = OnChildClickListener.class;
					} else if (name.equalsIgnoreCase(ViewListenerType.OnGroupClick.name())) {
						setListenerName = "setOnGroupClickListener";
						cls = OnGroupClickListener.class;
					} else if (name.equalsIgnoreCase(ViewListenerType.OnGroupCollapse.name())) {
						setListenerName = "setOnGroupCollapseListener";
						cls = OnGroupCollapseListener.class;
					} else if (name.equalsIgnoreCase(ViewListenerType.OnGroupExpand.name())) {
						setListenerName = "setOnGroupExpandListener";
						cls = OnGroupExpandListener.class;
					} else if (name.equalsIgnoreCase(ViewListenerType.OnCheckedChange.name())) {
						setListenerName = "setOnCheckedChangeListener";
						if (view instanceof RadioGroup) cls = RadioGroup.OnCheckedChangeListener.class;
						else if (view instanceof CompoundButton) cls = CompoundButton.OnCheckedChangeListener.class;
					} else if (name.equalsIgnoreCase(ViewListenerType.OnTabChange.name())) {
						setListenerName = "setOnTabChangedListener";
						cls = OnTabChangeListener.class;
					} else if (name.equalsIgnoreCase(ViewListenerType.OnScrollChanged.name())) {
						setListenerName = "addOnScrollChangedListener";
						cls = OnScrollChangedListener.class;
					} else if (name.equalsIgnoreCase(ViewListenerType.OnScrollStateChanged.name())) {
						Method method0 = null; Method method1 = null;
						Map<String, Method> map2 = kkv.get(info);
						for (String tempName : map2.keySet()) {
							if (tempName.equalsIgnoreCase(ViewListenerType.OnScrollStateChanged.name())) {
								method0 = map2.get(tempName);
							} else if (tempName.equalsIgnoreCase(ViewListenerType.OnScroll.name())) {
								method1 = map2.get(tempName);
							}
						}
						setListenerName = "setOnScrollListener";
						tempListener = new ViewListener(obj, method0, method1);
						cls = OnScrollListener.class;
					} else if (name.equalsIgnoreCase(ViewListenerType.OnItemSelected.name())) {
						Method method0 = null; Method method1 = null;
						Map<String, Method> map2 = kkv.get(info);
						for (String tempName : map2.keySet()) {
							if (tempName.equalsIgnoreCase(ViewListenerType.OnItemSelected.name())) {
								method0 = map2.get(tempName);
							} else if (tempName.equalsIgnoreCase(ViewListenerType.OnNothingSelected.name())) {
								method1 = map2.get(tempName);
							}
						}
						setListenerName = "setOnItemSelectedListener";
						tempListener = new ViewListener(obj, method0, method1);
						cls = OnItemSelectedListener.class;
					} else if (name.equalsIgnoreCase(ViewListenerType.OnProgressChanged.name())) {
						Method method0 = null; Method method1 = null; Method method2 = null;
						Map<String, Method> map2 = kkv.get(info);
						for (String tempName : map2.keySet()) {
							if (tempName.equalsIgnoreCase(ViewListenerType.OnProgressChanged.name())) {
								method0 = map2.get(tempName);
							} else if (tempName.equalsIgnoreCase(ViewListenerType.OnStartTrackingTouch.name())) {
								method1 = map2.get(tempName);
							} else if (tempName.equalsIgnoreCase(ViewListenerType.OnStopTrackingTouch.name())) {
								method2 = map2.get(tempName);
							}
						}
						setListenerName = "setOnSeekBarChangeListener";
						tempListener = new ViewListener(obj, method0, method1, method2);
						cls = OnSeekBarChangeListener.class;
					} else if (name.equalsIgnoreCase(ViewListenerType.onPullRefresh.name())){
						Method method0 = null, method1 = null;
						Map<String, Method> map2 = kkv.get(info);
						for (String tempName : map2.keySet()) {
							if (tempName.equalsIgnoreCase(ViewListenerType.onPullRefresh.name())) {
								method0 = map2.get(tempName);
							} else if (tempName.equalsIgnoreCase(ViewListenerType.onPullLoadMore.name())) {
								method1 = map2.get(tempName);
							}
						}
						setListenerName = "setOnPullListViewListener";
						tempListener = new ViewListener(obj, method0, method1);
						cls = OnPullListViewListener.class;
					} else if (name.equalsIgnoreCase(ViewListenerType.onPullExpandedRefresh.name())){
						Method method0 = null, method1 = null;
						Map<String, Method> map2 = kkv.get(info);
						for (String tempName : map2.keySet()) {
							if (tempName.equalsIgnoreCase(ViewListenerType.onPullExpandedRefresh.name())) {
								method0 = map2.get(tempName);
							} else if (tempName.equalsIgnoreCase(ViewListenerType.onPullExpandedLoadMore.name())) {
								method1 = map2.get(tempName);
							}
						}
						setListenerName = "setOnPullExpandedListViewListener";
						tempListener = new ViewListener(obj, method0, method1);
						cls = OnPullListViewListener.class;
					} else if (name.equalsIgnoreCase(ViewListenerType.onCalendarSelected.name())) {
						Method method0 = null, method1 = null;
						Map<String, Method> map2 = kkv.get(info);
						for (String tempName : map2.keySet()) {
							if (tempName.equalsIgnoreCase(ViewListenerType.onCalendarSelected.name())) {
								method0 = map2.get(tempName);
							} else if (tempName.equalsIgnoreCase(ViewListenerType.onCalendarItemClick.name())) {
								method1 = map2.get(tempName);
							}
						}
						tempListener = new ViewListener(obj, method0, method1);
						setListenerName = "setOnCalendarViewListener";
						cls = OnCalendarViewListener.class;
					}
					setEventListener(view, setListenerName, cls, tempListener);
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		}
	}
	
	private static void setEventListener(Object view, String setEventListenerMethod, Class<?> eventListenerType,
			ViewListener listener) {
		try {
			if (view == null || setEventListenerMethod == null || eventListenerType == null || listener == null) return;
			Method setMethod = view.getClass().getMethod(setEventListenerMethod, eventListenerType);
			if (setMethod != null) {
				setMethod.invoke(view, listener);
			}
		} catch (Throwable e) {
			LogUtil.e("ViewListenerException--setEventListener"+setEventListenerMethod, e.getMessage());
		}
	}

}

package com.example.PCenter.fragments;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.plus.RequestManager;
import com.android.volley.plus.VolleyErrorHelper;
import com.android.volley.toolbox.ImageLoader;
import com.common.tools.L;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.Toast;

public class BaseFragment extends Fragment{
	protected String TAG;
	protected Activity mActivity;
	
	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		Log.d("BaseFragment", "onViewCreated");
		TAG = getClass().getSimpleName();
		mActivity = getActivity();
	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		Log.d("BaseFragment", "onActivityCreated");
	}

	@Override
	public void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		Log.d("BaseFragment", "onStart");
	}

	@Override
	public void onResume() {
		// TODO Auto-generated method stub
		super.onResume();
		Log.d("BaseFragment", "onResume");
	}

	@Override
	public void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		Log.d("BaseFragment", "onPause");
	}

	@Override
	public void onStop() {
		// TODO Auto-generated method stub
		super.onStop();
		RequestManager.cancelAll(this);
		Log.d("BaseFragment", "onStop");
	}
    public void onDestroyView(){
    	super.onDestroyView();
    	Log.d("BaseFragment", "onDestroyView");
    }
    protected void executeRequest(Request<?> request) {
		RequestManager.addRequest(request, this);
	}
    protected Response.ErrorListener errorListener() {
		return new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				L.d(TAG, error.getMessage());
				Toast.makeText(mActivity, VolleyErrorHelper.getErrorType(error), Toast.LENGTH_LONG).show();
			}
		};
	}
    protected static ImageLoader.ImageListener getImageListener(ImageView imageView) {
		return RequestManager.getImageListener(imageView);
	}
    
}

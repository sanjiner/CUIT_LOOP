package com.common.tools;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpResponse;
import org.apache.http.HttpStatus;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.conn.ClientConnectionManager;
import org.apache.http.conn.params.ConnManagerParams;
import org.apache.http.conn.scheme.PlainSocketFactory;
import org.apache.http.conn.scheme.Scheme;
import org.apache.http.conn.scheme.SchemeRegistry;
import org.apache.http.conn.ssl.SSLSocketFactory;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.conn.tsccm.ThreadSafeClientConnManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.params.BasicHttpParams;
import org.apache.http.params.HttpConnectionParams;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.example.PCenter.Constant;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class MyHttpClient {
	private static HttpClient client = null;

	private MyHttpClient() {
	}
	
	public static HttpClient getclient() {// 单例模式

		if (client == null) {
			BasicHttpParams httpParams = new BasicHttpParams();
			ConnManagerParams.setTimeout(httpParams,10*1000);
			HttpConnectionParams.setConnectionTimeout(httpParams,
					Constant.REQUEST_TIMEOUT); // 请求超时
			HttpConnectionParams.setSoTimeout(httpParams, Constant.SO_TIMEOUT); // 连接超时
			SchemeRegistry schReg =new SchemeRegistry();
			 // 设置我们的HttpClient支持HTTP和HTTPS两种模式
			schReg.register(new Scheme("http", PlainSocketFactory
                    .getSocketFactory(), 80));
            schReg.register(new Scheme("https", SSLSocketFactory
                    .getSocketFactory(), 443));
            // 使用线程安全的连接管理来创建HttpClient
			ClientConnectionManager conMgr =new ThreadSafeClientConnManager(httpParams, schReg);
			client = new DefaultHttpClient(conMgr,httpParams);
			client.getParams().setParameter("http.socket.timeout", new Integer(30000));
		}
		else 
		{
			System.out.println("测试");
		}
		return client;
	}

	/**
	 * 通过URL获取返回�?
	 * 
	 * @param method
	 *            要调用的接口
	 * @param params
	 *            参数数组
	 * @param context
	 *            调用此函数的Activity
	 * @return 返回json字符�?
	 * @author geno
	 *//*
	public static JSONObject getJson(Context context, String method,
			List<NameValuePair> params) {

		JSONObject jsonObject = new JSONObject();
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {// 网络连接正常
			try {
				String url = Constant.baseURL + method;// 构建url
				HttpPost post = new HttpPost(url);
				post.setEntity(new UrlEncodedFormEntity(params));
				HttpResponse response = getclient().execute(post);
				
				
				if (response.getStatusLine().getStatusCode() == 200) {
					String result = EntityUtils.toString(response.getEntity(), "utf-8");
					jsonObject = new JSONObject(result);
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				try {
					jsonObject.put("status", "0");
					jsonObject.put("message", "网络连接超时，请稍后重试");
				} catch (JSONException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			} catch (JSONException e) {
				e.printStackTrace();
			} 
		} else {// 网络连接错误
			try {
				jsonObject.put("status", "0");
				jsonObject.put("message", "网络连接错误，请先检查网络连接是否打开");
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		return jsonObject;
	
	}

	
	
	// 另一种获取json的方法
	public static String get(String url) throws IOException {
		HttpGet httpRequest = new HttpGet(url);
		// 请求HttpClient，取得HttpResponse
		HttpResponse httpResponse = client.execute(httpRequest);
		// 请求成功
		if (httpResponse.getStatusLine().getStatusCode() == HttpStatus.SC_OK) {
			// 取得返回的字符串
			return EntityUtils.toString(httpResponse.getEntity());
		} else {
			return "{'status':'-1','message':'网络不给力，请稍后重试！'}";
		}
	}

	public static Bitmap getBitmap(Context context, String pic) {

		Bitmap bmp = null;
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();

		if (info != null && info.isConnected()) {// 网络连接正常
			try {
				String url = Constant.baseURL + pic;// 构建url
				HttpPost post = new HttpPost(url);
				HttpResponse response = getclient().execute(post);
				if (response.getStatusLine().getStatusCode() == 200) {
					byte[] data = EntityUtils.toByteArray(response.getEntity());// 获取返回的byte数组
					bmp = BitmapFactory.decodeByteArray(data, 0, data.length);// 构�?Bitmap
				}
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			} catch (ClientProtocolException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return bmp;
	}

	
	public static HttpClient httpClient = new DefaultHttpClient();

	// post方法访问服务器，返回json字符串
	public static String getRequest(String url) {
		String result = null;
		HttpGet httpGet = new HttpGet(url);

		try {
			HttpResponse httpResponse = httpClient.execute(httpGet);

			if (httpResponse.getStatusLine().getStatusCode() == 200) {
				result = EntityUtils
						.toString(httpResponse.getEntity(), "utf-8");
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return result;

	}

	// 字符串转成集合数据
	public static void resultString2List(List<Map<String, Object>> list,
			String str, String title) {
		try {
			JSONObject jsonObject = new JSONObject(str);
			JSONArray jsonArray = jsonObject.getJSONArray(title);

			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject2 = jsonArray.getJSONObject(i);
				Map<String, Object> map = new HashMap<String, Object>();
				Iterator<String> iterator = jsonObject2.keys();
				while (iterator.hasNext()) {
					String key = iterator.next();
					Object value = jsonObject2.get(key);
					map.put(key, value);
				}

				list.add(map);

			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	// post方法访问服务器，返回集合数据
	public static List<Map<String, Object>> getRequest2List(String url,
			String title) {

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		resultString2List(list, url, title);

		return list;

	}

	// get方法访问服务器，返回json字符串
	public static String postRequest(String url, Map<String, String> rawParams)
			throws Exception {

		HttpPost post = new HttpPost(url);

		List<NameValuePair> params = new ArrayList<NameValuePair>();

		for (String key : rawParams.keySet()) {
			params.add(new BasicNameValuePair(key, rawParams.get(key)));

		}
		post.setEntity(new UrlEncodedFormEntity(params, "UTF-8"));

		HttpResponse httpResponse = client.execute(post);

		if (httpResponse.getStatusLine().getStatusCode() == 200) {
			String result = EntityUtils.toString(httpResponse.getEntity(),"utf-8");

			return result;
		}

		return null;
	}*/
	
}

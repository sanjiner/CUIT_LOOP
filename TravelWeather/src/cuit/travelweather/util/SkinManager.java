package cuit.travelweather.util;



import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources.NotFoundException;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import cuit.travelweather.activity.JpushApplication;

public class SkinManager {
	static Context deafultContext =null;
	static Context currentContext =null;
	static LayoutInflater	currentInflater=null;
	static LayoutInflater	defalutInflater=null;
	public static void setSkinContext(Context context,PackageInfo pageInfo){
		String packageName="";
		try{
		   packageName = pageInfo.packageName;
		}catch(Exception e){
			e.printStackTrace();
		}
		setSkinContext(context,packageName);
	}
	
	
	public static String getCurrentSkinPackageName(){
		if(currentContext != null){
			return currentContext.getPackageName();
		}
		return "";
	}
	public static void setSkinContext(Context context,String packageName){
		
		deafultContext =context;
		try {
			currentContext = context.createPackageContext(
					packageName,
					Context.CONTEXT_INCLUDE_CODE|Context.CONTEXT_IGNORE_SECURITY);
			
		} catch(Exception e) {
			e.printStackTrace();
			currentContext =context;
		}
		try{
			currentInflater = (LayoutInflater) currentContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}catch(Exception e){
			e.printStackTrace();
		}
		try{
			defalutInflater = (LayoutInflater) deafultContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		}catch(Exception e){
			e.printStackTrace();
		} 
	}
	
	
	public static Context getSkinContext(Context context){
		  if(currentContext == null){
			  return context;
		  }else{
		      return currentContext;
		  }
	}
	
	
	public static  View createViewFromResource(Context context,String layoutName,ViewGroup root, boolean  attachToRoot) {
		View resultView =null;
		 
		try{
			Context ct =getSkinContext(context);
			int resid = ct.getResources().getIdentifier(layoutName, "layout", ct.getPackageName());
			if(resid != 0){
			    resultView= currentInflater.inflate(resid, root, attachToRoot);
			}else{
				resid = context.getResources().getIdentifier(layoutName, "layout",context.getPackageName());
				resultView= defalutInflater.inflate(resid, root, attachToRoot);
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return resultView;
	}
	
	
	public static int getResourceId(Context context,String layoutName,ViewGroup root, boolean  attachToRoot) {
		int return_id = 0 ;
		try{
			Context ct =getSkinContext(context);
			int resid = ct.getResources().getIdentifier(layoutName, "layout", ct.getPackageName());
			if(resid != 0){
				return_id = resid;
			}else{
				resid = context.getResources().getIdentifier(layoutName, "layout",context.getPackageName());
				return_id = resid;
			}
			
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return return_id;
		
	}
	
	 
	public static  Drawable getDrawable(Context context,String drawableName) throws NotFoundException {
		Drawable resultDrawable =null;
		Context ct =getSkinContext(context);
		try{
			 int resid=getIdentifier2(ct,drawableName.trim(),"drawable");
			 if(resid == 0){				 
				 resid=getIdentifier2(context,drawableName.trim(),"drawable");
				 resultDrawable=context.getResources().getDrawable(resid);
			 }else{
			   resultDrawable=ct.getResources().getDrawable(resid);
			 }
		}catch(Exception e){
			e.printStackTrace();
			
		}
		return resultDrawable;
	}
	
	public static int getIdentifier2(Context context,String name,String defType) throws NotFoundException{
		int result=0;
		try{
			result = context.getResources().getIdentifier(name, defType, context.getPackageName());
		}catch(Exception e){
			e.printStackTrace();
			 
		}
		return result;
	}
	
	public static int getIdentifier(Context context,String name,String defType) throws NotFoundException{
		int result=0;
		try{
			Context ct =getSkinContext(context);
			result = ct.getResources().getIdentifier(name, defType, ct.getPackageName());
			 if(result == 0){
				 result = context.getResources().getIdentifier(name, defType, context.getPackageName()); 
			 }
		}catch(Exception e){
			e.printStackTrace();
			result = context.getResources().getIdentifier(name, defType, context.getPackageName());
		}
		return result;
	}
	
	
	
	public static Drawable createDrawableByPath(Context context,String imagePath){
		try {
			
			if(StringUtils.isNull(imagePath))return null;

		    Bitmap bm = BitmapFactory.decodeFile(imagePath);
		    
		    bm.setDensity(context.getResources().getDisplayMetrics().DENSITY_HIGH);

		    Drawable dw = new BitmapDrawable(context.getResources(), bm);

		    return dw;
		
		} catch (Exception e) { 
			e.printStackTrace();
		}catch (OutOfMemoryError ex) {
	      	System.gc();
	          ex.printStackTrace(); 
	    } 	
		return null;
	}
	
	public static boolean isInstallPackageName(Context context,String packageName){
		PackageManager pm = context.getPackageManager();
		try {
			pm.getPackageGids(packageName);
			return true;
		} catch (NameNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	public  static Document getDocument(InputStream is) throws Exception {
		DocumentBuilderFactory dbfactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbfactory.newDocumentBuilder();
		return db.parse(is);
	}
	
	
	public static int getColorByName(String name){
		int res = -1;
		 try {			
				
			 String xmlFileName ="skin_color.xml";
			 Document doc  =  getDocumentByFile(xmlFileName);
			 if(doc != null){
				 String value = getColorByName(doc,name);
				 res=Color.parseColor(value);
			 }			 
		 
		} catch (Exception e) {
			e.printStackTrace();
		}
		return res;
		
	} 
	
	private synchronized static Document getDocumentByFile(String xmlFileName) throws Exception{
		 
		String path = Constant.SKIN_DIR+"skin_color.xml";
		File file = new File(path);
		if(file.exists())
		{
			
			InputStream inputStream = null;
			try {				
				inputStream = new FileInputStream(file);
				return getDocument(inputStream);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return getDocument(JpushApplication.getApplication().getAssets().open(xmlFileName));
		 
	}

	private static String getColorByName(Document doc,String name){
		 NodeList nodeList= doc.getElementsByTagName(name);
		 String res =null;
		 if(nodeList != null){
			 int len = nodeList.getLength();
			 if(len > 0){
				 Element el = (Element)nodeList.item(0);
				 res = el.getAttribute("value");
				
			 }else{
				 
			 }
		 }
		 return res;
	}
 
}

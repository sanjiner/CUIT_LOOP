package cuit.travelweather.util;
import java.io.File;

import android.content.Context;
import android.content.SharedPreferences;

public class Constant {
	public static final int REQUEST_TIMEOUT = 10 * 1000; // 请求超时5秒
	public static final int SO_TIMEOUT = 8 * 1000; // 连接超时5秒
	////
	public static int flag = 0;
	public static int keysize =0;
	public static String tel = "";
	public static int raod = 0;
	/////
	public static String keyvalue = "2";
	public static String temperature_night0 = "3";
	public static String temperature_day0= "5";///
	public static String temperature_night1 = "2";
	public static String temperature_day1= "4";///
	public static String temperature_night2 = "1";
	public static String temperature_day2= "6";///
	public static int Falgqing ;
	//	public static final int Falg2 = 2;
	public static int Falg = 1;
	//	public static String baseURL = "http://222.18.158.197:8080/travelInterface";
	public static String baseURL = "http://222.209.224.81:8080/travelInterface";
	public static String pictureBaseUrl="http://222.18.158.197:8080/scnjwTravel";
	public static final String regist = "/user/register";
	public static final String login = "/user/login";
	public static final String logout = "/user/loginOut";
	public static final String getLines = "/routeLines/getRouteLines";
	public static final String getForecast = "/city/cityWeather";
	public static final String findPwd = "/user/findPW";
	public static final String reSetPwd = "/user/reSetPwd";
	public static final String findAdress = "/city/findAdress";
	public static final String addCity = "/city/addCity";
	public static final String getCity = "/city/getCity";
	public static final String delCity = "/city/delCity";
	public static final String getRoutieWeather = "/weather/weather";
	public static final String getWeatherPicture = "/weather/getWeatherPicture";
	public static final String getWeatherIndex = "/weather/weatherIndex";
	public static final String getWeatherInde = "/weather/weatherInde";
	public static final String guessWeather = "/weather/guessWeather";
	public static final String getWeather = "/weather/getWeather";
	public static final String getsceniclist = "/attractions/GetScenicList";
	public static final String getWeathertrend = "/city/cityWeather";
	public static final String getscenicSingle = "/attractions/GetScenicSingle";
	public static final String routeLinesDefine = "/routeLines/routeLinesDefine";
	public static final String routeLinesDelete = "/routeLines/routeLinesDelete";
	public static final String weatherSK = "/weather/weatherSK";
	public static final String weatherSKnew= "/weather/weatherSKnew";
	public static final String getFoodList = "/attractions/GetFoodList";
	public static final String getHotelList = "/attractions/GetHotelList";
	public static final String getShopsList = "/attractions/GetShopsList";
	public static final String getShopsSingle = "/attractions/GetShopsSingle";

	public static final String getCoinrating = "/user/coinList";
	public static final String getHotelSingle = "/attractions/GetHotelSingle";
	public static final String getFoodSingle = "/attractions/GetFoodSingle";

	public static final String getCommentList = "/comment/GetCommentList";
	public static final String addComment = "/comment/AddComment";
	public static final String addLikesOrBads = "/comment/picture";
	public static final String uploadPic="/picture/addPicture";
	public static final String getFoodpic = "http://222.209.224.81:8080/travelInterface/attractions/GetFoodSingle?foodID=";
	public static final String condition ="/routeLines/condition";
	public static final String coinList ="/user/coinList";
	public static final String addPicture ="/picture/addPicture";
	public static final String addScenic ="/attractions/addScenic";
	public static final String addFood ="/attractions/addFood";
	public static final String addHotel ="/attractions/addHotel";
	public static final String addShop ="/attractions/addShop";

	public static String city = "N/A";


	private Constant() {
	}

	// 换肤相关
	public static String SKIN_DIR = null;

	public static void initSkinDir(Context context) {

		SKIN_DIR = context.getFilesDir().getPath() + File.separator + "skin"+ File.separator;
		File file = new File(SKIN_DIR);		
		if (!file.exists()) {
			file.mkdirs();
		}

	}


	public static void setAppSkin(Context context, String packAgeName) {
		SharedPreferences uiState = context.getSharedPreferences("changeskin",
				Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = uiState.edit();
		editor.putString("currnetskin",packAgeName);
		editor.commit();

	}


	public static String getAppSkin(Context context) {
		SharedPreferences uiState = context.getSharedPreferences("changeskin",
				Context.MODE_WORLD_WRITEABLE);
		return uiState.getString("currnetskin", context.getPackageName());

	}

	public static boolean isFromZip(Context context){
		SharedPreferences uiState = context.getSharedPreferences("changeskin",
				Context.MODE_WORLD_WRITEABLE);
		return uiState.getBoolean("fromzip", false);
	}

	public static void setFromZip(Context context,boolean fromzip){
		SharedPreferences uiState = context.getSharedPreferences("changeskin",
				Context.MODE_WORLD_WRITEABLE);
		SharedPreferences.Editor editor = uiState.edit();
		editor.putBoolean("fromzip",fromzip);
		editor.commit();
	}
}

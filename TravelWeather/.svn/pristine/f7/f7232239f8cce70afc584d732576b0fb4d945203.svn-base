package cuit.travelweather.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.HttpResponse;

@SuppressWarnings("unused")
public class HttpUtils {
        private static String PATH = "http://222.209.224.81:8080/travelInterface/attractions/GetScenicListFromJH?seller=&title=&cityId=&price=&grade=&sort=price_desc&skip=";
//    private static String PATH = "http://172.27.189.2:8080/logserv/logservlet";
//    172.27.189.2
//        private static String PATH = "http://192.168.254.1:8080/logserv/logservlet";
   
        private static URL url; 
        
        public HttpUtils() {
            
        }
        
        //发送POST消息
        public static int sendPostMessage(Map<String, String> params, String encode)
        {
        	//请求参数
            StringBuffer reqParam = new StringBuffer();
            
            if ( null != params && !params.isEmpty() )
            {
                for ( Map.Entry<String, String> entry : params.entrySet() )
                {
                	reqParam.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue())).append("&");
                }
                ///< 删除多余的&
                reqParam.deleteCharAt(reqParam.length() - 1);
            }
            reqParam.toString();   
            try 
            {
                url = new URL(PATH);
                if ( null != url )
                {
                    HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                    if ( null == urlConnection )
                    {
                        return -1;
                    }
                    urlConnection.setConnectTimeout(3000);
                    urlConnection.setRequestMethod("POST");    ///< 设置请求方式
                    urlConnection.setDoInput(true);            ///< 表示从服务器获取数据
                    urlConnection.setDoOutput(true);           ///< 表示向服务器发送数据

                    byte[] data = reqParam.toString().getBytes();
                    //设置请求体的是文本类型
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    urlConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
                    //获得输出流
                    OutputStream outputStream = urlConnection.getOutputStream();
                    outputStream.write(data);
                    outputStream.close();
                    //获得服务器的响应结果和状态码
                    int responseCode = urlConnection.getResponseCode();
                    if ( 200 == responseCode )
                    {
                        return intResponse(urlConnection.getInputStream(), encode);
                    }                  
                }
            } 
            catch (IOException e) 
            {
                e.printStackTrace();
            }
            
            return -1;
        }
        

        public static String sendGetMessage(Map<String, String> params, String encode)
        {
        	//请求参数
            StringBuffer reqParam = new StringBuffer();
            
            if ( null != params && !params.isEmpty() )
            {
                for ( Map.Entry<String, String> entry : params.entrySet() )
                {
                	reqParam.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue())).append("&");
                }
                //删除多余的&
                reqParam.deleteCharAt(reqParam.length() - 1);
                
            }
            reqParam.toString();   
            try 
            {
            	//组合GET请求的字符串 "http://地址?参数=值&参数2=值2"
            	PATH += "?"; 
            	PATH += reqParam.toString();
                url = new URL(PATH);
                if ( null != url )
                {
                    HttpURLConnection urlConnection = (HttpURLConnection)url.openConnection();
                    if ( null == urlConnection )
                    {
                        return "";
                    }
                    urlConnection.setConnectTimeout(3000);
                    urlConnection.setRequestMethod("GET");     //< 设置请求方式
                    urlConnection.setDoInput(true);            //< 表示从服务器获取数据
                    byte[] data = reqParam.toString().getBytes();
                    int responseCode = urlConnection.getResponseCode();
                    if ( 200 == responseCode)
                    {
                        return stringResponse(urlConnection.getInputStream(), encode);
                    }                    
                }
            } 
            catch ( IOException e )
            {
                e.printStackTrace();
            }
            
            return "";
        }
        
        // 返回的结果是整形
        private static int intResponse(InputStream inputStream, String encode) 
        {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = 0;
            String result = "";
            if ( null != inputStream )
            {
                try 
                {
                    while ((len = inputStream.read(data)) != -1)
                    {
                        outputStream.write(data, 0, len);
                    }
                    result = new String(outputStream.toByteArray(), encode);
                    
                    len = Integer.parseInt(result.substring(0, 1));
                    
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
            return len;
        }
        
        // 返回的结果是字符串
        private static String stringResponse(InputStream inputStream, String encode) 
        {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            byte[] data = new byte[1024];
            int len = 0;
            String result = "";
            if ( null != inputStream )
            {
                try 
                {
                    while ((len = inputStream.read(data)) != -1)
                    {
                        outputStream.write(data, 0, len);
                    }
                    result = new String(outputStream.toByteArray(), encode);
                } 
                catch (IOException e) 
                {
                    e.printStackTrace();
                }
            }
            return result;
        }
}

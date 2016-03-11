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
        
        //����POST��Ϣ
        public static int sendPostMessage(Map<String, String> params, String encode)
        {
        	//�������
            StringBuffer reqParam = new StringBuffer();
            
            if ( null != params && !params.isEmpty() )
            {
                for ( Map.Entry<String, String> entry : params.entrySet() )
                {
                	reqParam.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue())).append("&");
                }
                ///< ɾ�������&
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
                    urlConnection.setRequestMethod("POST");    ///< ��������ʽ
                    urlConnection.setDoInput(true);            ///< ��ʾ�ӷ�������ȡ����
                    urlConnection.setDoOutput(true);           ///< ��ʾ���������������

                    byte[] data = reqParam.toString().getBytes();
                    //��������������ı�����
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    urlConnection.setRequestProperty("Content-Length", String.valueOf(data.length));
                    //��������
                    OutputStream outputStream = urlConnection.getOutputStream();
                    outputStream.write(data);
                    outputStream.close();
                    //��÷���������Ӧ�����״̬��
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
        	//�������
            StringBuffer reqParam = new StringBuffer();
            
            if ( null != params && !params.isEmpty() )
            {
                for ( Map.Entry<String, String> entry : params.entrySet() )
                {
                	reqParam.append(entry.getKey()).append("=").append(URLEncoder.encode(entry.getValue())).append("&");
                }
                //ɾ�������&
                reqParam.deleteCharAt(reqParam.length() - 1);
                
            }
            reqParam.toString();   
            try 
            {
            	//���GET������ַ��� "http://��ַ?����=ֵ&����2=ֵ2"
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
                    urlConnection.setRequestMethod("GET");     //< ��������ʽ
                    urlConnection.setDoInput(true);            //< ��ʾ�ӷ�������ȡ����
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
        
        // ���صĽ��������
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
        
        // ���صĽ�����ַ���
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

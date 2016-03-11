package cc.util.java.http;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.SocketTimeoutException;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.GZIPInputStream;
import java.util.zip.InflaterInputStream;

/**
 * @author wangcccong
 * @version 1.1406
 * create at: Fri, 16 Jun 2014
 * update at: Thur, 17 Sep 2014
 * 修改：获取数据压缩和未压缩情况
 * <br>update at: Fri, 19 Sep. 2014
 *  &nbsp;&nbsp;修改获取数据成功和失败新增返回状态,为后续数据缓存和断点续传做准备
 * <br>update at: Tue, 23 Sep. 2014
 * &nbsp;&nbsp;新增断点续传功能
 */
public class HttpResponse {
	
	private final int statusCode;
    private final Map<String, String> cookies;
    private final Map<String, List<String>> headers;
    private InputStream inputStream;
    private volatile boolean isCancel = false;
    private Exception exception;

    HttpResponse(final int statusCode, Exception exception, final InputStream inputStream, final Map<String, List<String>> headers,
            final Map<String, String> cookies) {
        this.statusCode = statusCode;
        this.exception = exception;
        this.inputStream = inputStream;
        if (headers == null) this.headers = new HashMap<String, List<String>>();
        else this.headers = Collections.unmodifiableMap(headers);
        if (cookies == null) this.cookies = new HashMap<String, String>();
        else this.cookies = Collections.unmodifiableMap(cookies);
    }

    /**
     * Get the content type for this response, or <code>null</code> if unknown.
     */
    public String getContentType() {
        final String contentType = getFirstHeaderValue("Content-Type") == null ? 
        		getFirstHeaderValue("content-type") : getFirstHeaderValue("Content-Type");
        if (contentType == null) {
            return null;
        }
        final int i = contentType.indexOf(';');
        return i == -1 ? contentType : contentType.substring(0, i).trim();
    }

    /**
     * Get the charset for this response, or <code>null</code> if unknown.
     */
    public String getContentCharset() {
    	final String contentType = getFirstHeaderValue("Content-Type") == null ? 
        		getFirstHeaderValue("content-type") : getFirstHeaderValue("Content-Type");
        if (contentType == null) {
            return null;
        }
        final int i = contentType.indexOf('=');
        return i == -1 ? null : contentType.substring(i + 1).trim();
    }
    
    /**
     * 获取返回内容的长度
     * @return
     */
    public int getContentLength() {
    	final String contentLength = getFirstHeaderValue("Content-Length") == null ? 
    			getFirstHeaderValue("content-length") : getFirstHeaderValue("Content-Length");
    	return contentLength == null ? 0 : Integer.parseInt(contentLength);
    }
    
    /**
     * 获取到Content-Range数据总大小
     * @return
     */
    public long getContentRangeSize() {
    	final String range = getFirstHeaderValue("Content-Range") == null ? 
    			getFirstHeaderValue("content-range") : getFirstHeaderValue("Content-Range");
    	return range == null ? getContentLength() : Long.parseLong(range.substring(range.indexOf("/")+1));
    }

    /**
     * 直接将数据读取到StringBuilder中
     * @param buffer
     * @throws IOException
     */
    public void read(StringBuilder buffer) throws IOException {
        String enc = getContentCharset();
        if (enc == null) {
            enc = "utf-8";
        }
        if (inputStream == null) return;
        final InputStreamReader reader = new InputStreamReader(inputStream, enc);
        final char[] inBuf = new char[64];
        for (int charsRead; (charsRead = reader.read(inBuf)) != -1;) {
            buffer.append(inBuf, 0, charsRead);
        }
        reader.close();
        inputStream.close();
    }
    
    /**
     * 带有反馈响应的读取数据
     * @param responseHandler
     */
    public void read(HttpResponseHandler responseHandler) throws IOException {
    	if (exception instanceof SocketTimeoutException)
    		responseHandler.onTimeOut();
    	else if (exception instanceof IOException) responseHandler.onError(statusCode, "请求语法格式或请求方式错误".getBytes());
        if (inputStream == null) return;
        
        //数据总长度
        int contentLength = getContentLength();
        int readLength  = 0;
        int length = 0;
        int available = 0;     //当前从输入流中获取到数据的大小
        while (available == 0) available = inputStream.available();
        byte[] bts = new byte[available];   //用来读取数据
        byte[] resultData = null;           //封装最后的数据
        //如果数据是压缩了的，则使用if中的方式获取数据
        if (inputStream instanceof GZIPInputStream || inputStream instanceof InflaterInputStream) {
        	final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        	while ((length = inputStream.read(bts)) != -1) {
            	if (isCancel) {
            		responseHandler.onCancel();
            		inputStream.close();
            		return;
            	}
            	readLength += length;
            	responseHandler.onProgress(contentLength, readLength);
            	baos.write(bts);
            	while (available == 0) available = inputStream.available();
            	bts = new byte[available];
            }
        	baos.flush();
        	resultData = baos.toByteArray();
        	baos.close();
        } else {  //数据未压缩
        	final byte[] data = new byte[contentLength];
        	while (readLength != contentLength) {
        		if (isCancel) {
            		responseHandler.onCancel();
            		inputStream.close();
            		return;
            	}
        		length = inputStream.read(bts);
        		System.arraycopy(bts, 0, data, readLength, length);
        		readLength += length;
        		responseHandler.onProgress(contentLength, readLength);
        		bts = new byte[inputStream.available()];
        	}
        	resultData = data;
        }
        inputStream.close();
        if (statusCode >= 400) {
        	responseHandler.onError(statusCode, resultData);
        } else {
        	responseHandler.onSuccess(this, resultData);
        }
    }
    
    /**
     * 取消读取数据流
     */
    public void cancel() {
    	isCancel = true;
    }

    /**
     * Get the response status code.
     */
    public int getStatusCode() {
        return this.statusCode;
    }

    /**
     * Get the response headers.
     */
    public Map<String, List<String>> getHeaders() {
        return headers;
    }

    /**
     * Get the first header value, or <code>null</code> if unset.
     */
    public String getFirstHeaderValue(String name) {
        final List<String> values = headers.get(name);
        if (values == null || values.isEmpty()) {
            return null;
        }
        return values.get(0);
    }

    /**
     * Get the response cookies.
     */
    public Map<String, String> getCookies() {
        return cookies;
    }
    
}

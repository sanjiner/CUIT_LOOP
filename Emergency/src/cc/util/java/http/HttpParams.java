package cc.util.java.http;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 一个帮助类，封装提交的数据，支持 文字、文件、及输入流、字节数组操作 
 * @author wangcccong
 * @version 1.1406
 * create at: Fri, 16 Jun 2014
 * update at: Tue, 26 Aug 2014
 */
public final class HttpParams {
	
	private final String PREFIX = "--";
	private final String SUBFIX = "--";
	private final String CR_LF = "\r\n";
	private final String BOUNDARY = UUID.randomUUID().toString();

	private final ConcurrentHashMap<String, String> sEntity;
	private final ConcurrentHashMap<String, File> fEntity;
	private final ConcurrentHashMap<String, InputStream> isEntity;
	private final ConcurrentHashMap<String, byte[]> isByte;
	
	private String contentType; 
	private String BOUNDARY_LINE;
	private String BOUNDARY_END;

	public HttpParams() {
		sEntity = new ConcurrentHashMap<String, String>();
		fEntity = new ConcurrentHashMap<String, File>();
		isEntity = new ConcurrentHashMap<String, InputStream>();
		isByte = new ConcurrentHashMap<String, byte[]>();
		contentType = "application/x-www-form-urlencoded";
		BOUNDARY_LINE = PREFIX + BOUNDARY + CR_LF;
		BOUNDARY_END = PREFIX + BOUNDARY + SUBFIX + CR_LF;
	}
	
	/**
	 * 添加字段Entity
	 * @param key
	 * @param value
	 */
	public void put(String key, String value) {
		sEntity.put(key, value);
	}
	
	/**
	 * 添加文件Entity
	 * @param fileName
	 * @param file
	 */
	public void put(String fileName, File file) {
		if (file == null || !file.exists()) {
			try {
				throw new FileNotFoundException();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		fEntity.put(fileName, file);
	}

	/**
	 * 添加流entity
	 * @param inputStreamName
	 * @param is
	 */
	public void put(String inputStreamName, InputStream is) {
		if (is == null) {
			try {
				throw new IOException();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		isEntity.put(inputStreamName, is);
	}
	
	/**
	 * 添加字节数组
	 * @param inputStreamName
	 * @param is
	 */
	public void put(String byteName, byte[] bts) {
		if (bts == null) {
			throw new NullPointerException("bts 不能为空");
		}
		isByte.put(byteName, bts);
	}
	
	/**
	 * 添加参数 
	 * @param params {@link java.util.Map}
	 */
	public <T> void put(Map<String, T> params) {
		for (Map.Entry<String, T> entry : params.entrySet()) {
			final String name = entry.getKey();
			final T obj = entry.getValue();
			if (name == null || obj == null) continue;
			if (obj instanceof File) {
				fEntity.put(name, (File)obj);
			} else if (obj instanceof InputStream) {
				isEntity.put(name, (InputStream)obj);
			} else if (obj instanceof String){
				sEntity.put(name, (String)obj);
			} else {
				isByte.put(name, (byte[])obj);
			}
		}
	}
	
	/**
	 * 根据上传数据类型得到Content-Type, 默认为:application/x-www-form-urlencoded
	 * @return
	 */
	public String getContentType() {
		int flag = 0;
		if (!sEntity.isEmpty()) flag += 1;
		if (!fEntity.isEmpty()) flag += 1;
		if (!isEntity.isEmpty()) flag += 1;
		if (!isByte.isEmpty()) flag += 1;
		if (flag > 1) {
			contentType = "multipart/from-data;boundary="+BOUNDARY;
		}
		return contentType;
	}

	/**
	 * 获取需要上传的数据 
	 * @param charSet
	 * @return
	 */
	public byte[] toBytes(String charSet) {
		try {
			ByteArrayOutputStream baos = new ByteArrayOutputStream();
			StringBuilder sb = new StringBuilder();
			Iterator<Entry<String, String>> sIterator = sEntity.entrySet().iterator();
			Iterator<Entry<String, File>> fIterator = fEntity.entrySet().iterator();
			Iterator<Entry<String, InputStream>> isIterator = isEntity.entrySet().iterator();
			Iterator<Entry<String, byte[]>> bteIterator = isByte.entrySet().iterator();
			
			/** 文本数据 */
			int index = 0;
			while (sIterator.hasNext()) {
				if (index != 0) sb.append('&');
				Entry<String, String> entry = sIterator.next();
				sb.append(URLEncoder.encode(entry.getKey(), charSet)).append('=')
					.append(URLEncoder.encode(entry.getValue(), charSet));
				index ++;
			}
			if (!fIterator.hasNext() && !isIterator.hasNext()) {
				baos.write(sb.toString().getBytes());
				baos.flush();
				return baos.toByteArray();
			}
			baos.write(BOUNDARY_LINE.getBytes());
			baos.write(("Content-Disposition:from-data;name=\"data\""+CR_LF).getBytes());
			baos.write(("Content-Type:text/plain"+CR_LF).getBytes());
			baos.write(CR_LF.getBytes());
			baos.write((sb + CR_LF).getBytes());
			
			/** 文件 */
			while (fIterator.hasNext()) {
				Entry<String, File> entry = fIterator.next();
				baos.write(BOUNDARY_LINE.getBytes());
				baos.write(("Content-Disposition:from-data;name="+entry.getValue()+";filename="+entry.getValue()+CR_LF).getBytes());
				baos.write(CR_LF.getBytes());
				try {
					FileInputStream fis = new FileInputStream(entry.getValue());
					FileChannel channel = fis.getChannel();
					ByteBuffer buffer = ByteBuffer.allocate(1024);
					while (channel.read(buffer) != -1) {
						buffer.flip();
						while (buffer.hasRemaining()) baos.write(buffer.get());
						buffer.clear();
					}
					fis.close();
					channel.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				baos.write(CR_LF.getBytes());
			}
			
			/** 流 */
			while (isIterator.hasNext()) {
				Entry<String, InputStream> entry = isIterator.next();
				baos.write(BOUNDARY_LINE.getBytes());
				baos.write(("Content-Disposition:from-data;name="+entry.getKey()+";filename="+entry.getKey()+CR_LF).getBytes());
				baos.write(CR_LF.getBytes());
				try {
					InputStream is = entry.getValue();
					byte[] bts = new byte[1024];
					while (is.read(bts) != -1) {
						baos.write(bts);
					}
					is.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
				}
				baos.write(CR_LF.getBytes());
			}	
			baos.write(BOUNDARY_END.getBytes());
			
			/** 字节 */
			while (bteIterator.hasNext()) {
				Entry<String, byte[]> entry = bteIterator.next();
				baos.write(BOUNDARY_LINE.getBytes());
				baos.write(("Content-Disposition:from-data;name="+entry.getKey()+";filename="+entry.getKey()+CR_LF).getBytes());
				baos.write(CR_LF.getBytes());
				baos.write(entry.getValue());
				baos.write(CR_LF.getBytes());
			}	
			baos.write(BOUNDARY_END.getBytes());
			return baos.toByteArray();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println("HttpParams---" + e.getMessage());
			return new byte[0];
		}
	}
}

package com.common.tools;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import android.graphics.Bitmap;
import android.util.Base64;

public class BitmapToBase64 {
	
	public static String bitmaptoString(Bitmap bitmap) {  
        String string = null;  
        ByteArrayOutputStream baos = null;
        try {  
            if (bitmap != null) {  
                baos = new ByteArrayOutputStream();  
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);  
                baos.flush();  
                baos.close();  
                byte[] bitmapBytes = baos.toByteArray();  
                string = Base64.encodeToString(bitmapBytes, Base64.DEFAULT);
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            try {  
                if (baos != null) {  
                    baos.flush();  
                    baos.close();  
                }  
            } catch (IOException e) {  
                e.printStackTrace();  
            }  
        }  
        return string;  
	}  
	
	
	public static void base64ToFile(String base64Data,String targetPath) throws Exception {
		byte[] bytes = Base64.decode(base64Data, Base64.DEFAULT);
		FileOutputStream out = new FileOutputStream(targetPath);
		out.write(bytes);
		out.close();
	}
}

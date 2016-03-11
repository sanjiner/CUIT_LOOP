package cuit.travelweather.util;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class FileUtils{
	/**
	 * ��������Ŀ¼
	 */
	public static void mkDirs(String dirPath) {
		(new File(dirPath)).mkdirs();
	}

	public static boolean directoryExists(String dirPath) {
		File f = new File(dirPath);
		return f.exists() && f.isDirectory();
	}

	public static boolean copyToFile(InputStream inputStream, File destFile, int bufferSize) {
		if (bufferSize < 4096) {
			bufferSize = 4096;
		}
		try {
			if (destFile.exists()) {
				destFile.delete();
			}
			FileOutputStream out = new FileOutputStream(destFile);
			try {
				byte[] buffer = new byte[bufferSize];
				int bytesRead;
				while ((bytesRead = inputStream.read(buffer)) >= 0) {
					out.write(buffer, 0, bytesRead);
				}
			} finally {
				out.flush();
				try {
					out.getFD().sync();
				} catch (IOException e) {
				}
				out.close();
			}
			return true;
		} catch (IOException e) {
			return false;
		}
	}
}

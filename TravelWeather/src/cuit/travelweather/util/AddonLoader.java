package cuit.travelweather.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import dalvik.system.DexClassLoader;

public class AddonLoader {

	public static final String ADDON_WORK_DIR = "addon_only";
	/**
	 * Android����
	 */
	private Context context;
	/**
	 * ��չ���apk������
	 */
	private ApkLoader apkLoader;


	public AddonLoader(Context context) {
		if (null == context) throw new IllegalArgumentException("AddonLoader must be created with a not null context");
		this.context = context;
	}

	/**
	 * ��ȡApk�İ���
	 */
	protected String getPackageName(String apkFilePath) {
		PackageManager pm = context.getPackageManager();
		PackageInfo pi = pm.getPackageArchiveInfo(apkFilePath, 0);
		return pi.packageName;
	}

	/**
	 * ����PackageName��ȡ����Ŀ¼
	 *
	 * @param packageName ����
	 */
	protected String getWorkDir(String packageName) {
		return String.format("%s/%s/w%d", context.getCacheDir().getAbsolutePath(), ADDON_WORK_DIR, packageName.hashCode());
	}

	/**
	 * ����PackageName��ȡ������Apk�ļ���
	 *
	 * @param packageName ����
	 */
	protected String getWorkApkFilename(String packageName) {
		return String.format("w%d.apk", packageName.hashCode());
	}

	/**
	 * ������չ���
	 *
	 * @param src           ԴInputStream
	 * @param indentifyName ��ʶ��
	 * @param parent        ��ClassLoader
	 */
	protected DexClassLoader loadAddon(InputStream src, String indentifyName, ClassLoader parent) throws IOException {
		String workDir = getWorkDir(indentifyName);
		String workFilename = getWorkApkFilename(indentifyName);
		updateWorkApk(src, workDir, workFilename);
		// ���� apk
		apkLoader = new ApkLoader(workDir + File.separatorChar + workFilename, workDir, parent);
		return apkLoader.load();
	}

	/**
	 * ���¹�����apk������ļ���size���ޱ仯��������£�
	 */
	protected void updateWorkApk(InputStream src, String workDir, String workFilename) throws IOException {
		boolean needReplace = true;
		File destFile = new File(workDir + File.separatorChar + workFilename);

		// ���ҽ�Ŀ��apk���ڲ�����Դapk�Ĵ�С���ʱ���Ų���Ҫ�ٴθ���
		if (FileUtils.directoryExists(workDir)) {
			if (destFile.exists()) {
				FileInputStream dest = new FileInputStream(destFile);
				needReplace = dest.available() != src.available();
			}
		} else {
			FileUtils.mkDirs(workDir);
		}

		if (needReplace) {
			FileUtils.copyToFile(src, destFile, 512 * 1024);
		}
	}

	public ApkLoader getApkLoader() {
		return apkLoader;
	}

	/**
	 * ����Assert��Դ�е���չ��� (���� Asset·������hash ȷ������Ŀ¼)
	 */
	public DexClassLoader loadAssetAddon(String addonAssetPath, ClassLoader parent) throws IOException {
		InputStream src = context.getAssets().open(addonAssetPath);
		return loadAddon(src, addonAssetPath, parent);
	}
}

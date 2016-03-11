package cuit.travelweather.util;
import java.io.File;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Environment;
import android.view.View;
import cn.trinea.android.common.entity.CacheObject;
import cn.trinea.android.common.service.CacheFullRemoveType;
import cn.trinea.android.common.service.FileNameRule;
import cn.trinea.android.common.service.impl.FileNameRuleImageUrl;
import cn.trinea.android.common.service.impl.ImageMemoryCache;
import cn.trinea.android.common.service.impl.ImageMemoryCache.OnImageCallbackListener;
import cn.trinea.android.common.service.impl.ImageSDCardCache;
import cn.trinea.android.common.service.impl.PreloadDataCache;
import cn.trinea.android.common.service.impl.RemoveTypeUsedCountSmall;
import cn.trinea.android.common.service.impl.SimpleCache;
import cn.trinea.android.common.util.FileUtils;

public class ImageCache extends ImageMemoryCache {

    private static final long  serialVersionUID     = 1L;
    private ImageSDCardCache   secondaryCache;
    private int                compressSize         = 1;
    private CompressListener   compressListener;

    public static final String DEFAULT_CACHE_FOLDER = new StringBuilder().append(Environment.getExternalStorageDirectory()
                                                                                            .getAbsolutePath())
                                                                         .append(File.separator).append("Trinea")
                                                                         .append(File.separator)
                                                                         .append("AndroidCommon")
                                                                         .append(File.separator).append("ImageCache")
                                                                         .toString();

    public ImageCache(){
        this(ImageMemoryCache.DEFAULT_MAX_SIZE, PreloadDataCache.DEFAULT_THREAD_POOL_SIZE,
             ImageSDCardCache.DEFAULT_MAX_SIZE, PreloadDataCache.DEFAULT_THREAD_POOL_SIZE);
    }

    public ImageCache(int primaryCacheMaxSize){
        this(primaryCacheMaxSize, PreloadDataCache.DEFAULT_THREAD_POOL_SIZE, ImageSDCardCache.DEFAULT_MAX_SIZE,
             PreloadDataCache.DEFAULT_THREAD_POOL_SIZE);
    }

    public ImageCache(int primaryCacheMaxSize, int secondaryCacheMaxSize){
        this(primaryCacheMaxSize, PreloadDataCache.DEFAULT_THREAD_POOL_SIZE, secondaryCacheMaxSize,
             PreloadDataCache.DEFAULT_THREAD_POOL_SIZE);
    }

    public ImageCache(int primaryCacheMaxSize, int primaryCacheThreadPoolSize, int secondaryCacheMaxSize,
                      int secondaryCacheThreadPoolSize){
        super(primaryCacheMaxSize, primaryCacheThreadPoolSize);

        setOnGetDataListener(new OnGetDataListener<String, Bitmap>() {

            private static final long serialVersionUID = 1L;

            @Override
            public CacheObject<Bitmap> onGetData(String key) {
                CacheObject<String> object = secondaryCache.get(key);
                String imagePath = (object == null ? null : object.getData());
                if (FileUtils.isFileExist(imagePath)) {
                    if (compressListener != null) {
                        compressSize = compressListener.getCompressSize(imagePath);
                    }
                    Bitmap bm;
                    if (compressSize > 1) {
                        BitmapFactory.Options option = new BitmapFactory.Options();
                        option.inSampleSize = compressSize;
                        bm = BitmapFactory.decodeFile(imagePath, option);
                    } else {
                        bm = BitmapFactory.decodeFile(imagePath);
                    }
                    return (bm == null ? null : new CacheObject<Bitmap>(bm));
                } else {
                    secondaryCache.remove(key);
                }
                return null;
            }
        });
        super.setCheckNetwork(false);
        setCacheFullRemoveType(new RemoveTypeUsedCountSmall<Bitmap>());

        secondaryCache = new ImageSDCardCache(secondaryCacheMaxSize, secondaryCacheThreadPoolSize);
        secondaryCache.setCacheFolder(DEFAULT_CACHE_FOLDER);
        secondaryCache.setFileNameRule(new FileNameRuleImageUrl().setFileExtension(""));
    }

    public int getCompressSize() {
        return compressSize;
    }

    public void setCompressSize(int compressSize) {
        this.compressSize = compressSize;
    }
    public void setCompressListener(CompressListener compressListener) {
        this.compressListener = compressListener;
    }

    public CompressListener getCompressListener() {
        return compressListener;
    }

    public interface CompressListener {

        public int getCompressSize(String imagePath);
    }

    @Override
    public int getHttpReadTimeOut() {
        return secondaryCache.getHttpReadTimeOut();
    }

    @Override
    public void setHttpReadTimeOut(int readTimeOutMillis) {
        secondaryCache.setHttpReadTimeOut(readTimeOutMillis);
    }

    @Override
    public void clear() {
        super.clear();
        secondaryCache.clear();
    }

    @Override
    public void setForwardCacheNumber(int forwardCacheNumber) {
        super.setForwardCacheNumber(forwardCacheNumber);
        secondaryCache.setForwardCacheNumber(forwardCacheNumber);
    }

    @Override
    public void setBackwardCacheNumber(int backwardCacheNumber) {
        super.setForwardCacheNumber(backwardCacheNumber);
        secondaryCache.setForwardCacheNumber(backwardCacheNumber);
    }

    @Override
    public int getAllowedNetworkTypes() {
        return secondaryCache.getAllowedNetworkTypes();
    }

    @Override
    public void setAllowedNetworkTypes(int allowedNetworkTypes) {
        secondaryCache.setAllowedNetworkTypes(allowedNetworkTypes);
    }

    @Override
    public boolean isCheckNetwork() {
        return secondaryCache.isCheckNetwork();
    }

    @Override
    public void setCheckNetwork(boolean isCheckNetwork) {
        secondaryCache.setCheckNetwork(isCheckNetwork);
    }

    @Override
    public boolean checkIsNetworkTypeAllowed() {
        return secondaryCache.checkIsNetworkTypeAllowed();
    }

    @Override
    public Context getContext() {
        return secondaryCache.getContext();
    }

    @Override
    public void setContext(Context context) {
        secondaryCache.setContext(context);
    }

    public void setRequestProperties(Map<String, String> requestProperties) {
        secondaryCache.setRequestProperties(requestProperties);
    }

    public Map<String, String> getRequestProperties() {
        return secondaryCache.getRequestProperties();
    }

    public void setRequestProperty(String field, String newValue) {
        secondaryCache.setRequestProperty(field, newValue);
    }

    public String getCacheFolder() {
        return secondaryCache.getCacheFolder();
    }

    public void setCacheFolder(String cacheFolder) {
        secondaryCache.setCacheFolder(cacheFolder);
    }

    public FileNameRule getFileNameRule() {
        return secondaryCache.getFileNameRule();
    }

    public void setFileNameRule(FileNameRule fileNameRule) {
        secondaryCache.setFileNameRule(fileNameRule);
    }

    public void initData(Context context, String tag) {
        loadDataFromDb(context, tag);
        deleteUnusedFiles();
    }

    public void deleteUnusedFiles() {
        secondaryCache.deleteUnusedFiles();
    }

    public boolean loadDataFromDb(Context context, String tag) {
        return ImageSDCardCache.loadDataFromDb(context, secondaryCache, tag);
    }

    public boolean saveDataToDb(Context context, String tag) {
        return ImageSDCardCache.saveDataToDb(context, secondaryCache, tag);
    }

    public String getImagePath(String imageUrl) {
        return secondaryCache.getImagePath(imageUrl);
    }

    @Override
    protected void shutdown() {
      //  secondaryCache.shutdown();
        super.shutdown();
    }

    @Override
    public List<Runnable> shutdownNow() {
        secondaryCache.shutdownNow();
        return super.shutdownNow();
    }

    public OnGetDataListener<String, Bitmap> getOnGetImageListenerOfPrimaryCache() {
        return getOnGetDataListener();
    }

    public void setOnGetImageListenerOfPrimaryCache(OnGetDataListener<String, Bitmap> onGetImageListener) {
        this.onGetDataListener = onGetImageListener;
    }

    public OnGetDataListener<String, String> getOnGetImageListenerOfSecondaryCache() {
        return secondaryCache.getOnGetDataListener();
    }

    public void setOnGetImageListenerOfSecondaryCache(OnGetDataListener<String, String> onGetImageListener) {
        secondaryCache.setOnGetDataListener(onGetImageListener);
    }

    public CacheFullRemoveType<String> getCacheFullRemoveTypeOfSecondaryCache() {
        return secondaryCache.getCacheFullRemoveType();
    }

    public void setCacheFullRemoveTypeOfSecondaryCache(CacheFullRemoveType<String> cacheFullRemoveType) {
        secondaryCache.setCacheFullRemoveType(cacheFullRemoveType);
    }
}

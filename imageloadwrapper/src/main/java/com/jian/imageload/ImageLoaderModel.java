package com.jian.imageload;

import android.content.Context;
import android.graphics.Bitmap;

import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;

public class ImageLoaderModel {
    private ImageLoader mImageLoader;

    private DisplayImageOptions.Builder mOptionBuilder;
    private DisplayImageOptions.Builder mRoundOptionBuilder;

    private final static class ModelHolder
    {
        public static ImageLoaderModel instance = new ImageLoaderModel();
    }

    public static ImageLoaderModel instance()
    {
        return ModelHolder.instance;
    }

    private ImageLoaderModel()
    {}

    public void init(Context context, int loadRes, int emptyRes, int failRes)
    {
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        mOptionBuilder = new DisplayImageOptions.Builder();
        //配置全局config
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.memoryCacheSize(10 * 1024 * 1024);
        config.diskCacheFileNameGenerator(new HashCodeFileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024).threadPoolSize(6);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        //提供默认options
        mOptionBuilder .cacheInMemory(true);
        mOptionBuilder.cacheOnDisk(true);
        mOptionBuilder.resetViewBeforeLoading(true);
        mOptionBuilder.delayBeforeLoading(150);
        mOptionBuilder.imageScaleType(ImageScaleType.IN_SAMPLE_INT);
        mOptionBuilder.bitmapConfig(Bitmap.Config.RGB_565);
        mOptionBuilder.showImageOnLoading(loadRes);
        mOptionBuilder.showImageForEmptyUri(emptyRes);
        mOptionBuilder.showImageOnFail(failRes);
        //真正初始化ImageLoader
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(config.build());
    }

    public ImageLoader getImageLoader()
    {
        return mImageLoader;
    }

    public DisplayImageOptions.Builder getOptionBuilder(){
        return mOptionBuilder;
    }
}

package com.jian.imageload;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.View;
import android.widget.ImageView;

import com.jian.imageload.listener.LoadListener;
import com.jian.imageload.option.LoadOptoin;
import com.nostra13.universalimageloader.cache.disc.naming.HashCodeFileNameGenerator;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

public class ImageLoaderModel {
    private ImageLoader mImageLoader;

    private LoadOptoin mOptions;

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
        mOptions = new LoadOptoin();
        ImageLoaderConfiguration.Builder config = new ImageLoaderConfiguration.Builder(context);
        DisplayImageOptions.Builder options = new DisplayImageOptions.Builder();
        //配置全局config
        config.threadPriority(Thread.NORM_PRIORITY - 2);
        config.denyCacheImageMultipleSizesInMemory();
        config.memoryCacheSize(10 * 1024 * 1024);
        config.diskCacheFileNameGenerator(new HashCodeFileNameGenerator());
        config.diskCacheSize(50 * 1024 * 1024).threadPoolSize(6);
        config.tasksProcessingOrder(QueueProcessingType.LIFO);
        //提供默认options
        options .cacheInMemory(true);
        options.cacheOnDisk(true);
        options.resetViewBeforeLoading(true);
        options.delayBeforeLoading(150);
        options.imageScaleType(ImageScaleType.IN_SAMPLE_INT);
        options.bitmapConfig(Bitmap.Config.RGB_565);

        options.showImageOnLoading(loadRes);
        options.showImageForEmptyUri(emptyRes);
        options.showImageOnFail(failRes);

        mOptions.setOptions(options.build());

        //真正初始化ImageLoader
        mImageLoader = ImageLoader.getInstance();
        mImageLoader.init(config.build());
    }

    public ImageLoader getImageLoader()
    {
        return mImageLoader;
    }

    public LoadOptoin getDefaultOptions()
    {
        return mOptions;
    }

    public void displayImage(String uri, ImageView imageView)
    {
        displayImage(uri, imageView, mOptions);
    }

    public void displayImage(String uri, ImageView imageView,LoadOptoin options)
    {
        mImageLoader.displayImage(uri, imageView, options.getOptions());
    }

    public void displayImage(String uri, ImageView imageView,final LoadListener listener)
    {
        mImageLoader.displayImage(uri, imageView, mOptions.getOptions(), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String s, View view) {
                listener.onLoadingStarted(s, view);
            }

            @Override
            public void onLoadingFailed(String s, View view, FailReason failReason) {
                listener.onLoadingFailed(s, view);
            }

            @Override
            public void onLoadingComplete(String s, View view, Bitmap bitmap) {
                listener.onLoadingComplete(s, view, bitmap);
            }

            @Override
            public void onLoadingCancelled(String s, View view) {
                listener.onLoadingCancelled(s, view);
            }
        });
    }

}

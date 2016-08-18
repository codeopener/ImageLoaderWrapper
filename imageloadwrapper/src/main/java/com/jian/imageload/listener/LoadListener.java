package com.jian.imageload.listener;

import android.graphics.Bitmap;
import android.view.View;

import com.nostra13.universalimageloader.core.assist.FailReason;

/**
 * Created by wangjian on 2016/8/17.
 */
public interface LoadListener {
    void onLoadingStarted(String s, View view);
    void onLoadingFailed(String s, View view);
    void onLoadingComplete(String s, View view, Bitmap bitmap);
    void onLoadingCancelled(String s, View view);
}

package com.jian.imageload.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

/**
 * Created by wangjian on 2016/8/17.
 */
public class WImageView extends ImageView{
    public WImageView(Context context) {
        super(context);
    }

    public WImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     *  recycle resource when detach
     * */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageDrawable(null);
    }
}

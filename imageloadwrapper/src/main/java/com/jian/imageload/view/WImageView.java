package com.jian.imageload.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.PaintFlagsDrawFilter;
import android.graphics.Path;
import android.graphics.Region;
import android.support.annotation.NonNull;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import com.jian.imageload.ImageLoaderModel;
import com.jian.imageload.R;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import com.nostra13.universalimageloader.core.listener.ImageLoadingProgressListener;

/**
 * Created by wangjian on 2016/8/17.
 */
public class WImageView extends ImageView{
    private Path path;
    private PaintFlagsDrawFilter mPaintFlagsDrawFilter;
    private Paint paint;
    private boolean mIsRound;

    public WImageView(Context context) {
        super(context);
        init();
    }
    public WImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public void init(){
        mPaintFlagsDrawFilter = new PaintFlagsDrawFilter(0,
                Paint.ANTI_ALIAS_FLAG|Paint.FILTER_BITMAP_FLAG);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setFilterBitmap(true);
        paint.setColor(Color.WHITE);
    }
    /**
     *  recycle resource when detach
     * */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        setImageDrawable(null);
    }

    public void displayImage(@NonNull String url){
        displayImage(url,false);
    }
    public void displayImage(@NonNull String url,boolean isRound){
        displayImage(url,isRound,null);
    }

    public void displayImage(@NonNull String url, boolean isRound, DisplayImageOptions options){
        displayImage(url,isRound,options,null);
    }
    public void displayImage(@NonNull String url, boolean isRound, DisplayImageOptions options,ImageLoadingListener listener){
        displayImage(url,isRound,options,listener,null);
    }
    public void displayImage(@NonNull String url, boolean isRound, DisplayImageOptions options,ImageLoadingListener listener,ImageLoadingProgressListener progressListener){
        mIsRound = isRound;
        if(options==null){
            options = ImageLoaderModel.instance().getOptionBuilder().build();
        }
        setOnTouchListener(onTouchListener);
        ImageLoaderModel.instance().getImageLoader().displayImage(url,this,options,listener,progressListener);
    }

    @Override
    protected void onDraw(Canvas cns) {
        if(mIsRound){
            float h = getMeasuredHeight()- 3.0f;
            float w = getMeasuredWidth()- 3.0f;
            if (path == null) {
                path = new Path();
                path.addCircle(w/2.0f,h/2.0f,(float) Math.min(w/2.0f, (h / 2.0)),Path.Direction.CCW);
                path.close();
            }
            cns.drawCircle(w/2.0f, h/2.0f,  Math.min(w/2.0f, h / 2.0f) + 1.5f, paint);
            int saveCount = cns.getSaveCount();
            cns.save();
            cns.setDrawFilter(mPaintFlagsDrawFilter);
            cns.clipPath(path, Region.Op.REPLACE);
            cns.setDrawFilter(mPaintFlagsDrawFilter);
            cns.drawColor(Color.WHITE);
            super.onDraw(cns);
            cns.restoreToCount(saveCount);
        }else {
            super.onDraw(cns);
        }

    }

    private OnTouchListener onTouchListener=new OnTouchListener() {
        @Override
        public boolean onTouch(View v, MotionEvent event) {
            switch (event.getAction()) {
                case MotionEvent.ACTION_UP:
                    setColorFilter(null);
                    break;
                case MotionEvent.ACTION_DOWN:
                    changeLight();
                    break;
                case MotionEvent.ACTION_MOVE:
                    break;
                case MotionEvent.ACTION_CANCEL:
                    setColorFilter(null);
                    break;
                default:
                    break;
            }
            return true;
        }
    };
    private void changeLight() {
        int brightness=-10;
        ColorMatrix matrix = new ColorMatrix();
        matrix.set(new float[] { 1, 0, 0, 0, brightness, 0, 1, 0, 0,
                brightness, 0, 0, 1, 0, brightness, 0, 0, 0, 1, 0 });
        setColorFilter(new ColorMatrixColorFilter(matrix));

    }

}

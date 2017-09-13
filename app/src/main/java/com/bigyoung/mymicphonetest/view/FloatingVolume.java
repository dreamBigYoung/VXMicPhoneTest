package com.bigyoung.mymicphonetest.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.bigyoung.mymicphonetest.utils.DensityUtils;
import com.bigyoung.mymicphonetest.utils.MediaRecorderUtils;

/**
 * Created by BigYoung on 2017/9/8.
 */

public class FloatingVolume extends View implements MediaRecorderUtils.FractionListener {

    public int mWidth;
    public int mHeight;

    private static  int mStartX;
    public int mDy;
    public int mDx;
    public Paint mPaint;
    public int mDyFill;
    public int mDyEmpty;
    public int mNumMax;//可描绘的梯度片段最大数目
    public int mCurNum;//当前可描绘梯度片段数目

    public FloatingVolume(Context context) {
        this(context,null);
    }

    public FloatingVolume(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FloatingVolume(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mStartX= DensityUtils.dp2px(context,10);
        mDx = DensityUtils.dp2px(context,4);//mDy=3*mDx

        mDyFill = DensityUtils.dp2px(context,8);
        mDyEmpty = DensityUtils.dp2px(context,4);
        mDy=mDyEmpty+mDyFill;

        mPaint = new Paint();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        //可以绘制的着墨区域的最大数
        int numDesignY=mHeight/mDy;
        int numDesignX=(mWidth-mStartX)/mDx;
        mNumMax = Math.min(numDesignX,numDesignY);
        //init pen
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeWidth(1);
        //绘制多边形
        Path path=null;
        for (int i = 0; i < mCurNum; i++) {
            path=new Path();
            path.moveTo(0,mHeight-i*(mDy));
            path.lineTo(0,mHeight-(mDyFill+i*(mDy)));
            path.lineTo(mStartX+i*mDx+mDx,mHeight-(mDyFill+i*(mDy)));
            path.lineTo(mStartX+i*mDx,mHeight-i*(mDy));
            path.close();
            canvas.drawPath(path,mPaint);
        }

    }

    /**
     * 设置当前可描绘层数,then 重绘
     */
    @Override
    public void updateFraction(double curFraction) {
        mCurNum = (int) (curFraction*mNumMax);
        postInvalidate();
    }
}

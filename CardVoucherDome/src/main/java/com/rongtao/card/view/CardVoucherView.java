package com.rongtao.card.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.widget.LinearLayout;

/**
 * Created by rongtao on 2016/5/24.
 */
public class CardVoucherView extends LinearLayout {
    private Paint mPaint;
    /**
     * 圆间距
     */
    private float mGap = 8;

    public void setRadius(float radius) {
        mRadius = radius;
    }

    public void setGap(float gap) {
        mGap = gap;
    }

    /**
     * 半径
     */
    private float mRadius = 10;
    /**
     * 圆数量
     */
    private int mCircleNum_H;
    private int mCircleNum_V;
    /**
     * 除过圆和间隙外多余出来的部分
     */
    private float mRemain_H;//水平
    private float mRemain_V;//垂直
    /*
       指定绘制的 方向
     */
    public final static int DRAW_HORIZONTAL = 0;
    public final static int DRAW_VERTICAL = 1;
    private int mOrientation = DRAW_HORIZONTAL;
    //设置画笔的颜色
    private int mPaintColor = Color.WHITE;

    public void setPaintColor(int paintColor) {
        mPaintColor = paintColor;
    }

    @Override
    public void setOrientation(int orientation) {
        mOrientation = orientation;
    }

    public CardVoucherView(Context context) {
        this(context, null);
    }

    public CardVoucherView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);

    }

    public CardVoucherView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }


    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
        mPaint.setColor(mPaintColor);
        mPaint.setStyle(Paint.Style.FILL);
    }

    /**
     * 圆数量的 计算公式 circleNum = (int) ((w-gap)/(2*radius+gap));
     *
     * @param w
     * @param h
     * @param oldw
     * @param oldh
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        switch (mOrientation) {
            case DRAW_HORIZONTAL:
                measureHorCicleNum(w);
                break;
            case DRAW_VERTICAL:
                measurehValCicleNum(h);
                break;
            default:
                measureHorCicleNum(w);
                measurehValCicleNum(h);
                break;
        }


    }

    /**
     * 测量水平的值
     *
     * @param w
     */
    private void measureHorCicleNum(int w) {
        if (mRemain_H == 0) {
            mRemain_H = (w - mGap) % (mRadius * 2 + mGap);
        }
        mCircleNum_H = (int) ((w - mGap) / (mRadius * 2 + mGap));
    }

    /**
     * 测量垂直的值
     *
     * @param h
     */
    private void measurehValCicleNum(int h) {
        if (mRemain_V == 0) {
            mRemain_V = (h - mGap) % (mRadius * 2 + mGap);
        }
        mCircleNum_V = (int) ((h - mGap) / (mRadius * 2 + mGap));
    }

    /**
     * 绘制不同方向上的原型锯齿
     *
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mOrientation) {
            case DRAW_HORIZONTAL:
            //    drawHorCircle(canvas);
                drawTriangle(canvas);
                break;
            case DRAW_VERTICAL:
                drawValCircle(canvas);
                break;
            default:
                drawHorCircle(canvas);
                drawValCircle(canvas);
                break;
        }
    }

    /**
     * 绘制水平的圆
     *
     * @param canvas
     */
    private void drawHorCircle(Canvas canvas) {
        for (int i = 0; i < mCircleNum_H; i++) {
            //float x = mGap+mRadius+mRemain_H/2+((mGap+mRadius*2)*i);
            float x = mGap + mRadius + mRemain_H / 2 + ((mGap + mRadius * 2) * i);
            canvas.drawCircle(x, mRadius + 1, mRadius, mPaint);
            canvas.drawCircle(x, getHeight(), mRadius, mPaint);
        }
    }

    Path path = new Path();

    private void drawTriangle(Canvas canvas) {
        for (int i = 0; i < mCircleNum_H; i++) {
            //float x = mGap+mRadius+mRemain_H/2+((mGap+mRadius*2)*i);
            float x = mRadius + mRemain_H / 2 + ((mRadius * 2) * i);
            path.reset();
            path.moveTo(x - mRadius, 0);
            path.lineTo(x + mRadius, 0);
            path.lineTo(x, mRadius);
            path.close();
            canvas.drawPath(path, mPaint);
        }
    }

    /**
     * 绘制垂直的圆
     *
     * @param canvas
     */
    private void drawValCircle(Canvas canvas) {
        for (int i = 0; i < mCircleNum_V; i++) {
            float y = mGap + mRadius + mRemain_V / 2 + ((mGap + mRadius * 2) * i);
            canvas.drawCircle(0, y, mRadius, mPaint);
            canvas.drawCircle(getWidth(), y, mRadius, mPaint);
        }
    }
}

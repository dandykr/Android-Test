package com.bri.ojt.Widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Shader;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import android.util.AttributeSet;

public class CurvedBottomNav extends BottomNavigationView {

    private Path mPath;
    private Paint mPaint;

    private final int CURVE_CIRCLE_RADIUS = 120 / 2;
    private final int TOP_CURVE = 8;
    private int mNavigationBarWidth;
    private int mNavigationBarHeight;

    public CurvedBottomNav(Context context) {
        super(context);
        init();
    }

    public CurvedBottomNav(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public CurvedBottomNav(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPath = new Path();
        mPaint = new Paint();
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setColor(Color.WHITE);
        //add shadow
        mPaint.setShadowLayer(8,0,0,Color.GRAY);
        setBackgroundColor(Color.TRANSPARENT);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

        // get width and height of navigation bar
        // Navigation bar bounds (width & height)
        mNavigationBarWidth = getWidth();
        mNavigationBarHeight = getHeight();

        mPath.reset();
        mPath.moveTo(0, TOP_CURVE);
        mPath.lineTo(mNavigationBarWidth, TOP_CURVE);
        mPath.lineTo(mNavigationBarWidth, mNavigationBarHeight);
        mPath.lineTo(0, mNavigationBarHeight);
        mPath.close();
    }



    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawPath(mPath, mPaint);
    }
}

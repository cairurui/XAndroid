package com.charry.xandroid.ui.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by xiaocai on 2018/4/24.
 */

public class ShapeView extends View {
    private int mWidth;
    private int mCenter;
    private Paint mPaint;
    private Shape mShape = Shape.Triangle;

    public ShapeView(Context context) {
        this(context, null);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ShapeView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.YELLOW);
    }

    public void setShape(Shape shape) {
        this.mShape = shape;
        invalidate();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);
        if (width < height)
            width = height;
        else
            height = width;

        mWidth = width;
        mCenter = mWidth / 2;
        setMeasuredDimension(width, height);
    }

    public void exchange() {
        switch (mShape) {
            case Circle:
                mShape = Shape.Square;
                break;
            case Square:
                mShape = Shape.Triangle;
                break;
            case Triangle:
                mShape = Shape.Circle;
                break;
        }
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        switch (mShape) {
            case Circle:
                mPaint.setColor(Color.YELLOW);
                canvas.drawCircle(mCenter, mCenter, mCenter, mPaint);
                break;
            case Square:
                Rect rect = new Rect(0, 0, mWidth, mWidth);
                mPaint.setColor(Color.BLUE);
                canvas.drawRect(rect, mPaint);
                break;
            case Triangle:
                mPaint.setColor(Color.RED);
                Path path = new Path();
                path.moveTo(mCenter, 0);
                path.lineTo(0, mWidth);
                path.lineTo(mWidth, mWidth);
                path.close();
                canvas.drawPath(path,mPaint);
                break;
        }
    }

    public enum Shape {
        Circle, Square, Triangle
    }
}

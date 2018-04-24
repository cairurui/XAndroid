package com.charry.xandroid.ui.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Region;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

import com.charry.xandroid.utils.SizeUtils;

/**
 * Created by xiaocai on 2018/4/22.
 */
public class ColorTrackTextView extends View {
    private Paint mPaint;
    private int width;
    private int height;
    private int defWidth;

    float centerRectStart = 0;
    float centerRectEnd = 0;
    float range = 0;
    float rangeLength = 0;

    float leftTextwidth;
    float rightTextwidth;

    String leftText;
    String rightText;
    float defRange = 0;

    int leftColor;
    int rightColor;
    int textColor;

    public ColorTrackTextView(Context context) {
        this(context, null);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ColorTrackTextView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.YELLOW);

        mPaint.setTextSize(SizeUtils.sp2px(18));

        leftText = "300";
        rightText = "700";

        leftTextwidth = mPaint.measureText(leftText);
        rightTextwidth = mPaint.measureText(rightText);

        defWidth = SizeUtils.dp2px(300);
        setRange(defRange);

        leftColor = Color.parseColor("#FDA70E");
        rightColor = Color.parseColor("#5197DF");
        textColor = Color.WHITE;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);

        if (widthMode == MeasureSpec.EXACTLY) {
            width = widthSize;
        } else if (heightMode == MeasureSpec.EXACTLY) {
            width = heightSize * 6;
        } else {
            width = defWidth;
        }

        if (heightMode == MeasureSpec.EXACTLY) {
            height = heightSize;
        } else {
            height = width / 6;
        }


        Log.d("xiaocai", "width:" + width + "    height:" + height);

        setMeasuredDimension(width, height);

        centerRectStart = height / 2;
        centerRectEnd = width - centerRectStart;
    }


    public void setRange(float r) {
        this.range = r;
        rangeLength = width * r;
        invalidate();

        if (width <= 0) {
            post(new Runnable() {
                @Override
                public void run() {
                    rangeLength = width * range;
                }
            });
        }
    }

    public void setLeftText(String leftText) {
        this.leftText = leftText;
        invalidate();
    }

    public void setRightText(String rightText) {
        this.rightText = rightText;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawBottom(canvas);

        drawLeftRightText(canvas);
    }

    // 绘制文字
    private void drawLeftRightText(Canvas canvas) {
        mPaint.setColor(textColor);
        float x = rangeLength / 2 - leftTextwidth / 2;
        float x2 = rangeLength + (width - rangeLength) / 2 - rightTextwidth / 2;

        Paint.FontMetricsInt fontMetrics = mPaint.getFontMetricsInt();
        int baseline = (height - fontMetrics.bottom - fontMetrics.top) / 2;
        mPaint.setTextAlign(Paint.Align.CENTER);

        canvas.drawText(leftText, x, baseline, mPaint);
        canvas.drawText(rightText, x2, baseline, mPaint);
    }

    // 绘制底图
    private void drawBottom(Canvas canvas) {

        // 先绘制右边的 （会铺满整个空间，之后使用剪切覆盖的方式）
        mPaint.setColor(rightColor);
        Path path = new Path();
        RectF rectFLeftArc = new RectF(0, 0, height, height);
        path.addArc(rectFLeftArc, 90, 180);

        RectF rectFCenter = new RectF(centerRectStart, 0, centerRectEnd, height);
        path.addRect(rectFCenter, Path.Direction.CCW);

        RectF rectFRightArc = new RectF(width - height, 0, width, height);
        path.addArc(rectFRightArc, 270, 180);

        canvas.drawPath(path, mPaint);

        // 绘制左边的
        mPaint.setColor(leftColor);
        Path path2 = new Path();
        RectF r = new RectF(0, 0, rangeLength, height);
        path2.addRect(r, Path.Direction.CCW);
//        path2.op(path, Path.Op.INTERSECT); // 需要 sdk>19

        canvas.clipPath(path, Region.Op.INTERSECT);
        canvas.drawPath(path2, mPaint);
    }

}
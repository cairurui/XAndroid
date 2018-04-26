package com.charry.xandroid.ui.customview;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.View;

import com.charry.xandroid.utils.Xlog;

/**
 * Created by xiaocai on 2018/4/26.
 */

public class LetterSideBar extends View {

    private Paint mPaint;
    // 定义26个字母
    public static String[] mLetters = {"A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};

    // 当前触摸的位置字母
    private String mCurrentTouchLetter;
    private int mCurrentLetterIndex = -1;
    private int mALetterHeight;


    public LetterSideBar(Context context) {
        this(context, null);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LetterSideBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        // 自定义属性，颜色  字体大小
        mPaint.setTextSize(sp2px(14));// 设置的是像素
        // 默认颜色
        mPaint.setColor(Color.BLUE);
    }

    // sp 转 px
    private float sp2px(int sp) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                sp, getResources().getDisplayMetrics());
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);

        int width = (int) mPaint.measureText("A");
        width += getPaddingLeft() + getPaddingRight();

        setMeasuredDimension(width, height);

        mALetterHeight = (int) height / mLetters.length;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        canvas.drawColor(Color.GRAY);


        int heighht = (int) (getMeasuredHeight() - getPaddingTop() - getPaddingBottom()) / mLetters.length;


        int x = getPaddingLeft();

        String text = null;
        for (int i = 0; i < mLetters.length; i++) {
            text = mLetters[i];
            int letterCenterY = i * heighht + heighht / 2 + getPaddingTop();
            Paint.FontMetrics fontMetrics = mPaint.getFontMetrics();
            int dy = (int) ((fontMetrics.bottom - fontMetrics.top) / 2 + fontMetrics.bottom);
            int baseLine = letterCenterY + dy;

            x = (int) (getMeasuredWidth() / 2 - mPaint.measureText(mLetters[i]) / 2);

            if (i == mCurrentLetterIndex) {
                mPaint.setColor(Color.BLUE);
            } else {
                mPaint.setColor(Color.BLACK);
            }

            Xlog.d("i:" + i + "   mCurrentLetterIndex:" + mCurrentLetterIndex);

            canvas.drawText(text, x, baseLine, mPaint);
        }

    }

    public interface OnLetterSelectListener {
        void onLetterSelected(String letter);
    }

    private OnLetterSelectListener mLetterListener;

    public void setLetterListener(OnLetterSelectListener letterListener) {
        this.mLetterListener = letterListener;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                float y = event.getY();
                int currentLetterIndex = (int) (y / mALetterHeight);

                if (currentLetterIndex < 0) currentLetterIndex = 0;
                if (currentLetterIndex > mLetters.length - 1)
                    currentLetterIndex = mLetters.length - 1;
                if (currentLetterIndex == mCurrentLetterIndex) return true;

                mCurrentLetterIndex = currentLetterIndex;
                mCurrentTouchLetter = mLetters[mCurrentLetterIndex];
                if (mLetterListener != null) {
                    mLetterListener.onLetterSelected(mCurrentTouchLetter);
                }
                invalidate();

                break;


        }


        return true;
    }
}

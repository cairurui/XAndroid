package com.charry.xandroid.ui.customview;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import com.charry.xandroid.R;

/**
 * Created by xiaocai on 2018/4/25.
 */

public class RatingBar extends View {
    private static final String TAG = "RatingBar";

    private Bitmap mStarNormalBitmap, mStarFocusBitmap;
    private int mGradeNumber = 5;

    private int mCurrentGrade = 0;


    public RatingBar(Context context) {
        this(context, null);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public RatingBar(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs);
    }

    private void init(Context context, AttributeSet attrs) {
        TypedArray a = context.obtainStyledAttributes(attrs, R.styleable.RatingBar);
        int startNormal = a.getResourceId(R.styleable.RatingBar_starNormal, 0);
        int startFoces = a.getResourceId(R.styleable.RatingBar_starFocus, 0);
        mStarNormalBitmap = BitmapFactory.decodeResource(getResources(), startNormal);
        mStarFocusBitmap = BitmapFactory.decodeResource(getResources(), startFoces);
        mGradeNumber = a.getInteger(R.styleable.RatingBar_gradeNumber, 0);
        a.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int width = mStarNormalBitmap.getWidth();
        int height = mStarNormalBitmap.getHeight();
        setMeasuredDimension(width * mGradeNumber, height);
    }

    public void setCurrentGrade(int currentGrade) {
        this.mCurrentGrade = currentGrade;
        invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawColor(Color.RED);
        int x = 0;
        for (int i = 0; i < mGradeNumber; i++) {
            x = i * mStarNormalBitmap.getWidth();
            if (i < mCurrentGrade) {
                canvas.drawBitmap(mStarFocusBitmap, x, 0, null);
            } else {
                canvas.drawBitmap(mStarNormalBitmap, x, 0, null);
            }
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {

            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:

                float x = event.getX();
                int currentGrade = (int) ((x / mStarNormalBitmap.getWidth()) + 1);
                if (currentGrade < 0)
                    currentGrade = 0;
                if (currentGrade > mGradeNumber)
                    currentGrade = mGradeNumber;

                if (mCurrentGrade == currentGrade) return true;

                mCurrentGrade = currentGrade;
                invalidate();

                return true;
            default:
                break;
        }


        return super.onTouchEvent(event);
    }
}

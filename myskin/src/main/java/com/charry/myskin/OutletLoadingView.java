package com.charry.myskin;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

import static com.charry.myskin.RightMarkView.ANIMATOR_TIME;

/**
 * Created by charry on 2018/6/12.
 */

public class OutletLoadingView extends View {

    private ValueAnimator mAnimator;
    Paint mPaint;
    private int mMaxOvalRadius;
    private int mPathRadius;
    private int mReduceRadius;
    private int mWidth;
    private final int mTotalCount = 11;
    float mCurrentRotationAngle;
    private String TAG = "OutletLoadingView";
    private int mCenterX;
    private int mCenterY;
    private long ROTATION_ANIMATION_TIME = 2000;
    float mFactor;
    private Path mDstPath;
    private float mAnimatorValue;
    private PathMeasure mPathMeasure;
    private float mPathLength;
    private boolean isCompleteAnim = false;

    public OutletLoadingView(Context context) {
        this(context, null);
    }

    public OutletLoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OutletLoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.parseColor("#56C4B1"));

        mMaxOvalRadius = dip2px(20);
        mReduceRadius = mMaxOvalRadius / mTotalCount;
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        mWidth = width > height ? height : width;

        initSize();
        setMeasuredDimension(mWidth, mWidth);
        Log.d(TAG, "onMeasure: mWidth=" + mWidth + "  mCenterX=" + mCenterX);
    }

    private void initSize() {
        mCenterX = mWidth / 2;
        mCenterY = mWidth / 2;
        mPathRadius = mWidth / 2 - mMaxOvalRadius / 2;

        initRightMarkPath();
    }

    /**
     * 关联对号 Path
     */
    private void initRightMarkPath() {
        mPathMeasure = new PathMeasure();
        mDstPath = new Path();
        Path path = new Path();
        // 对号起点
        float startX = (float) (mCenterX - mPathRadius / 3);
        float startY = (float) (mCenterY - mPathRadius / 8);
        path.moveTo(startX, startY);

        // 对号拐角点
        float cornerX = (float) (mCenterX - mPathRadius / 16);
        float cornerY = (float) (mCenterY + mPathRadius / 8);
        path.lineTo(cornerX, cornerY);

        // 对号终点
        float endX = (float) (mCenterX + mPathRadius / 3);
        float endY = (float) (mCenterY - mPathRadius / 4);
        path.lineTo(endX, endY);

        // 重新关联 Path
        mPathMeasure.setPath(path, false);

        // 此时为对号 Path 的长度
        mPathLength = mPathMeasure.getLength();
    }

    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

//        canvas.drawColor(Color.RED);
//        mPaint.setColor(Color.YELLOW);
//        canvas.drawCircle(mCenterX, mCenterY, mPathRadius, mPaint);


//        mPaint.setColor(Color.parseColor("#56C4B1"));

        if (!isCompleteAnim) {
            mPaint.setStyle(Paint.Style.FILL);


            int alpha = 255;
            int r = 86;
            int g = 196;
            int b = 177;

            Log.d(TAG, "onDraw: mCurrentRotationAngle=" + mCurrentRotationAngle);

            float preAngle = (float) (2 * Math.PI / mTotalCount) * mFactor;
            Log.d(TAG, "onDraw: preAngle = " + preAngle);
            for (int i = 0; i < mTotalCount; i++) {
                // 初始角度 + 当前旋转的角度
                double angle = Math.PI + i * preAngle + mCurrentRotationAngle;


                Log.d(TAG, "onDraw: andle=" + angle);
                float cx = (float) (mCenterX + mPathRadius * Math.sin(angle));
                float cy = (float) (mCenterY + mPathRadius * Math.cos(angle));

                int radius = (mMaxOvalRadius - mReduceRadius * i) / 2;
                alpha = 255 * (mTotalCount - i) / mTotalCount;
                mPaint.setColor(Color.argb(alpha, r, g, b));
                Log.d(TAG, "onDraw: cx=" + cx + "  cy=" + cy);
                canvas.drawCircle(cx, cy, radius, mPaint);

            }

        } else {
            mPaint.setColor(Color.parseColor("#56C4B1"));
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setStrokeJoin(Paint.Join.ROUND);
            mPaint.setStrokeWidth(mMaxOvalRadius / 2);

            // 刷新当前截取 Path
            mDstPath.reset();

            // 避免硬件加速的Bug
            mDstPath.lineTo(0, 0);

            // 截取片段
            float stop = mPathLength * mAnimatorValue;

            // 这个API用于截取整个Path的片段，通过参数startD和stopD来控制截取的长度，并将截取的Path保存到dst中，
            // 最后一个参数startWithMoveTo表示起始点是否使用moveTo方法，通常为True，保证每次截取的Path片段都是正常的、完整的。
            mPathMeasure.getSegment(0, stop, mDstPath, true);
            // 绘制截取的片段
            canvas.drawPath(mDstPath, mPaint);
        }
    }

    /**
     * 初始化对号动画
     */
    private void initMarkAnimator() {
        mAnimator = ValueAnimator.ofFloat(0, 1);
        // 动画过程
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                mAnimatorValue = (float) animation.getAnimatedValue();
                invalidate();
            }
        });

        // 动画时间
        mAnimator.setDuration(ANIMATOR_TIME);

        // 插值器
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.start();
    }

    private void reverseLoading() {
        stopLoading();

        // 属性动画
        float twoPI = ((float) Math.PI * 2);
        mAnimator = ValueAnimator.ofFloat(twoPI, 0);
        mAnimator.setDuration(ROTATION_ANIMATION_TIME);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 不断获取值 当前大圆旋转的角度
                mCurrentRotationAngle = (float) animation.getAnimatedValue();
                mFactor = (float) (mCurrentRotationAngle / (2 * Math.PI));

                // 提醒View重新绘制
                invalidate();
            }
        });
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
//                startLoading();
                isCompleteAnim = true;
                initMarkAnimator();
            }
        });
        // 开始计算
        mAnimator.start();
    }

    public void startLoading() {
        isCompleteAnim = false;
        stopLoading();

        // 属性动画
        float twoPI = ((float) Math.PI * 2);
        float PI = (float) Math.PI;
        mAnimator = ValueAnimator.ofFloat(twoPI, twoPI * 2 / 3, twoPI * 1 / 3, 0);
        mAnimator.setDuration(ROTATION_ANIMATION_TIME);
        mAnimator.setInterpolator(new AccelerateDecelerateInterpolator());
        mAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                // 不断获取值 当前大圆旋转的角度
                mCurrentRotationAngle = (float) animation.getAnimatedValue();
                mFactor = (float) (((Math.PI * 2) - mCurrentRotationAngle) / (2 * Math.PI));
                // 提醒View重新绘制
                invalidate();
            }
        });
//        mAnimator.setRepeatCount(-1);
        mAnimator.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                reverseLoading();
            }
        });
        // 开始计算
        mAnimator.start();
    }

    public void stopLoading() {
        if (mAnimator != null) {
            mAnimator.cancel();
            mAnimator.setDuration(0);
            mAnimator.removeAllUpdateListeners();
            mAnimator.removeAllListeners();
            mAnimator = null;
        }
    }


}

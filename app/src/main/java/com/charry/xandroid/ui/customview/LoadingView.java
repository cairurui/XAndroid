package com.charry.xandroid.ui.customview;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.charry.xandroid.R;

/**
 * Created by xiaocai on 2018/5/18.
 */

public class LoadingView extends LinearLayout {
    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initLayout(context);
        mTranslationDistance = dip2px(80);
    }

    ShapeView mShapeView;
    View mShadowView;
    TextView mTextView;
    private int mTranslationDistance;
    private final long ANIMATOR_DURATION = 350;
    // 是否停止动画
    private boolean mIsStopAnimator = false;

    private void initLayout(Context context) {
        if (mIsStopAnimator)
            return;
        // 添加到当前 view
        inflate(context, R.layout.ui_loading_view, this);

        mShapeView = findViewById(R.id.shape_view);
        mShadowView = findViewById(R.id.shadow_view);

        post(new Runnable() {
            @Override
            public void run() {
                startFallAnimator();
            }
        });

    }


    private int dip2px(int dip) {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dip, getResources().getDisplayMetrics());
    }


    private void startFallAnimator() {
        if (mIsStopAnimator)
            return;
        ObjectAnimator translationY = ObjectAnimator.ofFloat(mShapeView, "translationY", 0, mTranslationDistance);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mShadowView, "scaleX", 1f, 0.3f);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATOR_DURATION);
        set.setInterpolator(new AccelerateInterpolator());
        set.playTogether(translationY, scaleX);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mShapeView.exchange();
                startRotationAnimator();
                startUpAnimator();
            }
        });
    }


    private void startUpAnimator() {
        ObjectAnimator translationY = ObjectAnimator.ofFloat(mShapeView, "translationY", mTranslationDistance, 0);
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(mShadowView, "scaleX", 0.3f, 1f);

        AnimatorSet set = new AnimatorSet();
        set.setDuration(ANIMATOR_DURATION);
        set.setInterpolator(new DecelerateInterpolator());
        set.playTogether(translationY, scaleX);
        set.start();

        set.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                mShapeView.exchange();
                startFallAnimator();
            }
        });
    }


    private void startRotationAnimator() {
        int endAngle = 0;
        switch (mShapeView.getShape()) {
            case Circle:
                break;
            case Square:
                endAngle = 180;
                break;
            case Triangle:
                endAngle = -120;
                break;
        }
        ObjectAnimator rotation = ObjectAnimator.ofFloat(mShapeView, "rotation", 0, endAngle);
        rotation.setDuration(ANIMATOR_DURATION);
        rotation.setInterpolator(new DecelerateInterpolator());
        rotation.start();
    }
}

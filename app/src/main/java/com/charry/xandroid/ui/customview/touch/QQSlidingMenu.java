package com.charry.xandroid.ui.customview.touch;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;

import com.charry.xandroid.R;

/**
 * Created by xiaocai on 2018/5/10.
 */

public class QQSlidingMenu extends HorizontalScrollView {


    private final int mMenuWidth;
    private String TAG = "SlidingMenu";
    private int mScreenWidth;
    private ViewGroup mMenuView;
    private ViewGroup mContentView;
    private GestureDetector mGestureDetector;
    private boolean mIsMenuOpen = false;

    public QQSlidingMenu(Context context) {
        this(context, null);
    }

    public QQSlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public QQSlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Log.d(TAG, "SlidingMenu() called with: context = [" + context + "], attrs = [" + attrs + "], defStyleAttr = [" + defStyleAttr + "]");
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu);
        float rightMargin = typedArray.getDimension(R.styleable.SlidingMenu_menuRightMargin, dip2px(context, 50));
        mMenuWidth = (int) (getScreenWidth(context) - rightMargin);
        typedArray.recycle();

        mScreenWidth = getScreenWidth(getContext());

        mGestureDetector = new GestureDetector(context, mSimpleOnGestureListener);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate() called");
        ViewGroup container = (ViewGroup) getChildAt(0);
        mMenuView = (ViewGroup) container.getChildAt(0);
        mContentView = (ViewGroup) container.getChildAt(1);

        // 设置菜单的宽度
        ViewGroup.LayoutParams menuParams = mMenuView.getLayoutParams();
        menuParams.width = mMenuWidth;
        mMenuView.setLayoutParams(menuParams);
        // 设置内容的宽度
        ViewGroup.LayoutParams contentParams = mContentView.getLayoutParams();
        contentParams.width = getScreenWidth(getContext());
        mContentView.setLayoutParams(contentParams);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        closeMenu();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        Log.d(TAG, "onMeasure() called with: widthMeasureSpec = [" + widthMeasureSpec + "], heightMeasureSpec = [" + heightMeasureSpec + "]");
    }

    @Override
    protected void onScrollChanged(int l, int t, int oldl, int oldt) {
        super.onScrollChanged(l, t, oldl, oldt);
        Log.d(TAG, "onScrollChanged() called with: l = [" + l + "], t = [" + t + "], oldl = [" + oldl + "], oldt = [" + oldt + "]");

        float contentScale = (float) (0.7 + 0.3 * 1f * l / mMenuWidth);
        mContentView.setPivotX(0);
        mContentView.setScaleX(contentScale);
        mContentView.setScaleY(contentScale);

        float menuAlpha = 0.5f + 0.5f * (1 - 1f * l / mMenuWidth);
        mMenuView.setAlpha(menuAlpha);

        float menuScale = 0.7f + 0.3f * (1 - 1f * l / mMenuWidth);
        mMenuView.setScaleX(menuScale);
        mMenuView.setScaleY(menuScale);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        if (mGestureDetector.onTouchEvent(ev)) {
            return false;
        }

        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                float currentX = ev.getX();
                Log.d(TAG, "currentX: " + currentX + "   mMenuWidth/2:" + mMenuWidth / 2);
                if (currentX > mScreenWidth / 2) {
                    openMenu();
                } else {
                    closeMenu();
                }
                // 确保 super.onTouchEvent() 不会执行
                return true;
        }
        return super.onTouchEvent(ev);
    }

    private void openMenu() {
        mIsMenuOpen = true;
        smoothScrollTo(0, 0);
    }

    private void closeMenu() {
        mIsMenuOpen = false;
        smoothScrollTo(mMenuWidth, 0);
    }

    private int getScreenWidth(Context context) {
        WindowManager wm = (WindowManager) context
                .getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        return outMetrics.widthPixels;
    }

    private int dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    GestureDetector.SimpleOnGestureListener mSimpleOnGestureListener = new GestureDetector.SimpleOnGestureListener() {
        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            Log.d(TAG, "onFling() velocityX = [" + velocityX + "], velocityY = [" + velocityY + "] " + "  mIsMenuOpen:" + mIsMenuOpen);
            if (mIsMenuOpen) {
                if (velocityX < 0) {
                    closeMenu();
                    return true;
                }
            } else {
                if (velocityX > 0) {
                    openMenu();
                    return true;
                }
            }
            return super.onFling(e1, e2, velocityX, velocityY);
        }
    };
}

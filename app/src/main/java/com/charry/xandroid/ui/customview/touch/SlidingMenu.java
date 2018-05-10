package com.charry.xandroid.ui.customview.touch;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;

import com.charry.xandroid.R;

/**
 * Created by xiaocai on 2018/5/10.
 */

public class SlidingMenu extends HorizontalScrollView {


    private final int mMenuWidth;
    private String TAG = "SlidingMenu";

    public SlidingMenu(Context context) {
        this(context, null);
    }

    public SlidingMenu(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        Log.d(TAG, "SlidingMenu() called with: context = [" + context + "], attrs = [" + attrs + "], defStyleAttr = [" + defStyleAttr + "]");
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.SlidingMenu);
        float rightMargin = typedArray.getDimension(R.styleable.SlidingMenu_menuRightMargin, dip2px(context, 50));
        mMenuWidth = (int) (getScreenWidth(context) - rightMargin);
        typedArray.recycle();
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        Log.d(TAG, "onFinishInflate() called");
        ViewGroup container = (ViewGroup) getChildAt(0);
        ViewGroup menu = (ViewGroup) container.getChildAt(0);
        ViewGroup content = (ViewGroup) container.getChildAt(1);

        // 设置菜单的宽度
        ViewGroup.LayoutParams menuParams = menu.getLayoutParams();
        menuParams.width = mMenuWidth;
        menu.setLayoutParams(menuParams);
        // 设置内容的宽度
        ViewGroup.LayoutParams contentParams = content.getLayoutParams();
        contentParams.width = getScreenWidth(getContext());
        content.setLayoutParams(contentParams);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        super.onLayout(changed, l, t, r, b);
        openMenu();
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
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {

        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:
                float currentX = ev.getX();
                Log.d(TAG, "currentX: " + currentX + "   mMenuWidth/2:" + mMenuWidth / 2);
                if (currentX > mMenuWidth / 2) {
                    closeMenu();
                } else {
                    openMenu();
                }
                // 确保 super.onTouchEvent() 不会执行
                return true;
        }
        return super.onTouchEvent(ev);
    }

    private void openMenu() {
        smoothScrollTo(mMenuWidth, 0);
    }

    private void closeMenu() {
        smoothScrollTo(0, 0);
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
}

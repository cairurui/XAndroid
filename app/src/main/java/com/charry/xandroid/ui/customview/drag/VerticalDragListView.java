package com.charry.xandroid.ui.customview.drag;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.ListViewCompat;
import android.support.v4.widget.ViewDragHelper;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ListView;

/**
 * Created by xiaocai on 2018/5/13.
 */

public class VerticalDragListView extends FrameLayout {

    private static final String TAG = "VerticalDragListView";
    private ViewDragHelper mViewDragHelper;
    private View mDragListView;
    private int mMenuHeight;
    private float mDowmY;
    // 菜单是否打开
    private boolean mMenuIsOpen = false;

    public VerticalDragListView(@NonNull Context context) {
        this(context, null);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public VerticalDragListView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mViewDragHelper = ViewDragHelper.create(this, mDragCallback);
    }


    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int childCount = getChildCount();
        if (childCount != 2)
            throw new IllegalArgumentException("有且仅有两个子布局");

        mDragListView = getChildAt(1);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        if (changed)
            mMenuHeight = getChildAt(0).getMeasuredHeight();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mViewDragHelper.processTouchEvent(event);
        return true;
    }


    ViewDragHelper.Callback mDragCallback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child == mDragListView;
        }

//        @Override
//        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
//            return left;
//        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            if (top < 0) top = 0;
            if (top > mMenuHeight) top = mMenuHeight;
            return top;
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            Log.d(TAG, "onViewReleased() called with xvel = [" + xvel + "], yvel = [" + yvel + "]");
            Log.d(TAG, "onViewReleased: mMenuHeight" + mMenuHeight);
            if (releasedChild == mDragListView) {
                if (mDragListView.getTop() > mMenuHeight / 2) {
                    mViewDragHelper.settleCapturedViewAt(0, mMenuHeight);
                    mMenuIsOpen = true;
                } else {
                    mViewDragHelper.settleCapturedViewAt(0, 0);
                    mMenuIsOpen = false;
                }
                invalidate();
            }

        }
    };

    @Override
    public void computeScroll() {
        if (mViewDragHelper.continueSettling(true)) {
            invalidate();
        }
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        if (mMenuIsOpen)
            return true;

        switch (ev.getAction()) {
            case MotionEvent.ACTION_UP:

                break;
            case MotionEvent.ACTION_DOWN:

                mDowmY = ev.getY();
                mViewDragHelper.processTouchEvent(ev);

                break;
            case MotionEvent.ACTION_MOVE:
                float curY = ev.getY();
                if (curY > mDowmY && !canChildScrollUp()) {
                    return true;
                }
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    public boolean canChildScrollUp() {
        if (mDragListView instanceof ListView) {
            return ListViewCompat.canScrollList((ListView) mDragListView, -1);
        }
        return mDragListView.canScrollVertically(-1);
    }
}

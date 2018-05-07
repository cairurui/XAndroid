package com.charry.xandroid.ui.customview;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by xiaocai on 2018/5/7.
 */

public class TagLayout extends ViewGroup {


    private static final String TAG = "TagLayout";

    private List<List<View>> mChildViews;

    public TagLayout(Context context) {
        this(context, null);
    }

    public TagLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public TagLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);

        mChildViews = new ArrayList<>();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mChildViews.clear();
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = getPaddingTop() + getPaddingBottom();
        Log.d(TAG, "111 onMeasure() called with: width = [" + width + "], height = [" + height + "]");

        int left = getPaddingLeft();

        int childCount = getChildCount();

        List<View> childViewList = new ArrayList<>();
        mChildViews.add(childViewList);
        int maxHeight = 0;
        for (int i = 0; i < childCount; i++) {

            View view = getChildAt(i);
            measureChild(view, widthMeasureSpec, heightMeasureSpec);

            ViewGroup.MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();

            Log.d(TAG, "onMeasure: left + view.getMeasuredWidth()" + (left + view.getMeasuredWidth()) + " text:" + (((TextView) view).getText()));
            if (left + view.getMeasuredWidth() + params.leftMargin + params.rightMargin > width) {
                // 需要换行
                height += view.getMeasuredHeight() + params.topMargin + params.bottomMargin;
                left = getPaddingLeft() + view.getMeasuredWidth() + params.leftMargin + params.rightMargin;

                childViewList = new ArrayList<>();
                mChildViews.add(childViewList);
            } else {
                left += view.getMeasuredWidth() + params.leftMargin + params.rightMargin;
                maxHeight = view.getMeasuredHeight() + params.topMargin + params.bottomMargin;
            }

            childViewList.add(view);
        }
        height += maxHeight;
        int singleChildHeight = getChildAt(0).getMeasuredHeight();
        Log.d(TAG, "onMeasure: childcound:" + childCount + " childHeight:" + singleChildHeight + " totalHeight:" + (childCount * singleChildHeight));
        Log.d(TAG, "222 onMeasure() called with: width = [" + width + "], height = [" + height + "]");

        setMeasuredDimension(width, height);
    }

    @Override
    protected LayoutParams generateLayoutParams(LayoutParams p) {
        return super.generateLayoutParams(p);
    }

    @Override
    public LayoutParams generateLayoutParams(AttributeSet attrs) {
        return new MarginLayoutParams(getContext(), attrs);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

        int top = getPaddingTop();

        Log.d(TAG, "onLayout: top :" + top);
        for (List<View> childViewList : mChildViews) {
            int left = getPaddingLeft();

            for (View view : childViewList) {

                ViewGroup.MarginLayoutParams params = (MarginLayoutParams) view.getLayoutParams();
                left += params.leftMargin;
                int childLeft = left + params.leftMargin;
                int right = childLeft + view.getMeasuredWidth();
                int bottom = top + params.topMargin + view.getMeasuredHeight();


                Log.d(TAG, "onLayout: l:" + left + "    t:" + top + "   r:" + right + " b:" + bottom);
                view.layout(childLeft, top + params.topMargin, right, bottom);
                left += view.getMeasuredWidth() + params.rightMargin;
            }
            ViewGroup.MarginLayoutParams params = (MarginLayoutParams) childViewList.get(0).getLayoutParams();
            top += childViewList.get(0).getMeasuredHeight() + params.topMargin + params.bottomMargin;
        }

    }
}

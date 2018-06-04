package com.charry.xandroid.ui.customview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.widget.TextView;

/**
 * Created by charry on 2018/4/27.
 */

@SuppressLint("AppCompatCustomView")
public class FollowView extends TextView {
    public FollowView(Context context) {
        this(context,null);
    }

    public FollowView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs,0);
    }

    public FollowView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}

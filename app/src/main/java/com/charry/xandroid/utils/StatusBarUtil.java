package com.charry.xandroid.utils;

import android.app.Activity;
import android.graphics.Color;
import android.os.Build;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

/**
 * Created by xiaocai on 2018/5/15.
 */

public class StatusBarUtil {

    public static void setStatusBarBg(Activity activity, int color) {

        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.LOLLIPOP) {
            activity.getWindow().setStatusBarColor(color);
        } else {
            // 透明的状态栏
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);

            // 获取状态栏的高度
            int statusBarHeight = getStatusBarHeight(activity);

            // decore view 中添加一个 子view ，其高度为 状态栏高度，设置背景颜色
            View view = new View(activity);
            view.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, statusBarHeight));
            view.setBackgroundColor(color);

            // 添加到 decorview 中
            ViewGroup decorView = (ViewGroup) activity.getWindow().getDecorView();
            decorView.addView(view);

            // 设置 contentView 的 padding top 为 状态栏的高度
            ViewGroup contentView = activity.findViewById(android.R.id.content);
            contentView.setPadding(0, statusBarHeight, 0, 0);
        }
    }

    public static int getStatusBarHeight(Activity activity) {
        int identifier = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
        return activity.getResources().getDimensionPixelOffset(identifier);
    }


    /**
     * 设置activity全屏
     * @param activity
     */
    public static void setActivityTranslucent(Activity activity){
        // 5.0 以上
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            // 这个怎么写有没有思路？看源码  29次
            View decorView = activity.getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            activity.getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        // 4.4 - 5.0 之间  采用一个技巧，首先把他弄成全屏，在状态栏的部分加一个布局
        else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
    }

}

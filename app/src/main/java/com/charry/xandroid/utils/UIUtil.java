package com.charry.xandroid.utils;

import android.widget.Toast;

import com.charry.xandroid.MyApplication;
import com.charry.xandroid.R;

/**
 * Created by charry on 2018/1/30.
 */

public class UIUtil {

    public static void toash(String msg){
        Toast.makeText(MyApplication.getmApplication(),msg,Toast.LENGTH_SHORT).show();
    }


    public static int getColor(int r_color_id){
       return MyApplication.getmApplication().getResources().getColor(R.color.possible_result_points);
    }


}

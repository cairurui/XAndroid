package com.charry.xandroid.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

/**
 * 网络工具类
 */
public class NetworkUtil {

    public final static int NETWORK_NO_CONNECT = 0;            //没有网络
    public final static int NETWORK_WIFI = 1;                //Wifi网络
    public final static int NETWORK_3G = 2;                    //3G网络
    public final static int NETWORK_OTHER = 3;                //其他网络

    public static int getNetworkType(Context mContext) {
        if (null != mContext) {
            ConnectivityManager connectivityManager = (ConnectivityManager) mContext
                    .getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo net = connectivityManager.getActiveNetworkInfo();
            if (net == null) {
                return NETWORK_NO_CONNECT;
            } else {
                if ("WIFI".equals(net.getTypeName())) {
                    return NETWORK_WIFI;
                } else if ("mobile".equals(net.getTypeName())) {
                    return NETWORK_3G;
                } else {
                    return NETWORK_OTHER;
                }
            }
        }
        return NETWORK_NO_CONNECT;
    }


    /**
     * 是否联网
     *
     * @return
     */
    public static boolean isNetworkEnable(Context mContext) {
        ConnectivityManager cm = (ConnectivityManager) mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (null == cm || (null != cm && null == cm.getActiveNetworkInfo())) {
            return false;
        }
        NetworkInfo info = cm.getActiveNetworkInfo();
        if (info == null) {
            return false;
        }
        return info.isAvailable();

    }


}

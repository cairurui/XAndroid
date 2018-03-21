package com.charry.xandroid.utils;

import android.text.TextUtils;
import android.util.Log;

public class Xlog {

    private static String TAG = "xiaocai";
    private static final boolean isLog = true;

    public static void setTag(String tag) {
        TAG = tag;
    }

    public static void m() {
        m(5, 4);
    }

    public static void m(int methodCount) {
        m(methodCount, 4);
    }

    /**
     * @param methodCount 想要观察的方法数
     * @param stackOffset 偏移量(前面几个方法不需要输出) 外部传入 3 即可
     */
    public static void m(int methodCount, int stackOffset) {
        m(TAG, methodCount, stackOffset);
    }

    /**
     * @param methodCount 想要观察的方法数
     * @param stackOffset 偏移量(前面几个方法不需要输出) 外部传入 3 即可
     */
    public static void m(String tag, int methodCount, int stackOffset) {
        m(tag, null, methodCount, stackOffset);
    }

    /**
     * @param methodCount 想要观察的方法数
     * @param stackOffset 偏移量(前面几个方法不需要输出) 外部传入 3 即可
     */
    public static void m(String tag, String msg, int methodCount, int stackOffset) {
        if (!isLog) return;

        StackTraceElement[] trace = Thread.currentThread().getStackTrace();
        String threadName = " Thread: " + Thread.currentThread().getName();
        Log.d(tag, "Xlog:┌  thread name: " + threadName);


        //corresponding method count with the current stack may exceeds the stack trace. Trims the count
        if (methodCount + stackOffset > trace.length) {
            methodCount = trace.length - stackOffset - 1;
        }

        for (int i = 0; i < methodCount; i++) {
            int stackIndex = i + stackOffset;
            if (stackIndex >= trace.length) {
                continue;
            }
            StringBuilder builder = new StringBuilder();
            builder.append(' ').append(getSimpleClassName(trace[stackIndex].getClassName())).append(".").append(trace[stackIndex].getMethodName()).append(" ").append(" (").append(trace[stackIndex].getFileName()).append(":").append(trace[stackIndex].getLineNumber()).append(")");
            if (i == methodCount - 1) { // 最后一个了
                if (!TextUtils.isEmpty(msg))
                    Log.d(tag, "Xlog:├  " + msg);
                Log.d(tag, "Xlog:└ " + builder.toString());
            } else {
                Log.d(tag, "Xlog:├ " + builder.toString());
            }

        }
    }

    private static String getSimpleClassName(String name) {
        int lastIndex = name.lastIndexOf(".");
        return name.substring(lastIndex + 1);
    }

    public static void d(String tag, String msg) {
        Xlog.m(tag, msg, 1, 3);
    }

    public static void d(String msg) {
        Xlog.m(TAG, msg, 1, 5);
    }
}
package com.charry.xandroid;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;

import com.facebook.stetho.Stetho;
import com.charry.xandroid.utils.Xlog;

/**
 * Created by charry on 2018/1/30.
 */

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private static MyApplication mApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        mApplication = this;

        Stetho.initializeWithDefaults(this);

        registerActivityLifecycleCallbacks(this);

    }

    public static MyApplication getmApplication() {
        return mApplication;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
        Xlog.d("onActivityCreated " + activity.getComponentName());
    }

    @Override
    public void onActivityStarted(Activity activity) {
        Xlog.d("onActivityStarted " + activity.getComponentName());
    }

    @Override
    public void onActivityResumed(Activity activity) {
        Xlog.d("onActivityResumed " + activity.getComponentName());
    }

    @Override
    public void onActivityPaused(Activity activity) {
        Xlog.d("onActivityPaused " + activity.getComponentName());
    }

    @Override
    public void onActivityStopped(Activity activity) {
        Xlog.d("onActivityStopped " + activity.getComponentName());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
        Xlog.d("onActivitySaveInstanceState " + activity.getComponentName());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
        Xlog.d("onActivityDestroyed " + activity.getComponentName());
    }
}

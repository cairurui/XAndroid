package com.charry.xandroid;

import android.app.Activity;
import android.app.Application;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;

import com.alipay.euler.andfix.patch.PatchManager;
import com.facebook.stetho.Stetho;
import com.charry.xandroid.utils.Xlog;

import java.io.File;
import java.io.IOException;

/**
 * Created by charry on 2018/1/30.
 */

public class MyApplication extends Application implements Application.ActivityLifecycleCallbacks {

    private static final String TAG = "xiaocai";
    private static MyApplication mApplication;
    private PatchManager mPatchManager;
    private static final String APATCH_PATH = "/out.apatch";

    @Override
    public void onCreate() {
        super.onCreate();

        mApplication = this;

        Stetho.initializeWithDefaults(this);

        registerActivityLifecycleCallbacks(this);

        // initialize
        mPatchManager = new PatchManager(this);
        mPatchManager.init("1.2");
        // load patch
        mPatchManager.loadPatch();
        Log.d(TAG, "apatch loaded.");

        // add patch at runtime
        try {
            // .apatch file path
            String patchFileString = Environment.getExternalStorageDirectory()
                    .getAbsolutePath() + APATCH_PATH;
            mPatchManager.addPatch(patchFileString);
            Log.d(TAG, "apatch:" + patchFileString + " added.");

            File f = new File(patchFileString);
            if (f.exists()) {
                boolean result =f.delete();
                if (!result)
                    Log.e(TAG, patchFileString + " delete fail");
            }
        } catch (IOException e) {
            Log.e(TAG, "", e);
        }
    }

    public PatchManager getPatchManager() {
        return mPatchManager;
    }

    public static MyApplication getApplication() {
        return mApplication;
    }

    @Override
    public void onActivityCreated(Activity activity, Bundle savedInstanceState) {
//        Xlog.d("onActivityCreated " + activity.getComponentName());
    }

    @Override
    public void onActivityStarted(Activity activity) {
//        Xlog.d("onActivityStarted " + activity.getComponentName());
    }

    @Override
    public void onActivityResumed(Activity activity) {
//        Xlog.d("onActivityResumed " + activity.getComponentName());
    }

    @Override
    public void onActivityPaused(Activity activity) {
//        Xlog.d("onActivityPaused " + activity.getComponentName());
    }

    @Override
    public void onActivityStopped(Activity activity) {
//        Xlog.d("onActivityStopped " + activity.getComponentName());
    }

    @Override
    public void onActivitySaveInstanceState(Activity activity, Bundle outState) {
//        Xlog.d("onActivitySaveInstanceState " + activity.getComponentName());
    }

    @Override
    public void onActivityDestroyed(Activity activity) {
//        Xlog.d("onActivityDestroyed " + activity.getComponentName());
    }
}

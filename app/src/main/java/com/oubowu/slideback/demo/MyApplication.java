package com.oubowu.slideback.demo;

import android.app.Application;

import com.facebook.common.logging.FLog;
import com.facebook.drawee.backends.pipeline.Fresco;
import com.facebook.imagepipeline.core.ImagePipelineConfig;
import com.facebook.imagepipeline.listener.RequestListener;
import com.facebook.imagepipeline.listener.RequestLoggingListener;
import com.oubowu.slideback.ActivityHelper;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by Oubowu on 2016/9/22 0022 18:13.
 */
public class MyApplication extends Application {

    private ActivityHelper mActivityHelper;

    private static MyApplication sMyApplication;

    @Override
    public void onCreate() {
        super.onCreate();

        mActivityHelper = new ActivityHelper();
        registerActivityLifecycleCallbacks(mActivityHelper);

        Set<RequestListener> requestListeners = new HashSet<>();
        requestListeners.add(new RequestLoggingListener());
        ImagePipelineConfig config = ImagePipelineConfig.newBuilder(this)
                .setDownsampleEnabled(true)
                .setRequestListeners(requestListeners)
                .build();
        Fresco.initialize(this, config);
        FLog.setMinimumLoggingLevel(FLog.VERBOSE);

        sMyApplication = this;

    }

    public static ActivityHelper getActivityHelper(){
        return sMyApplication.mActivityHelper;
    }

}

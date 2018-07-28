package com.udacity.gradle.builditbigger;

import android.app.Application;

import timber.log.Timber;

/**
 * Created by Mohamed on 7/28/2018.
 *
 */
public class BaseApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        // Planting timber tree
        Timber.plant(new Timber.DebugTree());
    }

}

package com.corentin.evanno.lebonson

import android.app.Application
import timber.log.Timber

class AndroidApp : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}
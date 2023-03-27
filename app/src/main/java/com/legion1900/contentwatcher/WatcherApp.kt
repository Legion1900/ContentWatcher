package com.legion1900.contentwatcher

import android.app.Application
import com.legion1900.contentwatcher.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class WatcherApp : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WatcherApp)
            modules(
                appModule
            )
        }
    }
}

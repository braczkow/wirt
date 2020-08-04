package com.braczkow.wirt

import android.app.Application
import com.braczkow.lib.Logg
import timber.log.Timber

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(
            Timber.DebugTree()
        )

        Logg.init { Timber.d(it) }
    }
}
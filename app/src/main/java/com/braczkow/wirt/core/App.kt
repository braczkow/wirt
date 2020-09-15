package com.braczkow.wirt.core

import android.app.Application
import com.braczkow.lib.Logg
import dagger.Component
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        Timber.plant(
            Timber.DebugTree()
        )

        Logg.init { Timber.d(it) }

        Timber.d("App onCreate")
    }


}

@Component
interface AppComponent {

}
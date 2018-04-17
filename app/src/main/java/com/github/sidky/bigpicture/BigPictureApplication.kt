package com.github.sidky.bigpicture

import android.app.Application
import com.facebook.stetho.Stetho
import com.github.sidky.bigpicture.common.constantsModule
import com.github.sidky.bigpicture.network.networkModule
import com.github.sidky.bigpicture.xml.xmlModule
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class BigPictureApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin(this, arrayListOf(constantsModule, networkModule, xmlModule))

        initializeStetho()

        Timber.plant(Timber.DebugTree())
    }

    private fun initializeStetho() {
        if (BuildConfig.DEBUG) {
            Stetho.initializeWithDefaults(this)
        }
    }
}

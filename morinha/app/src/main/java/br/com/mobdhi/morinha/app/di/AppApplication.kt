package br.com.mobdhi.morinha.app.di

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.android.logger.AndroidLogger
import org.koin.core.context.startKoin

class AppApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            AndroidLogger()
            androidContext(this@AppApplication)
            modules(morinhaAppKoinModule())
        }
    }
}
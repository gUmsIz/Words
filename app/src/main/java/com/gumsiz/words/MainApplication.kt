package com.gumsiz.words

import android.app.Application
import com.gumsiz.words.di.androidModule
import com.gumsiz.shared.di.sharedModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            androidLogger()
            modules(sharedModule() + androidModule)
        }
    }
}
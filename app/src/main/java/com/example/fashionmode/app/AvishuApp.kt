package com.example.fashionmode.app

import android.app.Application
import com.example.fashionmode.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin


class AvishuApp : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@AvishuApp)
            modules(appModule)
        }
    }
}
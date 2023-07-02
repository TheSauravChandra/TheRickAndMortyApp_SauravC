package com.saurav.therickandmorty_sauravc.application

import android.app.Application
import com.mocklets.pluto.Pluto
import com.saurav.therickandmorty_sauravc.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        AppContext.initialize(this)
        Pluto.initialize(this, true)

        startKoin {
            androidLogger()
            androidContext(this@MyApp)
            modules(appModule)
        }
    }
}
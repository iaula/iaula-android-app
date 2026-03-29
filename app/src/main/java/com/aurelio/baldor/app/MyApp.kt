package com.aurelio.baldor.app

import android.app.Application
import com.aurelio.baldor.core.di.coreModule
import com.aurelio.baldor.feature_login.di.loginModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MyApp : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MyApp)
            modules(
                listOf(
                    coreModule,
                    loginModule
                )
            )
        }
    }
}
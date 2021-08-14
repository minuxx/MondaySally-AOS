package com.moon.android.mondaysally

import android.app.Application
import com.moon.android.mondaysally.di.networkModule
import com.moon.android.mondaysally.di.repositoryModule
import com.moon.android.mondaysally.di.utilityModule
import com.moon.android.mondaysally.di.viewModelModule

import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.BuildConfig.DEBUG
import org.koin.core.context.startKoin
import org.koin.core.logger.Level

class ApplicationClass : Application(){
    override fun onCreate() {
        super.onCreate()

        startKoin{
            if (DEBUG) {
                androidLogger()
            } else {
            androidLogger(Level.NONE)
        }
            androidContext(this@ApplicationClass)
            modules(
                utilityModule,
                viewModelModule,
                    networkModule,
                repositoryModule
            )
        }
    }
}
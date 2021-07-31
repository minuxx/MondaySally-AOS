package com.moon.android.mondaysally.di

import com.moon.android.mondaysally.utils.GlobalConstant
import com.moon.android.mondaysally.utils.SharedPreferencesManager
import com.moon.android.mondaysally.ui.LottieDialog
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val utilityModule = module {
    single { SharedPreferencesManager(androidContext()) }
    single { GlobalConstant() }
}
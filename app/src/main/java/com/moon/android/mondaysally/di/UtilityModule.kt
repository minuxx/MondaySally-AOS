package com.moon.android.mondaysally.di

import com.moon.android.mondaysally.utils.GlobalConstant
import com.moon.android.mondaysally.utils.SharedPreferencesManager
import com.moon.android.mondaysally.utils.GridItemDecoration
import com.moon.android.mondaysally.utils.GridItemDecoration_15_15
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module


val utilityModule = module {
    single { SharedPreferencesManager(androidContext()) }
    single { GlobalConstant() }
    single { GridItemDecoration(get())}
    single { GridItemDecoration_15_15(get())}
}
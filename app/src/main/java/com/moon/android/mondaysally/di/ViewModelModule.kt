package com.moon.android.mondaysally.di

import com.moon.android.mondaysally.ui.login.LoginViewModel
import com.moon.android.mondaysally.ui.splash.SplashViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get()) }
    viewModel { LoginViewModel(get(), get())}
}
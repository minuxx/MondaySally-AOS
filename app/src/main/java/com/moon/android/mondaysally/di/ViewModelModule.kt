package com.moon.android.mondaysally.di

import com.moon.android.mondaysally.ui.login.LoginViewModel
import com.moon.android.mondaysally.ui.onboarding.OnBoardingViewModel
import com.moon.android.mondaysally.ui.splash.SplashViewModel
import com.moon.android.mondaysally.ui.tutorial.TutorialFragmentViewModel
import com.moon.android.mondaysally.ui.tutorial.TutorialViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get(), get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { OnBoardingViewModel() }
    viewModel { TutorialViewModel() }
    viewModel { TutorialFragmentViewModel() }
}
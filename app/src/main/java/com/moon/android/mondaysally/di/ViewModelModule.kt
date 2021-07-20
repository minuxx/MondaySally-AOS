package com.moon.android.mondaysally.di

import com.moon.android.mondaysally.ui.login.LoginViewModel
import com.moon.android.mondaysally.ui.onboarding.OnBoardingViewModel
import com.moon.android.mondaysally.ui.splash.SplashViewModel
import com.moon.android.mondaysally.ui.team_code.TeamCodeViewModel
import com.moon.android.mondaysally.ui.tutorial.fragment.TutorialFragmentViewModel
import com.moon.android.mondaysally.ui.tutorial.TutorialViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get(), get()) }
    viewModel { LoginViewModel(get(), get()) }
    viewModel { OnBoardingViewModel() }
    viewModel { TutorialViewModel(get()) }
    viewModel { TutorialFragmentViewModel() }
    viewModel { TeamCodeViewModel(get(), get()) }

}
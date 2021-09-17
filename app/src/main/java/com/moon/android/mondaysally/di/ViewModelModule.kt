package com.moon.android.mondaysally.di

import com.moon.android.mondaysally.ui.main.MainViewModel
import com.moon.android.mondaysally.ui.main.auth.AuthViewModel
import com.moon.android.mondaysally.ui.main.clover.CloverViewModel
import com.moon.android.mondaysally.ui.main.gift.GiftViewModel
import com.moon.android.mondaysally.ui.main.home.HomeViewModel
import com.moon.android.mondaysally.ui.main.twinkle.TwinkleViewModel
import com.moon.android.mondaysally.ui.onboarding.OnBoardingViewModel
import com.moon.android.mondaysally.ui.splash.SplashViewModel
import com.moon.android.mondaysally.ui.team_code.TeamCodeViewModel
import com.moon.android.mondaysally.ui.terms.TermsViewModel
import com.moon.android.mondaysally.ui.tutorial.TutorialViewModel
import com.moon.android.mondaysally.ui.tutorial.fragment.TutorialFragmentViewModel
import com.moon.android.mondaysally.ui.welcome.WelcomeViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { SplashViewModel(get(), get(), get()) }
    viewModel { OnBoardingViewModel() }
    viewModel { TutorialViewModel(get()) }
    viewModel { TutorialFragmentViewModel() }
    viewModel { TeamCodeViewModel(get(), get()) }
    viewModel { TermsViewModel() }
    viewModel { WelcomeViewModel() }
    viewModel { MainViewModel(get()) }
    viewModel { GiftViewModel(get()) }
    viewModel { TwinkleViewModel(get()) }
    viewModel { HomeViewModel(get(), get()) }
    viewModel { CloverViewModel(get(), get()) }
    viewModel { AuthViewModel(get(), get()) }
}
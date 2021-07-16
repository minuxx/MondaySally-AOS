package com.moon.android.mondaysally.ui.onboarding

import androidx.annotation.LayoutRes
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityOnboardingBinding
import com.moon.android.mondaysally.ui.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class OnBoardingActivity : BaseActivity<ActivityOnboardingBinding>() {

    private val onBoardingViewModel: OnBoardingViewModel by viewModel()

    @LayoutRes
    override fun getLayoutResId() = R.layout.activity_onboarding

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = onBoardingViewModel
    }

    override fun initAfterBinding() {
//        splashViewModel.serverVersionCheck()
    }
}
package com.moon.android.mondaysally.ui.welcome

import androidx.annotation.LayoutRes

import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityWelcomeBinding
import com.moon.android.mondaysally.ui.BaseActivity
import com.moon.android.mondaysally.ui.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class WelcomeActivity : BaseActivity<ActivityWelcomeBinding>() {

    private val welcomeViewModel: WelcomeViewModel by viewModel()

    @LayoutRes
    override fun getLayoutResId() = R.layout.activity_welcome

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
//        binding.viewModel = welcomeViewModel

        welcomeViewModel.goNextActivity.observe(this, { goNextActivity ->
            if (goNextActivity) {
                startNextActivity(LoginActivity::class.java)
            }
        })
    }

    override fun initAfterBinding() {

    }
}
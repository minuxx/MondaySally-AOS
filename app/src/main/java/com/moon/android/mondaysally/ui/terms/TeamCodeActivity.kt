package com.moon.android.mondaysally.ui.terms

import android.text.method.ScrollingMovementMethod
import androidx.annotation.LayoutRes

import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityTermsBinding
import com.moon.android.mondaysally.ui.BaseActivity
import com.moon.android.mondaysally.ui.login.LoginActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class TermsActivity : BaseActivity<ActivityTermsBinding>() {

    private val termsViewModel: TermsViewModel by viewModel()

    @LayoutRes
    override fun getLayoutResId() = R.layout.activity_terms

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = termsViewModel

        termsViewModel.goNextActivity.observe(this, { goNextActivity ->
            if (goNextActivity) {
                startNextActivity(LoginActivity::class.java)
            }
        })
    }

    override fun initAfterBinding() {
        binding.activityTeamCodeTvServiceTerms.movementMethod = ScrollingMovementMethod()
    }
}
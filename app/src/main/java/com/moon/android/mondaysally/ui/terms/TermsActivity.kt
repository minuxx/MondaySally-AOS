package com.moon.android.mondaysally.ui.terms

import android.text.method.ScrollingMovementMethod
import androidx.annotation.LayoutRes

import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityTermsBinding
import com.moon.android.mondaysally.ui.BaseActivity
import com.moon.android.mondaysally.ui.login.LoginActivity
import com.moon.android.mondaysally.ui.main.MainActivity
import com.moon.android.mondaysally.ui.welcome.WelcomeActivity
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
                startActivityWithClear(MainActivity::class.java)
                startNextActivity(WelcomeActivity::class.java)
            }
        })

        termsViewModel.allAgree.observe(this, { allAgree ->
            if (allAgree) {
                binding.activityTermsBtnAll.setImageResource(R.drawable.check_box_on_orange)
            }else{
                binding.activityTermsBtnAll.setImageResource(R.drawable.check_box_off_gray)
            }
        })

        termsViewModel.serviceAgree.observe(this, { serviceAgree ->
            if (serviceAgree) {
                binding.activityTermsBtnService.setImageResource(R.drawable.check_box_on_orange)
            }else{
                binding.activityTermsBtnService.setImageResource(R.drawable.check_box_off_gray)
            }
        })

        termsViewModel.privacyAgree.observe(this, { privacyAgree ->
            if (privacyAgree) {
                binding.activityTermsBtnPrivacy.setImageResource(R.drawable.check_box_on_orange)
            }else{
                binding.activityTermsBtnPrivacy.setImageResource(R.drawable.check_box_off_gray)
            }
        })
    }

    override fun initAfterBinding() {
        binding.activityTeamCodeTvServiceTerms.movementMethod = ScrollingMovementMethod()
        binding.activityTeamCodeTvPrivacyTerms.movementMethod = ScrollingMovementMethod()
    }
}
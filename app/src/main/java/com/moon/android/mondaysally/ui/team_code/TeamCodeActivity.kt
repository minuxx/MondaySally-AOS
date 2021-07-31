package com.moon.android.mondaysally.ui.team_code

import android.os.Bundle
import androidx.annotation.LayoutRes
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityTeamCodeBinding
import com.moon.android.mondaysally.ui.BaseActivity
import com.moon.android.mondaysally.ui.terms.TermsActivity
import com.moon.android.mondaysally.ui.terms.TermsViewModel
import com.moon.android.mondaysally.ui.welcome.WelcomeActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class TeamCodeActivity : BaseActivity<ActivityTeamCodeBinding>() {

    private val splashViewModel: TeamCodeViewModel by viewModel()

    @LayoutRes
    override fun getLayoutResId() = R.layout.activity_team_code

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = splashViewModel

        splashViewModel.goNextActivity.observe(this, { goNextActivity ->
            if (goNextActivity) {
                startNextActivity(TermsActivity::class.java)
            }
        })

        //dialog null 에러 해결이안돼서 일단 주석
//        splashViewModel.loading.observe(this, { loading ->
//            if (loading) showLottieDialog()
//            else hideLottieDialog()
//        })
        splashViewModel.fail.observe(this, { fail ->
//            341	"존재하지 않는 사용자입니다."
//            378	"코드 형식을 정확하게 입력해주세요."
//            378	"코드를 입력해주세요."
//            404	"네트워크 오류가 발생했습니다."
            animateViewShake(binding.activityTeamCodeEtInput)
            when (fail.code) {
                341, 402, 378 -> {
                    showToast(fail.message)
                }
                404 -> {
                    showToast(getString(R.string.default_fail))
                }
            }
        })
    }

    override fun initAfterBinding() {

    }
}
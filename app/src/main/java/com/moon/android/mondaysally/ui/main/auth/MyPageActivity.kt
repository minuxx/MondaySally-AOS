package com.moon.android.mondaysally.ui.main.auth

import android.content.Intent
import androidx.annotation.LayoutRes
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityMyPageBinding
import com.moon.android.mondaysally.ui.BaseActivity
import com.moon.android.mondaysally.ui.main.clover.clover_history.CloverHistoryActivity
import com.moon.android.mondaysally.ui.main.gift.gift_history.GiftHistoryActivity
import com.moon.android.mondaysally.ui.terms.TermsActivity
import com.moon.android.mondaysally.ui.tutorial.TutorialActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class MyPageActivity : BaseActivity<ActivityMyPageBinding>() {

    private val authViewModel: AuthViewModel by viewModel()

    @LayoutRes
    override fun getLayoutResId() = R.layout.activity_my_page

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = authViewModel

        authViewModel.finish.observe(this, { finish ->
            if (finish) {
                finish()
            }
        })

        authViewModel.goProfileEdit.observe(this, { goProfileEdit ->
            if (goProfileEdit) {
                val profileEditActivity = Intent(this, ProfileEditActivity::class.java)
                profileEditActivity.putExtra("imgUrl", authViewModel.authResult.value?.imgUrl)
                profileEditActivity.putExtra("nickname", authViewModel.authResult.value?.nickname)
                profileEditActivity.putExtra("phone", authViewModel.authResult.value?.phoneNumber)
                profileEditActivity.putExtra("bank", authViewModel.authResult.value?.bankAccount)
                profileEditActivity.putExtra("email", authViewModel.authResult.value?.email)
                startActivity(profileEditActivity)
            }
        })

        authViewModel.menuClick.observe(this, { flag ->
            when(flag){
                1->startNextActivity(CloverHistoryActivity::class.java) //클로버내역
                2->startNextActivity(GiftHistoryActivity::class.java) //기프트내역
//                3->startNextActivity(GiftHistoryActivity::class.java)
                4->startNextActivity(TutorialActivity::class.java) //온보딩
//                5->startNextActivity(TermsActivity::class.java) //오픈소스
                6->startNextActivity(TermsActivity::class.java) //이용약관
//                7->startNextActivity(TermsActivity::class.java) //버전정보
//                8->startNextActivity(TermsActivity::class.java) //퇴사신청
//                9->startNextActivity(TermsActivity::class.java) //로그아웃
            }
        })

        authViewModel.fail.observe(this,
            { fail ->
//            341	"존재하지 않는 사용자입니다."
//            378	"코드 형식을 정확하게 입력해주세요."
//            378	"코드를 입력해주세요."
//            404	"네트워크 오류가 발생했습니다."
                when (fail.code) {
                    341, 402, 378 -> {
                        showToast(fail.message)
                    }
                    else -> {
                        showToast(getString(R.string.default_fail))
                    }
                }
            })
    }

    override fun initAfterBinding() {
        authViewModel.getMyPageData()
    }

    override fun onRestart() {
        authViewModel.getMyPageData()
        super.onRestart()
    }
}
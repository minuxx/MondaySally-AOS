package com.moon.android.mondaysally.ui.main.auth

import android.content.Intent
import androidx.annotation.LayoutRes
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityMyPageBinding
import com.moon.android.mondaysally.ui.BaseActivity
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
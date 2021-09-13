package com.moon.android.mondaysally.ui.main.auth

import androidx.annotation.LayoutRes
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityProfileEditBinding
import com.moon.android.mondaysally.ui.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class ProfileEditActivity : BaseActivity<ActivityProfileEditBinding>() {

    private val authViewModel: AuthViewModel by viewModel()

    @LayoutRes
    override fun getLayoutResId() = R.layout.activity_profile_edit

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = authViewModel

        authViewModel.finish.observe(this, { finish ->
            if (finish) {
                finish()
            }
        })

        authViewModel.validateMessage.observe(this, { validateMessage ->
            showToast(validateMessage)
        })

        authViewModel.profileEditSuccess.observe(this, { profileEditSuccess ->
            if (profileEditSuccess) {
                finish()
            }
        })


        authViewModel.fail.observe(this, { fail ->
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
        authViewModel.editTextNicknameString.set(intent.getStringExtra("nickname"))
        authViewModel.editTextPhoneString.set(intent.getStringExtra("phone"))
        authViewModel.editTextBankString.set(intent.getStringExtra("bank"))
        authViewModel.editTextEmailString.set(intent.getStringExtra("email"))
        authViewModel.profileUrl = intent.getStringExtra("imgUrl")
        if (intent.getStringExtra("imgUrl").isNullOrEmpty()) {
            authViewModel.profileUrl = ""
        }

//        binding.activityMyPageEtPhone.addTextChangedListener(PhoneNumberFormattingTextWatcher())
    }
}
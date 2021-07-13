package com.moon.android.mondaysally.ui.login

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.data.entities.Auth
import com.moon.android.mondaysally.data.remote.auth.listeners.LoginListener
import com.moon.android.mondaysally.databinding.ActivityLoginBinding
import com.moon.android.mondaysally.ui.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class LoginActivity: BaseActivity(), LoginListener {
    private lateinit var binding : ActivityLoginBinding
    private val viewModel : LoginViewModel by viewModel()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login)
        binding.lifecycleOwner = this
        binding.viewModel = viewModel

        viewModel.loginListener = this

        binding.loginLoginBtn.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        super.onClick(v)

        when(v){
            binding.loginLoginBtn -> viewModel.login()
        }
    }

    override fun onLoginStarted() {
        // 로딩바
    }

    override fun onLoginSuccess(auth: Auth) {
        // 로딩바 없애주는 작업

        // main 화면으로 넘어가는 코드

        Log.d("login-test", "$auth")
    }

    override fun onLoginFailure(code: Int, message: String) {
        // 로딩바 없애주는 작업

        // 실패에 대한 안내 팝업,

        Log.d("login-test", "$code : $message")
    }
}
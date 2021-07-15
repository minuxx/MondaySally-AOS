package com.moon.android.mondaysally.ui.login

import android.os.Bundle
import androidx.annotation.LayoutRes
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityLoginBinding
import com.moon.android.mondaysally.ui.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class LoginActivity : BaseActivity<ActivityLoginBinding>() {

//    val viewModel: LoginViewModel by viewModel()
    private val loginViewModel : LoginViewModel by viewModel()

    @LayoutRes
    override fun getLayoutResId() = R.layout.activity_login

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
    }

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = loginViewModel
    }

    override fun initAfterBinding() {
    }
}
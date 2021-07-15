package com.moon.android.mondaysally.ui.splash

import android.os.Bundle
import androidx.annotation.LayoutRes
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivitySplashBinding
import com.moon.android.mondaysally.ui.BaseActivity
import com.moon.android.mondaysally.ui.login.LoginActivity
import com.moon.android.mondaysally.ui.main.MainActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class SplashActivity : BaseActivity<ActivitySplashBinding>() {

    private val splashViewModel: SplashViewModel by viewModel()

    @LayoutRes
    override fun getLayoutResId() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        setContentView(R.layout.activity_login)
        super.onCreate(savedInstanceState)
    }

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = splashViewModel
    }

    override fun initAfterBinding() {
        splashViewModel.autoLoginResponse.observe(this, { islogin ->
            if (splashViewModel.autoLoginResponse.value == true) {
                startActivityWithClear(MainActivity::class.java)
            } else {
                startNextActivity(LoginActivity::class.java)
            }
            finish()
        })

        splashViewModel.isAutoLogin();
    }

//
//    override fun onAutoLoginSuccessWithChannelUrl(message: String, channelUrl: String) {
//        val intent = Intent(this@SplashActivity, MainActivity::class.java)
//        intent.putExtra("groupChannelUrl",channelUrl)
//
//        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TASK
//        intent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP) //푸시알람으로는 반드시 새로 activity 만들어야함
//
//        startActivity(intent)
//        finish()
//    }

//    override fun onOKClicked() {
//        when(mFlag){
//            FLAG_SERVER_CHECK,
//            FLAG_NETWORK_ERROR -> {
//
//                finish()
//            }
//
//            FLAG_VERSION_UPDATE -> {
//                val intent = Intent(Intent.ACTION_VIEW)
//                intent.data = Uri.parse("market://details?id=$packageName")
//                startActivity(intent)
//                finish()
//            }
//        }
//    }
}
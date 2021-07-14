package com.moon.android.mondaysally.ui.splash

import android.os.Bundle
import androidx.annotation.LayoutRes
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivitySplashBinding
import com.moon.android.mondaysally.ui.BaseActivity
import org.koin.androidx.viewmodel.ext.android.getViewModel


class SplashActivity: BaseActivity<ActivitySplashBinding>() {

    private lateinit var splashViewModel: SplashViewModel

    @LayoutRes
    override fun getLayoutResId() = R.layout.activity_splash

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        setContentView(R.layout.activity_splash)
        binding.lifecycleOwner = this;
        binding.viewModel = getViewModel()
        binding.viewModel?.let {
            splashViewModel = it
        }
    }

    override fun initDataBinding() {


    }

    override fun initAfterBinding() {
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
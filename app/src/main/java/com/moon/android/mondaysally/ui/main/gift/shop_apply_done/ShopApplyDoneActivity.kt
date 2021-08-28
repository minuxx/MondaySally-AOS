package com.moon.android.mondaysally.ui.main.gift.shop_apply_done

import android.content.Context
import android.content.Intent
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityShopApplyDoneBinding
import com.moon.android.mondaysally.ui.BaseActivity
import com.moon.android.mondaysally.ui.main.MainActivity
import com.moon.android.mondaysally.ui.main.gift.GiftViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ShopApplyDoneActivity : BaseActivity<ActivityShopApplyDoneBinding>() {

    private val giftViewModel: GiftViewModel by viewModel()

    lateinit var context: Context

    override fun getLayoutResId() = R.layout.activity_shop_apply_done

    override fun initDataBinding() {
        context = this;
        binding.lifecycleOwner = this;
        binding.viewModel = giftViewModel

        giftViewModel.goHome.observe(this, { goHome ->
            if (goHome) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                intent.putExtra("navigation", "home")
                startActivity(intent)
                finish()
            }
        })

        giftViewModel.goTwinkle.observe(this, { goHome ->
            if (goHome) {
                val mainActivityIntent = Intent(this, MainActivity::class.java)
                mainActivityIntent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                mainActivityIntent.putExtra("navigation", "twinklePost")
                mainActivityIntent.putExtra("idx", intent.getIntExtra("idx",0))
                mainActivityIntent.putExtra("name", intent.getStringExtra("name"))
                mainActivityIntent.putExtra("usedClover", intent.getIntExtra("usedClover",0))
                startActivity(mainActivityIntent)
                finish()
            }
        })
    }

    override fun initAfterBinding() {

    }
}
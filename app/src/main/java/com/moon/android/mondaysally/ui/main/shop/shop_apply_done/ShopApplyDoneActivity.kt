package com.moon.android.mondaysally.ui.main.shop.shop_apply_done

import android.content.Context
import android.content.Intent
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityShopApplyDoneBinding
import com.moon.android.mondaysally.ui.BaseActivity
import com.moon.android.mondaysally.ui.main.MainActivity
import com.moon.android.mondaysally.ui.main.shop.ShopViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel


class ShopApplyDoneActivity : BaseActivity<ActivityShopApplyDoneBinding>() {

    private val shopViewModel: ShopViewModel by viewModel()

    lateinit var context: Context

    override fun getLayoutResId() = R.layout.activity_shop_apply_done

    override fun initDataBinding() {
        context = this;
        binding.lifecycleOwner = this;
        binding.viewModel = shopViewModel

        shopViewModel.goHome.observe(this, { goHome ->
            if (goHome) {
                val intent = Intent(this, MainActivity::class.java)
                intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
                intent.putExtra("goHome",true)
                startActivity(intent)
                finish()
            }
        })
    }

    override fun initAfterBinding() {

    }
}
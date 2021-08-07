package com.moon.android.mondaysally.ui.main.shop.shop_detail

import android.view.View
import co.lujun.androidtagview.TagContainerLayout
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityShopDetailBinding
import com.moon.android.mondaysally.ui.BaseActivity
import com.moon.android.mondaysally.ui.main.shop.ShopViewModel
import org.koin.android.ext.android.bind
import org.koin.androidx.viewmodel.ext.android.viewModel


class ShopDetailActivity : BaseActivity<ActivityShopDetailBinding>() {

    private val shopViewModel: ShopViewModel by viewModel()

    override fun getLayoutResId() = R.layout.activity_shop_detail

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = shopViewModel

        shopViewModel.finishActivity.observe(this, { finishActivity ->
            finish()
        })

        shopViewModel.giftResult.observe(this, {giftResult ->
            binding.activityShopDetailTagOption.setOption(shopViewModel.giftResult.value?.options)
        })

        shopViewModel.fail.observe(this, { fail ->
            when (fail.code) {
                341, 388, 389 -> {
                    showToast(fail.message)
                }
                402 -> {
                    showToast(getString(R.string.default_fail))
                }
                404 -> {
                    showToast(getString(R.string.default_fail))
                }
            }
        })
    }

    override fun initAfterBinding() {
        shopViewModel.getGiftDetail(intent.getIntExtra("idx", 0))

    }

}
package com.moon.android.mondaysally.ui.main.shop

import com.moon.android.mondaysally.R

import com.moon.android.mondaysally.databinding.FragmentShopBinding
import com.moon.android.mondaysally.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShopFragment() :
    BaseFragment<FragmentShopBinding>() {

    private val shopViewModel: ShopViewModel by viewModel()

    override fun getLayoutResId() = R.layout.fragment_shop

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = shopViewModel

        shopViewModel.giftTotalCount.observe(this, { giftTotalCount ->

        })

        shopViewModel.isLoading.observe(this, { isLoading ->
            if (isLoading) {
            } else {
            }
        })

        binding.fragmentShopRvGift.adapter = GiftShopAdapter()
    }

    override fun initAfterBinding() {
        shopViewModel.getGiftList1()
    }

}
package com.moon.android.mondaysally.ui.main.shop

import com.moon.android.mondaysally.R

import com.moon.android.mondaysally.databinding.FragmentShopBinding
import com.moon.android.mondaysally.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class ShopFragment() :
    BaseFragment<FragmentShopBinding>() {

    private val tutorialViewModel: ShopViewModel by viewModel()

    override fun getLayoutResId() = R.layout.fragment_shop

    override fun initDataBinding() {
        binding.lifecycleOwner = activity;
        binding.viewModel = tutorialViewModel
    }

    override fun initAfterBinding() {

    }

}
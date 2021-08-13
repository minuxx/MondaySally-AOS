package com.moon.android.mondaysally.ui.main.twinkle

import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.FragmentTwinkleBinding
import com.moon.android.mondaysally.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TwinkleFragment() :
    BaseFragment<FragmentTwinkleBinding>() {

    private val twinkleViewModel: TwinkleViewModel by viewModel()

    override fun getLayoutResId() = R.layout.fragment_twinkle

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = twinkleViewModel
    }

    override fun initAfterBinding() {
        twinkleViewModel._getMyTwinkleList()
    }

}
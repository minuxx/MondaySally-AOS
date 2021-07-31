package com.moon.android.mondaysally.ui.main.twinkle

import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.FragmentHomeBinding
import com.moon.android.mondaysally.databinding.FragmentTwinkleBinding
import com.moon.android.mondaysally.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class TwinkleFragment() :
    BaseFragment<FragmentTwinkleBinding>() {

    private val tutorialViewModel: TwinkleViewModel by viewModel()

    override fun getLayoutResId() = R.layout.fragment_twinkle

    override fun initDataBinding() {
        binding.lifecycleOwner = activity;
        binding.viewModel = tutorialViewModel
    }

    override fun initAfterBinding() {

    }

}
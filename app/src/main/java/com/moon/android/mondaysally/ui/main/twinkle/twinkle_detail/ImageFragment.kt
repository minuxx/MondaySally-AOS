package com.moon.android.mondaysally.ui.main.twinkle.twinkle_detail

import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.FragmentImageBinding
import com.moon.android.mondaysally.ui.BaseFragment
import com.moon.android.mondaysally.ui.main.twinkle.TwinkleViewModel
import org.koin.androidx.viewmodel.ext.android.viewModel

class ImageFragment(val imgUrl: String) :
    BaseFragment<FragmentImageBinding>() {

    private val twinkleViewModel: TwinkleViewModel by viewModel()

    override fun getLayoutResId() = R.layout.fragment_image

    override fun initDataBinding() {
        binding.lifecycleOwner = activity;
        binding.viewModel = twinkleViewModel
        setImage(binding.fragmentImageIv,imgUrl)
    }

    override fun initAfterBinding() {

    }

}
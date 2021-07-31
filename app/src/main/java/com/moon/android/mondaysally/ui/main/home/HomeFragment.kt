package com.moon.android.mondaysally.ui.main.home

import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.FragmentHomeBinding
import com.moon.android.mondaysally.ui.BaseFragment
import org.koin.androidx.viewmodel.ext.android.viewModel

class HomeFragment() :
    BaseFragment<FragmentHomeBinding>() {

    private val tutorialViewModel: HomeViewModel by viewModel()

    override fun getLayoutResId() = R.layout.fragment_home

    override fun initDataBinding() {
        binding.lifecycleOwner = activity;
        binding.viewModel = tutorialViewModel
    }

    override fun initAfterBinding() {

    }

}
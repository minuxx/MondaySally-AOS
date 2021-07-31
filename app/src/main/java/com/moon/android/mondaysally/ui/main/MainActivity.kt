package com.moon.android.mondaysally.ui.main

import androidx.navigation.ui.NavigationUI
import androidx.navigation.findNavController
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityMainBinding

import com.moon.android.mondaysally.ui.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val mainViewModel: MainViewModel by viewModel()

    override fun getLayoutResId() = R.layout.activity_main

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = mainViewModel
    }

    override fun initAfterBinding() {
        NavigationUI.setupWithNavController(binding.navi, findNavController(R.id.navi_host))
    }
}
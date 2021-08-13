package com.moon.android.mondaysally.ui.main

import android.app.Activity
import android.app.Instrumentation
import android.content.Intent
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityMainBinding
import com.moon.android.mondaysally.ui.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val mainViewModel: MainViewModel by viewModel()
    override fun getLayoutResId() = R.layout.activity_main
    private lateinit var navHostFragment: NavHostFragment

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = mainViewModel

        mainViewModel.navigationFlag.observe(this, { navigationFlag ->
            if (navigationFlag == 1)
                navHostFragment.navController.navigate(R.id.homeFragment)
        })
    }

    override fun initAfterBinding() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController: NavController = navHostFragment.findNavController()
        binding.activityMainBottomNavigation.setupWithNavController(navController)
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
//        Log.d("인텐트", intent?.getBooleanExtra("goHome", false).toString())
        navHostFragment.navController.navigate(R.id.homeFragment)
    }
}
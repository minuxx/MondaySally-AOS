package com.moon.android.mondaysally.ui.main

import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationBarView
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityMainBinding
import com.moon.android.mondaysally.ui.BaseActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>(){
    private val mainViewModel: MainViewModel by viewModel()
    override fun getLayoutResId() = R.layout.activity_main

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = mainViewModel
    }

    override fun initAfterBinding() {
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController: NavController = navHostFragment.findNavController()
        binding.activityMainBottomNavigation.setupWithNavController(navController)
    }

    private inner class ViewPagerPageChangeCallback : NavigationBarView.OnItemSelectedListener {
        override fun onNavigationItemSelected(item: MenuItem): Boolean {
            val id = item.itemId
            when (id) {
                R.id.homeFragment -> 0
                R.id.shopFragment -> 0
                R.id.twinkleFragment -> 0
            }
            return true
        }
    }
}
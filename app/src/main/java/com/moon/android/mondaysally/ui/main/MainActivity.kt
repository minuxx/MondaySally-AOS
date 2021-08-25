package com.moon.android.mondaysally.ui.main

import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.databinding.ActivityMainBinding
import com.moon.android.mondaysally.ui.BaseActivity
import com.moon.android.mondaysally.ui.main.twinkle.twinkle_post.TwinklePostActivity
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
        when (intent?.getStringExtra("navigation")) {
            "home" -> navHostFragment.navController.navigate(R.id.homeFragment)
            "twinkle" -> navHostFragment.navController.navigate(R.id.twinkleFragment)
            "twinklePost" -> {
                navHostFragment.navController.navigate(R.id.twinkleFragment)
                val twinklePostIntent = Intent(this, TwinklePostActivity::class.java)
                twinklePostIntent.putExtra("idx", intent.getIntExtra("idx",0))
                twinklePostIntent.putExtra("name", intent.getStringExtra("name"))
                twinklePostIntent.putExtra("usedClover", intent.getIntExtra("usedClover",0))
                startActivity(twinklePostIntent)
            }
        }
    }
}
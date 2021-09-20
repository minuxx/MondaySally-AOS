package com.moon.android.mondaysally.ui.main


import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Intent
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.ktx.Firebase
import com.google.firebase.messaging.FirebaseMessaging
import com.google.firebase.messaging.ktx.messaging
import com.moon.android.mondaysally.R
import com.moon.android.mondaysally.data.repository.SharedPrefRepository
import com.moon.android.mondaysally.databinding.ActivityMainBinding
import com.moon.android.mondaysally.ui.BaseActivity
import com.moon.android.mondaysally.ui.main.auth.MyPageActivity
import com.moon.android.mondaysally.ui.main.auth.qr_camera.QRCameraActivity
import com.moon.android.mondaysally.ui.main.gift.gift_history.GiftHistoryActivity
import com.moon.android.mondaysally.ui.main.twinkle.twinkle_detail.TwinkleDetailActivity
import com.moon.android.mondaysally.ui.main.twinkle.twinkle_post.TwinklePostActivity
import org.koin.androidx.viewmodel.ext.android.viewModel


class MainActivity : BaseActivity<ActivityMainBinding>() {
    private val mainViewModel: MainViewModel by viewModel()
    override fun getLayoutResId() = R.layout.activity_main
    private lateinit var navHostFragment: NavHostFragment

    private lateinit var notificationManager: NotificationManager

    override fun initDataBinding() {
        binding.lifecycleOwner = this;
        binding.viewModel = mainViewModel

        mainViewModel.navigationFlag.observe(this, { navigationFlag ->
            if (navigationFlag == 1)
                navHostFragment.navController.navigate(R.id.homeFragment)
        })

        mainViewModel.goMyPage.observe(this, { goMyPage ->
            if (goMyPage)
                startNextActivity(MyPageActivity::class.java)
        })

        mainViewModel.goQR.observe(this, { goQR ->
            if (goQR)
                startNextActivity(QRCameraActivity::class.java)
        })
    }

    override fun initAfterBinding() {
        navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController: NavController = navHostFragment.findNavController()
        binding.activityMainBottomNavigation.setupWithNavController(navController)

        Firebase.messaging.isAutoInitEnabled = true
        tokenCheck()
        setNotificationChannel()
        notificationFlagCheck()
    }

    override fun onNewIntent(intent: Intent?) {
        super.onNewIntent(intent)
        when (intent?.getStringExtra("navigation")) {
            "home" -> navHostFragment.navController.navigate(R.id.homeFragment)
            "twinkle" -> navHostFragment.navController.navigate(R.id.twinkleFragment)
            "twinklePost" -> {
                navHostFragment.navController.navigate(R.id.twinkleFragment)
                val twinklePostIntent = Intent(this, TwinklePostActivity::class.java)
                twinklePostIntent.putExtra("idx", intent.getIntExtra("idx", 0))
                twinklePostIntent.putExtra("name", intent.getStringExtra("name"))
                twinklePostIntent.putExtra("usedClover", intent.getIntExtra("usedClover", 0))
                startActivity(twinklePostIntent)
            }
        }
    }

    private fun tokenCheck() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener(OnCompleteListener { task ->
            if (!task.isSuccessful) {
                return@OnCompleteListener
            }
            val token = task.result
            token?.let {
                mainViewModel.postFcmToken(it)
            }
        })
    }

    private fun setNotificationChannel() {
        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            val notificationChannel: NotificationChannel =
                NotificationChannel("CHANNEL", "먼데이샐리", NotificationManager.IMPORTANCE_HIGH)
            notificationChannel.enableLights(true)
            notificationChannel.lightColor = getColor(R.color.pinkish_orange)
            notificationChannel.enableVibration(true)
            notificationChannel.description = "description"
            notificationChannel.setShowBadge(true)
            notificationManager.createNotificationChannel(notificationChannel)
        }
    }

    private fun notificationFlagCheck() {
        val sharedPrefRepository = SharedPrefRepository(this)
        if (sharedPrefRepository.notificationCategory != null) {
            when (sharedPrefRepository.notificationCategory) {
                //좋아요
                "좋아요" -> {
                    navHostFragment.navController.navigate(R.id.twinkleFragment)
                    val intent = Intent(this, TwinkleDetailActivity::class.java)
                    intent.putExtra("idx", sharedPrefRepository.notificationTwinkleIdx)
                    startActivity(intent)
                }
                //댓글
                "댓글" -> {
                    navHostFragment.navController.navigate(R.id.twinkleFragment)
                    val intent = Intent(this, TwinkleDetailActivity::class.java)
                    intent.putExtra("idx", sharedPrefRepository.notificationTwinkleIdx)
                    startActivity(intent)
                }
                //승인/거절
                "기프트" -> {
                    startActivity(Intent(this, GiftHistoryActivity::class.java))
                }
                //출퇴근
                "출근", "퇴근" -> {

                }
            }
            sharedPrefRepository.clearNotificationFlag()
        }
    }
}
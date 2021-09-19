package com.moon.android.mondaysally

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Intent
import android.util.Log
import androidx.core.app.NotificationCompat
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import com.moon.android.mondaysally.data.repository.SharedPrefRepository
import com.moon.android.mondaysally.ui.splash.SplashActivity
import com.moon.android.mondaysally.utils.GlobalConstant

class MyFirebaseMessagingService :
    FirebaseMessagingService() {

    private lateinit var notificationManager: NotificationManager

    override fun onNewToken(token: String) {
        Log.d("네트워크", "Refreshed token: $token")
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        // TODO(developer): Handle FCM messages here.
        Log.d("네트워크", "From: ${remoteMessage.from}")

        // Check if message contains a data payload.
        if (remoteMessage.data.isNotEmpty()) {
            Log.d("네트워크", "Message data payload: ${remoteMessage.data}")
            payloadProcess(remoteMessage.data)
            if (true/* Check if data needs to be processed by long running job */) {
                // For long-running tasks (10 seconds or more) use WorkManager.
//                scheduleJob()
            } else {
                // Handle message within 10 seconds
//                handleNow()
            }
        }
        remoteMessage.notification?.let {
            Log.d("네트워크", "Message Notification Body: ${it.body}")
        }
    }

    private fun payloadProcess(data: Map<String, String>) {
        val sharedPrefRepository = SharedPrefRepository(this)
        val intent = Intent(this, SplashActivity::class.java)
        intent.flags = Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP
        if (data["twinkleIdx"] != null) {
//            intent.putExtra("twinkleIdx", Integer.valueOf(data["twinkleIdx"]!!))
            sharedPrefRepository.saveNotificationTwinkleIdx(Integer.valueOf(data["twinkleIdx"]!!))
        }
        if (data["permission"] != null) {
//            intent.putExtra("permission", data["permission"]!!)
            sharedPrefRepository.saveNotificationPermission(data["permission"])

        }
        if (data["category"] != null) {
//            intent.putExtra("category", data["category"]!!)
            sharedPrefRepository.saveNotificationCategory(data["category"])
        }

        val pendingIntent: PendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        val builder = NotificationCompat.Builder(this, "CHANNEL")
            .setSmallIcon(R.drawable.app_store_512_px)
            .setContentTitle(data["title"])
            .setContentText(data["body"])
            .setContentIntent(pendingIntent)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setDefaults(NotificationCompat.DEFAULT_ALL)
            .setAutoCancel(true)

        notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(GlobalConstant.NOTIFICATION_ID, builder.build())
    }
}
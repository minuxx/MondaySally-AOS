package com.moon.android.mondaysally.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.moon.android.mondaysally.utils.GlobalConstant
import com.moon.android.mondaysally.utils.GlobalConstant.Companion.NOTIFICATION_CATEGORY
import com.moon.android.mondaysally.utils.GlobalConstant.Companion.NOTIFICATION_PERMISSION
import com.moon.android.mondaysally.utils.GlobalConstant.Companion.NOTIFICATION_TWINKLE_IDX
import com.moon.android.mondaysally.utils.GlobalConstant.Companion.WORK_STATUS
import com.moon.android.mondaysally.utils.GlobalConstant.Companion.WORK_STATUS_OFF
import com.moon.android.mondaysally.utils.GlobalConstant.Companion.WORK_STATUS_ON
import com.moon.android.mondaysally.utils.GlobalConstant.Companion.X_ACCESS_TOKEN

class SharedPrefRepository(private val context: Context) {
//    var sharedPreferencesManager: SharedPreferencesManager = SharedPreferencesManager(context)

    fun getSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(GlobalConstant.TAG, Context.MODE_PRIVATE)
    }

    fun getJwtToken() : String? {
        return getSharedPreferences().getString(X_ACCESS_TOKEN, null)
    }

    val nickname: String? = getSharedPreferences()
        .getString(GlobalConstant.NICKNAME, null)

    val isFirstLaunch: Boolean = getSharedPreferences()
        .getBoolean(GlobalConstant.FIRST_LAUNCH, true)

    fun saveNickName(nickname: String) {
        val spf = getSharedPreferences()
        val editor = spf.edit()
        editor.putString(GlobalConstant.NICKNAME, nickname)
        editor.apply()
    }

    fun saveJwtToken(jwtToken: String) {
        val spf = getSharedPreferences()
        val editor = spf.edit()
        editor.putString(X_ACCESS_TOKEN, jwtToken)
        editor.apply()
    }

    fun noMoreTutorial() {
        getSharedPreferences().edit()
            .putBoolean(GlobalConstant.FIRST_LAUNCH, false)
            .apply()
    }

    fun saveNotificationTwinkleIdx(twinkleIdx: Int) {
        val spf = getSharedPreferences()
        val editor = spf.edit()
        editor.putInt(NOTIFICATION_TWINKLE_IDX, twinkleIdx)
        editor.apply()
    }

    fun saveNotificationPermission(permission: String?) {
        val spf = getSharedPreferences()
        val editor = spf.edit()
        editor.putString(NOTIFICATION_PERMISSION, permission)
        editor.apply()
    }

    fun saveNotificationCategory(category: String?) {
        val spf = getSharedPreferences()
        val editor = spf.edit()
        editor.putString(NOTIFICATION_CATEGORY, category)
        editor.apply()
    }

    fun clearNotificationFlag() {
        val spf = getSharedPreferences()
        val editor = spf.edit()
        editor.remove(NOTIFICATION_TWINKLE_IDX)
        editor.remove(NOTIFICATION_PERMISSION)
        editor.remove(NOTIFICATION_CATEGORY)
        editor.apply()
    }

    val notificationTwinkleIdx: Int = getSharedPreferences()
        .getInt(NOTIFICATION_TWINKLE_IDX, 0)
    val notificationPermission: String? = getSharedPreferences()
        .getString(NOTIFICATION_PERMISSION, null)
    val notificationCategory: String? = getSharedPreferences()
        .getString(NOTIFICATION_CATEGORY, null)

    fun saveWorkStatus(status: String?) {
        val spf = getSharedPreferences()
        val editor = spf.edit()
        editor.putString(WORK_STATUS, status)
        editor.apply()
    }

    fun getWorkState() : String? {
        return getSharedPreferences().getString(NOTIFICATION_CATEGORY, WORK_STATUS_OFF)
    }
}
package com.moon.android.mondaysally.data.repository

import android.content.Context
import android.content.SharedPreferences
import com.moon.android.mondaysally.utils.GlobalConstant

class SharedPrefRepository(private val context: Context) {
//    var sharedPreferencesManager: SharedPreferencesManager = SharedPreferencesManager(context)

    fun getSharedPreferences(): SharedPreferences {
        return context.getSharedPreferences(GlobalConstant.TAG, Context.MODE_PRIVATE)
    }

    val jwtToken: String? = getSharedPreferences()
        .getString(GlobalConstant.X_ACCESS_TOKEN, null)

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
        editor.putString(GlobalConstant.X_ACCESS_TOKEN, jwtToken)
        editor.apply()
    }

    fun noMoreTutorial() {
        getSharedPreferences().edit()
            .putBoolean(GlobalConstant.FIRST_LAUNCH, false)
            .apply()
    }
}
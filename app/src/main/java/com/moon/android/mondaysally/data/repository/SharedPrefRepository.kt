package com.moon.android.mondaysally.data.repository

import android.content.Context
import android.text.BoringLayout
import com.moon.android.mondaysally.utils.GlobalConstant
import com.moon.android.mondaysally.utils.SharedPreferencesManager

class SharedPrefRepository(context: Context) {
    var sharedPreferencesManager: SharedPreferencesManager = SharedPreferencesManager(context)
    val jwtToken: String? = sharedPreferencesManager.getSharedPreferences()
        .getString(GlobalConstant.X_ACCESS_TOKEN, null)

    val isFirstLaunch: Boolean = sharedPreferencesManager.getSharedPreferences()
        .getBoolean(GlobalConstant.FIRST_LAUNCH, true)

    fun saveJwtToken(jwtToken: String) {
        sharedPreferencesManager.saveJwtToken(jwtToken);
    }

}
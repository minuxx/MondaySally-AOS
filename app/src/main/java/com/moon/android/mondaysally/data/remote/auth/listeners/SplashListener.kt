package com.moon.android.mondaysally.data.remote.auth.listeners

import com.moon.android.mondaysally.data.entities.Auth

interface SplashListener {
    fun onStarted()
    fun onAutoLoginSuccess(message: String)
    fun onAutoLoginFailure(code: Int, message: String)
    fun onAutoLoginSuccessWithChannelUrl(message:String, channelUrl:String)

    fun onGetVersionSuccess(auth: Auth)
    fun onGetVersionFailure(code: Int, message: String)
}
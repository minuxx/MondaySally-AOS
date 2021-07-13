package com.moon.android.mondaysally.data.remote.auth.listeners

import com.moon.android.mondaysally.data.entities.Auth


interface LoginListener {
    fun onLoginStarted()
    fun onLoginSuccess(auth: Auth)
    fun onLoginFailure(code: Int, message: String)
}
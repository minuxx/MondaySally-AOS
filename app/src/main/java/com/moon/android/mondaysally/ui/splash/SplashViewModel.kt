package com.moon.android.mondaysally.ui.splash

import androidx.lifecycle.ViewModel
import com.moon.android.mondaysally.data.remote.auth.listeners.SplashListener
import com.moon.android.mondaysally.data.repository.auth.AuthNetworkRepository
import com.moon.android.mondaysally.utils.ApiException
import com.moon.android.mondaysally.utils.Coroutines
import com.moon.android.mondaysally.utils.SharedPreferencesManager

import kotlinx.coroutines.delay

class SplashViewModel(private val repository: AuthNetworkRepository, private val sharedPreferencesManager: SharedPreferencesManager) : ViewModel() {
    var splashListener: SplashListener? = null

    init {
        getVersion()
    }

    fun autoLogin() {
        splashListener?.onStarted()

        Coroutines.main {
            try {
                delay(3000)
                val authResponse = repository.autoLogin()

                if(authResponse.isSuccess) {
                    authResponse.auth?.let {
                        splashListener?.onAutoLoginSuccess(authResponse.message)
                        sharedPreferencesManager.saveJwtToken(authResponse.auth.jwtToken!!)
                        return@main
                    }
                }else{
                    splashListener?.onAutoLoginFailure(authResponse.code, authResponse.message)
                }
            } catch (e: ApiException) {
                splashListener?.onAutoLoginFailure(404, e.message!!)
            } catch (e: Exception){
                splashListener?.onAutoLoginFailure(404, e.message!!)
            }
        }
    }

    fun autoLoginWithChannelUrl(channelUrl:String){
        Coroutines.main {
            try {
                delay(1000)
                val authResponse = repository.autoLogin()

                if(authResponse.isSuccess) {
                    authResponse.auth?.let {
                        splashListener?.onAutoLoginSuccessWithChannelUrl(authResponse.message,channelUrl)
                        sharedPreferencesManager.saveJwtToken(authResponse.auth.jwtToken!!)
                        return@main
                    }
                }else{
                    splashListener?.onAutoLoginFailure(authResponse.code, authResponse.message)
                }
            } catch (e: ApiException) {
                splashListener?.onAutoLoginFailure(404, e.message!!)
            } catch (e: Exception){
                splashListener?.onAutoLoginFailure(404, e.message!!)
            }
        }
    }

    private fun getVersion() {
        splashListener?.onStarted()

        Coroutines.main {
            try {
                val authResponse = repository.getVersion()

                if(authResponse.isSuccess) {
                    authResponse.auth?.let {
                        splashListener?.onGetVersionSuccess(authResponse.auth)
                        return@main
                    }
                }else{
                    splashListener?.onGetVersionFailure(authResponse.code, authResponse.message)
                }
            } catch (e: ApiException) {
                splashListener?.onGetVersionFailure(404, e.message!!)
            } catch (e: Exception){
                splashListener?.onGetVersionFailure(404, e.message!!)
            }
        }
    }
}
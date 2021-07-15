package com.moon.android.mondaysally.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moon.android.mondaysally.data.repository.SharedPrefRepository
import com.moon.android.mondaysally.data.repository.auth.AuthNetworkRepository
import com.moon.android.mondaysally.utils.Coroutines


class SplashViewModel(
    private val sharedPrefRepository: SharedPrefRepository,
    private val authNetworkRepository: AuthNetworkRepository
) : ViewModel() {

    //view 에서 observe하고있을 변수
    var autoLoginResponse: MutableLiveData<Boolean> = MutableLiveData()

    fun isAutoLogin() {
        val jwtToken = sharedPrefRepository.jwtToken
        if (jwtToken != null) {
            tokenCheck()
        } else {
            autoLoginResponse.value = false;
        }
    }

    private fun tokenCheck() {
        Coroutines.main {
            try {
//                delay(3000)
                val authResponse = authNetworkRepository.autoLogin()

                if (authResponse.isSuccess) {
                    authResponse.auth?.let {
                        splashListener?.onAutoLoginSuccess(authResponse.message)
                        sharedPreferencesManager.saveJwtToken(authResponse.auth.jwtToken!!)
                        return@main
                    }
                } else {
                    splashListener?.onAutoLoginFailure(authResponse.code, authResponse.message)
                }
            } catch (e: ApiException) {
                splashListener?.onAutoLoginFailure(404, e.message!!)
            } catch (e: Exception) {
                splashListener?.onAutoLoginFailure(404, e.message!!)
            }
        }
    }


//    private fun getVersion() {
//        splashListener?.onStarted()
//
//        Coroutines.main {
//            try {
//                val authResponse = repository.getVersion()
//
//                if (authResponse.isSuccess) {
//                    authResponse.auth?.let {
//                        splashListener?.onGetVersionSuccess(authResponse.auth)
//                        return@main
//                    }
//                } else {
//                    splashListener?.onGetVersionFailure(authResponse.code, authResponse.message)
//                }
//            } catch (e: ApiException) {
//                splashListener?.onGetVersionFailure(404, e.message!!)
//            } catch (e: Exception) {
//                splashListener?.onGetVersionFailure(404, e.message!!)
//            }
//        }
//    }
}
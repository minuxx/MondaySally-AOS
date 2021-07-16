package com.moon.android.mondaysally.ui.splash

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moon.android.mondaysally.data.remote.Fail
import com.moon.android.mondaysally.data.repository.SharedPrefRepository
import com.moon.android.mondaysally.data.repository.auth.AuthNetworkRepository
import com.moon.android.mondaysally.utils.ApiException
import com.moon.android.mondaysally.utils.Coroutines


class SplashViewModel(
    private val sharedPrefRepository: SharedPrefRepository,
    private val authNetworkRepository: AuthNetworkRepository
) : ViewModel() {

    //view 에서 observe하고있을 변수
    var isAutoLogin: MutableLiveData<Boolean> = MutableLiveData()
    var fail: MutableLiveData<Fail> = MutableLiveData()

    fun autoLoginCheck() {
        val jwtToken = sharedPrefRepository.jwtToken
        tokenCheck()
//        if (jwtToken != null) {
//            tokenCheck()
//        } else {
//            isAutoLogin.value = false
//        }
    }

    private fun tokenCheck() {
        Log.d("통신", "start")
        Coroutines.main {
            try {
//                delay(3000)
                val authResponse = authNetworkRepository.autoLogin()
                Log.d("통신", authResponse.message)
                if (authResponse.isSuccess) {
                    authResponse.auth?.let {
                        isAutoLogin.value = true
                        sharedPrefRepository.saveJwtToken(authResponse.auth.jwtToken!!)
                        return@main
                    }
                } else {
                    fail.value = Fail(authResponse.message, authResponse.code)
                }
            } catch (e: ApiException) {
                fail.value = Fail(e.message!!, 404)
            } catch (e: Exception) {
                fail.value = Fail(e.message!!, 404)
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
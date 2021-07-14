package com.moon.android.mondaysally.ui.splash

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.moon.android.mondaysally.data.repository.SharedPrefRepository
import kotlinx.coroutines.Dispatchers.Main
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class SplashViewModel(application: Application) : AndroidViewModel(application) {

    var sharedPrefRepository = SharedPrefRepository(application)

    //view 에서 observe하고있을 변수
    var isAutoLoginLive: MutableLiveData<Boolean> = MutableLiveData()


    init {
//        isAutoLoginLive.value = false;
    }

    fun isAutoLogin() {
        val jwtToken = sharedPrefRepository.jwtToken
        isAutoLoginLive.value = jwtToken != null
//        viewModelScope.launch {
//            val jwtToken = sharedPrefRepository.jwtToken
//            withContext(Main) {
//                isAutoLoginLive.value = jwtToken != null
//            }
//        }
    }

//    fun autoLogin() {
//        splashListener?.onStarted()
//
//        Coroutines.main {
//            try {
//                delay(3000)
//                val authResponse = repository.autoLogin()
//
//                if(authResponse.isSuccess) {
//                    authResponse.auth?.let {
//                        splashListener?.onAutoLoginSuccess(authResponse.message)
//                        sharedPreferencesManager.saveJwtToken(authResponse.auth.jwtToken!!)
//                        return@main
//                    }
//                }else{
//                    splashListener?.onAutoLoginFailure(authResponse.code, authResponse.message)
//                }
//            } catch (e: ApiException) {
//                splashListener?.onAutoLoginFailure(404, e.message!!)
//            } catch (e: Exception){
//                splashListener?.onAutoLoginFailure(404, e.message!!)
//            }
//        }
//    }


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
package com.moon.android.mondaysally.ui.splash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moon.android.mondaysally.data.remote.Fail
import com.moon.android.mondaysally.data.repository.SharedPrefRepository
import com.moon.android.mondaysally.data.repository.auth.AuthNetworkRepository
import com.moon.android.mondaysally.utils.ApiException
import kotlinx.coroutines.launch


class SplashViewModel(
    private val sharedPrefRepository: SharedPrefRepository,
    private val authNetworkRepository: AuthNetworkRepository
) : ViewModel() {

    var serverAccessible: MutableLiveData<Boolean> = MutableLiveData()
    var firstLaunch: MutableLiveData<Boolean> = MutableLiveData()
    var autoLogin: MutableLiveData<Boolean> = MutableLiveData()
    var fail: MutableLiveData<Fail> = MutableLiveData()

    fun autoLoginCheck() {
        val jwtToken = sharedPrefRepository.jwtToken
        if (jwtToken != null) {
            tokenCheck()
        } else {
            autoLogin.value = false
        }
    }

    private fun tokenCheck() = viewModelScope.launch {
        try {
            val authResponse = authNetworkRepository.autoLogin()
            if (authResponse.isSuccess) {
                authResponse.auth?.let {
                    autoLogin.value = true
                    sharedPrefRepository.saveJwtToken(authResponse.auth.jwtToken!!)
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

    fun serverVersionCheck() = viewModelScope.launch {
        try {
            val authResponse = authNetworkRepository.getVersion()
            if (authResponse.code == 200) {
                authResponse.auth?.let {
                    serverAccessible.value = true
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

    fun firstLaunchCheck() {
        val isFirstLaunch = sharedPrefRepository.isFirstLaunch
        firstLaunch.value = isFirstLaunch
    }

}
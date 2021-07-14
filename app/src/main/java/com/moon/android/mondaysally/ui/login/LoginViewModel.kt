package com.moon.android.mondaysally.ui.login

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.moon.android.mondaysally.data.entities.User
import com.moon.android.mondaysally.data.remote.auth.listeners.LoginListener
import com.moon.android.mondaysally.data.repository.auth.AuthNetworkRepository
import com.moon.android.mondaysally.utils.ApiException
import com.moon.android.mondaysally.utils.Coroutines
import com.moon.android.mondaysally.utils.SharedPreferencesManager

class LoginViewModel(private val repository: AuthNetworkRepository, private val sharedPreferencesManager: SharedPreferencesManager): ViewModel() {
    var loginListener: LoginListener? = null

    val id: MutableLiveData<String> by lazy {
        MutableLiveData<String>().apply {
            postValue("")
        }
    }

    val pw: MutableLiveData<String> by lazy {
        MutableLiveData<String>().apply {
            postValue("")
        }
    }

    private fun getUser(): User = User(id= id.value.toString(),pw= pw.value.toString())

    fun login() {
        loginListener?.onLoginStarted()

        val user = getUser()

        Coroutines.main {
            try {
                val authResponse = repository.login(getUser())

                if(authResponse.isSuccess) {
                    authResponse.auth?.let {
                        loginListener?.onLoginSuccess(authResponse.auth)
                        sharedPreferencesManager.saveJwtToken(authResponse.auth.jwtToken!!)

                        return@main
                    }
                }else{
                    loginListener?.onLoginFailure(authResponse.code, authResponse.message)
                }
            } catch (e: ApiException) {
                loginListener?.onLoginFailure(404, e.message!!)
            } catch (e: Exception){
                loginListener?.onLoginFailure(404, e.message!!)
            }
        }
    }
}
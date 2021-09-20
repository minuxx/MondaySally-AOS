package com.moon.android.mondaysally.ui.main

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moon.android.mondaysally.data.entities.FcmBody
import com.moon.android.mondaysally.data.repository.network.AuthNetworkRepository
import kotlinx.coroutines.launch


class MainViewModel(private val authNetworkRepository: AuthNetworkRepository) : ViewModel() {

    var navigationFlag: MutableLiveData<Int> = MutableLiveData()
    var goMyPage: MutableLiveData<Boolean> = MutableLiveData()
    var goQR: MutableLiveData<Boolean> = MutableLiveData()

    fun whenMyPageClicked() {
        goMyPage.value = true
    }

    fun whenQRClicked() {
        goQR.value = true
    }

    fun postFcmToken(token: String) = viewModelScope.launch {
        try {
            val authResponse =
                authNetworkRepository.postFcmToken(
                    FcmBody(
                        token, "Y"
                    )
                )
//            if (authResponse.code == 200) {
                Log.d("네트워크", authResponse.toString())
//            }
        } catch (e: Exception) {
//            fail.value = Fail(e.message!!, 404)
        } catch (e: Exception) {
//            fail.value = Fail(e.message!!, 404)
        }
    }
}
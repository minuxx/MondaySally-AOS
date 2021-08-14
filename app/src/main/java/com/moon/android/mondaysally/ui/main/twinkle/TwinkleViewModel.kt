package com.moon.android.mondaysally.ui.main.twinkle

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moon.android.mondaysally.data.entities.MyTwinkle
import com.moon.android.mondaysally.data.entities.Twinkle
import com.moon.android.mondaysally.data.remote.Fail
import com.moon.android.mondaysally.data.repository.network.TwinkleNetworkRepository

import com.moon.android.mondaysally.utils.ApiException
import com.moon.android.mondaysally.utils.ListLiveData
import kotlinx.coroutines.launch


class TwinkleViewModel(private val giftNetworkRepository: TwinkleNetworkRepository) : ViewModel() {

    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    val myTwinkleList = ListLiveData<MyTwinkle>()
    val twinkleList = ListLiveData<Twinkle>()
    var fail: MutableLiveData<Fail> = MutableLiveData()

    fun _getMyTwinkleList() = viewModelScope.launch {
        try {
            val myTwinkleResponse = giftNetworkRepository.getMyTwinkleList(1)
            if (myTwinkleResponse.code == 200) {
                myTwinkleResponse.result?.let {
                    myTwinkleList.clear()
                    myTwinkleList.addAll(myTwinkleResponse.result.giftLogs)
                }
            } else {
                fail.value = Fail(myTwinkleResponse.message, myTwinkleResponse.code)
            }
        } catch (e: ApiException) {
            fail.value = Fail(e.message!!, 404)
        } catch (e: Exception) {
            fail.value = Fail(e.message!!, 404)
        }
    }

    fun _getTwinkleList() = viewModelScope.launch {
        try {
            val twinkleResponse = giftNetworkRepository.getTwinkleList(1)
            isLoading.value = false
            if (twinkleResponse.code == 200) {
                twinkleResponse.result?.let {
                    twinkleList.clear()
                    twinkleList.addAll(twinkleResponse.result.twinkles)
                }
            } else {
                fail.value = Fail(twinkleResponse.message, twinkleResponse.code)
            }
        } catch (e: ApiException) {
            fail.value = Fail(e.message!!, 404)
        } catch (e: Exception) {
            fail.value = Fail(e.message!!, 404)
        }
    }
}
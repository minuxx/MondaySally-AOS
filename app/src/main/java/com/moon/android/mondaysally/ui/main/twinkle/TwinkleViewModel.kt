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

    val myTwinkleList = ListLiveData<MyTwinkle>()
    val twinkleList = ListLiveData<Twinkle>()
    var fail: MutableLiveData<Fail> = MutableLiveData()

    fun _getMyTwinkleList() = viewModelScope.launch {
        try {
            val myTwinkleResponse = giftNetworkRepository.getMyTwinkleList(1)
            Log.d("네트워크", myTwinkleResponse.result.toString())
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
            Log.d("네트워크", e.toString())
            fail.value = Fail(e.message!!, 404)
        }
    }

}
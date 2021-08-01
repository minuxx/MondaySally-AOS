package com.moon.android.mondaysally.ui.main.home

import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moon.android.mondaysally.data.entities.GiftHistory
import com.moon.android.mondaysally.data.entities.Home
import com.moon.android.mondaysally.data.entities.Member
import com.moon.android.mondaysally.data.remote.Fail
import com.moon.android.mondaysally.data.repository.auth.HomeNetworkRepository
import com.moon.android.mondaysally.utils.ApiException
import com.moon.android.mondaysally.utils.ListLiveData
import kotlinx.coroutines.launch


class HomeViewModel(private val homeNetworkRepository: HomeNetworkRepository) : ViewModel() {

    var homeResult: MutableLiveData<Home> = MutableLiveData()

    val giftHistoryList = ListLiveData<GiftHistory>()
    val memberList = ListLiveData<Member>()
    var fail: MutableLiveData<Fail> = MutableLiveData()

    fun getHomeData() = viewModelScope.launch {
        try {
            val homeResponse = homeNetworkRepository.getHome()
            if (homeResponse.code == 200) {
                homeResponse.result?.let {
                    giftHistoryList.addAll(homeResponse.result.giftHistory)
                    memberList.addAll(homeResponse.result.workingMemberlist)
                    homeResult.value = homeResponse.result
                    Log.d("네트워크", homeResponse.result.toString())
                }
            } else {
                fail.value = Fail(homeResponse.message, homeResponse.code)
            }
        } catch (e: ApiException) {
            fail.value = Fail(e.message!!, 404)
        } catch (e: Exception) {
            fail.value = Fail(e.message!!, 404)
        }
    }
}
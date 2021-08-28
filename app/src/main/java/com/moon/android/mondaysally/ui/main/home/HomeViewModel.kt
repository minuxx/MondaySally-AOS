package com.moon.android.mondaysally.ui.main.home

import android.util.Log
import androidx.lifecycle.MutableLiveData

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moon.android.mondaysally.data.entities.GiftHistory
import com.moon.android.mondaysally.data.entities.HomeResult
import com.moon.android.mondaysally.data.entities.Member
import com.moon.android.mondaysally.data.remote.Fail
import com.moon.android.mondaysally.data.repository.SharedPrefRepository
import com.moon.android.mondaysally.data.repository.network.HomeNetworkRepository
import com.moon.android.mondaysally.utils.ApiException
import com.moon.android.mondaysally.utils.ListLiveData
import kotlinx.coroutines.launch


class HomeViewModel(
    private val homeNetworkRepository: HomeNetworkRepository,
    private val sharedPrefRepository: SharedPrefRepository,
) : ViewModel() {

    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var goGiftHistory: MutableLiveData<Boolean> = MutableLiveData()
    var goTwinkleRanking: MutableLiveData<Boolean> = MutableLiveData()
    var goCloverHistory: MutableLiveData<Boolean> = MutableLiveData()

    var homeResultResult: MutableLiveData<HomeResult> = MutableLiveData()
    val giftHistoryList = ListLiveData<GiftHistory>()
    val memberList = ListLiveData<Member>()

    var fail: MutableLiveData<Fail> = MutableLiveData()

    fun getHomeData() = viewModelScope.launch {
        try {
            isLoading.value = true
            val homeResponse = homeNetworkRepository.getHome()
            isLoading.value = false
            if (homeResponse.code == 200) {
                homeResponse.result?.let {
                    giftHistoryList.clear()
                    memberList.clear()
                    giftHistoryList.addAll(homeResponse.result.giftHistory)
                    memberList.addAll(homeResponse.result.workingMemberlist)
                    homeResultResult.value = homeResponse.result
                    sharedPrefRepository.saveNickName(homeResponse.result.nickname)
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

    fun whenMoreGiftHistoryClicked() {
        goGiftHistory.value = true
    }

    fun whenMoreRankingClicked() {
        goTwinkleRanking.value = true
    }

    fun whenCloverClicked() {
        goCloverHistory.value = true
    }
}
package com.moon.android.mondaysally.ui.main.clover

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.moon.android.mondaysally.data.entities.CloverResult
import com.moon.android.mondaysally.data.entities.TwinkleRanking
import com.moon.android.mondaysally.data.remote.Fail
import com.moon.android.mondaysally.data.repository.SharedPrefRepository
import com.moon.android.mondaysally.data.repository.network.CloverNetworkRepository
import com.moon.android.mondaysally.ui.main.clover.clover_history.paging.AccumCloverHistoryPagingSource
import com.moon.android.mondaysally.ui.main.clover.clover_history.paging.NowCloverHistoryPagingSource
import com.moon.android.mondaysally.ui.main.clover.clover_history.paging.UsedCloverHistoryPagingSource
import com.moon.android.mondaysally.ui.main.clover.paging.TwinkleRankingPagingSource
import com.moon.android.mondaysally.utils.ApiException
import com.moon.android.mondaysally.utils.ListLiveData
import kotlinx.coroutines.launch

class CloverViewModel(
    private var cloverNetworkRepository: CloverNetworkRepository,
    val sharedPrefRepository: SharedPrefRepository,
) : ViewModel() {

    var finishActivity: MutableLiveData<Boolean> = MutableLiveData()
    var fail: MutableLiveData<Fail> = MutableLiveData()

    var goMoreGift: MutableLiveData<Boolean> = MutableLiveData()
    var nickname: MutableLiveData<String> = MutableLiveData()
    var test = ""
    val rankingList = ListLiveData<TwinkleRanking>()

    //CloverHistory
    var cloverHistoryResult: MutableLiveData<CloverResult> = MutableLiveData()

    val twinkleRankingFlow = Pager(PagingConfig(pageSize = 20)) {
        TwinkleRankingPagingSource(cloverNetworkRepository.cloverService)
    }.flow.cachedIn(viewModelScope)

    val nowCloverHistoryFlow = Pager(PagingConfig(pageSize = 20)) {
        NowCloverHistoryPagingSource(cloverNetworkRepository.cloverService)
    }.flow.cachedIn(viewModelScope)

    val accumCloverHistoryFlow = Pager(PagingConfig(pageSize = 20)) {
        AccumCloverHistoryPagingSource(cloverNetworkRepository.cloverService)
    }.flow.cachedIn(viewModelScope)

    val usedCloverHistoryFlow = Pager(PagingConfig(pageSize = 20)) {
        UsedCloverHistoryPagingSource(cloverNetworkRepository.cloverService)
    }.flow.cachedIn(viewModelScope)

    fun _getRankingList() = viewModelScope.launch {
        try {
            val cloverResponse = cloverNetworkRepository.getTwinkleRanking()
            Log.d("네트워크", cloverResponse.result.toString())
            if (cloverResponse.code == 200) {
                cloverResponse.result?.ranks.let {
                    if (it != null) {
                        rankingList.addAll(it)
                    }
                }
            } else {
                fail.value = Fail(cloverResponse.message, cloverResponse.code)
            }
        } catch (e: ApiException) {
            Log.d("네트워크", e.toString())
            fail.value = Fail(e.message!!, 404)
        } catch (e: Exception) {
            Log.d("네트워크", e.toString())
            fail.value = Fail(e.message!!, 404)
        }
    }

    fun _getCloverHistory(type: String) = viewModelScope.launch {
        try {
            val cloverResponse = cloverNetworkRepository.getCloverHistory(type)
            Log.d("네트워크", cloverResponse.result.toString())
            if (cloverResponse.code == 200) {
                cloverHistoryResult.value = cloverResponse.result
                when (type) {
                    "accumulate" -> {

                    }
                    "current" -> {

                    }
                    "used" -> {

                    }
                }
            } else {
                fail.value = Fail(cloverResponse.message, cloverResponse.code)
            }
        } catch (e: ApiException) {
            Log.d("네트워크", e.toString())
            fail.value = Fail(e.message!!, 404)
        } catch (e: Exception) {
            Log.d("네트워크", e.toString())
            fail.value = Fail(e.message!!, 404)
        }
    }

    fun whenTvBackClicked() {
        finishActivity.value = true
    }

    fun whenTvMoreGiftClicked() {
        goMoreGift.value = true
    }
}
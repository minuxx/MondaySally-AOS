package com.moon.android.mondaysally.ui.main.twinkle

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.moon.android.mondaysally.data.entities.TwinkleResult
import com.moon.android.mondaysally.data.remote.Fail
import com.moon.android.mondaysally.data.repository.network.TwinkleNetworkRepository
import com.moon.android.mondaysally.ui.main.twinkle.paging.MyTwinklePagingSource
import com.moon.android.mondaysally.ui.main.twinkle.paging.TwinklePagingSource
import com.moon.android.mondaysally.utils.ApiException
import kotlinx.coroutines.launch

class TwinkleViewModel(private val twinkleNetworkRepository: TwinkleNetworkRepository) :
    ViewModel() {

    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    //TwinkleDetail
    var twinkleIndex: MutableLiveData<Int> = MutableLiveData()
    var twinkleResult: MutableLiveData<TwinkleResult> = MutableLiveData()
    var finishActivity: MutableLiveData<Boolean> = MutableLiveData()

    var fail: MutableLiveData<Fail> = MutableLiveData()

    val myTwinkleFlow = Pager(PagingConfig(pageSize = 20)) {
        MyTwinklePagingSource(twinkleNetworkRepository.twinkleService)
    }.flow.cachedIn(viewModelScope)

    val twinkleFlow = Pager(PagingConfig(pageSize = 20)) {
        TwinklePagingSource(twinkleNetworkRepository.twinkleService)
    }.flow.cachedIn(viewModelScope)

    fun getTwinkleDetail(idx: Int) = viewModelScope.launch {
        try {
            val giftResponse = twinkleNetworkRepository.getTwinkleDetail(idx)
            Log.d("네트워크", giftResponse.result.toString())
            if (giftResponse.code == 200) {
                giftResponse.result?.let {
                    twinkleResult.value = giftResponse.result
                }
            } else {
                fail.value = Fail(giftResponse.message, giftResponse.code)
            }
        } catch (e: ApiException) {
            fail.value = Fail(e.message!!, 404)
        } catch (e: Exception) {
            fail.value = Fail(e.message!!, 404)
        }
    }

    fun whenBtnBackClicked() {
        finishActivity.value = true
    }
}
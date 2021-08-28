package com.moon.android.mondaysally.ui.main.gift

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.moon.android.mondaysally.data.entities.*
import com.moon.android.mondaysally.data.remote.Fail
import com.moon.android.mondaysally.data.repository.network.GiftNetworkRepository
import com.moon.android.mondaysally.ui.main.gift.paging.GiftHistoryPagingSource
import com.moon.android.mondaysally.ui.main.gift.paging.GiftShopPagingSource
import com.moon.android.mondaysally.utils.ApiException
import kotlinx.coroutines.launch


class GiftViewModel(private val giftNetworkRepository: GiftNetworkRepository) : ViewModel() {

    var finishActivity: MutableLiveData<Boolean> = MutableLiveData()
    var fail: MutableLiveData<Fail> = MutableLiveData()

    //GiftDetail
    var giftIndex: MutableLiveData<Int> = MutableLiveData()
    var twinkleIndex: MutableLiveData<Int> = MutableLiveData()
    var giftOption: MutableLiveData<GiftOption> = MutableLiveData()
    var optionIndex: MutableLiveData<Int> = MutableLiveData()
    var isOptionSelected: MutableLiveData<Boolean> = MutableLiveData()
    var showDialog: MutableLiveData<Boolean> = MutableLiveData()
    var postSuccess: MutableLiveData<Boolean> = MutableLiveData()

    //GiftList
    var giftResult: MutableLiveData<GiftResult> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()
    var giftCount: MutableLiveData<Int> = MutableLiveData()

    //GiftHistory
    var giftHistoryCount: MutableLiveData<Int> = MutableLiveData()

    //ApplyDone
    var goHome: MutableLiveData<Boolean> = MutableLiveData()
    var goTwinkle: MutableLiveData<Boolean> = MutableLiveData()


    val giftShopFlow = Pager(PagingConfig(pageSize = 20)) {
        GiftShopPagingSource(giftNetworkRepository.giftService)
    }.flow.cachedIn(viewModelScope)

    val giftHistoryFlow = Pager(PagingConfig(pageSize = 20)) {
        GiftHistoryPagingSource(giftNetworkRepository.giftService)
    }.flow.cachedIn(viewModelScope)

    fun getGiftDetail(idx: Int) = viewModelScope.launch {
        try {
            val giftResponse = giftNetworkRepository.getGiftDetail(idx)
            Log.d("네트워크", giftResponse.result.toString())
            if (giftResponse.code == 200) {
                giftResponse.result?.let {
                    giftResult.value = giftResponse.result
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

    fun _getGiftHistoryCount() = viewModelScope.launch {
        try {
            val giftResponse = giftNetworkRepository.getGiftHistoryList(1)
            if (giftResponse.code == 200) {
                giftResponse.result?.let {
                    giftHistoryCount.value = giftResponse.result.totalCount
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

    fun postGift() = viewModelScope.launch {
        try {
            val body: GiftPostBody
            if (giftIndex.value != null && giftOption.value != null) {
                body = GiftPostBody(giftIndex.value!!, giftOption.value!!.usedClover)
            } else {
                fail.value = Fail("", 404)
                return@launch
            }
            val giftResponse = giftNetworkRepository.postGift(body)
            if (giftResponse.code == 200) {
                giftResponse.result?.let {
                    twinkleIndex.value = giftResponse.result.idx
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

    fun whenBtnApplyClicked() {
        showDialog.value = true
    }

    fun whenTvGoHomeClicked() {
        goHome.value = true
    }

    fun whenGoTwinkleClicked() {
        goTwinkle.value = true
    }
}
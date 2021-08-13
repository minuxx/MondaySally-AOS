package com.moon.android.mondaysally.ui.main.shop

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.moon.android.mondaysally.data.entities.*
import com.moon.android.mondaysally.data.remote.Fail
import com.moon.android.mondaysally.data.repository.network.GiftNetworkRepository
import com.moon.android.mondaysally.utils.ApiException
import com.moon.android.mondaysally.utils.ListLiveData
import kotlinx.coroutines.launch


class ShopViewModel(private val giftNetworkRepository: GiftNetworkRepository) : ViewModel() {

    //GiftDetail
    var giftIndex: MutableLiveData<Int> = MutableLiveData()
    var giftOption: MutableLiveData<GiftOption> = MutableLiveData()
    var optionIndex: MutableLiveData<Int> = MutableLiveData()
    var isOptionSelected: MutableLiveData<Boolean> = MutableLiveData()
    var showDialog: MutableLiveData<Boolean> = MutableLiveData()

    //GiftList
    var giftResult: MutableLiveData<GiftResult> = MutableLiveData()
    var postSuccess: MutableLiveData<Boolean> = MutableLiveData()
    var finishActivity: MutableLiveData<Boolean> = MutableLiveData()
    var giftTotalCount: MutableLiveData<Int> = MutableLiveData()
    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    //ApplyDone
    var goHome: MutableLiveData<Boolean> = MutableLiveData()

    val giftList = ListLiveData<Gift>()
    var fail: MutableLiveData<Fail> = MutableLiveData()

    fun getGiftList1() = viewModelScope.launch {
        try {
            val giftResponse = giftNetworkRepository.getGiftList(1)
            Log.d("네트워크", giftResponse.result.toString())
            if (giftResponse.code == 200) {
                giftResponse.result?.let {
                    giftList.clear()
                    giftList.addAll(giftResponse.result.gifts)
                    giftTotalCount.value = giftResponse.result.totalCount
                }
            } else {
                fail.value = Fail(giftResponse.message, giftResponse.code)
            }
        } catch (e: ApiException) {
            fail.value = Fail(e.message!!, 404)
        } catch (e: Exception) {
            Log.d("네트워크", e.toString())
            fail.value = Fail(e.message!!, 404)
        }
    }

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
                    postSuccess.value = true
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
//        postGift()
    }

    fun whenTvGoHomeClicked() {
        goHome.value = true
    }
}
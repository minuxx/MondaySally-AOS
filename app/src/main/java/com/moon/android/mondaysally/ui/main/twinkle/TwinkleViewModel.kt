package com.moon.android.mondaysally.ui.main.twinkle

import android.util.Log
import android.view.View
import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.moon.android.mondaysally.data.entities.CommentPostBody
import com.moon.android.mondaysally.data.entities.TwinkleComment
import com.moon.android.mondaysally.data.entities.TwinkleResult
import com.moon.android.mondaysally.data.remote.Fail
import com.moon.android.mondaysally.data.repository.network.TwinkleNetworkRepository
import com.moon.android.mondaysally.ui.main.twinkle.paging.MyTwinklePagingSource
import com.moon.android.mondaysally.ui.main.twinkle.paging.TwinklePagingSource
import com.moon.android.mondaysally.utils.ApiException
import com.moon.android.mondaysally.utils.ListLiveData
import kotlinx.coroutines.launch

class TwinkleViewModel(private val twinkleNetworkRepository: TwinkleNetworkRepository) :
    ViewModel() {

    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

    //TwinkleDetail
    var twinkleIndex: MutableLiveData<Int> = MutableLiveData()
    var twinkleResult: MutableLiveData<TwinkleResult> = MutableLiveData()
    var finishActivity: MutableLiveData<Boolean> = MutableLiveData()
    var commentPostSuccess: MutableLiveData<Boolean> = MutableLiveData()
    var hideKeyboard: MutableLiveData<Boolean> = MutableLiveData()
    var commentRefresh: MutableLiveData<Boolean> = MutableLiveData()
    val commentList = ListLiveData<TwinkleComment>()
    var editTextCommentString = ObservableField("")

    var fail: MutableLiveData<Fail> = MutableLiveData()

    val myTwinkleFlow = Pager(PagingConfig(pageSize = 20)) {
        MyTwinklePagingSource(twinkleNetworkRepository.twinkleService)
    }.flow.cachedIn(viewModelScope)

    val twinkleFlow = Pager(PagingConfig(pageSize = 20)) {
        TwinklePagingSource(twinkleNetworkRepository.twinkleService)
    }.flow.cachedIn(viewModelScope)

    fun getTwinkleDetail(idx: Int) = viewModelScope.launch {
        try {
            val twinkleResponse = twinkleNetworkRepository.getTwinkleDetail(idx)
            Log.d("네트워크", twinkleResponse.result.toString())
            if (twinkleResponse.code == 200) {
                twinkleResponse.result?.let {
                    twinkleResult.value = twinkleResponse.result
                    commentList.clear()
                    commentList.addAll(twinkleResponse.result.commentLists)
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

    private fun postComment() = viewModelScope.launch {
        try {
            val body: CommentPostBody
            if (twinkleIndex.value != null) {
                body = CommentPostBody(editTextCommentString.get()!!)
            } else {
                fail.value = Fail("", 404)
                return@launch
            }
            val twinkleResponse = twinkleNetworkRepository.postComment(twinkleIndex.value!!, body)
            Log.d("네트워크", body.toString())
            Log.d("네트워크", twinkleResponse.toString())
            if (twinkleResponse.code == 200) {
                commentRefresh.value = true
                commentPostSuccess.value = true
            } else {
                fail.value = Fail(twinkleResponse.message, twinkleResponse.code)
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

    fun whenTvPostClicked() {
        postComment()
        hideKeyboard.value = true
    }
}
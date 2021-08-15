package com.moon.android.mondaysally.ui.main.twinkle

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import com.moon.android.mondaysally.data.remote.Fail
import com.moon.android.mondaysally.data.repository.network.TwinkleNetworkRepository
import com.moon.android.mondaysally.ui.main.twinkle.paging.MyTwinklePagingSource
import com.moon.android.mondaysally.ui.main.twinkle.paging.TwinklePagingSource

class TwinkleViewModel(private val giftNetworkRepository: TwinkleNetworkRepository) : ViewModel() {

    var isLoading: MutableLiveData<Boolean> = MutableLiveData()

//    val myTwinkleList = ListLiveData<MyTwinkle>()
//    val twinkleList = ListLiveData<Twinkle>()
    var fail: MutableLiveData<Fail> = MutableLiveData()

    val myTwinkleFlow = Pager(PagingConfig(pageSize = 20)){
        MyTwinklePagingSource(giftNetworkRepository.twinkleService)
    }.flow.cachedIn(viewModelScope)

    val twinkleFlow = Pager(PagingConfig(pageSize = 20)){
        TwinklePagingSource(giftNetworkRepository.twinkleService)
    }.flow.cachedIn(viewModelScope)

}
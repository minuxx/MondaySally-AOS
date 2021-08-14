package com.moon.android.mondaysally.data.repository.network

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.liveData
import com.moon.android.mondaysally.data.remote.twinkke.TwinkleResponse
import com.moon.android.mondaysally.data.remote.twinkke.TwinkleService
import com.moon.android.mondaysally.data.repository.BaseNetworkRepository
import com.moon.android.mondaysally.ui.main.twinkle.paging.TwinklePagingSource

class TwinkleNetworkRepository(private val twinkleService: TwinkleService) :
    BaseNetworkRepository() {

    suspend fun getMyTwinkleList(page: Int): TwinkleResponse {
        return apiRequest { twinkleService.getMyTwinkleList(page) }
    }

    suspend fun getTwinkleList(page: Int): TwinkleResponse {
        return apiRequest { twinkleService.getTwinkleList(page) }
    }
}

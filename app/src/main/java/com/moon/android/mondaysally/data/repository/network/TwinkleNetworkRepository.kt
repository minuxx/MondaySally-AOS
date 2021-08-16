package com.moon.android.mondaysally.data.repository.network

import com.moon.android.mondaysally.data.remote.twinkke.TwinkleResponse
import com.moon.android.mondaysally.data.remote.twinkke.TwinkleService
import com.moon.android.mondaysally.data.repository.BaseNetworkRepository

class TwinkleNetworkRepository(val twinkleService: TwinkleService) :
    BaseNetworkRepository() {

    suspend fun getMyTwinkleList(page: Int): TwinkleResponse {
        return apiRequest { twinkleService.getMyTwinkleList(page) }
    }

    suspend fun getTwinkleList(page: Int): TwinkleResponse {
        return apiRequest { twinkleService.getTwinkleList(page) }
    }

    suspend fun getTwinkleDetail(index: Int): TwinkleResponse {
        return apiRequest { twinkleService.getTwinkleDetail(index) }
    }
}

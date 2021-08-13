package com.moon.android.mondaysally.data.repository.network

import com.moon.android.mondaysally.data.remote.twinkke.TwinkleResponse
import com.moon.android.mondaysally.data.remote.twinkke.TwinkleService
import com.moon.android.mondaysally.data.repository.BaseNetworkRepository

class TwinkleNetworkRepository(private val authService: TwinkleService) : BaseNetworkRepository(){

    suspend fun getMyTwinkleList(page: Int): TwinkleResponse {
        return apiRequest { authService.getMyTwinkleList(page)}
    }
}

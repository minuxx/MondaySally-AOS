package com.moon.android.mondaysally.data.repository.network

import com.moon.android.mondaysally.data.remote.auth.CloverResponse
import com.moon.android.mondaysally.data.remote.clover.CloverService
import com.moon.android.mondaysally.data.repository.BaseNetworkRepository

class CloverNetworkRepository(val cloverService: CloverService) : BaseNetworkRepository(){

    suspend fun getTwinkleRanking(): CloverResponse {
        return apiRequest { cloverService.getTwinkleRanking(1) }
    }

}

package com.moon.android.mondaysally.data.repository.auth

import com.moon.android.mondaysally.data.remote.home.HomeResponse
import com.moon.android.mondaysally.data.remote.home.HomeService
import com.moon.android.mondaysally.data.repository.BaseNetworkRepository

class HomeNetworkRepository(private val authService: HomeService) : BaseNetworkRepository(){

    suspend fun getHome(): HomeResponse {
        return apiRequest { authService.getHomeData() }
    }
}

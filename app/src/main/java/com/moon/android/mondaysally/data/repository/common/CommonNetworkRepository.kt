package com.moon.android.mondaysally.data.repository.common

import com.moon.android.mondaysally.data.remote.common.CommonResponse
import com.moon.android.mondaysally.data.remote.common.CommonService
import com.moon.android.mondaysally.data.repository.BaseNetworkRepository

class CommonNetworkRepository(private val commonService: CommonService) : BaseNetworkRepository() {

    suspend fun getVersion(): CommonResponse {
        return apiRequest { commonService.getVersion() }
    }
}

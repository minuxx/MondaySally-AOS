package com.moon.android.mondaysally.data.repository.gift

import com.moon.android.mondaysally.data.remote.shop.GiftResponse
import com.moon.android.mondaysally.data.remote.shop.GiftService
import com.moon.android.mondaysally.data.repository.BaseNetworkRepository

class GiftNetworkRepository(private val giftService: GiftService) : BaseNetworkRepository(){

    suspend fun getGiftList(page: Int): GiftResponse {
        return apiRequest { giftService.getGiftList(page) }
    }
}

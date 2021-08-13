package com.moon.android.mondaysally.data.repository.network

import com.moon.android.mondaysally.data.entities.GiftPostBody
import com.moon.android.mondaysally.data.remote.shop.GiftResponse
import com.moon.android.mondaysally.data.remote.shop.GiftService
import com.moon.android.mondaysally.data.repository.BaseNetworkRepository

class GiftNetworkRepository(private val giftService: GiftService) : BaseNetworkRepository(){

    suspend fun getGiftList(page: Int): GiftResponse {
        return apiRequest { giftService.getGiftList(page) }
    }

    suspend fun getGiftDetail(idx: Int): GiftResponse {
        return apiRequest { giftService.getGiftDetail(idx) }
    }

    suspend fun postGift(giftPostBody: GiftPostBody): GiftResponse {
        return apiRequest { giftService.postGift(giftPostBody) }
    }
}

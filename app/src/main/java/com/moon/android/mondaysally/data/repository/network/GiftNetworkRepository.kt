package com.moon.android.mondaysally.data.repository.network

import com.moon.android.mondaysally.data.entities.GiftPostBody
import com.moon.android.mondaysally.data.remote.gift.GiftResponse
import com.moon.android.mondaysally.data.remote.gift.GiftService
import com.moon.android.mondaysally.data.repository.BaseNetworkRepository

class GiftNetworkRepository(val giftService: GiftService) : BaseNetworkRepository(){

    suspend fun getGiftList(page: Int): GiftResponse {
        return apiRequest { giftService.getGiftList(page) }
    }

    suspend fun getGiftHistoryList(page: Int): GiftResponse {
        return apiRequest { giftService.getGiftHistoryList(page) }
    }

    suspend fun getGiftDetail(idx: Int): GiftResponse {
        return apiRequest { giftService.getGiftDetail(idx) }
    }

    suspend fun postGift(giftPostBody: GiftPostBody): GiftResponse {
        return apiRequest { giftService.postGift(giftPostBody) }
    }
}

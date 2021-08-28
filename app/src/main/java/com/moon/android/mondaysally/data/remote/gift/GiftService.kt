package com.moon.android.mondaysally.data.remote.gift

import com.moon.android.mondaysally.data.entities.GiftPostBody
import retrofit2.Response
import retrofit2.http.*

interface GiftService {
    @GET("/gift")
    suspend fun getGiftList(@Query("page") page: Int): Response<GiftResponse>

    @GET("/giftlog")
    suspend fun getGiftHistoryList(@Query("page") page: Int): Response<GiftResponse>

    @GET("/gift/{idx}")
    suspend fun getGiftDetail(@Path("idx") idx: Int): Response<GiftResponse>

    @POST("/gift")
    suspend fun postGift(@Body giftPostBody: GiftPostBody): Response<GiftResponse>
}

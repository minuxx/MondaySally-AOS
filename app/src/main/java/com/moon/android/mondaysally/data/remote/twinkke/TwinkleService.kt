package com.moon.android.mondaysally.data.remote.twinkke

import com.moon.android.mondaysally.data.entities.GiftPostBody
import retrofit2.Response
import retrofit2.http.*

interface TwinkleService {
    @GET("/prove")
    suspend fun getMyTwinkleList(@Query("page") page: Int): Response<TwinkleResponse>

    @GET("/twinkle")
    suspend fun getTwinkleList(@Query("page") page: Int): Response<TwinkleResponse>
//
//    @POST("/gift")
//    suspend fun postGift(@Body giftPostBody: GiftPostBody): Response<TwinkleResponse>
}

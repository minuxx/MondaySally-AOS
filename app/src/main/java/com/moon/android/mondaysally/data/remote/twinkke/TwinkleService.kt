package com.moon.android.mondaysally.data.remote.twinkke

import com.moon.android.mondaysally.data.entities.GiftPostBody
import com.moon.android.mondaysally.data.remote.shop.GiftResponse
import retrofit2.Response
import retrofit2.http.*

interface TwinkleService {
    @GET("/prove")
    suspend fun getMyTwinkleList(@Query("page") page: Int): Response<TwinkleResponse>

    @GET("/twinkle")
    suspend fun getTwinkleList(@Query("page") page: Int): Response<TwinkleResponse>

    @GET("/twinkle/{idx}")
    suspend fun getTwinkleDetail(@Path("idx") idx: Int): Response<TwinkleResponse>

}

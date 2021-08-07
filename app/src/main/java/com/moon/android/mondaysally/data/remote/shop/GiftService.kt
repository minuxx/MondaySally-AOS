package com.moon.android.mondaysally.data.remote.shop

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface GiftService {
    @GET("/gift")
    suspend fun getGiftList(@Query("page") page: Int): Response<GiftResponse>

    @GET("/gift/{idx}")
    suspend fun getGiftDetail(@Path("idx") idx: Int): Response<GiftResponse>
}

package com.moon.android.mondaysally.data.remote.shop

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface GiftService {
    @GET("/gift")
    suspend fun getGiftList(@Query("page") page: Int): Response<GiftResponse>
}

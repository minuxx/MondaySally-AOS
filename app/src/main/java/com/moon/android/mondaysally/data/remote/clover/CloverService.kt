package com.moon.android.mondaysally.data.remote.clover

import com.moon.android.mondaysally.data.remote.auth.CloverResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface CloverService {
    @GET("/rank")
    suspend fun getTwinkleRanking(@Query("page") page: Int): Response<CloverResponse>

    @GET("/clover")
    suspend fun getCloverHistory(
        @Query("page") page: Int,
        @Query("type") type: String
    ): Response<CloverResponse>
}
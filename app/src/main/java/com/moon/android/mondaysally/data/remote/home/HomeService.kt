package com.moon.android.mondaysally.data.remote.home

import com.moon.android.mondaysally.data.entities.Code
import com.moon.android.mondaysally.data.entities.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface HomeService {
    @GET("/main")
    suspend fun getHomeData(): Response<HomeResponse>
}

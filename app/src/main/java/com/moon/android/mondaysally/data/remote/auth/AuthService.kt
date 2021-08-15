package com.moon.android.mondaysally.data.remote.auth

import com.moon.android.mondaysally.data.entities.Code
import com.moon.android.mondaysally.data.entities.User
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST

interface AuthService {
    @GET("/auto-login")
    suspend fun autoLogin(): Response<AuthResponse>

    @POST("/code")
    suspend fun checkTeamCode(@Body code: Code): Response<AuthResponse>
}
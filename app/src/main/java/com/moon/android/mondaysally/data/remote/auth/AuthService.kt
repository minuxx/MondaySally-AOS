package com.moon.android.mondaysally.data.remote.auth

import com.moon.android.mondaysally.data.entities.Code
import com.moon.android.mondaysally.data.entities.FcmBody
import com.moon.android.mondaysally.data.entities.ProfileBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.PATCH
import retrofit2.http.POST

interface AuthService {
    @GET("/auto-login")
    suspend fun autoLogin(): Response<AuthResponse>

    @POST("/code")
    suspend fun checkTeamCode(@Body code: Code): Response<AuthResponse>

    @GET("/mypage")
    suspend fun getMyPage(): Response<AuthResponse>

    @PATCH("/profile")
    suspend fun patchProfile(@Body profileBody: ProfileBody): Response<AuthResponse>

    @POST("/firebase")
    suspend fun postFcmToken(@Body fcmBody: FcmBody): Response<AuthResponse>
}
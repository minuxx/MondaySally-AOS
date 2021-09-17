package com.moon.android.mondaysally.data.repository.network

import com.moon.android.mondaysally.data.entities.Code
import com.moon.android.mondaysally.data.entities.FcmBody
import com.moon.android.mondaysally.data.entities.ProfileBody
import com.moon.android.mondaysally.data.remote.auth.AuthResponse
import com.moon.android.mondaysally.data.remote.auth.AuthService
import com.moon.android.mondaysally.data.repository.BaseNetworkRepository

class AuthNetworkRepository(private val authService: AuthService) : BaseNetworkRepository() {

    suspend fun autoLogin(): AuthResponse {
        return apiRequest { authService.autoLogin() }
    }

    suspend fun checkTeamCode(code: Code): AuthResponse {
        return apiRequest { authService.checkTeamCode(code) }
    }

    suspend fun getMyPage(): AuthResponse {
        return apiRequest { authService.getMyPage() }
    }

    suspend fun patchProfile(profileBody: ProfileBody): AuthResponse {
        return apiRequest { authService.patchProfile(profileBody) }
    }

    suspend fun postFcmToken(fcmBody: FcmBody): AuthResponse {
        return apiRequest { authService.postFcmToken(fcmBody) }
    }
}

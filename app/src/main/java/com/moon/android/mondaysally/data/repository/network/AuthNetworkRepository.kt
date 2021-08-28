package com.moon.android.mondaysally.data.repository.network

import com.moon.android.mondaysally.data.entities.Code
import com.moon.android.mondaysally.data.remote.auth.AuthResponse
import com.moon.android.mondaysally.data.remote.auth.AuthService
import com.moon.android.mondaysally.data.repository.BaseNetworkRepository

class AuthNetworkRepository(private val authService: AuthService) : BaseNetworkRepository(){

    suspend fun autoLogin(): AuthResponse {
        return apiRequest { authService.autoLogin() }
    }

    suspend fun checkTeamCode(code: Code): AuthResponse {
        return apiRequest { authService.checkTeamCode(code) }
    }
}

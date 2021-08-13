package com.moon.android.mondaysally.data.repository.network

import com.moon.android.mondaysally.data.entities.Code
import com.moon.android.mondaysally.data.entities.User
import com.moon.android.mondaysally.data.remote.auth.AuthResponse
import com.moon.android.mondaysally.data.remote.auth.AuthService
import com.moon.android.mondaysally.data.repository.BaseNetworkRepository

class AuthNetworkRepository(private val authService: AuthService) : BaseNetworkRepository(){

    suspend fun login(user: User): AuthResponse {
        return apiRequest { authService.login(user) }
    }

    suspend fun autoLogin(): AuthResponse {
        return apiRequest { authService.autoLogin() }
    }

    suspend fun checkTeamCode(code: Code): AuthResponse {
        return apiRequest { authService.checkTeamCode(code) }
    }
}

package com.moon.android.mondaysally.data.repository.auth

import com.moon.android.mondaysally.data.entities.User
import com.moon.android.mondaysally.data.remote.auth.AuthResponse
import com.moon.android.mondaysally.data.remote.auth.AuthService
import com.moon.android.mondaysally.data.repository.BaseRepository

class AuthRepository(private val authService: AuthService) : BaseRepository(){
    suspend fun signUp(user: User): AuthResponse {
        return apiRequest { authService.signUp(user) }
    }

    suspend fun login(user: User): AuthResponse {
        return apiRequest { authService.login(user) }
    }

    suspend fun autoLogin(): AuthResponse {
        return apiRequest { authService.autoLogin() }
    }

    suspend fun getVersion(): AuthResponse {
        return apiRequest { authService.getVersion() }
    }
}

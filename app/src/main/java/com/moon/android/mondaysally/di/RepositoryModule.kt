package com.moon.android.mondaysally.di

import com.moon.android.mondaysally.data.repository.auth.AuthNetworkRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AuthNetworkRepository(get()) }
}
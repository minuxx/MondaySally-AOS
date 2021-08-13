package com.moon.android.mondaysally.di

import com.moon.android.mondaysally.data.repository.SharedPrefRepository
import com.moon.android.mondaysally.data.repository.network.*
import org.koin.android.ext.koin.androidContext
import org.koin.dsl.module

val repositoryModule = module {
    single { AuthNetworkRepository(get()) }
    single { HomeNetworkRepository(get()) }
    single { CommonNetworkRepository(get()) }
    single { GiftNetworkRepository(get()) }
    single { TwinkleNetworkRepository(get()) }
    single { SharedPrefRepository(androidContext()) }
}
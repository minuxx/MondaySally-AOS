package com.moon.android.mondaysally.di

import com.moon.android.mondaysally.data.repository.auth.AuthRepository
import org.koin.dsl.module

val repositoryModule = module {
    single { AuthRepository(get()) }
}
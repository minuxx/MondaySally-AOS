package com.moon.android.mondaysally.di


import android.util.Log
import com.moon.android.mondaysally.data.remote.auth.AuthService
import com.moon.android.mondaysally.data.remote.clover.CloverService
import com.moon.android.mondaysally.data.remote.common.CommonService
import com.moon.android.mondaysally.data.remote.gift.GiftService
import com.moon.android.mondaysally.data.remote.home.HomeService
import com.moon.android.mondaysally.data.remote.twinkke.TwinkleService
import com.moon.android.mondaysally.data.repository.SharedPrefRepository
import com.moon.android.mondaysally.utils.GlobalConstant.Companion.X_ACCESS_TOKEN
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.module.Module
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

const val PRODUCTION_URL = "https://api.mondaysally.com/"
const val TEST_URL = "https://test.mondaysally.com"
private val base_url: String = TEST_URL

fun getBaseUrl() = base_url

val networkModule: Module = module {
    fun provideHeaderInterceptor(sharedPrefRepository: SharedPrefRepository) =
        Interceptor { chain ->
            val request = chain.request().newBuilder()
                .addHeader(X_ACCESS_TOKEN, "${sharedPrefRepository.getJwtToken()}")
                .build()
            Log.d("X_ACCESS_TOKEN", "${sharedPrefRepository.getJwtToken()}")
            chain.proceed(request)
        }

    fun provideHttpLoggingInterceptor() =
        HttpLoggingInterceptor().apply { HttpLoggingInterceptor.Level.BODY }

    fun provideOkHttpClient(
        httpLoggingInterceptor: HttpLoggingInterceptor,
        headerInterceptor: Interceptor
    ) = OkHttpClient.Builder()
        .readTimeout(5000, TimeUnit.MILLISECONDS)
        .connectTimeout(5000, TimeUnit.MILLISECONDS)
        .addInterceptor(headerInterceptor)
        .addInterceptor(httpLoggingInterceptor)
        .build()

    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit = Retrofit.Builder()
        .baseUrl(getBaseUrl())
        .addConverterFactory(GsonConverterFactory.create())
        .client(okHttpClient)
        .build()


    fun provideAuthService(retrofit: Retrofit): AuthService =
        retrofit.create(AuthService::class.java)

    fun provideCommonService(retrofit: Retrofit): CommonService =
        retrofit.create(CommonService::class.java)

    fun provideHomeService(retrofit: Retrofit): HomeService =
        retrofit.create(HomeService::class.java)

    fun provideGiftService(retrofit: Retrofit): GiftService =
        retrofit.create(GiftService::class.java)

    fun provideTwinkleService(retrofit: Retrofit): TwinkleService =
        retrofit.create(TwinkleService::class.java)

    fun provideCloverService(retrofit: Retrofit): CloverService =
        retrofit.create(CloverService::class.java)

    single { provideHeaderInterceptor(get()) }
    single { provideHttpLoggingInterceptor() }
    single { provideOkHttpClient(get(), get()) }
    single { provideRetrofit(get()) }
    single { provideAuthService(get()) }
    single { provideCommonService(get()) }
    single { provideHomeService(get()) }
    single { provideGiftService(get()) }
    single { provideCloverService(get()) }
    single { provideTwinkleService(get()) }
}
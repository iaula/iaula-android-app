package com.aurelio.baldor.core.di

import com.aurelio.baldor.core.components.AlertMessage.AlertMessageViewModel
import com.aurelio.baldor.core.data.local.AuthPreferences
import com.aurelio.baldor.core.data.remote.AuthApiService
import com.aurelio.baldor.core.data.remote.AuthInterceptor
import com.aurelio.baldor.core.data.repository.AuthRepository
import com.aurelio.baldor.core.domain.usecase.LoginUseCase
import okhttp3.OkHttpClient
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.scope.get
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.dsl.module

val coreModule = module {
    single { AuthPreferences(get()) }

    single {
        val prefs: AuthPreferences = get()
        val client = OkHttpClient.Builder()
            .addInterceptor(AuthInterceptor(prefs, provideApiWithoutInterceptor()))
            .build()

        Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(AuthApiService::class.java)
    }

    single { AuthRepository(get(), get()) }
    factory { LoginUseCase(get()) }
    viewModel { AlertMessageViewModel() }
}


private fun provideApiWithoutInterceptor(): AuthApiService {
    return Retrofit.Builder()
        .baseUrl(NetworkConfig.BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(AuthApiService::class.java)
}
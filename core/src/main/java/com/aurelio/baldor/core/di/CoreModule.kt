package com.aurelio.baldor.core.di

import com.aurelio.baldor.core.components.AlertMessage.AlertMessageViewModel
import com.aurelio.baldor.core.components.Shared.NavigationBottomBarViewModel
import com.aurelio.baldor.core.data.local.AuthPreferences
import com.aurelio.baldor.core.data.remote.AuthApiService
import com.aurelio.baldor.core.data.middleware.AuthInterceptor
import com.aurelio.baldor.core.data.middleware.DynamicUrlInterceptor
import com.aurelio.baldor.core.data.remote.AsistenciaApiService
import com.aurelio.baldor.core.data.remote.FamiliaApiService
import com.aurelio.baldor.core.data.repository.AsistenciaRepository
import com.aurelio.baldor.core.data.repository.AuthRepository
import com.aurelio.baldor.core.data.repository.FamiliaRepository
import com.aurelio.baldor.core.domain.usecase.AsistenciaUseCase
import com.aurelio.baldor.core.domain.usecase.FamiliasUseCase
import com.aurelio.baldor.core.domain.usecase.InstitutionsUseCase
import com.aurelio.baldor.core.domain.usecase.LoginUseCase
import com.aurelio.baldor.core.domain.usecase.LogoutUseCase
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.qualifier.named
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import org.koin.dsl.module

val coreModule = module {
    single { AuthPreferences(get()) }

    single(named("baseClient")) {
        OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.BODY
            })
            .addInterceptor(DynamicUrlInterceptor(get()))
            .build()
    }

    single(named("authClient")) {
        get<OkHttpClient>(named("baseClient")).newBuilder()
            .addInterceptor(AuthInterceptor(get(), get()))
            .build()
    }

    single(named("authRetrofit")) {
        Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .client(get(named("baseClient")))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single(named("mainRetrofit")) {
        Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .client(get(named("authClient")))
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    single<AuthApiService> { get<Retrofit>(named("authRetrofit")).create(AuthApiService::class.java) }
    single<FamiliaApiService> { get<Retrofit>(named("mainRetrofit")).create(FamiliaApiService::class.java) }
    single<AsistenciaApiService> { get<Retrofit>(named("mainRetrofit")).create(AsistenciaApiService::class.java) }

    single { AuthRepository(get(), get()) }
    single { FamiliaRepository(get()) }
    single { AsistenciaRepository(get()) }

    factory { InstitutionsUseCase(get()) }
    factory { LoginUseCase(get()) }
    factory { LogoutUseCase(get()) }
    factory { FamiliasUseCase(get()) }
    factory { AsistenciaUseCase(get()) }

    viewModel { AlertMessageViewModel() }
    viewModel { NavigationBottomBarViewModel(get()) }
}
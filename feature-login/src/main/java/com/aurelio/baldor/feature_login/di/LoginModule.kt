package com.aurelio.baldor.feature_login.di

import com.aurelio.baldor.feature_login.login.LoginViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val loginModule = module {
    viewModel { LoginViewModel(get()) }
}

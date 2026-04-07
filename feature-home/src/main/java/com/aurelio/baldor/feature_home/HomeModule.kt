package com.aurelio.baldor.feature_home

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import com.aurelio.baldor.feature_home.home.HomeViewModel

val homeModule = module {
    viewModel { HomeViewModel(get(), get(), get()) }
}
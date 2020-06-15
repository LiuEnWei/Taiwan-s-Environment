package com.wayne.taiwan_s_environment.di

import com.wayne.taiwan_s_environment.view.home.HomeViewModel
import com.wayne.taiwan_s_environment.view.main.MainViewModel
import com.wayne.taiwan_s_environment.view.more.MoreViewModel
import com.wayne.taiwan_s_environment.view.splash.SplashViewModel
import com.wayne.taiwan_s_environment.view.taiwan.TaiwanViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val viewModelModule = module {
    viewModel { MainViewModel() }
    viewModel { SplashViewModel() }
    viewModel { HomeViewModel() }
    viewModel { TaiwanViewModel() }
    viewModel { MoreViewModel() }
}
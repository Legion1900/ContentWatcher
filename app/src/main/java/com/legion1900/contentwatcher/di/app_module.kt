package com.legion1900.contentwatcher.di

import com.legion1900.contentwatcher.MainViewModel
import com.legion1900.game_list_feature.di.gameListModule
import com.legion1900.network.networkModule
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {

    includes(
        networkModule,
        gameListModule
    )

    viewModelOf(::MainViewModel)
}

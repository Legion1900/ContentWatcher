package com.legion1900.game_list_feature.di

import com.legion1900.game_list_feature.GameListViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val gameListModule = module {
    viewModelOf(::GameListViewModel)
}

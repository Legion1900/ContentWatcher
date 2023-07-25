package com.legion1900.game_list_feature.di

import com.legion1900.game_list_feature.GameListViewModel
import com.legion1900.game_list_feature.providers.GamesRepository
import com.legion1900.game_list_feature.providers.IGDBUrlFactory
import com.legion1900.game_list_feature.providers.RemoteGameSource
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val gameListModule = module {
    viewModelOf(::GameListViewModel)

    factoryOf(::IGDBUrlFactory)
    single { RemoteGameSource(get(), get()) }
    singleOf(::GamesRepository)
}

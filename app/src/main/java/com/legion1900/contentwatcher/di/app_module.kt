package com.legion1900.contentwatcher.di

import com.legion1900.contentwatcher.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val appModule = module {
    viewModelOf(::MainViewModel)
}

package com.gumsiz.words.di

import com.gumsiz.words.ui.detayf.DetailViewModel
import com.gumsiz.words.ui.mainf.MainViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
}
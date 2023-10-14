package com.gumsiz.words.di

import android.content.Context
import com.gumsiz.words.ui.detayf.DetailViewModel
import com.gumsiz.words.ui.mainf.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val androidModule = module {
    single { androidApplication().getSharedPreferences("data", Context.MODE_PRIVATE) }
    viewModel { MainViewModel(androidApplication(), get()) }
    viewModel { DetailViewModel(get()) }
}
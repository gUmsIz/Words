package com.gumsiz.shared.di

import com.gumsiz.shared.data.DataBase
import org.koin.core.module.dsl.bind
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

fun sharedModule() = module {
    singleOf(::DataBase) { bind() }
}
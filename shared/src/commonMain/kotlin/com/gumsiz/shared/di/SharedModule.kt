package com.gumsiz.shared.di

import com.gumsiz.shared.data.Repository
import com.gumsiz.shared.data.db.DataBaseImpl
import com.gumsiz.shared.data.db.Database
import com.gumsiz.shared.data.network.NetworkService
import com.gumsiz.shared.data.network.NetworkServiceImp
import com.gumsiz.shared.getEngine
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import org.koin.dsl.module

fun sharedModule() = module {
    includes(platformDatabaseModule())
    single<Database> { DataBaseImpl(get()) }
    single {
        HttpClient(getEngine()) {
            install(ContentNegotiation) {
                json(
                    Json {
                        isLenient = true
                    }
                )
            }
        }
    }
    single<NetworkService> { NetworkServiceImp(get()) }
    single { Repository(get(), get()) }
}
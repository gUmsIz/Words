package com.gumsiz.shared.di

import com.gumsiz.shared.data.db.DataBaseImpl
import com.gumsiz.shared.data.db.Database
import com.gumsiz.shared.data.model.WordDatabaseModel
import io.realm.kotlin.Realm
import io.realm.kotlin.RealmConfiguration
import org.koin.dsl.module

fun sharedModule() = module {
    single {
        val config = RealmConfiguration.create(schema = setOf(WordDatabaseModel::class))
        Realm.open(config)
    }
    single<Database> { DataBaseImpl(get()) }
}
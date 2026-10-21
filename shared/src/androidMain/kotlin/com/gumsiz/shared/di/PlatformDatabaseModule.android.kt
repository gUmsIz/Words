package com.gumsiz.shared.di

import android.content.Context
import androidx.room.Room
import com.gumsiz.shared.data.db.WordsDatabase
import com.gumsiz.shared.data.db.getRoomDatabase
import com.gumsiz.shared.data.settings.AndroidSettingsStorage
import com.gumsiz.shared.data.settings.SettingsStorage
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformDatabaseModule(): Module = module {
    single<WordsDatabase> {
        val context: Context = get()
        val dbFile = context.getDatabasePath("words.db")
        val builder = Room.databaseBuilder<WordsDatabase>(
            context = context,
            name = dbFile.absolutePath
        )
        getRoomDatabase(builder)
    }
    single<SettingsStorage> { AndroidSettingsStorage(get()) }
}

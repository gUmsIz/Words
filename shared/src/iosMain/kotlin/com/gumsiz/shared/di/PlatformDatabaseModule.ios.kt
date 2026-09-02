package com.gumsiz.shared.di

import androidx.room.Room
import com.gumsiz.shared.data.db.WordsDatabase
import com.gumsiz.shared.data.db.getRoomDatabase
import kotlinx.cinterop.ExperimentalForeignApi
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSFileManager
import platform.Foundation.NSUserDomainMask

@OptIn(ExperimentalForeignApi::class)
actual fun platformDatabaseModule(): Module = module {
    single<WordsDatabase> {
        val documentDirectory = NSFileManager.defaultManager.URLForDirectory(
            directory = NSDocumentDirectory,
            inDomain = NSUserDomainMask,
            appropriateForURL = null,
            create = false,
            error = null
        )
        val dbFilePath = (documentDirectory?.path ?: "") + "/words.db"
        val builder = Room.databaseBuilder<WordsDatabase>(
            name = dbFilePath
        )
        getRoomDatabase(builder)
    }
}

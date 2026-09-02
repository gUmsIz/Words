package com.gumsiz.shared.data.db

import androidx.room.ConstructedBy
import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.RoomDatabaseConstructor
import androidx.sqlite.driver.bundled.BundledSQLiteDriver
import com.gumsiz.shared.data.model.SettingDatabaseModel
import com.gumsiz.shared.data.model.WordDatabaseModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO

@Database(
    entities = [WordDatabaseModel::class, SettingDatabaseModel::class],
    version = 1
)
@ConstructedBy(WordsDatabaseConstructor::class)
abstract class WordsDatabase : RoomDatabase() {
    abstract fun wordsDao(): WordsDao
    abstract fun settingsDao(): SettingsDao
}

@Suppress("KotlinNoActualForExpect", "EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect object WordsDatabaseConstructor : RoomDatabaseConstructor<WordsDatabase> {
    override fun initialize(): WordsDatabase
}

fun getRoomDatabase(builder: RoomDatabase.Builder<WordsDatabase>): WordsDatabase {
    return builder
        .setDriver(BundledSQLiteDriver())
        .setQueryCoroutineContext(Dispatchers.IO)
        .build()
}

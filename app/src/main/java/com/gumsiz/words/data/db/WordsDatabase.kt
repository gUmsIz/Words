package com.gumsiz.words.data.db

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [WordDB::class], version = 1, exportSchema = false)
abstract class WordsDatabase : RoomDatabase() {
    abstract val WordsDAO: WordsDAO

    companion object {
        @Volatile
        private var INSTANCE: WordsDatabase? = null
        fun getInstance(context: Context): WordsDatabase {
            synchronized(this) {
                var instance = INSTANCE
                if (instance == null) {
                    instance = Room.databaseBuilder(
                        context.applicationContext,
                        WordsDatabase::class.java,
                        "words_database"
                    )
                        .fallbackToDestructiveMigration()
                        .build()
                    INSTANCE = instance
                }
                return instance
            }
        }
    }
}
package com.gumsiz.shared.data.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.gumsiz.shared.data.model.SettingDatabaseModel
import com.gumsiz.shared.data.model.WordDatabaseModel
import kotlinx.coroutines.flow.Flow

@Dao
interface WordsDao {
    @Query("SELECT * FROM words")
    fun getAllWords(): Flow<List<WordDatabaseModel>>

    @Query("SELECT * FROM words WHERE favorite = 1")
    fun getFavoriteWords(): Flow<List<WordDatabaseModel>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(words: List<WordDatabaseModel>)

    @Query("SELECT * FROM words WHERE name = :name LIMIT 1")
    suspend fun getWord(name: String): WordDatabaseModel?

    @Query("UPDATE words SET favorite = :favorite, translation = :translation WHERE name = :name")
    suspend fun updateWord(name: String, favorite: Boolean, translation: String?)

    @Query("SELECT * FROM words WHERE name LIKE '%' || :searchQuery || '%'")
    fun searchWords(searchQuery: String): Flow<List<WordDatabaseModel>>
}

@Dao
interface SettingsDao {
    @Query("SELECT dataLoaded FROM settings WHERE id = 1 LIMIT 1")
    suspend fun isDataLoaded(): Boolean?

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun setSettings(setting: SettingDatabaseModel)
}

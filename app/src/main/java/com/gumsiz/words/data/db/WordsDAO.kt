package com.gumsiz.words.data.db

import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface WordsDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(vararg words: WordDB)

    @Update
    suspend fun update(wordDB: WordDB)

    @Query("SELECT * from words_table WHERE name = :key")
    fun get(key: String): LiveData<WordDB>

    @Query("SELECT * from words_table ")
    fun getAll(): LiveData<List<WordDB>>

    @Query("SELECT * FROM words_table WHERE favorite= 1")
    fun getSearch(): LiveData<List<WordDB>>
}
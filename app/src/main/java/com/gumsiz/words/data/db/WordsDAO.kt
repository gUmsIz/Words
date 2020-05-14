package com.gumsiz.words.data.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface WordsDAO {
    @Insert
    fun insert(wordDB: WordDB)

    @Query("SELECT * from words_table WHERE wordId = :key")
    fun get(key: Int): LiveData<WordDB>

    @Query("SELECT * from words_table ")
    fun getAll(): List<WordDB>

    @Query("SELECT * FROM words_table WHERE name LIKE '%' || :value  || '%'")
    fun getSearch(value: String): LiveData<List<WordDB>>
}
package com.gumsiz.words.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.gumsiz.words.data.db.WordsDAO
import com.gumsiz.words.data.db.toWordList
import com.gumsiz.words.data.network.WordApi
import com.gumsiz.words.data.network.toDbData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordRepository(val database: WordsDAO, application: Application) {

    val wordList: LiveData<List<Word>> = Transformations.map(database.getAll()) {
        it.toWordList()
    }
    val wordListFav: LiveData<List<Word>> = Transformations.map(database.getSearch()) {
        it.toWordList()
    }

    suspend fun getDataFromServer() {
        withContext(Dispatchers.IO) {
            val list = WordApi.retrofitService.getWords()
            database.insertAll(*list.toDbData())
        }
    }

}
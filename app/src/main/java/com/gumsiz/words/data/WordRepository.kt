package com.gumsiz.words.data

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.liveData
import com.gumsiz.words.data.db.WordDB
import com.gumsiz.words.data.db.WordsDAO
import com.gumsiz.words.data.db.toWordList
import com.gumsiz.words.data.network.WordApi
import com.gumsiz.words.data.network.WordN
import com.gumsiz.words.data.network.toDbData
import com.gumsiz.words.data.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class WordRepository(val database: WordsDAO, application: Application) {

    val wordlist: LiveData<List<Word>> = Transformations.map(database.getAll()) {
        it.toWordList()
    }

    suspend fun getDatafromServer() {
        withContext(Dispatchers.IO) {
            val list = WordApi.retrofitService.getWords()
            database.insertAll(*list.toDbData())
        }
    }

}
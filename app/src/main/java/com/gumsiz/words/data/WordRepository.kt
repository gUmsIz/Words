package com.gumsiz.words.data

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gumsiz.words.R
import com.gumsiz.words.data.db.WordsDAO
import com.gumsiz.words.data.db.toWordList
import com.gumsiz.words.data.network.WordApi
import com.gumsiz.words.data.network.WordN
import com.gumsiz.words.data.network.toDbData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.StringWriter

class WordRepository(val database: WordsDAO, val application: Application) {

    val wordList: LiveData<List<Word>> = Transformations.map(database.getAll()) {
        it.toWordList()
    }
    val wordListFav: LiveData<List<Word>> = Transformations.map(database.getSearch()) {
        it.toWordList()
    }

    // For real ServerData
    suspend fun getDataFromServer() {
        withContext(Dispatchers.IO) {
            val list = WordApi.retrofitService.getWords()
            database.insertAll(*list.toDbData())
        }
    }

    suspend fun getDataFromMockServer() {
        withContext(Dispatchers.IO) {
            val gson = Gson()
            val jsonString: String
            try {
                jsonString = application.resources.openRawResource(R.raw.words).bufferedReader()
                    .use { it.readText() }
                val list = gson.fromJson(jsonString, Array<WordN>::class.java).toList()
                database.insertAll(*list.toDbData())
            } catch (ioException: IOException) {
                ioException.printStackTrace()
            }

        }
    }

}
package com.gumsiz.words.data

import com.gumsiz.shared.data.db.Database
import android.app.Application
import com.google.gson.Gson
import com.gumsiz.words.R
import com.gumsiz.words.data.network.WordApi
import com.gumsiz.words.data.network.WordN
import com.gumsiz.words.data.network.toNewDBData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject
import java.io.IOException

class WordRepository : KoinComponent {
    val newDataBase: Database by inject()
    private val application: Application by inject()

    // For real ServerData
    suspend fun getDataFromServer() {
        withContext(Dispatchers.IO) {
            val list = WordApi.retrofitService.getWords()

            val newModelList = list.toNewDBData()
            newDataBase.addAllItems(newModelList)
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
                newDataBase.addAllItems(list.toNewDBData())
            } catch (ioException: IOException) {
                ioException.printStackTrace()
            }

        }
    }

}
package com.gumsiz.words.ui.mainf

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData
import com.gumsiz.words.R
import com.gumsiz.words.data.Word
import com.gumsiz.words.data.WordRepository
import com.gumsiz.words.data.db.WordsDAO
import com.gumsiz.words.data.utils.Resource
import kotlinx.coroutines.*
import java.lang.Exception

class MainViewModel(database: WordsDAO, application: Application) :
    AndroidViewModel(application) {
    //CoroutineJOB
    private var viewModelJob = Job()

    val msg = application.getString(R.string.load_message)

    // Coroutine Scope
    private val scope = CoroutineScope(Dispatchers.Main + viewModelJob)

    val repository = WordRepository(database, application)

    val sData = application.getSharedPreferences("data", Context.MODE_PRIVATE)


    //List of words from db
    private var _data = repository.wordList as MutableLiveData<List<Word>>
    private var _favData = repository.wordListFav as MutableLiveData<List<Word>>
    private var totalList: List<Word>? = null

    val data: LiveData<List<Word>> = _data
    val favoritedata: LiveData<List<Word>> = _favData
    //get() = _data

    init {
        prepare()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    fun update() {
        sData.edit().putBoolean("dataLoaded", false).apply()
        prepare()
    }

    fun searchInList(text: String){
        if (totalList == null) totalList = data.value
        if (text.isEmpty()) {
            _data.value = totalList
        } else {
            _data.value = totalList?.filter {
                it.name.contains(text, true)
            }
        }
    }
    fun searchInFavList(text: String){
        val favList = data.value?.filter { it.favorite }
        if (text.isEmpty()) {
            _favData.value = favList
        } else {
            _favData.value = favList?.filter {
                it.name.contains(text, true)
            }
        }
    }


    fun prepare() = liveData(Dispatchers.IO) {
        if (!sData.getBoolean("dataLoaded", false)) {
            emit(Resource.loading(data = null, message = msg))
            try {
                // From Api activate this method instead of MockData
                //repository.getDataFromServer()
                repository.getDataFromMockServer()
                sData.edit().putBoolean("dataLoaded", true).apply()
                emit(Resource.success(data = null))

            } catch (exception: Exception) {
                emit(Resource.error(data = null, message = exception.message ?: "Error Occured!"))
            }
        } else {
            emit(Resource.success(data = null))
        }
    }

}



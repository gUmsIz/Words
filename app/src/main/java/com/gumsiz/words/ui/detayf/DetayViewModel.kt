package com.gumsiz.words.ui.detayf

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gumsiz.words.data.db.WordDB
import com.gumsiz.words.data.db.WordsDAO
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

class DetayViewModel(
    val database: WordsDAO,
    application: Application
) : AndroidViewModel(application) {

    //CoroutineJOB
    private var viewModelJob = Job()
    // Coroutine Scope
    private val scope = CoroutineScope(Dispatchers.Main + viewModelJob)


    fun doAction(wordDB: WordDB){
        scope.launch {
            database.update(wordDB)
        }
    }

    fun getWord(key:String)=database.get(key)


    private val _favUpdate=MutableLiveData<Int>()
    val favUpdate:LiveData<Int>
        get() = _favUpdate

    fun addFav(wordDB: WordDB){
        scope.launch {
            database.update(wordDB)
            if (wordDB.favorite) _favUpdate.value=1 else _favUpdate.value=2
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
package com.gumsiz.words.ui.detayf

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.gumsiz.shared.data.Repository
import com.gumsiz.shared.data.model.WordModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class DetailViewModel(
    private val wordRepository: Repository
) : ViewModel() {


    //CoroutineJOB
    private var viewModelJob = Job()

    // Coroutine Scope
    private val scope = CoroutineScope(Dispatchers.Main + viewModelJob)


    fun doAction(wordDB: WordModel) {
        scope.launch {
            wordRepository.updateFavoriteStateInDB(wordDB)
        }
    }

    fun getVerb(key: String): StateFlow<WordModel?> = flow {
        emit(wordRepository.getWordFromDB(key))
    }.stateIn(scope, SharingStarted.Eagerly, null)


    private val _favUpdate = MutableLiveData<Int>()
    val favUpdate: LiveData<Int>
        get() = _favUpdate

    fun addFav(wordDB: WordModel) {
        scope.launch {
            //database.update(wordDB)
            if (wordDB.favorite) _favUpdate.value = 1 else _favUpdate.value = 2
            wordRepository.updateFavoriteStateInDB(wordDB)
        }
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }
}
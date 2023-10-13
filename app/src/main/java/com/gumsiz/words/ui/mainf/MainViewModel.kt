package com.gumsiz.words.ui.mainf

import android.app.Application
import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.gumsiz.words.R
import com.gumsiz.words.data.WordRepository
import com.gumsiz.words.data.utils.Resource
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.mapLatest
import kotlinx.coroutines.flow.stateIn
import java.lang.Exception

//TODO get rid of livedata
//TODO refactor sharedpref to a manager
@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class MainViewModel(application: Application, private val repository: WordRepository) :
    ViewModel() {

    private val msg = application.getString(R.string.load_message)

    val sData = application.getSharedPreferences("data", Context.MODE_PRIVATE)


    //List of words from db
    private var _newData = repository.newDataBase.verbList
    private var _newFavData = repository.newDataBase.verbListFavorite

    val searchQuery = MutableStateFlow("")
    val searchQueryInFavorite = MutableStateFlow("")
    val newData = searchQuery.debounce(250).flatMapLatest { searchQuery ->
        _newData.mapLatest { verbList ->
            verbList.filter {
                it!!.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val newFavData = searchQueryInFavorite.debounce(250).flatMapLatest { searchQuery ->
        _newFavData.mapLatest { favoriteVerbList ->
            favoriteVerbList.filter {
                it!!.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    //get() = _data

    init {
        prepare()
    }

    fun update() {
        sData.edit().putBoolean("dataLoaded", false).apply()
        prepare()
    }

    fun searchInList(text: String) {
        searchQuery.value = text
    }

    fun searchInFavList(text: String) {
        searchQueryInFavorite.value = text
    }


    fun prepare() = liveData(Dispatchers.IO) {
        if (!sData.getBoolean("dataLoaded", false)) {
            emit(Resource.loading(data = null, message = msg))
            try {
                // From Api activate this method instead of MockData
                repository.getDataFromServer()
                //repository.getDataFromMockServer()
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



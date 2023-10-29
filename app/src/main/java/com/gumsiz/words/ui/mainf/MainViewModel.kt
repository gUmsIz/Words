package com.gumsiz.words.ui.mainf

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumsiz.shared.data.Repository
import com.gumsiz.words.R
import com.gumsiz.words.utils.Resource
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
class MainViewModel(application: Application, private val wordRepository: Repository) :
    ViewModel() {

    private val msg = application.getString(R.string.load_message)

    val sData = application.getSharedPreferences("data", Context.MODE_PRIVATE)


    val searchQuery = MutableStateFlow("")
    val searchQueryInFavorite = MutableStateFlow("")
    val allVerbsList = searchQuery.debounce(250).flatMapLatest { searchQuery ->
        wordRepository.verbList.mapLatest { verbList ->
            verbList.filter {
                it!!.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())
    val favoriteVerbsList = searchQueryInFavorite.debounce(250).flatMapLatest { searchQuery ->
        wordRepository.verbListFavorite.mapLatest { favoriteVerbList ->
            favoriteVerbList.filter {
                it!!.name.contains(searchQuery, ignoreCase = true)
            }
        }
    }.stateIn(viewModelScope, SharingStarted.Eagerly, emptyList())

    val dataStateFlow = MutableStateFlow(Resource.loading(data = "null", message = msg))

    init {
        prepare()
    }

    fun update() {
        sData.edit().putBoolean("dataLoaded", false).apply()
    }

    fun searchInList(text: String) {
        searchQuery.value = text
    }

    fun searchInFavList(text: String) {
        searchQueryInFavorite.value = text
    }


    private fun prepare() {
        viewModelScope.launch {

            Log.i("MainScreen", "prepare called")
            if (!sData.getBoolean("dataLoaded", false)) {
                dataStateFlow.value = Resource.loading(data = null, message = msg)
                try {
                    wordRepository.loadAllData()
                    Log.i("MainScreen", "prepare called2")
                    sData.edit().putBoolean("dataLoaded", true).apply()
                    dataStateFlow.value = Resource.success(data = "null")
                } catch (exception: Exception) {
                    dataStateFlow.value =
                        Resource.error(data = null, message = exception.message ?: "Error Occured!")
                }
            } else {
                dataStateFlow.value = Resource.success(data = "null")
            }
        }
    }

}



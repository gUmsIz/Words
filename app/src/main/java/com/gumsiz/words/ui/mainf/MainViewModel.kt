package com.gumsiz.words.ui.mainf

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gumsiz.shared.data.Repository
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
@OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
class MainViewModel(private val wordRepository: Repository) :
    ViewModel() {

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

    val dataStateFlow = MutableStateFlow(Resource.loading(data = "null", message = ""))

    init {
        prepare()
    }

    fun searchInList(text: String) {
        searchQuery.value = text
    }

    fun searchInFavList(text: String) {
        searchQueryInFavorite.value = text
    }


    private fun prepare() {
        viewModelScope.launch {
            try {
                wordRepository.loadAllData()
                dataStateFlow.value = Resource.success(data = "null")
            } catch (exception: Exception) {
                dataStateFlow.value =
                    Resource.error(data = null, message = exception.message ?: "Error Occured!")
            }
        }
    }

}



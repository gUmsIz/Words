package com.gumsiz.words.ui.mainf

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gumsiz.words.data.db.WordsDAO

class MainViewModelFactory(
    private val database: WordsDAO,
    private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MainViewModel::class.java)) {
            return MainViewModel(database, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

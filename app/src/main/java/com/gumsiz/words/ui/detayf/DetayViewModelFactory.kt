package com.gumsiz.words.ui.detayf

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.gumsiz.words.data.db.WordsDAO
import com.gumsiz.words.ui.mainf.MainViewModel

class DetayViewModelFactory(
private val database: WordsDAO,
private val application: Application
) : ViewModelProvider.Factory {
    @Suppress("unchecked_cast")
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DetayViewModel::class.java)) {
            return DetayViewModel(database, application) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
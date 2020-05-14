package com.gumsiz.words.ui.mainf

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.gumsiz.words.data.db.WordDB
import com.gumsiz.words.data.db.WordsDAO
import kotlinx.coroutines.*

class MainViewModel(val database: WordsDAO, application: Application) :
    AndroidViewModel(application) {
    //CoroutineJOB
    private var viewModelJob = Job()

    // Coroutine Scope
    private val scope = CoroutineScope(Dispatchers.Main + viewModelJob)

    var word1 = WordDB()
    var word2 = WordDB()
    var word3 = WordDB()
    var word4 = WordDB()
    var word5 = WordDB()
    var word6 = WordDB()
    var word7 = WordDB()
    var word8 = WordDB()
    var word9 = WordDB()
    var word10 = WordDB()

    //List of words from db
    private var _data = MutableLiveData<List<WordDB>>()

    val data: LiveData<List<WordDB>>
        get() = _data

    init {
        makeWords()
        entrWord()
    }

    override fun onCleared() {
        super.onCleared()
        viewModelJob.cancel()
    }

    private fun getWord() {
        scope.launch {
            _data.value = getFromDB()
        }
    }

    private suspend fun getFromDB(): List<WordDB> {
        return withContext(Dispatchers.IO) {
            var word = database.getAll()
            word
        }
    }


    private fun entrWord() {
        scope.launch { entWord() }
    }

    private suspend fun entWord() {
        withContext(Dispatchers.IO) {
            database.insert(word1)
            database.insert(word2)
            database.insert(word3)
            getWord()
        }
    }

    fun makeWords() {
        word1.name = "abbiegen"
        word1.Imp = "bieg ab"
        word1.firstSg = "biege ab"
        word1.secondSg = "biegst ab"
        word1.pret = "bog ab"
        word1.perfSg = "bin abgebogen"
        word1.konj2FSg = "böge ab"
        word1.sampleSentence = "Das Auto biegt in eine Nebenstraße ab."
        word2.name = "abbrechen"
        word2.Imp = "brich ab"
        word2.firstSg = "breche ab"
        word2.secondSg = "brichst ab"
        word2.pret = "brach ab"
        word2.perfSg = "habe/bin abgebrochen"
        word2.konj2FSg = "bräche ab"
        word2.sampleSentence = "Der Zweig bricht ab."
        word3.name = "abbringen"
        word3.Imp = "bring ab"
        word3.firstSg = "bringe ab"
        word3.secondSg = "bringst ab"
        word3.pret = "brachte ab"
        word3.perfSg = "habe abgebracht"
        word3.konj2FSg = "brächte ab"
        word3.sampleSentence = "Ich bringe dich von deinem Plan ab."
    }

}



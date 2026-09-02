package com.gumsiz.shared.data.db

import com.gumsiz.shared.data.model.SettingDatabaseModel
import com.gumsiz.shared.data.model.WordDatabaseModel
import com.gumsiz.shared.data.model.WordModel
import com.gumsiz.shared.data.model.toWordModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

interface Database {
    val verbList: Flow<List<WordModel?>>
    val verbListFavorite: Flow<List<WordModel?>>
    suspend fun addAllItems(words: List<WordDatabaseModel>)
    suspend fun update(wordModel: WordModel)
    suspend fun getVerb(name: String): WordModel?
    suspend fun searchInVerbs(searchQuery: String): Flow<List<WordModel?>>
    suspend fun getHasDataLoaded(): Boolean
    suspend fun setDataLoaded(isDataLoaded: Boolean)
}

class DataBaseImpl(private val wordsDatabase: WordsDatabase) : Database {
    private val wordsDao = wordsDatabase.wordsDao()
    private val settingsDao = wordsDatabase.settingsDao()

    override val verbList: Flow<List<WordModel?>>
        get() = wordsDao.getAllWords().map { list ->
            list.map { it.toWordModel() }
        }

    override val verbListFavorite: Flow<List<WordModel?>>
        get() = wordsDao.getFavoriteWords().map { list ->
            list.map { it.toWordModel() }
        }

    override suspend fun addAllItems(words: List<WordDatabaseModel>) {
        val sanitizedWords = words.map { word ->
            word.copy(
                sampleSentence = word.sampleSentence
                    ?.replace("&#8222;", "„")
                    ?.replace("&#8220;", "“")
                    ?.replace("&#8211;", "–")
            )
        }
        wordsDao.insertAll(sanitizedWords)
    }

    override suspend fun update(wordModel: WordModel) {
        wordsDao.updateWord(
            name = wordModel.name,
            favorite = wordModel.favorite,
            translation = Json.encodeToString(wordModel.translation)
        )
    }

    override suspend fun getVerb(name: String): WordModel? =
        wordsDao.getWord(name)?.toWordModel()

    override suspend fun searchInVerbs(searchQuery: String): Flow<List<WordModel?>> =
        wordsDao.searchWords(searchQuery).map { list ->
            list.map { it.toWordModel() }
        }

    override suspend fun getHasDataLoaded(): Boolean =
        settingsDao.isDataLoaded() ?: false

    override suspend fun setDataLoaded(isDataLoaded: Boolean) {
        settingsDao.setSettings(SettingDatabaseModel(id = 1, dataLoaded = isDataLoaded))
    }
}
package com.gumsiz.shared.data.db

import com.gumsiz.shared.data.model.SettingDatabaseModel
import com.gumsiz.shared.data.model.WordDatabaseModel
import com.gumsiz.shared.data.model.WordModel
import com.gumsiz.shared.data.model.toWordModel
import io.realm.kotlin.Realm
import io.realm.kotlin.ext.query
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

interface Database {
    val verbList: Flow<List<WordModel?>>
    val verbListFavorite: Flow<List<WordModel?>>
    suspend fun addAllItems(words: List<WordDatabaseModel>)
    suspend fun update(name: String)
    suspend fun getVerb(name: String): WordModel?
    suspend fun searchInVerbs(searchQuery: String): Flow<List<WordModel?>>
    suspend fun getHasDataLoaded(): Boolean
    suspend fun setDataLoaded(isDataLoaded: Boolean)
}

class DataBaseImpl(private val realm: Realm) : Database {
    override val verbList: Flow<List<WordModel?>>
        get() = flow {
            realm.query<WordDatabaseModel>().find().asFlow().collect {
                emit(it.list.map { it.toWordModel() })
            }
        }
    override val verbListFavorite: Flow<List<WordModel?>>
        get() = flow {
            realm.query<WordDatabaseModel>("favorite == $0", true).find().asFlow().collect {
                emit(it.list.map { it.toWordModel() })
            }
        }

    override suspend fun addAllItems(words: List<WordDatabaseModel>) {
        CoroutineScope(Dispatchers.Default).launch {
            realm.write {
                words.map {
                    copyToRealm(it)
                }
            }
        }
    }

    override suspend fun update(name: String) {
        CoroutineScope(Dispatchers.Default).launch {
            realm.write {
                val wordInDB = this.query<WordDatabaseModel>("name==$0", name).find().first()
                wordInDB.favorite = !wordInDB.favorite
            }
        }
    }

    override suspend fun getVerb(name: String): WordModel? =
        realm.query<WordDatabaseModel>("name==$0", name).find().first().toWordModel()

    override suspend fun searchInVerbs(searchQuery: String) = flow {
        realm.query<WordDatabaseModel>("name TEXT $0", searchQuery).find().asFlow().collect {
            emit(it.list.map { it.toWordModel() })
        }
    }

    override suspend fun getHasDataLoaded(): Boolean =
        try {
            realm.query<SettingDatabaseModel>().find().first().dataLoaded
        } catch (e: NoSuchElementException) {
            false
        }

    override suspend fun setDataLoaded(isDataLoaded: Boolean) {
        CoroutineScope(Dispatchers.Default).launch {
            realm.write {
                val settings = try {
                    realm.query<SettingDatabaseModel>().find().first().dataLoaded
                } catch (e: NoSuchElementException) {
                    null
                }
                if (settings == null) copyToRealm(SettingDatabaseModel(dataLoaded = isDataLoaded)) else (settings as SettingDatabaseModel).dataLoaded =
                    isDataLoaded
            }
        }
    }
}
package com.gumsiz.shared.data

import com.gumsiz.shared.data.db.Database
import com.gumsiz.shared.data.model.WordModel
import com.gumsiz.shared.data.model.toDBData
import com.gumsiz.shared.data.network.NetworkService

class Repository(
    private val database: Database,
    private val networkService: NetworkService
) {
    val verbList = database.verbList

    val verbListFavorite = database.verbListFavorite
    suspend fun loadAllData(){
        val verbList = networkService.getData()
        database.addAllItems(verbList.toDBData())
    }

    suspend fun updateFavoriteStateInDB(name:String) = database.update(name)

    suspend fun getWordFromDB(name: String): WordModel? = database.getVerb(name)
}
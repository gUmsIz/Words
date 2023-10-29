package com.gumsiz.shared.data

import com.gumsiz.shared.data.db.Database
import com.gumsiz.shared.data.model.WordModel
import com.gumsiz.shared.data.model.toDBData
import com.gumsiz.shared.data.network.NetworkService
import io.ktor.utils.io.core.Closeable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class Repository(
    private val database: Database,
    private val networkService: NetworkService
) {
    val verbList : CommonFlow<List<WordModel?>> = database.verbList.asCommonFlow()

    val verbListFavorite : CommonFlow<List<WordModel?>> = database.verbListFavorite.asCommonFlow()
    suspend fun loadAllData(){
        val verbList = networkService.getData()
        println("Verbs loaded")
        database.addAllItems(verbList.toDBData())
        println("Verbs added to DB")
    }

    suspend fun updateFavoriteStateInDB(name:String) = database.update(name)

    suspend fun getWordFromDB(name: String): WordModel? = database.getVerb(name)
}

fun <T> Flow<T>.asCommonFlow(): CommonFlow<T> = CommonFlow(this)
class CommonFlow<T>(private val origin: Flow<T>) : Flow<T> by origin {
    fun watch(block: (T) -> Unit): Closeable {
        val job = Job()

        onEach {
            block(it)
        }.launchIn(CoroutineScope(Dispatchers.Main + job))

        return object : Closeable {
            override fun close() {
                job.cancel()
            }
        }
    }
}
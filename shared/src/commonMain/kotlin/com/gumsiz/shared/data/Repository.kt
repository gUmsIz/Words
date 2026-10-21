package com.gumsiz.shared.data

import com.gumsiz.shared.data.db.Database
import com.gumsiz.shared.data.model.WordModel
import com.gumsiz.shared.data.model.toDBData
import com.gumsiz.shared.data.network.NetworkService
import com.gumsiz.shared.data.settings.SettingsStorage
import io.ktor.utils.io.core.Closeable
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach

class Repository(
    private val database: Database,
    private val networkService: NetworkService,
    private val settingsStorage: SettingsStorage
) {
    val verbList : CommonFlow<List<WordModel?>> = database.verbList.asCommonFlow()

    val verbListFavorite : CommonFlow<List<WordModel?>> = database.verbListFavorite.asCommonFlow()

    private val _hasClickedCrossPromotion = MutableStateFlow(settingsStorage.hasClickedCrossPromotion())
    val hasClickedCrossPromotion: CommonFlow<Boolean> = _hasClickedCrossPromotion.asCommonFlow()

    fun isCrossPromotionClicked(): Boolean = _hasClickedCrossPromotion.value

    fun setCrossPromotionClicked() {
        settingsStorage.setCrossPromotionClicked(true)
        _hasClickedCrossPromotion.value = true
    }
    suspend fun loadAllData(){
        val hasData = database.getHasDataLoaded()
        if (hasData) return
        val verbList = networkService.getData()
        database.addAllItems(verbList.toDBData())
        database.setDataLoaded(true)
    }

    suspend fun updateFavoriteStateInDB(wordModel: WordModel) = database.update(wordModel)

    suspend fun getWordFromDB(name: String): WordModel? = database.getVerb(name)

    suspend fun sendFeedback(
        email: String,
        message: String,
        platform: String,
        appVersion: String = "",
        buildNumber: String = "",
        bundleId: String = ""
    ): Boolean = networkService.sendFeedback(email, message, platform, appVersion, buildNumber, bundleId)
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
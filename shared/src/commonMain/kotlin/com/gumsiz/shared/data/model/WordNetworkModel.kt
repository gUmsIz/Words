package com.gumsiz.shared.data.model

import kotlinx.serialization.Serializable
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

@Serializable
data class WordNetworkModel(
    val name: String,
    val cekim_1: String?,
    val cekim_2: String?,
    val imp: String?,
    val pret: String?,
    val perf: String?,
    val konj: String?,
    val struktur: Array<String?>?,
    val beispiel: Array<String?>?
)

fun List<WordNetworkModel>.toDBData(): List<WordDatabaseModel>{
    return map {
        WordDatabaseModel(
            it.name,
            Json.encodeToString(mutableListOf<String>()),
            it.cekim_1,
            it.cekim_2,
            it.imp,
            it.pret,
            it.perf,
            it.konj,
            Json.encodeToString(it.struktur),
            Json.encodeToString(it.beispiel),
            false
        )
    }
}
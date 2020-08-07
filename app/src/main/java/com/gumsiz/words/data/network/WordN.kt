package com.gumsiz.words.data.network

import com.google.gson.Gson
import com.gumsiz.words.data.db.WordDB


data class WordN(
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

fun List<WordN>.toDbData(): Array<WordDB> {
    val gson = Gson()
    return map {
        WordDB(
            it.name,
            gson.toJson(mutableListOf<String>()),
            it.cekim_1,
            it.cekim_2,
            it.imp,
            it.pret,
            it.perf,
            it.konj,
            gson.toJson(it.struktur),
            gson.toJson(it.beispiel),
            false
        )
    }.toTypedArray()
}
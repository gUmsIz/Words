package com.gumsiz.shared.data.model

import kotlinx.serialization.json.Json

data class WordModel(
    val name: String,
    var translation: List<String?>,
    val firstSg: String?,
    val secondSg: String?,
    val imp: String?,
    val pret: String?,
    val perfSg: String?,
    val konj2FSg: String?,
    val structure: List<String?>?,
    val sampleSentence: List<String?>?,
    var favorite: Boolean
)

fun WordDatabaseModel.toWordModel(): WordModel? {

    return this.translation?.let { Json.decodeFromString<List<String?>>(it) }?.let {
        WordModel(
            this.name,
            it,
            this.firstSg,
            this.secondSg,
            this.Imp,
            this.pret,
            this.perfSg,
            this.konj2FSg,
            this.structur?.let { Json.decodeFromString<List<String?>>(it) },
            this.sampleSentence?.let { Json.decodeFromString<List<String?>>(it) },
            this.favorite
        )
    }
}
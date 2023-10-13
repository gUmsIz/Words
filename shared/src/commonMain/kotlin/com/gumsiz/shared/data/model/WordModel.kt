package com.gumsiz.shared.data.model

import kotlinx.serialization.json.Json

data class WordModel(
    val name: String,
    var translation: MutableList<String?>,
    val firstSg: String?,
    val secondSg: String?,
    val imp: String?,
    val pret: String?,
    val perfSg: String?,
    val konj2FSg: String?,
    val structure: Array<String?>?,
    val sampleSentence: Array<String?>?,
    var favorite: Boolean
)

fun WordDatabaseModel.toWordModel(): WordModel? {

    return this.translation?.let { Json.decodeFromString<MutableList<String?>>(it) }?.let {
        WordModel(
            this.name,
            it,
            this.firstSg,
            this.secondSg,
            this.Imp,
            this.pret,
            this.perfSg,
            this.konj2FSg,
            this.structur?.let { Json.decodeFromString<Array<String?>>(it) },
            this.sampleSentence?.let { Json.decodeFromString<Array<String?>>(it) },
            this.favorite
        )
    }
}
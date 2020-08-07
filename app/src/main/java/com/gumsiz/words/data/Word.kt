package com.gumsiz.words.data

import com.google.gson.Gson
import com.gumsiz.words.data.db.WordDB

data class Word(
    val name: String
    , var translation: MutableList<String?>
    , val firstSg: String?
    , val secondSg: String?
    , val imp: String?
    , val pret: String?
    , val perfSg: String?
    , val konj2FSg: String?
    , val structure: Array<String?>?
    , val sampleSentence: Array<String?>?
    , var favorite: Boolean
)

fun Word.toWordDB(): WordDB {
    val wordJson = Gson()
    return WordDB(
        this.name,
        wordJson.toJson(this.translation),
        this.firstSg,
        this.secondSg,
        this.imp,
        this.pret,
        this.perfSg,
        this.konj2FSg,
        wordJson.toJson(this.structure),
        wordJson.toJson(this.sampleSentence),
        this.favorite
    )
}
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

fun Word.toWordDB():WordDB{
    val gson=Gson()
    return WordDB(
        this.name,
        gson.toJson(this.translation),
        this.firstSg,
        this.secondSg,
        this.imp,
        this.pret,
        this.perfSg,
        this.konj2FSg,
        gson.toJson(this.structure),
        gson.toJson(this.sampleSentence),
        this.favorite
    )
}
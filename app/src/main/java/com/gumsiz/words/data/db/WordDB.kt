package com.gumsiz.words.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.gumsiz.words.data.Word

@Entity(tableName = "words_table")
data class WordDB(
    @PrimaryKey var name: String = ""
    , var translation: String? = null
    , var firstSg: String? = null
    , var secondSg: String? = null
    , var Imp: String? = null
    , var pret: String? = null
    , var perfSg: String? = null
    , var konj2FSg: String? = null
    , var structur: String? = null
    , var sampleSentence: String? = null
    , var favorite: Boolean = false
)

fun List<WordDB>.toWordList():List<Word>{
    val gson= Gson()
    val listTutorialType = object : TypeToken<MutableList<String>>() {}.type
    return map {
        Word(
            it.name,
            gson.fromJson(it.translation,listTutorialType),
            it.firstSg,
            it.secondSg,
            it.Imp,
            it.pret,
            it.perfSg,
            it.konj2FSg,
            gson.fromJson(it.structur,Array<String?>::class.java),
            gson.fromJson(it.sampleSentence,Array<String?>::class.java),
            it.favorite
        )
    }
}

fun WordDB.toWord():Word{
    val gson= Gson()
    val listTutorialType = object : TypeToken<MutableList<String>>() {}.type
    return Word(
            this.name,
            gson.fromJson(this.translation,listTutorialType),
            this.firstSg,
            this.secondSg,
            this.Imp,
            this.pret,
            this.perfSg,
            this.konj2FSg,
            gson.fromJson(this.structur,Array<String?>::class.java),
            gson.fromJson(this.sampleSentence,Array<String?>::class.java),
            this.favorite
        )
}





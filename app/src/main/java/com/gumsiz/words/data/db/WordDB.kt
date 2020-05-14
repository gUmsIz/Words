package com.gumsiz.words.data.db

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words_table")
data class WordDB(
    @PrimaryKey(autoGenerate = true) var wordId: Int = 0
    , var name: String = ""
    , var firstSg: String? = null
    , var secondSg: String? = null
    , var Imp: String? = null
    , var pret: String? = null
    , var perfSg: String? = null
    , var konj2FSg: String? = null
    , var translation: String = ""
    , var sampleSentence: String? = null
)
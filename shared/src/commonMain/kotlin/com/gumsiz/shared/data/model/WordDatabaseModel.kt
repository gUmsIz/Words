package com.gumsiz.shared.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "words")
data class WordDatabaseModel(
    @PrimaryKey
    var name: String = "",
    var translation: String? = null,
    var firstSg: String? = null,
    var secondSg: String? = null,
    var Imp: String? = null,
    var pret: String? = null,
    var perfSg: String? = null,
    var konj2FSg: String? = null,
    var structur: String? = null,
    var sampleSentence: String? = null,
    var favorite: Boolean = false
)

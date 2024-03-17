package com.gumsiz.shared.data.model

import io.realm.kotlin.types.RealmObject
import io.realm.kotlin.types.annotations.PrimaryKey
import org.mongodb.kbson.ObjectId

class WordDatabaseModel() : RealmObject {
    @PrimaryKey
    var id: ObjectId = ObjectId()
    var name: String = ""
    var translation: String? = null
    var firstSg: String? = null
    var secondSg: String? = null
    var Imp: String? = null
    var pret: String? = null
    var perfSg: String? = null
    var konj2FSg: String? = null
    var structur: String? = null
    var sampleSentence: String? = null
    var favorite: Boolean = false

    constructor(
        name: String = "",
        translation: String? = null,
        firstSg: String? = null,
        secondSg: String? = null,
        Imp: String? = null,
        pret: String? = null,
        perfSg: String? = null,
        konj2FSg: String? = null,
        structur: String? = null,
        sampleSentence: String? = null,
        favorite: Boolean = false
    ) : this() {
        this.name = name
        this.translation = translation
        this.firstSg = firstSg
        this.secondSg = secondSg
        this.Imp = Imp
        this.pret = pret
        this.perfSg = perfSg
        this.konj2FSg = konj2FSg
        this.structur = structur
        this.sampleSentence = sampleSentence
        this.favorite = favorite
    }
}


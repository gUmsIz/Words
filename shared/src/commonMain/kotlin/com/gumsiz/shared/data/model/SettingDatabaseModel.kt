package com.gumsiz.shared.data.model

import io.realm.kotlin.types.RealmObject

class SettingDatabaseModel(var dataLoaded : Boolean) : RealmObject {
    constructor(): this(false)
}
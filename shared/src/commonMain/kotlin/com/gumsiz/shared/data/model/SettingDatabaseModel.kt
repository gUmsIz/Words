package com.gumsiz.shared.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "settings")
data class SettingDatabaseModel(
    @PrimaryKey
    var id: Int = 1,
    var dataLoaded: Boolean = false
)
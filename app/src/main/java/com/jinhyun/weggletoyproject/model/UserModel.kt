package com.jinhyun.weggletoyproject.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "User")
data class UserModel(
    @PrimaryKey(autoGenerate = true)
    var id: Long?,

    @ColumnInfo(name = "signUpType")
    var signUpType: String,

    @ColumnInfo(name = "socialId")
    var socialId: String,

    @ColumnInfo(name = "pushToken")
    var pushToken: String,

    @ColumnInfo(name = "os")
    var os: String,

    @ColumnInfo(name = "versionApp")
    var versionApp: String,

    @ColumnInfo(name = "email")
    var email: String
)
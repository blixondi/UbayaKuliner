package com.shem.ubayafood.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @ColumnInfo(name="username")
    var username: String,
){
    @PrimaryKey(autoGenerate = false)
    var id:Int = 0
}
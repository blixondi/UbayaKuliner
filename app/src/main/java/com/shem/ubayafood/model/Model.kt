package com.shem.ubayafood.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName


//model for Room database
@Entity
data class Session(
    @ColumnInfo(name="username")
    var username: String,
){
    @PrimaryKey(autoGenerate = false)
    var id:Int = 0
}

//model for Live Data
data class User(
    @SerializedName("id")
    val user_id:Int,
    val username: String,
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val password: String,
    val saldo: Int
)

data class Food(
    @SerializedName("id")
    val food_id:Int,
    @SerializedName("name")
    val food_name:String,
    val description:String,
    @SerializedName("price")
    val food_price:String,
    @SerializedName("img")
    val food_img:String
)

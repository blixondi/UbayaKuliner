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
@Entity("users")
data class User(
    @SerializedName("id")
    @ColumnInfo("id")
    @PrimaryKey(false)
    var user_id:Int,
    @ColumnInfo("username")
    var username: String,
    @SerializedName("first_name")
    @ColumnInfo("first_name")
    var firstName: String,
    @SerializedName("last_name")
    @ColumnInfo("last_name")
    var lastName: String,
    @ColumnInfo("password")
    var password: String,
    @SerializedName("balance")
    @ColumnInfo("balance")
    var balance: Int
)

@Entity("foods")
data class Food(
    @SerializedName("id")
    @ColumnInfo("id")
    @PrimaryKey(autoGenerate = false)
    var food_id:Int,
    @SerializedName("name")
    @ColumnInfo("name")
    var food_name:String,
    @ColumnInfo("description")
    var description:String,
    @SerializedName("price")
    @ColumnInfo("price")
    var food_price:String,
    @SerializedName("img")
    @ColumnInfo("img")
    var food_img:String,
    @ColumnInfo(name = "is_favourite")
    var is_favourite: Int
)

data class Order(
    var order_id:String,
    @SerializedName("name")
    var food_name:String,
    @SerializedName("price")
    var food_price: String,
    @SerializedName("img")
    var food_img:String,
    var quantity: String,
    var time: String,
    var behalf:String,
    var address:String,
    var order_total:String
)

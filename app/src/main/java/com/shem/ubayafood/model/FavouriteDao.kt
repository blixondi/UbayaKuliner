package com.shem.ubayafood.model


import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface FavouriteDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg food:Food)

    @Query("SELECT * FROM foods WHERE is_favourite = 1")
    fun selectAllFood(): Food

    @Query("UPDATE foods SET is_favourite = :is_favourite WHERE ID = :id")
    fun updateFavourite(id: Int, is_favourite:Int)

    @Query("DELETE FROM foods")
    fun deleteFavourite()

}
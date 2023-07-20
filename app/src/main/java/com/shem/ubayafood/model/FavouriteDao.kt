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
    fun selectAllFood(): List<Food>

    @Query("SELECT is_favourite FROM foods WHERE id = :id")
    fun checkFavorite(id: Int): Int

    @Query("UPDATE foods SET is_favourite = :is_favourite WHERE id = :id")
    fun updateFavourite(id: Int, is_favourite:Int)

    @Query("DELETE FROM foods")
    fun deleteFavourite()

}
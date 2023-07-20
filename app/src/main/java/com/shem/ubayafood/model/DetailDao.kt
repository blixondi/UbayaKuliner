package com.shem.ubayafood.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query


@Dao
interface DetailDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg detail:Detail)

    @Query("SELECT address, recipient FROM detail WHERE id = :id")
    fun getDetail(id: Int): Detail

    @Query("UPDATE detail SET address = :address, recipient = :recipient WHERE id = :id")
    fun updateDetail(address: String, recipient: String, id: Int)

    @Query("DELETE FROM detail")
    fun deleteDetail()
}
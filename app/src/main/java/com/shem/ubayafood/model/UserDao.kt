package com.shem.ubayafood.model

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg user:User)

    @Query("SELECT * FROM users WHERE id= :id")
    fun selectUser(id:Int): User

    @Query("DELETE FROM users")
    fun deleteUser()

}
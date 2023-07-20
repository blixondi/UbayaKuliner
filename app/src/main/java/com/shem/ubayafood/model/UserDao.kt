package com.shem.ubayafood.model

import androidx.room.*

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(vararg user:User)

    @Query("SELECT * FROM users WHERE id= :id")
    fun selectUser(id:Int): User

    @Query("SELECT balance FROM users WHERE id = :id")
    fun getBalance(id: Int): Int

    @Query("UPDATE users set username=:username , first_name=:firstName , last_name=:lastName , password=:password where id=:id")
    fun updateUser(id:Int, username: String, firstName: String, lastName: String, password: String)

    @Query("UPDATE users set balance=balance+:balance where id=:id")
    fun updateBalance(id:Int, balance: Int)

    @Query("UPDATE users set balance=balance-:balance where id=:id")
    fun reduceBalance(id:Int, balance: Int)

    @Query("DELETE FROM users")
    fun deleteUser()

}
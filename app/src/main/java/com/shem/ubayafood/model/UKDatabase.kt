package com.shem.ubayafood.model

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shem.ubayafood.util.MIGRATION_1_2
import com.shem.ubayafood.util.MIGRATION_2_3

@Database(entities = [User::class, Food::class, Detail::class], version = 3)
abstract class UKDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao
    abstract fun favouriteDao(): FavouriteDao

    abstract fun detailDao(): DetailDao

    companion object {
        @Volatile
        private var instance: UKDatabase? = null
        private val LOCK = Any()
        private fun buildDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                UKDatabase::class.java,
                "ukdb"
            ).addMigrations(MIGRATION_1_2, MIGRATION_2_3).build()

        operator fun invoke(context: Context) {
            if (instance != null) {
                synchronized(LOCK) {
                    instance ?: buildDatabase(context).also {
                        instance = it
                    }
                }
            }
        }

    }

}
package com.mamunsproject.grocery_roomdb

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [GroceryItem::class], version = 1)
abstract class GroceryDatabase : RoomDatabase() {

    abstract fun getGroceryDao(): GroceryDao


    companion object {
        @Volatile
        private var instanceOfGrocery: GroceryDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context) = instanceOfGrocery ?: synchronized(LOCK) {
            instanceOfGrocery ?: createDatabase(context).also {
                instanceOfGrocery = it
            }


        }


        private fun createDatabase(context: Context) =
            Room.databaseBuilder(
                context.applicationContext,
                GroceryDatabase::class.java,
                "Grocery.db"
            ).build()
    }
}
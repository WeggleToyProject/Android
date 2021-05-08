package com.jinhyun.weggletoyproject.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.jinhyun.weggletoyproject.model.UserModel

@Database(entities = [UserModel::class], version = 1)
abstract class WeggleToyDatabase : RoomDatabase() {
    abstract fun userDao(): UserDAO

    companion object {
        @Volatile
        private var INSTANCE: WeggleToyDatabase? = null

        fun getInstance(context: Context): WeggleToyDatabase = INSTANCE ?: synchronized(this) {
            INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(
            context.applicationContext,
            WeggleToyDatabase::class.java,
            "Weggle.db"
        ).build()
    }
}
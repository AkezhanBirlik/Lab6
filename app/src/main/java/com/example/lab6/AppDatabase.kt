package com.example.lab6

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [ListItem::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    companion object {
        private var app: AppDatabase? = null
        fun getInstance(context: Context): AppDatabase{
            app = Room.databaseBuilder(context, AppDatabase::class.java, "mydb.db").build()
            return app!!
        }
    }
    abstract fun getPersonDao(): PersonDao
}
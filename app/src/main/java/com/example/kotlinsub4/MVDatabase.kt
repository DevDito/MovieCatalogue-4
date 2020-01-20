package com.example.kotlinsub4

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.kotlinsub4.ui.mv.MVDao
import com.example.kotlinsub4.ui.tv.TVDao
import com.example.kotlinsub4.ui.tv.pojo.ResultsItem

@Database(entities = [com.example.kotlinsub4.ui.mv.pojo.ResultsItem::class, ResultsItem::class], version = 1, exportSchema = false)
abstract class MVDatabase : RoomDatabase() {
    abstract fun mvDao(): MVDao
    abstract fun tvDao(): TVDao


    companion object{
        const val DB_NAME = "movie_catalogue_database"

        @Volatile
        private var INSTANCE: MVDatabase? = null

        fun getDatabase(context: Context): MVDatabase {
            if (INSTANCE == null) {
                synchronized(MVDatabase::class.java) {
                    if (INSTANCE == null) {
                        // Create database here
                        INSTANCE = Room.databaseBuilder(context,
                            MVDatabase::class.java, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build()
                    }
                }
            }
            return INSTANCE!!
        }
    }
}
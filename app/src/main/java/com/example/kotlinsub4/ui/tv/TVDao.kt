package com.example.kotlinsub4.ui.tv

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.kotlinsub4.ui.tv.pojo.ResultsItem

@Dao
interface TVDao {
    companion object{
        const val TABLE_NAME:String = "table_fav_tv"
    }

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(resultsItem: ResultsItem)

    @Query("SELECT * FROM ${TABLE_NAME}")
    fun getAllTvShow():MutableList<ResultsItem>

    @Query("DELETE FROM ${TABLE_NAME}")
    fun deleteAll()

    @Query("SELECT * FROM ${TABLE_NAME}")
    fun getTvShowById():List<ResultsItem>

    @Query("SELECT * FROM ${TABLE_NAME} WHERE id = :id LIMIT 1")
    fun getTvShowById(id:Int): ResultsItem?

    @Query("DELETE FROM ${TABLE_NAME} WHERE id = :id")
    fun deleteTvShowById(id:Int)
}
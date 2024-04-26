package com.example.homefarmer.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homefarmer.domain.entity.PlantReportItem

@Dao
interface PlantDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlantReportItem(plantReportItem: PlantReportItem)

    @Delete
    suspend fun deletePlantReportItem(plantReportItem: PlantReportItem)

    @Query("SELECT * FROM plant_table ORDER BY id ASC")
    fun getPlantReportList(): LiveData<List<PlantReportItem>>

    @Query("SELECT * FROM plant_table WHERE id = :itemId")
    suspend fun getPlantReportItem(itemId: Int): PlantReportItem

    @Query("DELETE FROM plant_table")
    suspend fun deletePlantReportList()
}
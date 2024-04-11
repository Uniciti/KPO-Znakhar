package com.example.homefarmer.data.room

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.homefarmer.domain.entity.PlantImgItem

@Dao
interface PlantImgDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addPlantImgItem(plantImgItem: PlantImgItem)

    @Query("SELECT * FROM plant_img_table WHERE id = :itemId")
    fun getPlantImgList(itemId: Int): LiveData<List<PlantImgItem>>
}
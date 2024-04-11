package com.example.homefarmer.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plant_img_table")
data class PlantImgItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val img: ByteArray
)

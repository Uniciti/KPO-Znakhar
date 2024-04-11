package com.example.homefarmer.domain.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "plant_table")
data class PlantReportItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int,
    val img: String,
    val defects: String,
    val recommendations: String,
    val date: String
)

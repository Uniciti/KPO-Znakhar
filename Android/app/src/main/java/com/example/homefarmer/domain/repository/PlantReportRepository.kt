package com.example.homefarmer.domain.repository

import com.example.homefarmer.data.room.PlantDao
import com.example.homefarmer.domain.entity.PlantReportItem

class PlantReportRepository(private val plantDao: PlantDao) {
    val plantReportList = plantDao.getPlantReportList()

    suspend fun addPlantReportItem(plantReportItem: PlantReportItem) {
        plantDao.addPlantReportItem(plantReportItem)
    }

    suspend fun deletePlantReportItem(plantReportItem: PlantReportItem) {
        plantDao.deletePlantReportItem(plantReportItem)
    }

    suspend fun getPlantReportItem(itemId: Int) = plantDao.getPlantReportItem(itemId)

    suspend fun deletePlantReportList() = plantDao.deletePlantReportList()
}
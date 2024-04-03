package com.example.homefarmer.domain.repository

import com.example.homefarmer.domain.entity.PlantReportItem

interface PlantReportRepositoryImpl {

    fun getPlantReport(plantReportId: Int): PlantReportItem

    fun addPlantReport(plantReport: PlantReportItem)

    fun deletePlantReport(plantReport: PlantReportItem)

    fun getPlantReportList(): List<PlantReportItem>
}
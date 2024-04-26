package com.example.homefarmer.domain.usecases

import com.example.homefarmer.domain.entity.PlantReportItem
import com.example.homefarmer.domain.repository.PlantReportRepository

class DeletePlantReportItemUseCase(private val repository: PlantReportRepository) {
    suspend operator fun invoke(plantReportItem: PlantReportItem) {
        repository.deletePlantReportItem(plantReportItem)
    }
}
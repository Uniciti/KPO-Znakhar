package com.example.homefarmer.domain.usecases

import com.example.homefarmer.domain.entity.PlantReportItem
import com.example.homefarmer.domain.repository.PlantReportRepository

class AddPlantReportItemUseCase(private val repository: PlantReportRepository) {
    suspend operator fun invoke(plantReportItem: PlantReportItem) {
        repository.addPlantReportItem(plantReportItem)
    }
}
package com.example.homefarmer.domain.usecases

import com.example.homefarmer.domain.repository.PlantReportRepository

class DeletePlantReportListUseCase(private val repository: PlantReportRepository) {

    suspend operator fun invoke() = repository.deletePlantReportList()
}
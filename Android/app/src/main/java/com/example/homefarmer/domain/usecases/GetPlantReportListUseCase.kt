package com.example.homefarmer.domain.usecases

import com.example.homefarmer.domain.repository.PlantReportRepository

class GetPlantReportListUseCase(private val repository: PlantReportRepository) {

    operator fun invoke() = repository.plantReportList
}
package com.example.homefarmer.domain.usecases

import com.example.homefarmer.data.room.PlantDao
import com.example.homefarmer.domain.repository.PlantReportRepository

class GetPlantReportItemUseCase(private val repository: PlantReportRepository) {

    suspend operator fun invoke(itemId: Int) = repository.getPlantReportItem(itemId)
}
package com.example.homefarmer.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.map
import androidx.lifecycle.viewModelScope
import com.example.homefarmer.data.room.PlantDatabase
import com.example.homefarmer.domain.entity.PlantImg
import com.example.homefarmer.domain.entity.toImg
import com.example.homefarmer.domain.repository.PlantReportRepository
import com.example.homefarmer.domain.usecases.GetPlantReportListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlantImgViewModel(application: Application) : AndroidViewModel(application) {

    private val repository: PlantReportRepository

    init {
        val plantDao = PlantDatabase.getDatabase(application).plantReportDao()
        repository = PlantReportRepository(plantDao)
    }

    private val getPlantReportListUseCase = GetPlantReportListUseCase(repository)

    val plantImgList = getPlantReportListUseCase().map { plantReportItems ->
        plantReportItems.map { it.toImg() }
    }

}
package com.example.homefarmer.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.example.homefarmer.data.room.PlantDatabase
import com.example.homefarmer.domain.entity.PlantReportItem
import com.example.homefarmer.domain.repository.PlantReportRepository
import com.example.homefarmer.domain.usecases.AddPlantReportItemUseCase
import com.example.homefarmer.domain.usecases.GetPlantReportListUseCase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class PlantReportItemViewModel(application: Application): AndroidViewModel(application) {
    private val repository: PlantReportRepository

    init {
        val plantDao = PlantDatabase.getDatabase(application).plantReportDao()
        repository = PlantReportRepository(plantDao)
    }

    private val addPlantReportItemUseCase = AddPlantReportItemUseCase(repository)
    private val getPlantReportListUseCase = GetPlantReportListUseCase(repository)

    val plantReportList = getPlantReportListUseCase()

    fun addPlantReport(plantReportItem: PlantReportItem) {
        viewModelScope.launch(Dispatchers.IO) {
            addPlantReportItemUseCase(plantReportItem)
        }
    }


}
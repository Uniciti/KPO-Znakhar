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
import com.example.homefarmer.domain.usecases.DeletePlantReportItemUseCase
import com.example.homefarmer.domain.usecases.DeletePlantReportListUseCase
import com.example.homefarmer.domain.usecases.GetPlantReportItemUseCase
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
    private val getPlantReportItemUseCase = GetPlantReportItemUseCase(repository)
    private val deletePlantReportListUseCase = DeletePlantReportListUseCase(repository)
    private val deletePlantReportItemUseCase = DeletePlantReportItemUseCase(repository)

    val plantReportList = getPlantReportListUseCase()

    private val _plantReportItem = MutableLiveData<PlantReportItem>()
    val plantReportItem: LiveData<PlantReportItem>
        get() = _plantReportItem

    fun addPlantReport(plantReportItem: PlantReportItem) {
        viewModelScope.launch(Dispatchers.IO) {
            addPlantReportItemUseCase(plantReportItem)
        }
    }

    fun getPlantReport(plantReportItemId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            val item = getPlantReportItemUseCase(plantReportItemId)
            _plantReportItem.postValue(item)
        }

    }

    fun deletePlantReportList() {
        viewModelScope.launch(Dispatchers.IO) {
            deletePlantReportListUseCase()
        }
    }

    fun deletePlantReportItem(plantReportItem: PlantReportItem) {
        viewModelScope.launch(Dispatchers.IO) {
            deletePlantReportItemUseCase(plantReportItem)
        }
    }

}
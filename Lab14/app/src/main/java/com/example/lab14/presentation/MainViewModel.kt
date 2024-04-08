package com.example.lab14.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.lab14.data.RepositoryCityImpl
import com.example.lab14.domain.entity.CityItem
import com.example.lab14.domain.usecase.GetCityItemUseCases
import com.example.lab14.domain.usecase.GetCityListUseCase

class MainViewModel(application: Application): AndroidViewModel(application) {
    private val repository = RepositoryCityImpl
    private val getCityListUseCase = GetCityListUseCase(repository)
    private val getCityItemUseCases = GetCityItemUseCases(repository)

    init {
        repository.initCities(application)
    }

    val cityList = getCityListUseCase()

    private val _cityItem = MutableLiveData<CityItem>()
    val cityItem: LiveData<CityItem>
        get() = _cityItem

    fun getCityItem(cityItemId: Int) {
        val item = getCityItemUseCases(cityItemId)
        _cityItem.value = item
    }

}
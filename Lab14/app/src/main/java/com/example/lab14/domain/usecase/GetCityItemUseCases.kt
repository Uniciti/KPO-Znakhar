package com.example.lab14.domain.usecase

import com.example.lab14.data.RepositoryCityImpl
import com.example.lab14.domain.entity.CityItem

class GetCityItemUseCases(private val repository: RepositoryCityImpl) {
    operator fun invoke(cityId: Int) = repository.getCityItem(cityId)
}
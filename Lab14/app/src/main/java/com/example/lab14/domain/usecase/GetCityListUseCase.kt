package com.example.lab14.domain.usecase

import com.example.lab14.data.RepositoryCityImpl

class GetCityListUseCase(private val repository: RepositoryCityImpl) {
    operator fun invoke() = repository.getCityList()
}
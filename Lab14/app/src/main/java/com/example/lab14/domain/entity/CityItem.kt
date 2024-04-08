package com.example.lab14.domain.entity

data class CityItem(
    val title: String,
    val region: String,
    val district: String,
    val postalCode: String,
    val timezone: String,
    val population: String,
    val founded: String,
    val lat: Float,
    val lon: Float
)

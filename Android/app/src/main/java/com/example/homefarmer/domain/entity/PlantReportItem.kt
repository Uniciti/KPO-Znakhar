package com.example.homefarmer.domain.entity

data class PlantReportItem(
    val id: Int,
    val img: String,
    val defects: List<String>,
    val recommendations: List<String>,
    val date: String
)

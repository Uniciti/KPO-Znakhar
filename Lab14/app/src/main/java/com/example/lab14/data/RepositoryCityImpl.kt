package com.example.lab14.data

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.lab14.R
import com.example.lab14.domain.entity.CityItem

object RepositoryCityImpl {
    private val citiesLD = MutableLiveData<List<CityItem>>()
    private val cities = mutableListOf<CityItem>()

    fun initCities(ctx: Context) {
        if (cities.isEmpty()) {
            val lines = ctx.resources.openRawResource(R.raw.cities)
                .bufferedReader().lines().toArray()
            for (i in 1 until lines.count()) {
                val parts = lines[i].toString().split(";")
                val city = CityItem(
                    parts[3], // city
                    parts[2], // region
                    parts[1], // federal_district
                    parts[0], // postal_code
                    parts[4], // timezone
                    parts[7], // population
                    parts[8], // founded
                    parts[5].toFloat(), // lat
                    parts[6].toFloat() // lon
                )
                cities.add(city)
            }
            cities.sortBy { it.title }
            updateList()
        }
    }

    fun getCityItem(cityId: Int) = cities[cityId]

    fun getCityList(): LiveData<List<CityItem>> {
        return citiesLD
    }

    private fun updateList() {
        citiesLD.value = cities
    }
}
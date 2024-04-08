package com.example.lab14.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.lab14.databinding.ItemCityBinding
import com.example.lab14.domain.entity.CityItem

class CityItemViewHolder(view: View): RecyclerView.ViewHolder(view) {
    private val binding = ItemCityBinding.bind(view)

    fun bind(city: CityItem) = with(binding) {
        tvCity.text = city.title
        tvRegion.text = city.region
    }
}
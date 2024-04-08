package com.example.lab14.presentation

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.lab14.R
import com.example.lab14.domain.entity.CityItem

class CityListAdapter: RecyclerView.Adapter<CityItemViewHolder>() {
    private val listCity = mutableListOf<CityItem>()
    var onCityItemClickListener: ((Int) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CityItemViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_city, parent, false)

        return CityItemViewHolder(view)
    }

    override fun getItemCount() = listCity.size

    override fun onBindViewHolder(holder: CityItemViewHolder, position: Int) {
        val city = listCity[position]

        holder.bind(city)

        holder.itemView.setOnClickListener {
            onCityItemClickListener?.invoke(position)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun addCities(cities: List<CityItem>) {
        listCity.addAll(cities)
        notifyDataSetChanged()
    }
}
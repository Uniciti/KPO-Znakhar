package com.example.homefarmer.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.homefarmer.R
import com.example.homefarmer.domain.entity.PlantReportItem

class PlantReportListAdapter : ListAdapter<PlantReportItem, PlantReportViewHolder>(PlantReportDiffCallback()) {
    var onPlantReportItemClickListener: ((PlantReportItem) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantReportViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.item_plant_report,
            parent,
            false
        )

        return PlantReportViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantReportViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)

        holder.itemView.setOnClickListener {
            onPlantReportItemClickListener?.invoke(item)
        }
    }

}
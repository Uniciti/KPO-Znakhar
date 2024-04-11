package com.example.homefarmer.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.homefarmer.domain.entity.PlantReportItem

class PlantReportDiffCallback : DiffUtil.ItemCallback<PlantReportItem>() {
    override fun areItemsTheSame(oldItem: PlantReportItem, newItem: PlantReportItem): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PlantReportItem, newItem: PlantReportItem): Boolean {
        return oldItem == newItem
    }
}
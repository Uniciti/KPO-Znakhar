package com.example.homefarmer.presentation

import androidx.recyclerview.widget.DiffUtil
import com.example.homefarmer.domain.entity.PlantImg

class PlantImgDiffCallback : DiffUtil.ItemCallback<PlantImg>() {
    override fun areItemsTheSame(oldItem: PlantImg, newItem: PlantImg): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: PlantImg, newItem: PlantImg): Boolean {
        return oldItem == newItem
    }
}
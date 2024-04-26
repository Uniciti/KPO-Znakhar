package com.example.homefarmer.presentation

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import com.example.homefarmer.R
import com.example.homefarmer.domain.entity.PlantImg


class PlantImgListAdapter : ListAdapter<PlantImg, PlantImgViewHolder>(PlantImgDiffCallback()) {
    var onPlantImgClickListener: ((PlantImg) -> Unit)? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PlantImgViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_img_plant, parent, false)

        return PlantImgViewHolder(view)
    }

    override fun onBindViewHolder(holder: PlantImgViewHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)

        holder.itemView.setOnClickListener {
            onPlantImgClickListener?.invoke(item)
        }
    }
}
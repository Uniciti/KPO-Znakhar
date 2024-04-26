package com.example.homefarmer.presentation

import android.graphics.BitmapFactory
import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.homefarmer.databinding.ItemImgPlantBinding
import com.example.homefarmer.domain.entity.PlantImg
import com.example.homefarmer.domain.entity.PlantReportItem
import java.io.File

class PlantImgViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemImgPlantBinding.bind(view)

    fun bind(plantImg: PlantImg) {
        binding.imgPlant.setImageBitmap(plantImg.img)
    }
}
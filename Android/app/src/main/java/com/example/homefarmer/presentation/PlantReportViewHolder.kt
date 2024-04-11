package com.example.homefarmer.presentation

import android.view.View
import androidx.recyclerview.widget.RecyclerView
import com.example.homefarmer.R
import com.example.homefarmer.databinding.ItemPlantReportBinding
import com.example.homefarmer.domain.entity.PlantReportItem

class PlantReportViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
    private val binding = ItemPlantReportBinding.bind(view)

    fun bind(plantReportItem: PlantReportItem) {
        binding.tvDateReportDate.text = view.context.getString(
            R.string.template_report,
            plantReportItem.date
        )
    }
}
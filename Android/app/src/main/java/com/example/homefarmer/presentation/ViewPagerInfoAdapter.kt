package com.example.homefarmer.presentation

import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.homefarmer.R
import com.example.homefarmer.databinding.ItemInfoPageBinding

class ViewPagerInfoAdapter(
    private val title: List<Int>,
    private val content: List<Int>,
    private val images: List<Int>
) : RecyclerView.Adapter<ViewPagerInfoAdapter.InfoPagerViewHolder>() {

    inner class InfoPagerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val binding = ItemInfoPageBinding.bind(view)

        fun bind(position: Int) = with(binding) {
            tvTitleInfoPage.text = itemView.context.getString(title[position])
            tvContentInfoPage.text = itemView.context.getString(content[position])
            imgInfoPage.setImageResource(images[position])
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InfoPagerViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_info_page, parent, false)
        return InfoPagerViewHolder(view)
    }

    override fun getItemCount(): Int {
        return title.size
    }

    override fun onBindViewHolder(holder: InfoPagerViewHolder, position: Int) {
        holder.bind(position)
        Log.i("MyLog", position.toString())

    }
}

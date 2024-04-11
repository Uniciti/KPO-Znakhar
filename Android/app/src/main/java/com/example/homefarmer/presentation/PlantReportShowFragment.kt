package com.example.homefarmer.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.homefarmer.R


class PlantReportShowFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_plant_report_show, container, false)
    }


    companion object {
        private const val PRIMARY_KEY_ID = "primary_key_id"
        private const val UNKNOWN_ID = -1
    }
}
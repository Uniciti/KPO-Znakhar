package com.example.homefarmer.presentation

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.homefarmer.R
import com.example.homefarmer.databinding.FragmentPlantReportShowBinding
import com.example.homefarmer.domain.entity.PlantReportItem
import java.io.File
import java.lang.RuntimeException


class PlantReportShowFragment : Fragment() {
    private var _binding: FragmentPlantReportShowBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[PlantReportItemViewModel::class.java]
    }
    private var primaryKeyId: Int? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPlantReportShowBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.tbReportPlant.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        launchScreen()
        observerViewModel()

    }

    private fun parseParams() {
        primaryKeyId = arguments?.getInt(PRIMARY_KEY_ID, UNKNOWN_ID)
    }

    private fun launchScreen() {
        if (primaryKeyId == UNKNOWN_ID || primaryKeyId == null) {
            throw RuntimeException("Can't find such id")
        } else {
            viewModel.getPlantReport(primaryKeyId!!)
        }

    }

    private fun observerViewModel() {
        viewModel.plantReportItem.observe(viewLifecycleOwner) {plantReportItem ->
            insertData(plantReportItem)
        }
    }

    private fun insertData(plantReportItem: PlantReportItem) = with(binding) {
        val imageFile = File(plantReportItem.img)
        val bitmap = BitmapFactory.decodeFile(imageFile.absolutePath)
        imgReportResult.setImageBitmap(bitmap)
        tvListDefects.text = plantReportItem.defects
        tvListRecommendation.text = plantReportItem.recommendations
    }



    companion object {
        private const val PRIMARY_KEY_ID = "primary_key_id"
        private const val UNKNOWN_ID = -1
    }
}
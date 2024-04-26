package com.example.homefarmer.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.homefarmer.R
import com.example.homefarmer.databinding.FragmentGalleryBinding


class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private val binding
        get() = _binding!!
    private lateinit var adapter: PlantImgListAdapter
    private val viewModel by lazy {
        ViewModelProvider(this)[PlantImgViewModel::class.java]
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentGalleryBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observerViewModel()

        binding.tbPlantGallery.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    private fun setupRecyclerView() {
        adapter = PlantImgListAdapter()
        binding.rvPlantImgList.adapter = adapter

        setupClickListener()
    }

    private fun observerViewModel() {
        viewModel.plantImgList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setupClickListener() {
        adapter.onPlantImgClickListener = {plantImg ->

        }
    }



}
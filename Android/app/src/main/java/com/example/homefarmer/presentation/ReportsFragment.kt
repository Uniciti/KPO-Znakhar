package com.example.homefarmer.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.homefarmer.R

import com.example.homefarmer.databinding.FragmentReportsBinding


class ReportsFragment : Fragment() {
    private var _binding: FragmentReportsBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[PlantReportItemViewModel::class.java]
    }
    private lateinit var adapter: PlantReportListAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentReportsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        observerViewModel()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        adapter = PlantReportListAdapter()
        binding.rvPlantReportList.adapter = adapter

        setupClickListener()
    }

    private fun observerViewModel() {
        viewModel.plantReportList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun setupClickListener() {
        adapter.onPlantReportItemClickListener = { plantReportItem ->
            launchPlantReportFragment(plantReportItem.id)
        }
    }

    private fun launchPlantReportFragment(plantReportItemId: Int) {
        findNavController().navigate(
            R.id.action_reportsFragment_to_plantReportShowFragment,
            bundleOf(PRIMARY_KEY_ID to plantReportItemId)
        )
    }

    companion object {
        private const val PRIMARY_KEY_ID = "primary_key_id"
    }

}
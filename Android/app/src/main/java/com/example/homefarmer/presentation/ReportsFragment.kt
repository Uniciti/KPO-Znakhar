package com.example.homefarmer.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
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

        binding.tbReportsScreen.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
        binding.tbReportsScreen.setOnMenuItemClickListener {
            if (it.itemId == R.id.item_delete) viewModel.deletePlantReportList()

            true
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        adapter = PlantReportListAdapter()
        binding.rvPlantReportList.adapter = adapter

        setupClickListener()
        setupSwipeListener()
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

    private fun setupSwipeListener() {
        val callback = object :
            ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT or ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val item = adapter.currentList[viewHolder.adapterPosition]
                viewModel.deletePlantReportItem(item)
            }
        }

        val itemTouchHelper = ItemTouchHelper(callback)
        itemTouchHelper.attachToRecyclerView(binding.rvPlantReportList)
    }

    companion object {
        private const val PRIMARY_KEY_ID = "primary_key_id"
    }

}
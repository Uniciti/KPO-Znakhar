package com.example.lab14.presentation

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentContainerView
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.ViewModelProvider
import com.example.lab14.R
import com.example.lab14.databinding.FragmentCityListBinding


class CityListFragment : Fragment() {
    private var _binding: FragmentCityListBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private lateinit var adapter: CityListAdapter
    private lateinit var fragmentManager: FragmentManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCityListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        setupRecyclerView()
        observe()
        setupClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun setupRecyclerView() {
        adapter = CityListAdapter()
        binding.rvCityList.adapter = adapter
    }

    private fun init() {
        fragmentManager = requireActivity().supportFragmentManager
    }

    private fun observe() {
        viewModel.cityList.observe(viewLifecycleOwner) {
            adapter.addCities(it)
        }
    }

    private fun setupClickListener() {
        adapter.onCityItemClickListener = {cityId ->
            launchFragment(CityItemFragment.newInstanceCityItem(cityId))
        }
    }

    private fun launchFragment(fragment: Fragment) {
        if (isOnePageMode()) {
            fragmentManager.popBackStack()
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack(null)
                .commit()
        } else {
            fragmentManager.popBackStack()
            fragmentManager.beginTransaction()
                .replace(R.id.fragment_city_item_container, fragment)
                .addToBackStack(null)
                .commit()
        }

    }

    private fun isOnePageMode(): Boolean {
        val cityItemContainer = requireActivity().findViewById<FragmentContainerView>(R.id.fragment_city_item_container)

        return cityItemContainer == null
    }
}
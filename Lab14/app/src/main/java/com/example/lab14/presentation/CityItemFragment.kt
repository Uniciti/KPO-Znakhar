package com.example.lab14.presentation

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.lab14.R
import com.example.lab14.databinding.FragmentCityItemBinding
import com.example.lab14.databinding.ItemCityBinding

class CityItemFragment : Fragment() {
    private var _binding: FragmentCityItemBinding? = null
    private val binding
        get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[MainViewModel::class.java]
    }
    private var cityItemId: Int = UNDEFINED_ID
    private val lat by lazy {
        viewModel.cityItem.value?.lat
    }
    private val lon by lazy {
        viewModel.cityItem.value?.lon
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        parseParams()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentCityItemBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        init()
        observe()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseParams() {
        val args = requireArguments()
        if (args.containsKey(CITY_ITEM_ID)) {
            cityItemId = args.getInt(CITY_ITEM_ID)
        } else {
            throw RuntimeException("Param screen mode is absent")
        }
    }

    private fun init() {
        viewModel.getCityItem(cityItemId)
        binding.btnShowCityMap.setOnClickListener {
            startActivity(newInstanceMap(lat!!, lon!!))
        }
    }

    private fun observe() {
        viewModel.cityItem.observe(viewLifecycleOwner) {city ->
            with(binding) {
                tvNameCity.text = "Город: ${city.title}"
                tvDistrictCity.text = "Федеральный округ: ${city.district}"
                tvRegionCity.text = "Регион: ${city.region}"
                tvPostalCodeCity.text = "Почтовый индекс: ${city.postalCode}"
                tvTimeZoneCity.text = "Часовой пояс: ${city.timezone}"
                tvPopulationCity.text = "Население: ${city.population}"
                tvFoundedCity.text = "Основан в: ${city.founded}"
            }
        }
    }


    companion object {
        private const val CITY_ITEM_ID = "extra_city_item_id"
        private const val UNDEFINED_ID = -1

        fun newInstanceCityItem(cityItemId: Int): CityItemFragment {
            return CityItemFragment().apply {
                arguments = Bundle().apply {
                    putInt(CITY_ITEM_ID, cityItemId)
                }
            }
        }

        private fun newInstanceMap(lat: Float, lon: Float) = Intent(Intent.ACTION_VIEW,
            Uri.parse("geo:$lat,$lon")
        )
    }

}
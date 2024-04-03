package com.example.homefarmer.presentation

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.navigation.fragment.findNavController
import com.example.homefarmer.R
import com.example.homefarmer.databinding.FragmentMainScreenBinding

class MainScreenFragment : Fragment() {
    private var _binding: FragmentMainScreenBinding? = null
    private val binding: FragmentMainScreenBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainScreenBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setOnClickListener()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


    private fun setOnClickListener() = with(binding) {
        cvPhoto.setOnClickListener {
            navigateTo(R.id.action_mainScreenFragment_to_photoCameraFragment)
        }
        cvReport.setOnClickListener {
            navigateTo(R.id.action_mainScreenFragment_to_reportsFragment)
        }
        cvGallery.setOnClickListener {
            navigateTo(R.id.action_mainScreenFragment_to_galleryFragment)
        }
        tbMainScreen.setOnMenuItemClickListener {
            if (it.itemId == R.id.info_item) {
                showDialog()
            }
            true
        }
    }

    private fun navigateTo(actionId: Int) {
        findNavController().navigate(
            actionId,
        )
    }

    private fun showDialog() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setTitle(getString(R.string.inst))
            .setMessage(getString(R.string.inst_using_app))
            .setPositiveButton(getString(R.string.clear)) {
                dialog, _ -> dialog.cancel()
            }
        val alertDialog = builder.create()
        alertDialog.show()
    }

}
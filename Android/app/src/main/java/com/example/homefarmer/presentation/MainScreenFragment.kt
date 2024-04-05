package com.example.homefarmer.presentation

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.homefarmer.R
import com.example.homefarmer.databinding.CustomDialogInfoBinding
import com.example.homefarmer.databinding.FragmentMainScreenBinding

class MainScreenFragment : Fragment() {
    private var _binding: FragmentMainScreenBinding? = null
    private val binding: FragmentMainScreenBinding
        get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[MainScreenViewModel::class.java]
    }



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
        checkFirstLaunch()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun checkFirstLaunch() {
        if (viewModel.checkFirstLaunch()) showDialog()
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
        val inflater = requireActivity().layoutInflater
        val dialogView = inflater.inflate(R.layout.custom_dialog_info, null)
        val dialogBinding = CustomDialogInfoBinding.bind(dialogView)
        viewPager(dialogBinding)
        builder.setView(dialogView)
        val alertDialog = builder.create()

        dialogBinding.btnCancel.setOnClickListener {
            alertDialog.cancel()
        }

        alertDialog.show()
    }

    private fun viewPager(dialogBinding: CustomDialogInfoBinding) {
        with(dialogBinding) {
            vpInfoPage.adapter = ViewPagerInfoAdapter(TITLE_LIST, CONTENT_LIST, IMG_LIST)
            vpInfoPage.orientation = ViewPager2.ORIENTATION_HORIZONTAL
            ciNumPageInfo.setViewPager(vpInfoPage)
        }
    }


    companion object {
        private const val KEY_IS_FIRST = "isFirstRun"
        private val TITLE_LIST = listOf(
            R.string.title_info_page1,
            R.string.title_info_page2,
            R.string.title_info_page3
        )
        val CONTENT_LIST = listOf(
            R.string.context_info_page1,
            R.string.context_info_page2,
            R.string.context_info_page3
        )
        val IMG_LIST = listOf(
            R.drawable.icon_photo_camera,
            R.drawable.icon_report,
            R.drawable.icon_gallery
        )
    }
}
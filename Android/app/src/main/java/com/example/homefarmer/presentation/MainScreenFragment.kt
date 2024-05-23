package com.example.homefarmer.presentation

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.homefarmer.R
import com.example.homefarmer.databinding.CustomDialogInfoBinding
import com.example.homefarmer.databinding.FragmentMainScreenBinding
import java.io.InputStream


class MainScreenFragment : Fragment() {
    private var _binding: FragmentMainScreenBinding? = null
    private val binding: FragmentMainScreenBinding
        get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[MainScreenViewModel::class.java]
    }
    private val PICK_IMAGE = 100



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
            val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
            startActivityForResult(intent, PICK_IMAGE)
//            navigateTo(R.id.action_mainScreenFragment_to_galleryFragment)
        }
        tbMainScreen.setOnMenuItemClickListener {
            when(it.itemId) {
                R.id.info_item -> showDialog()
                R.id.help_item -> openEmailApp()
            }
            true
        }
    }

    private fun openEmailApp() {
        val intent = Intent(Intent.ACTION_SENDTO)
        intent.data = Uri.parse("mailto:homefarmer@gmail.com")

        if (intent.resolveActivity(requireContext().packageManager) != null) {
            startActivity(intent)
        } else {
            Toast.makeText(requireContext(), "@strings/MailError", Toast.LENGTH_SHORT).show()
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
        alertDialog.window?.setBackgroundDrawableResource(android.R.color.transparent)

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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (resultCode == Activity.RESULT_OK && requestCode == PICK_IMAGE) {
            val selectedImage = data?.data
            launchPlantReportFragment(selectedImage.toString(), selectedImage.toString())
            selectedImage?.let {
                val inputStream: InputStream? = context?.contentResolver?.openInputStream(it)
            }


        }
    }

    private fun launchPlantReportFragment(imgPath: String, jpgPath: String) {

        findNavController().navigate(
            R.id.action_mainScreenFragment_to_reportFragment,
            bundleOf(
                ReportSaveFragment.REPORT_KEY to imgPath,
                PhotoCameraFragment.JPG to jpgPath,
                "FROM" to "gallery"
            )
        )
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
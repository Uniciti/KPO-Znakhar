package com.example.homefarmer.presentation

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.net.toFile
import androidx.core.os.bundleOf
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.homefarmer.R
import com.example.homefarmer.databinding.FragmentGalleryBinding
import com.example.homefarmer.presentation.PhotoCameraFragment.Companion.JPG
import com.example.homefarmer.presentation.ReportSaveFragment.Companion.REPORT_KEY
import java.io.InputStream


class GalleryFragment : Fragment() {
    private var _binding: FragmentGalleryBinding? = null
    private val binding
        get() = _binding!!
    private val PICK_IMAGE = 100


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val intent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI)
        startActivityForResult(intent, PICK_IMAGE)
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


        binding.tbPlantGallery.setNavigationOnClickListener {
            findNavController().popBackStack()
        }
    }

    @SuppressLint("Recycle")
    @Deprecated("Deprecated in Java")
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
        Log.i("MyLog", imgPath)
        findNavController().navigate(
            R.id.action_galleryFragment_to_reportFragment,
            bundleOf(
                REPORT_KEY to imgPath,
                JPG to jpgPath,
                "FROM" to "gallery"
            )
        )
    }


}
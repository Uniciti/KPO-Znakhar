package com.example.homefarmer.presentation

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.media.ExifInterface
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.homefarmer.R
import com.example.homefarmer.databinding.FragmentReportBinding
import java.io.File
import java.io.FileInputStream
import java.io.IOException

class ReportFragment : Fragment() {
    private var _binding: FragmentReportBinding? = null
    private val binding: FragmentReportBinding
        get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentReportBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val img = arguments?.getString(REPORT_KEY)
        val photoUri = Uri.parse(img)
        val bitmap: Bitmap? = try {
            BitmapFactory.decodeStream(requireContext().contentResolver.openInputStream(photoUri))
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }




    private fun rotateBitmap(bitmap: Bitmap?, degrees: Float): Bitmap? {
        bitmap ?: return null // Проверяем, что bitmap не null

        val matrix = Matrix()
        matrix.postRotate(degrees) // Указываем угол поворота

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }


    companion object {
        const val REPORT_KEY = "report_key"
    }

}
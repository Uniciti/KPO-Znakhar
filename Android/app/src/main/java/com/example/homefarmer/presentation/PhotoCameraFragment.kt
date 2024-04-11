package com.example.homefarmer.presentation

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.pm.PackageManager
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageCapture
import androidx.camera.core.ImageCaptureException
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.core.view.isVisible
import androidx.navigation.fragment.findNavController
import com.example.homefarmer.R
import com.example.homefarmer.databinding.FragmentPhotoCameraBinding
import java.io.File


class PhotoCameraFragment : Fragment() {
    private var _binding: FragmentPhotoCameraBinding? = null
    private val binding: FragmentPhotoCameraBinding
        get() = _binding!!
    private lateinit var imageCapture: ImageCapture



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentPhotoCameraBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkInternet()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun showCamera() = with(binding) {
        photoScreen.isVisible = true
        errorScreen.isVisible = false

        launchCamera()
    }

    private fun showError() = with(binding) {
        photoScreen.isVisible = false
        errorScreen.isVisible = true
        tvError.text = getString(R.string.error_internet)
        btnRefreshInternet.setOnClickListener {
            checkInternet()
        }
    }

    private fun checkInternet() {
        val isConnected = isInternetAvailable(requireContext())
        checkCameraPermissions()

        if (isConnected) {
            showCamera()
        } else {
            showError()
        }
    }

    @SuppressLint("ServiceCast")
    fun isInternetAvailable(context: Context): Boolean {
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val network = connectivityManager.activeNetwork ?: return false
        val networkCapabilities = connectivityManager.getNetworkCapabilities(network) ?: return false
        return networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
    }


    private fun launchCamera() {
        val cameraProviderFuture = ProcessCameraProvider.getInstance(requireContext())
        cameraProviderFuture.addListener({
            val cameraProvider = cameraProviderFuture.get()

            val preview = Preview.Builder()
                .build()
                .also {mPreview ->
                    mPreview.setSurfaceProvider(
                        binding.cameraPreview.surfaceProvider
                    )
                }

            imageCapture = ImageCapture.Builder()
                .build()

            val cameraSelector = CameraSelector.DEFAULT_BACK_CAMERA

            try {
                cameraProvider.unbindAll()
                cameraProvider.bindToLifecycle(
                    this@PhotoCameraFragment,
                    cameraSelector,
                    preview,
                    imageCapture
                )
            } catch (ex: Exception) {
                throw RuntimeException(ex.message.toString())
            }
        }, ContextCompat.getMainExecutor(requireContext()))

        binding.btnCapture.setOnClickListener {
            takePhoto()
        }
    }



    private fun takePhoto() {
        val photoFile = File(requireContext().externalMediaDirs.first(), "photo.jpg")
        val outputOptions = ImageCapture.OutputFileOptions.Builder(photoFile).build()

        imageCapture.takePicture(
            outputOptions,
            ContextCompat.getMainExecutor(requireContext()),
            object : ImageCapture.OnImageSavedCallback {
                override fun onImageSaved(outputFileResults: ImageCapture.OutputFileResults) {
                    // Путь к сохраненному изображению
                    val savedUri = Uri.fromFile(photoFile)
                    launchPlantReportFragment(savedUri.toString())
                }

                override fun onError(exception: ImageCaptureException) {
                    Log.e("MyLog", "Photo capture failed: ${exception.message}", exception)
                }


            }
        )
    }

    private fun checkCameraPermissions() {

        if (!hasRequiredPermissions()) {
            requestPermissions(CAMERAX_PERMISSIONS, CAMERA_PERMISSION_CODE)
        }
    }

    private fun hasRequiredPermissions(): Boolean {
        return CAMERAX_PERMISSIONS.all {
            ContextCompat.checkSelfPermission(
                requireContext(),
                it
            ) == PackageManager.PERMISSION_GRANTED
        }
    }

    private fun launchPlantReportFragment(imgPath: String) {
        findNavController().navigate(
            R.id.action_photoCameraFragment_to_reportFragment,
            bundleOf(REPORT_KEY to imgPath)
        )
    }


    companion object {
        private const val REPORT_KEY = "report_key"
        private const val CAMERA_PERMISSION_CODE = 1
        private val CAMERAX_PERMISSIONS = arrayOf(
            Manifest.permission.CAMERA
        )
    }

}
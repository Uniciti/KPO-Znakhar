package com.example.homefarmer.presentation

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.homefarmer.R
import com.example.homefarmer.databinding.FragmentReportBinding
import com.example.homefarmer.domain.entity.PlantReportItem
import com.example.homefarmer.domain.entity.PredictionResponse
import com.google.gson.Gson

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.request.forms.submitFormWithBinaryData
import io.ktor.client.statement.bodyAsText
import io.ktor.http.Headers
import io.ktor.http.HttpHeaders
import io.ktor.http.content.PartData
import io.ktor.utils.io.streams.asInput
import kotlinx.coroutines.runBlocking
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream
import java.lang.RuntimeException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale

class ReportSaveFragment : Fragment() {
    private var _binding: FragmentReportBinding? = null
    private val binding: FragmentReportBinding
        get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[PlantReportItemViewModel::class.java]
    }
    private var imgPath: String? = null
    private var jpgPath = ""
    private var predictionText = ""

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
        parseParam()

        binding.btnSaveResult.setOnClickListener {
            // todo изменить реализацию
            viewModel.addPlantReport(
                PlantReportItem(
                    0,
                    imgPath!!,
                    predictionText,
                    "",
                    getCurrentDate()
                )
            )
            launchPlantReportList()
        }

        binding.tbReportPlant.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseParam() {
        val imgPath = arguments?.getString(REPORT_KEY) ?: throw RuntimeException("Can't find img")
        jpgPath = arguments?.getString(PhotoCameraFragment.JPG) ?: throw RuntimeException("Can't find img")

        transformBitmap(imgPath)
    }

    private fun transformBitmap(imgPath: String) {
        val photoUri = Uri.parse(imgPath)
        var imgBitmap = try {
            BitmapFactory.decodeStream(requireContext().contentResolver.openInputStream(photoUri))
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
        imgBitmap = rotateBitmap(imgBitmap, DEGREES)

        imgBitmap?.let { saveFile(it) }

        // todo вынести в отдельную функцию для вставки, когда будет принимать данные с сервера
        getReport(imgBitmap!!)
    }

    private fun rotateBitmap(bitmap: Bitmap?, degrees: Float): Bitmap? {
        bitmap ?: return null // Проверяем, что bitmap не null

        val matrix = Matrix()
        matrix.postRotate(degrees) // Указываем угол поворота

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun saveFile(imgBitmap: Bitmap) {
        val file = File(context?.filesDir, getNameImage())
        imgPath = file.toString()
        val outputStream = FileOutputStream(file)
        imgBitmap.compress(Bitmap.CompressFormat.JPEG, QUALITY_IMG, outputStream)
        outputStream.close()

    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate(): String {
        // Получаем текущую дату
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat(DATE_FORMAT)

        // Форматируем дату в строку
        return dateFormat.format(calendar.time)
    }

    fun getNameImage(): String {
        val dateFormat = SimpleDateFormat("dd.MM.yyyy HH:mm:ss", Locale.getDefault())
        val currentTimeFormatted = dateFormat.format(Date())

        return "$dateFormat $currentTimeFormatted $TYPE_IMG"
    }

    private fun launchPlantReportList() {
        findNavController().navigate(R.id.action_reportFragment_to_reportsFragment)
    }

    private fun getReport(imgBitmap: Bitmap) {
        val client = HttpClient(CIO)
        val imageFile = File(jpgPath)

        runBlocking {
            val response = client.submitFormWithBinaryData(
                url = "http://51.250.107.19//upload",
                formData = listOf(
                    PartData.FileItem({
                        imageFile.inputStream().asInput()
                    }, {
                        imageFile.inputStream().close()
                    }, Headers.build {
                        append(HttpHeaders.ContentDisposition, "form-data; name=\"file\"; filename=\"${imageFile.name}\"")
                    })
                )
            )

            val json = response.bodyAsText()
            val gson = Gson()
            val predictionResponse = gson.fromJson(json, PredictionResponse::class.java)
            val confidence = predictionResponse.confidence
            val prediction = predictionResponse.prediction
            Log.i("MyLog", prediction)
            predictionText = "$prediction\nВероятность: $confidence"
            binding.imgReportResult.setImageBitmap(imgBitmap)
            binding.tvListDefects.text = predictionText
        }
    }

    companion object {
        private const val REPORT_KEY = "report_key"
        private const val DEGREES = 90f
        private const val DATE_FORMAT = "dd.MM.yyyy"
        private const val QUALITY_IMG = 100
        private const val TYPE_IMG = "image.jpg"
    }

}
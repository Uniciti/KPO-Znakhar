package com.example.homefarmer.presentation

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.example.homefarmer.R
import com.example.homefarmer.databinding.FragmentReportBinding
import com.example.homefarmer.domain.entity.PlantReportItem
import java.text.SimpleDateFormat
import java.util.Calendar

class ReportSaveFragment : Fragment() {
    private var _binding: FragmentReportBinding? = null
    private val binding: FragmentReportBinding
        get() = _binding!!
    private val viewModel by lazy {
        ViewModelProvider(this)[PlantReportItemViewModel::class.java]
    }
    private var imgPath: String? = null
    private var imgBitmap: Bitmap? = null

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
            viewModel.addPlantReport(PlantReportItem(
                0,
                imgPath!!,
                "",
                "",
                getCurrentDate()
            ))
            launchPlantReportList()
        }

    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun parseParam() {
        imgPath = arguments?.getString(REPORT_KEY)
        transformBitmap()
    }

    private fun transformBitmap() {
        val photoUri = Uri.parse(imgPath)
        imgBitmap = try {
            BitmapFactory.decodeStream(requireContext().contentResolver.openInputStream(photoUri))
        } catch (ex: Exception) {
            ex.printStackTrace()
            null
        }
        imgBitmap = rotateBitmap(imgBitmap, DEGREES)

        // todo вынести в отдельную функцию для вставки, когда будет принимать данные с сервера
        binding.imgReportResult.setImageBitmap(imgBitmap)
    }

    private fun rotateBitmap(bitmap: Bitmap?, degrees: Float): Bitmap? {
        bitmap ?: return null // Проверяем, что bitmap не null

        val matrix = Matrix()
        matrix.postRotate(degrees) // Указываем угол поворота

        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    @SuppressLint("SimpleDateFormat")
    fun getCurrentDate(): String {
        // Получаем текущую дату
        val calendar = Calendar.getInstance()
        val dateFormat = SimpleDateFormat(DATE_FORMAT)

        // Форматируем дату в строку
        return dateFormat.format(calendar.time)
    }

    private fun launchPlantReportList() {
        findNavController().navigate(R.id.action_reportFragment_to_reportsFragment)
    }

    companion object {
        const val REPORT_KEY = "report_key"
        private const val DEGREES = 90f
        private const val DATE_FORMAT = "dd.MM.yyyy"
    }

}
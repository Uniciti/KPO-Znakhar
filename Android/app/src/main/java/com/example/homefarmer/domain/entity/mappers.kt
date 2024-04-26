package com.example.homefarmer.domain.entity

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import java.io.File

fun PlantReportItem.toImg(): PlantImg {
    return PlantImg(
        id = id,
        img = prepareImg(img)
    )
}

fun prepareImg(file: String): Bitmap {
    val bitmap = BitmapFactory.decodeFile(File(file).absolutePath)
    val width = bitmap.width
    val height = bitmap.height
    val desiredWidth = width / 2
    val desiredHeight = height / 2

    return Bitmap.createScaledBitmap(bitmap, desiredWidth, desiredHeight, true)
}
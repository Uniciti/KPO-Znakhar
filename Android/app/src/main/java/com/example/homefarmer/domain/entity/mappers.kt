package com.example.homefarmer.domain.entity

import android.graphics.BitmapFactory
import java.io.File

fun PlantReportItem.toImg(): PlantImg {
    return PlantImg(
        id = id,
        img = BitmapFactory.decodeFile(File(img).absolutePath)
    )
}
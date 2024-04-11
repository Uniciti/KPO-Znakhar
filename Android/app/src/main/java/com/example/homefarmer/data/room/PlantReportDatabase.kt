package com.example.homefarmer.data.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.homefarmer.domain.entity.PlantImgItem
import com.example.homefarmer.domain.entity.PlantReportItem

@Database(entities = [PlantReportItem::class, PlantImgItem::class], version = 1, exportSchema = false)
abstract class PlantDatabase: RoomDatabase() {

    abstract fun plantReportDao(): PlantDao
    abstract fun plantImgDao(): PlantImgDao

    companion object {
        @Volatile
        private var INSTANCE: PlantDatabase? = null

        fun getDatabase(context: Context): PlantDatabase {
            val tempInstance = INSTANCE

            if (tempInstance != null) {
                return tempInstance
            }
            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    PlantDatabase::class.java,
                    "plant_database"
                ).build()
                INSTANCE = instance
                return instance
            }
        }

    }
}
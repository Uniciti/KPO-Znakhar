package com.example.homefarmer.presentation

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import com.example.homefarmer.data.shared_preferences.FirstLaunch

class MainScreenViewModel(application: Application) : AndroidViewModel(application) {
    private val firstLaunchPref = FirstLaunch(application)

    private var firstLaunch: Boolean? = null

    init {
        if (firstLaunchPref.isFirstLaunch) {
            firstLaunch = true
            firstLaunchPref.isFirstLaunch = false
        } else {
            firstLaunch = false
        }
    }

    fun checkFirstLaunch() = firstLaunch!!
}
package com.example.homefarmer.data.shared_preferences

import android.content.Context
import android.content.SharedPreferences

class FirstLaunch(context: Context) {

    private val preferences: SharedPreferences by lazy {
        context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    var isFirstLaunch: Boolean
        get() = preferences.getBoolean(FIRST_LAUNCH_KEY, true)
        set(value) {
            preferences.edit().putBoolean(FIRST_LAUNCH_KEY, value).apply()
        }

    companion object {
        private const val PREFS_NAME = "MyPrefs"
        private const val FIRST_LAUNCH_KEY = "first_launch"
    }
}
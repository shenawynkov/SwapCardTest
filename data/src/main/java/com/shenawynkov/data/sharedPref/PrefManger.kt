package com.shenawynkov.data.sharedPref

import android.content.SharedPreferences

class PrefManger(private val prefs: SharedPreferences) {
    private val COUNT = "count"
    fun alterBookmark(key: String): Boolean {
        val previousFav = isFav(key)
        val editor = prefs.edit()
        editor.putBoolean(key, !previousFav)
        editor.apply()
        if (previousFav) {
            updateCount(-1)
        } else {
            updateCount(1)
        }
        return !previousFav
    }

    private fun updateCount(change: Int) {
        val editor = prefs.edit()
        editor.putInt(COUNT, count + change)
        editor.apply()


    }

    val count: Int
        get() = prefs.getInt(COUNT, 0)

    fun isFav(key: String) = prefs.getBoolean(key, false)


}
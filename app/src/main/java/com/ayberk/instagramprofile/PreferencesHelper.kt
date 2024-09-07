package com.ayberk.instagramprofile

import android.content.Context
import android.content.SharedPreferences

class PreferencesHelper(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences("app_prefs", Context.MODE_PRIVATE)

    // Key'ler
    private val KEY_DESCRIPTION = "key_description"

    // Açıklamayı kaydet
    fun saveDescription(description: String) {
        sharedPreferences.edit().putString(KEY_DESCRIPTION, description).apply()
    }

    // Açıklamayı al
    fun getDescription(): String {
        return sharedPreferences.getString(KEY_DESCRIPTION, "") ?: ""
    }
}

package com.nextsolutions.starfpro.domain.user_preferences.preferences
import android.content.Context
import android.content.SharedPreferences

class PreferencesManager(context: Context) {


    private val sharedPreferences: SharedPreferences = context.getSharedPreferences("SprintSpherePro", Context.MODE_PRIVATE)


    fun saveString(key: String, value: String) {
        val editor = sharedPreferences.edit()
        editor.putString(key, value)
        editor.apply()
    }

    fun getString(key: String, defaultValue: String?): String? {
        return sharedPreferences.getString(key, defaultValue)
    }

    fun saveInt(key: String, value: Int) {
        val editor = sharedPreferences.edit()
        editor.putInt(key, value)
        editor.apply()
    }

    fun getInt(key: String, defaultValue: Int): Int {
        return sharedPreferences.getInt(key, defaultValue)
    }

    fun saveBool(key: String, value: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(key, value)
        editor.apply()
    }

    fun getBool(key: String, defaultValue: Boolean): Boolean {
        return sharedPreferences.getBoolean(key, defaultValue)
    }

    fun clearAll(){
        val editor = sharedPreferences.edit()
        editor.clear()
        editor.apply()
    }


}
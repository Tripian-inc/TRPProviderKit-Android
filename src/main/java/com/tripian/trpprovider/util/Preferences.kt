//package com.tripian.trpprovider.util
//
//import android.content.Context
//import android.content.SharedPreferences
//import androidx.preference.PreferenceManager
//import javax.inject.Inject
//
//class Preferences @Inject constructor(var context: Context) {
//
//    object Keys {
//        const val USER = "user"
//    }
//
//    var pref: SharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)
//
//    fun getString(key: String, defValue: String): String? {
//        return pref.getString(key, defValue)
//    }
//
//    fun getString(key: String): String? {
//        return pref.getString(key, "")
//    }
//
//    fun deleteKey(key: String) {
//        val editor = pref.edit()
//        editor.remove(key)
//        editor.apply()
//    }
//
//    fun setString(key: String, newValue: String) {
//        val editor = pref.edit()
//        editor.putString(key, newValue)
//        editor.apply()
//    }
//
//    fun setLong(key: String, newValue: Long) {
//        val editor: SharedPreferences.Editor = pref.edit()
//        editor.putLong(key, newValue)
//        editor.apply()
//    }
//
//    fun setInt(key: String, newValue: Int) {
//        val editor = pref.edit()
//        editor.putInt(key, newValue)
//        editor.apply()
//    }
//
//    fun setBoolean(key: String, newValue: Boolean?) {
//        val editor = pref.edit()
//        editor.putBoolean(key, newValue!!)
//        editor.apply()
//    }
//
//    fun getInt(key: String, defValue: Int): Int {
//        return pref.getInt(key, defValue)
//    }
//
//    fun getFloat(key: String, defValue: Float): Float {
//        return pref.getFloat(key, defValue)
//    }
//
//    fun getLong(key: String, defValue: Long): Long {
//        return pref.getLong(key, defValue)
//    }
//
//    fun getBoolean(key: String, defValue: Boolean): Boolean {
//        return pref.getBoolean(key, defValue)
//    }
//
//    fun clearAllData() {
//        val editor = pref.edit()
//        editor.clear()
//        editor.apply()
//    }
//}
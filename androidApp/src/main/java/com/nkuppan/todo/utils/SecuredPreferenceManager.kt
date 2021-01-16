package com.nkuppan.todo.utils

import android.content.Context
import android.content.SharedPreferences
import android.os.Build

object SecuredPreferenceManager {

    private var sharedPreferences: SharedPreferences? = null

    /**
     * Initializing and keeping shard preference value to access it often.
     *
     * @param aContext to create a secured share preference file
     * @param aPrefName name of the file
     */
    fun initialize(aContext: Context, aPrefName: String) {
        sharedPreferences = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            CryptoPreference.initialize(aContext, aPrefName)
        } else {
            aContext.getSharedPreferences(aPrefName, Context.MODE_PRIVATE)
        }
    }

    fun getStringValue(aKey: String): String? {

        if (sharedPreferences == null) {
            throw Exception("Should call this method before initialize preference")
        }

        return sharedPreferences?.getString(aKey, null)
    }

    fun getFloatValue(aKey: String): Float? {

        if (sharedPreferences == null) {
            throw Exception("Should call this method before initialize preference")
        }

        return sharedPreferences?.getFloat(aKey, 0f)
    }

    fun getBooleanValue(aKey: String): Boolean? {

        if (sharedPreferences == null) {
            throw Exception("Should call this method before initialize preference")
        }

        return sharedPreferences?.getBoolean(aKey, false)
    }

    fun getIntValue(aKey: String): Int? {

        if (sharedPreferences == null) {
            throw Exception("Should call this method before initialize preference")
        }

        return sharedPreferences?.getInt(aKey, 0)
    }

    fun getLongValue(aKey: String): Long? {

        if (sharedPreferences == null) {
            throw Exception("Should call this method before initialize preference")
        }

        return sharedPreferences?.getLong(aKey, 0L)
    }

    fun getStringSetValue(aKey: String): MutableSet<String>? {

        if (sharedPreferences == null) {
            throw Exception("Should call this method before initialize preference")
        }

        return sharedPreferences?.getStringSet(aKey, mutableSetOf())
    }

    fun storeValue(aKey: String, aValue: Any) {

        if (sharedPreferences == null) {
            throw java.lang.Exception("Should call this method before initialize preference")
        }

        val editor = sharedPreferences!!.edit()

        when (aValue) {
            is Boolean -> editor.putBoolean(aKey, aValue)
            is String -> editor.putString(aKey, aValue)
            is Int -> editor.putInt(aKey, aValue)
            is Long -> editor.putLong(aKey, aValue)
            is Float -> editor.putFloat(aKey, aValue)
            is MutableSet<*> -> editor.putStringSet(aKey, aValue as (MutableSet<String>))
            else -> {
                throw java.lang.Exception("Input valid value to store")
            }
        }

        editor.apply()
    }

    fun clearValue(aPrefKey: String) {
        sharedPreferences!!.edit().remove(aPrefKey).apply()
    }
}
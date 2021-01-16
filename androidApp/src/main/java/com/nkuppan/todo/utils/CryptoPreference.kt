package com.nkuppan.todo.utils


import android.content.Context
import android.content.SharedPreferences
import android.os.Build
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey

/**
 * Created by ancientinc on 28/05/20.
 **/
object CryptoPreference {

    fun initialize(aContext: Context, aPrefName: String): SharedPreferences {

        val masterKeyAlias: MasterKey = MasterKey.Builder(aContext)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        return if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            EncryptedSharedPreferences.create(
                aContext,
                aPrefName,
                masterKeyAlias,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        } else {
            aContext.getSharedPreferences(aPrefName, Context.MODE_PRIVATE)
        }
    }
}
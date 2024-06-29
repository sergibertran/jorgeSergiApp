package com.example.jorgesergiapp

import android.content.Context
import com.google.firebase.Timestamp

class Prefs(val context: Context) {
    val SHARED_NAME = "Mydtb"
    val SHARED_USER_NAME = "username"
    val SHARED_ID_USER = "idUsuario"
    val SHARED_TIMESTAMP = "ultimoOpening"
    val SHARED_FLIP_ENABLED = "flipEnabled"
    val storage = context.getSharedPreferences(SHARED_NAME,0)

    fun setName(name:String){
    storage.edit().putString(SHARED_USER_NAME, name).apply()
    }

    fun setultimoOpening(ultimoOpening:String){
        storage.edit().putString(SHARED_TIMESTAMP, ultimoOpening).apply()
    }

    fun setflipEnabled(flipEnabled:Boolean){
        storage.edit().putBoolean(SHARED_FLIP_ENABLED, flipEnabled).apply()
    }
    fun getflipEnabled(): Boolean {
        return storage.getBoolean(SHARED_FLIP_ENABLED,true)!!
    }


    fun setidUsuario(idUsuario:String){
        storage.edit().putString(SHARED_ID_USER, idUsuario).apply()
    }

    fun getidUsuario(): String {
        return storage.getString(SHARED_ID_USER,"")!!
    }
    fun getName(): String {
        return storage.getString(SHARED_USER_NAME,"")!!
    }

    fun getultimoOpening(): String {
        return storage.getString(SHARED_TIMESTAMP,"")!!
    }

    fun wipe(){
        storage.edit().clear().apply()
    }
}
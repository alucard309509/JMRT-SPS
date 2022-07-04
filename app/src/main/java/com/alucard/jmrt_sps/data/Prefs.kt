package com.alucard.jmrt_sps.data

import android.content.Context

class Prefs(val context: Context) {
        //Clase que se encarga de los process de guardado en shared preferences con la ubicacion latitud y longitud del usuario
    val SHAREDLOCATATION = "MYLOCALITATION"
    val SHARED_USER_LOCALITATION = "USERPOSITION"
    val storage = context.getSharedPreferences(SHAREDLOCATATION, 0)

    fun saveLocation(name: String) {
        storage.edit().putString(SHARED_USER_LOCALITATION, name).apply()
    }

    //RECUPERAR EL NOMBRE
    fun getName(): String {
        //SI NO ESTA EL NOMBRE DEVUELVEME UN STRING VACIO
        return storage.getString(SHARED_USER_LOCALITATION, "")!!
    }
}
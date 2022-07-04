package com.alucard.jmrt_sps.constants

import com.alucard.jmrt_sps.Interface.NewsService
import com.alucard.jmrt_sps.Remote.RetrofitClient

class Constants {

    //singlton donde se encuentra el valor de las constantes que se usaran en la aplicacion
    companion object{

        val BASE_URL="https://newsapi.org"
        val API_KEY="acae9d34f05f4f4d9c6d8487234b1aa1"
        val newService: NewsService
        get() = RetrofitClient.getClient(BASE_URL).create(NewsService::class.java)

    }
}
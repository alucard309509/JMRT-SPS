package com.alucard.jmrt_sps.Interface

import com.alucard.jmrt_sps.models.Website
import retrofit2.Call
import retrofit2.http.GET

interface NewsService {

    //Get
    @get:GET("v2/sources?apiKey=acae9d34f05f4f4d9c6d8487234b1aa1")
    //website es nuestro modelo de clase
    val sources: Call<Website>

}
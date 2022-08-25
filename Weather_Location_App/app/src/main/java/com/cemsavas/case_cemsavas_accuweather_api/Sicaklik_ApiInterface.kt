package com.cemsavas.case_cemsavas_accuweather_api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface Sicaklik_ApiInterface{

@GET("forecasts/v1/hourly/1hour/318251?apikey=t8ypw2laHooKPb5sEqT9IPRYdQSvk7Tw&language=tr&details=true&metric=true&")
    fun getData_Sicaklik(
    @Query("q") q:String) :Call<List<Sicaklik_dataItem>>
}


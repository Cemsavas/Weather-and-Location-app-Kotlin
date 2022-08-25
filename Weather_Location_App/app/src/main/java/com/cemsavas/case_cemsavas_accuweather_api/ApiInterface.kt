package com.cemsavas.case_cemsavas_accuweather_api

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiInterface {

    @GET("locations/v1/cities/autocomplete?apikey=t8ypw2laHooKPb5sEqT9IPRYdQSvk7Tw")//BaseUrl dışında kalan link yolundan konum data çekmek için

    fun getData(
@Query("q") sehir:String // Mainactivitiyde tanımladığım anahtar kelime sorgusu için Query oluşturdum.
    ):Call<List<Konum_dataItem>>
}



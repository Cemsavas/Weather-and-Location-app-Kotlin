package com.cemsavas.case_cemsavas_accuweather_api

data class Sicaklik_dataItem(
    val DateTime: String,
    val HasPrecipitation: Boolean,
    val IconPhrase: String,
    val IsDaylight: Boolean,
    val Temperature: Temperature,
    val WeatherIcon: Int
)
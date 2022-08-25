package com.cemsavas.case_cemsavas_accuweather_api

data class Konum_dataItem( // Kotlin data class file From JSON plugini ile Apiden aldığım dataclass.
    val AdministrativeArea: AdministrativeArea,
    val Country: Country,
    val Key: String,
    val LocalizedName: String,
    val Rank: Int,
    val Type: String,
    val Version: Int
)
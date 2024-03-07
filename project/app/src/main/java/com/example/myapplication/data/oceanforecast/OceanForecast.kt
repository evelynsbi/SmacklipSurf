package com.example.myapplication.data.oceanforecast

data class OceanForecast(
    val geometry: Geometry,
    val properties: Properties,
    val type: String
)
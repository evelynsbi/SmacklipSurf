package com.example.myapplication.data.oceanforecast

import android.net.http.HttpResponseCache.install
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson

public class AlpacaPartiesDataSource(private val path:String = "https://api.met.no/weatherapi/oceanforecast/2.0/complete?lat=62.1255693551118&lon=5.152407834229069") {

    val client = HttpClient(){
        install(ContentNegotiation){
            gson()
        }
    }

    suspend fun fetchAPI():OceanForecast {
        val oceanForecast: OceanForecast = client.get(path).body()
        return oceanForecast
    }
}
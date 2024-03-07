package com.example.myapplication.data.metalerts

import android.net.http.HttpResponseCache.install
import com.example.myapplication.model.metalerts.MetAlerts
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson
import java.io.File

class MetalertsDataSource {
    private val metalertsCurrentUrl = "https://api.met.no/weatherapi/metalerts/2.0/current.json"

    private val client = HttpClient {
        install(ContentNegotiation) {
            gson()
        }
    }

    suspend fun fetchMetalertsData(): MetAlerts {
        val response = client.get(metalertsCurrentUrl)
        return response.body()
    }
}

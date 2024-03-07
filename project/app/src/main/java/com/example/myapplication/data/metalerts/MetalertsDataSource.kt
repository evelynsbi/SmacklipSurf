package com.example.myapplication.data.metalerts

import android.net.http.HttpResponseCache.install
import com.google.gson.Gson
import com.google.gson.annotations.SerializedName
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.serialization.gson.gson
import java.io.File

data class MetAlerts (

    @SerializedName("features"   ) var features   : ArrayList<Features> = arrayListOf(),
    @SerializedName("lang"       ) var lang       : String?             = null,
    @SerializedName("lastChange" ) var lastChange : String?             = null,
    @SerializedName("type"       ) var type       : String?             = null

)


data class Geometry (

    @SerializedName("coordinates" ) var coordinates : Any?           = null,
    @SerializedName("type"        ) var type        : String?        = null

)

data class Resources (

    @SerializedName("description" ) var description : String? = null,
    @SerializedName("mimeType"    ) var mimeType    : String? = null,
    @SerializedName("uri"         ) var uri         : String? = null

)


data class Properties (

    @SerializedName("area"                 ) var area                 : String?              = null,
    @SerializedName("awarenessResponse"    ) var awarenessResponse    : String?              = null,
    @SerializedName("awarenessSeriousness" ) var awarenessSeriousness : String?              = null,
    @SerializedName("awareness_level"      ) var awarenessLevel       : String?              = null,
    @SerializedName("awareness_type"       ) var awarenessType        : String?              = null,
    @SerializedName("certainty"            ) var certainty            : String?              = null,
    @SerializedName("consequences"         ) var consequences         : String?              = null,
    @SerializedName("county"               ) var county               : ArrayList<String>    = arrayListOf(),
    @SerializedName("description"          ) var description          : String?              = null,
    @SerializedName("event"                ) var event                : String?              = null,
    @SerializedName("eventAwarenessName"   ) var eventAwarenessName   : String?              = null,
    @SerializedName("eventEndingTime"      ) var eventEndingTime      : String?              = null,
    @SerializedName("geographicDomain"     ) var geographicDomain     : String?              = null,
    @SerializedName("id"                   ) var id                   : String?              = null,
    @SerializedName("instruction"          ) var instruction          : String?              = null,
    @SerializedName("resources"            ) var resources            : ArrayList<Resources> = arrayListOf(),
    @SerializedName("riskMatrixColor"      ) var riskMatrixColor      : String?              = null,
    @SerializedName("severity"             ) var severity             : String?              = null,
    @SerializedName("title"                ) var title                : String?              = null,
    @SerializedName("triggerLevel"         ) var triggerLevel         : String?              = null,
    @SerializedName("type"                 ) var type                 : String?              = null

)

data class TimeInterval (

    @SerializedName("interval" ) var interval : ArrayList<String> = arrayListOf()

)

data class Features (

    @SerializedName("geometry"   ) var geometry   : Geometry?   = Geometry(),
    @SerializedName("properties" ) var properties : Properties? = Properties(),
    @SerializedName("type"       ) var type       : String?     = null,
    @SerializedName("when"       ) var timeInterval: TimeInterval? = TimeInterval()

)


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

suspend fun main() {

    val response = MetalertsDataSource().fetchMetalertsData()
    response.features.forEach { println(it) }
}

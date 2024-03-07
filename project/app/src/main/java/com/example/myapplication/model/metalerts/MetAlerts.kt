package com.example.myapplication.model.metalerts

import com.example.myapplication.data.metalerts.Features
import com.google.gson.annotations.SerializedName

data class MetAlerts (

    @SerializedName("features"   ) var features   : ArrayList<Features> = arrayListOf(),
    @SerializedName("lang"       ) var lang       : String?             = null,
    @SerializedName("lastChange" ) var lastChange : String?             = null,
    @SerializedName("type"       ) var type       : String?             = null

)
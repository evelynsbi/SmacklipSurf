package com.example.myapplication.model.metalerts

import com.google.gson.annotations.SerializedName

data class Resources (

    @SerializedName("description" ) var description : String? = null,
    @SerializedName("mimeType"    ) var mimeType    : String? = null,
    @SerializedName("uri"         ) var uri         : String? = null

)
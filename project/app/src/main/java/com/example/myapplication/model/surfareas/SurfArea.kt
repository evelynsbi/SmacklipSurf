package com.example.myapplication.model.surfareas

import com.example.myapplication.R


enum class SurfArea(
    val locationName: String,
    val lat: Double,
    val lon: Double,
    val image: Int
) {
    //surf areas

    //Stadt
    HODDEVIK("Hoddevik, Stadt",62.723, 5.103, R.drawable.cover___hoddevik, ),
    ERVIKA("Ervika, Stadt", 62.166674, 5.115609, 0),

    //Lofoten
    SKAGSANDEN("Skagsanden, Flakstad", 68.107052, 13.295348, 0),
    UNSTAD("Unstad, Lofoten", 68.268527, 13.580834, R.drawable.cover__unstad),
    GIMSTAD("Gimstad, ", 68.637591, 14.427877, 0),
    SANDVIKBUKTA("Sandvikbukta", 68.757964, 14.470910, 0),

    //Sør-vest
    JAEREN("Hellestø,Jæren", 58.800230, 5.548844, R.drawable.cover__jeren),
    KARMOEY("Karmøy (Stavasanden)", 59.233526, 5.183540, 0),

    //Østlandet
    SALTSTEIN("Saltstein, Tjøme", 58.969619, 9.832590, 0),


    //for tests
    NORDKAPP("Nordkapp",71.1655, 25.7992, 0),
    FEDJE("Fedje",60.7789, 4.71486, 0),


}
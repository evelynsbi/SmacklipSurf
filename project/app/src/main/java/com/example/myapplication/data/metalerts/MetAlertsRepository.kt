package com.example.myapplication.data.metalerts

import com.example.myapplication.model.metalerts.Features

interface MetAlertsRepository{
    suspend fun getFeatures(): List<Features>
    suspend fun getFeaturesHoddevik(): List<Features>

}
class MetAlertsRepositoryImpl (

    private val metAlertsDataSource : MetAlertsDataSource = MetAlertsDataSource()
) : MetAlertsRepository {
    override suspend fun getFeatures(): List<Features> {
        return metAlertsDataSource.fetchMetAlertsData().features
    }

    override suspend fun getFeaturesHoddevik(): List<Features> {
        // Coordinates for Hoddevik
        val hoddevikLatitude = 62.723
        val hoddevikLongitude = 5.103

        // Define a bounding box with approximately a 10 km radius around Hoddevik
        val radiusDegrees = 0.1
        val minLatitudeHoddevik = hoddevikLatitude - radiusDegrees
        val maxLatitudeHoddevik = hoddevikLatitude + radiusDegrees
        val minLongitudeHoddevik = hoddevikLongitude - radiusDegrees
        val maxLongitudeHoddevik = hoddevikLongitude + radiusDegrees

        val hoddevikLatLongArea = listOf(
            minLatitudeHoddevik,
            maxLatitudeHoddevik,
            minLongitudeHoddevik,
            maxLongitudeHoddevik
        )
        // Fetch all features
        val allFeatures: List<Features> = metAlertsDataSource.fetchMetAlertsData().features

        fun inArea(lat: Double, long: Double): Boolean {
            return (lat in hoddevikLatLongArea[0]..hoddevikLatLongArea[1] && long in hoddevikLatLongArea[2]..hoddevikLatLongArea[1])
        }

        fun getRelevantAlertsFor(surfArea: SurfArea): List<String> {
            val relevantAlerts: MutableList<String> = mutableListOf()
            allFeatures.forEach() {feature ->
                val coordinates = feature.geometry?.coordinates
                if (feature.geometry?.type == "Polygon") {
                    coordinates?.forEach {i ->
                        i.forEach { j ->
                            val lat = j[0] as Double
                            val long = j[1] as Double
                            if (inArea(lat, long)) {
                                feature.properties?.area?.let { relevantAlerts.add(it) }
                            }
                        }
                    }
                }
            }
            return relevantAlerts
        }
    }


}
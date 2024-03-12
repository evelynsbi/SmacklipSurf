package com.example.myapplication.data.metalerts

import androidx.compose.ui.geometry.CornerRadius
import com.example.myapplication.model.SurfArea
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

    private var allFeatures: List<Features> = listOf()
    override suspend fun getFeaturesHoddevik(): List<Features> {
        // Fetch all features
        allFeatures = metAlertsDataSource.fetchMetAlertsData().features
    }

    private fun inArea(lat: Double, long: Double, surfArea: SurfArea, radius: Double = 0.1): Boolean {
        return (
            lat in surfArea.lat - radius..surfArea.lat + radius &&
            long in surfArea.long - radius..surfArea.long + radius
        )
    }

    fun getRelevantAlertsFor(surfArea: SurfArea, allFeatures: List<Features>): List<String> {
        val relevantAlerts: MutableList<String> = mutableListOf()
        allFeatures.forEach() {feature ->
            val coordinates = feature.geometry?.coordinates
            if (feature.geometry?.type == "Polygon") {
                coordinates?.forEach {i ->
                    i.forEach { j ->
                        val lat = j[0] as Double
                        val long = j[1] as Double
                        if (inArea(lat, long, surfArea)) {
                            feature.properties?.area?.let { relevantAlerts.add(it) }
                        }
                    }
                }
            } else if (feature.geometry?.type == "MultiPolygon") {
                coordinates?.forEach { i ->
                    i.forEach { j ->
                        j.forEach { k ->
                            val coords = k as List<*>
                            val lat = k[0] as Double
                            val long = k[1] as Double
                            if (inArea(lat, long, surfArea)) {
                                feature.properties?.area?.let { relevantAlerts.add(it) }
                            }
                        }
                    }
                }
            }
        }
        return relevantAlerts
    }

}
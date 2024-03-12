package com.example.myapplication.data.metalerts

import com.example.myapplication.model.metalerts.Features
import com.example.myapplication.model.metalerts.Geometry

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
        val minLatitude = hoddevikLatitude - radiusDegrees
        val maxLatitude = hoddevikLatitude + radiusDegrees
        val minLongitude = hoddevikLongitude - radiusDegrees
        val maxLongitude = hoddevikLongitude + radiusDegrees

        // Fetch all features
        val allFeatures: List<Features> = metAlertsDataSource.fetchMetAlertsData().features

        allFeatures.forEach(){
            val coordinates = it.geometry?.coordinates
            if (it.geometry?.type == "Polygon"){
                polygonToArray(it.geometry?.coordinates)
            }
            else if(it.geometry?.type == "MultiPolygon"){
                multiPolygonToArray()
            }

        }
    }

    suspend fun multiPolygonToArray(polygon: List<List<Double>>){

    }
    suspend fun polygonToArray(polygon: List<List<Double>>){

    }

}
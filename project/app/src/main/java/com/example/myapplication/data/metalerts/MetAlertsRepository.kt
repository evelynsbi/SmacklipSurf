package com.example.myapplication.data.metalerts

import com.example.myapplication.model.metalerts.Features

interface MetAlertsRepository{
    suspend fun getFeatures(): List<Features>

}
class MetalertsRepositoryImpl (
    private val metalertsDataSource : MetAlertsDataSource = MetAlertsDataSource()
) : MetAlertsRepository {
    override suspend fun getFeatures(): List<Features> {
        return metalertsDataSource.fetchMetAlertsData().features
    }

}
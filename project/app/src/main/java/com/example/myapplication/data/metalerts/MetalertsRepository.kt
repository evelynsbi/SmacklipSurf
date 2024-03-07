package com.example.myapplication.data.metalerts

import com.example.myapplication.model.metalerts.Features

interface MetalertsRepository{
    suspend fun getFeatures(): List<Features>

}
class MetalertsRepositoryImpl (
    private val metalertsDataSource : MetalertsDataSource = MetalertsDataSource()
) : MetalertsRepository {
    override suspend fun getFeatures(): List<Features> {
        return metalertsDataSource.fetchMetalertsData().features
    }

}
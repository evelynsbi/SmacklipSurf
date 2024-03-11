package com.example.myapplication.data.oceanforecast

import com.example.myapplication.model.oceanforecast.Data
import com.example.myapplication.model.oceanforecast.Timeserie

class HoddevikRepository(private val dataSource: HoddevikDataSourceDataSource) {
    //vet ikke hva som er best practice: ha datasource som argument eller ha det inni klassen

    suspend fun getTimeSeries(): List<Pair<String, Data>> {
        //henter timeSeries som er en liste av TimeSerie-objekter som består av de to variablene time og data
        val timeSeries : List<Timeserie> = dataSource.fetchHoddevik().properties.timeseries;

        //returnerer en map som mapper time til data, dermed ser man data for hver tidspunkt
        return timeSeries.map {it.time to it.data}
    }



}
package com.example.myapplication


import com.example.myapplication.model.metalerts.MetAlerts
import com.google.gson.Gson
import com.example.myapplication.data.metalerts.MetAlertsRepositoryImpl
import kotlinx.coroutines.runBlocking
import com.example.myapplication.data.oceanforecast.HoddevikDataSourceDataSource
import com.example.myapplication.data.oceanforecast.HoddevikRepository
import com.example.myapplication.model.oceanforecast.Data
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest

import org.junit.Test
import org.junit.Assert.*
import java.io.File

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {

    private val repo = MetAlertsRepositoryImpl()
    private val hoddevikDataSourceDataSource = HoddevikDataSourceDataSource()
    private val hoddevikRepository = HoddevikRepository(hoddevikDataSourceDataSource)


    @Test
    fun addition_isCorrect() = runBlocking{

        val timeSeries: List<Pair<String, Data>> = hoddevikRepository.getTimeSeries()
        val time1 = timeSeries.get(0).first
        //val data1 = timeSeries.get(0).second
        //assertEquals("2024-03-07T13:00:00Z", time1)
        println("$time1 --------------hei----------------")

    }
    @Test
    fun testMetAlertsAreaNameWithProxy() = runBlocking {
        val feature = repo.getFeatures()[0]
        println(feature.properties?.area)
        assert(true)
    }

}
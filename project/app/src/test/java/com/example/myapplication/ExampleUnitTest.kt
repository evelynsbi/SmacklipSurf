package com.example.myapplication

import com.example.myapplication.model.metalerts.MetAlerts
import com.google.gson.Gson
import com.example.myapplication.data.metalerts.MetAlertsRepositoryImpl
import kotlinx.coroutines.runBlocking
import org.junit.Test
import org.junit.Assert.*
import java.io.File

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class ExampleUnitTest {
    val repo = MetAlertsRepositoryImpl()
    @Test
    fun testMetAlertsAreaNameWithProxy() = runBlocking {
        val feature = repo.getFeatures()[0]
        println(feature.properties?.area)
        assert(true)
    }

}
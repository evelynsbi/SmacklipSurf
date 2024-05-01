package com.example.myapplication.data.settings

import androidx.datastore.core.DataStore
import com.example.myapplication.Settings
import kotlinx.coroutines.flow.Flow

class SettingsRepository (private val settingsStore: DataStore<Settings>) {

    val settingsFlow: Flow<Settings> = settingsStore.data
    suspend fun setTest(test: Double) {
        settingsStore.updateData { it.toBuilder().setTest(test).build() }
    }
    suspend fun setDarkMode(darkMode: Boolean){
        settingsStore.updateData { it.toBuilder().setDarkMode(darkMode).build() }
    }
}
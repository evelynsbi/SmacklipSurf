package com.example.myapplication.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Settings
import com.example.myapplication.data.settings.SettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class SettingsScreenViewModel(private val settingsRepository: SettingsRepository) : ViewModel() {
    val settings: Flow<Settings> = settingsRepository.settingsFlow

    fun saveTestValue(test: Double){
        viewModelScope.launch {
            settingsRepository.setTest(test)
        }
    }
}
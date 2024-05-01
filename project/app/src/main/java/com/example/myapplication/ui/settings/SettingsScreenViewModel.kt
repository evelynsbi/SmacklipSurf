package com.example.myapplication.ui.settings

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.Settings
import com.example.myapplication.data.settings.SettingsRepository
import com.example.myapplication.data.settings.SettingsSerializer
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class SettingsUiState(
    val darkModeEnabled: Boolean = false,
    val testValue: Double = 0.0,
    val loading: Boolean = false
)
class SettingsScreenViewModel() : ViewModel() {
    private lateinit var settingsStore: DataStore<Settings>

    fun init(context: Context) {
        settingsStore = context.dataStore(
            fileName = "settings",
            serializer = SettingsSerializer()
        )
    }

    private val settingsRepository: SettingsRepository = SettingsRepository(settingsStore)
    private var _settingsUiState = MutableStateFlow(SettingsUiState())
    val settingsUiState = _settingsUiState.asStateFlow()

    init {
        viewModelScope.launch {
            try {
                val settings = settingsRepository.settingsFlow.first()
                _settingsUiState.value = _settingsUiState.value.copy(darkModeEnabled = settings.darkMode)
            }catch (e: Exception) {
                Log.e("SettingsScreenViewModel", "Error fetching settings: ${e.message}")
            }

        }
    }
    fun setDarkMode(enabled: Boolean){
        _settingsUiState.value = _settingsUiState.value.copy(loading = true)
        viewModelScope.launch {
            try {
                settingsRepository.setDarkMode(enabled)
                _settingsUiState.value = _settingsUiState.value.copy(loading = false, darkModeEnabled = enabled)
            } catch(e: Exception){
                Log.e("SettingsScreenViewModel", "Error setting dark mode: ${e.message}")
                _settingsUiState.value = _settingsUiState.value.copy(loading = false, darkModeEnabled = false)
            }
        }
    }

    fun saveTestValue(test: Double) {
        _settingsUiState.value = _settingsUiState.value.copy(loading = true)
        viewModelScope.launch {
            try {
                settingsRepository.setTest(test)
                _settingsUiState.value = _settingsUiState.value.copy(loading = false, testValue = test)
            } catch (e: Exception) {
                Log.e("SettingsScreenViewModel", "Error saving test value: ${e.message}")
                _settingsUiState.value = _settingsUiState.value.copy(loading = false, testValue = 0.0)
            }
        }
    }


}
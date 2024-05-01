package com.example.myapplication.ui.settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.myapplication.data.settings.SettingsRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

data class SettingsUiState(
    val darkModeEnabled: Boolean = false,
    val testValue: Double = 0.0,
    val loading: Boolean = false
)
class SettingsScreenViewModel(private val settingsRepository: SettingsRepository) : ViewModel() {
    private var _settingsUiState = MutableStateFlow(SettingsUiState())
    val settingsUiState = _settingsUiState.asStateFlow()
   // val settings: Flow<Settings> = settingsRepository.settingsFlow
    //private val _darkModeEnabled = MutableStateFlow(false)
    //val darkModeEnabled = _darkModeEnabled.asStateFlow()

    init {
        viewModelScope.launch {
            val settings = settingsRepository.settingsFlow.first()
            _settingsUiState.value = _settingsUiState.value.copy(darkModeEnabled = settings.darkMode)
        }
    }
    fun setDarkMode(enabled: Boolean){
        _settingsUiState.value = _settingsUiState.value.copy(loading = true)
        viewModelScope.launch {
            try {
                settingsRepository.setDarkMode(enabled)
                _settingsUiState.value = _settingsUiState.value.copy(loading = false, darkModeEnabled = enabled)
            } catch(e: Exception){
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
                _settingsUiState.value = _settingsUiState.value.copy(loading = false, testValue = 0.0)
            }
        }
    }

}
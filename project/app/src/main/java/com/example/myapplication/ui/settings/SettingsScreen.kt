package com.example.myapplication.ui.settings

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(settingsScreenViewmodel: SettingsScreenViewModel = viewModel()) {
    settingsScreenViewmodel.init()

    val settingsUiState : SettingsUiState by settingsScreenViewmodel.settingsUiState.collectAsState()
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(text = "Settings") }
            )
        },
        content = {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Switch(
                    checked = settingsUiState.darkModeEnabled,
                    onCheckedChange = { settingsScreenViewmodel.setDarkMode(it) },
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = "Test value: ${settingsUiState.testValue}",
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                OutlinedTextField(
                    value = settingsUiState.testValue.toString(),
                    onValueChange = { /* Handle value change */ },
                    label = { Text("Test Value") },
                    modifier = Modifier.fillMaxWidth()
                )
                Button(
                    onClick = { /* Handle save button click */ },
                    modifier = Modifier.padding(top = 8.dp)
                ) {
                    Text(text = "Save")
                }
            }
        }
    )
}



@Preview(showBackground = true)
@Composable
private fun PreviewSettingsScreen(){
    MyApplicationTheme {
        SettingsScreen()
    }
}




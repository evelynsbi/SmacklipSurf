package com.example.myapplication.ui.settings

import android.annotation.SuppressLint
import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.LineHeightStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.Settings
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(settingsScreenViewmodel: SettingsScreenViewModel = viewModel()) {
    val settingsState by settingsScreenViewmodel.settings.collectAsState(initial = Settings.getDefaultInstance())

    Scaffold(
        content ={
            val coroutineScope = rememberCoroutineScope()
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(16.dp)

            ) {
                Text(text = "Test value: ${settingsState.test}")

                Spacer(modifier = Modifier.height(16.dp))

                Button(
                    onClick = {
                        val newTestValue = 123.45
                        settingsScreenViewmodel.saveTestValue(newTestValue)

                    }
                ) {
                    Text(text = "update Test Value")
                }
            } 
            Text(text = "Test Value: ${settingsState.test}")
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


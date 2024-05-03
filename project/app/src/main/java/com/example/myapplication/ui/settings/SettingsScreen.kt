package com.example.myapplication.ui.settings

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Card
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.R
import com.example.myapplication.Settings
import com.example.myapplication.SmackLipApplication
import com.example.myapplication.ui.common.composables.BottomBar
import com.example.myapplication.ui.theme.AppTheme


@OptIn(ExperimentalMaterial3Api::class)
//@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun SettingsScreen(navController: NavController, settingsViewmodelFactory: SettingsScreenViewModel.SettingsViewModelFactory) {
    val settingsScreenViewModel : SettingsScreenViewModel = viewModel(factory = settingsViewmodelFactory)
    val settingsUiState by settingsScreenViewModel.settingsUiState.collectAsState()
    val isDarkThemeEnabled by settingsScreenViewModel.isDarkThemEnabled.collectAsState()
    //val navController = NavigationManager.navController
    AppTheme(darkTheme = isDarkThemeEnabled) {
        Scaffold(
            topBar = {
                CenterAlignedTopAppBar(
                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text(text = "Settings")
                    })
            },
            bottomBar = {
                BottomBar(navController = navController)
            }
        ) { innerPadding ->
            // SmackLip logo
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize(),
            ) {
                item {
                    Image(
                        painter = painterResource(id = R.drawable.smacklip_logo),
                        contentDescription = "app logo",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp)
                    )
                }
                // Kort for dark mode
                item {
                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp)
                    ) {
                        var expandedThemeCard by remember { mutableStateOf(false) }
                        Column(modifier = Modifier.padding(12.dp)) {
                            Text(text = "Velg app tema", style = MaterialTheme.typography.headlineMedium)
                            IconButton(onClick = { expandedThemeCard = !expandedThemeCard }) {
                                Icon(Icons.Default.ExpandMore, contentDescription = "Utvid")
                            }
                            // Expand theme card
                            if (expandedThemeCard){
                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .padding(8.dp),
                                    horizontalArrangement = Arrangement.SpaceBetween
                                ){
                                    Text(text = if (isDarkThemeEnabled) "Dark Mode" else "Light Mode")
                                    Switch(
                                        checked = isDarkThemeEnabled,
                                        onCheckedChange = {isChecked ->
                                            settingsScreenViewModel.updateTheme(if (isChecked) Settings.Theme.DARK else Settings.Theme.LIGHT)
                                        }
                                    )
                                }
                            }

                        }

                    }
                }
                //Info kort
                item{
                    InformationCard(title = "Hvorfor SmackLip Surf?", content = "Beskrivelse")
                }
                item {
                    InformationCard(title = "Hvorfor SmackLip Surf?", content = "Beskrivelse")
                }
            }

        }


    }


}

@Composable
fun InformationCard(title: String, content: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        var expanded by remember { mutableStateOf(false) }
        Column(modifier = Modifier.padding(12.dp)) {
            Text(text = title, style = MaterialTheme.typography.headlineMedium)
            IconButton(onClick = { expanded = !expanded }) {
                Icon(Icons.Default.ExpandMore, contentDescription = "Utvid")
            }
            //ekspandert innhold
            if (expanded) {
                Text(
                    text = content,
                    style = MaterialTheme.typography.bodyMedium,
                    modifier = Modifier.padding(top = 4.dp)
                )


            }

        }
    }

}

@Composable
fun DarkModeCard(
    darkModeEnabled: Boolean,
    onDarkModeToggle: (Boolean) -> Unit
){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(16.dp)
                .clickable { onDarkModeToggle(!darkModeEnabled) }
        ) {
            Switch(
                checked = darkModeEnabled,
                onCheckedChange = null,
                modifier = Modifier.padding(end = 16.dp)
            )
            Text(text = "Dark Mode")

        }

    }

}

@Composable
fun InfoCardSettings(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp),
    ) {
        Column(
            modifier = Modifier.padding(16.dp)
        ) {
            Text(
                text = "Info",
            )
            Text(
                text = "Info her...",
            )
        }
    }
}



@Preview(showBackground = true)
@Composable
private fun PreviewSettingsScreenLight(){
    AppTheme(darkTheme = false) {
        val context = LocalContext.current
        val viewModelFactory = remember {
            SettingsScreenViewModel.SettingsViewModelFactory(
                (context.applicationContext as SmackLipApplication).container)

        }
        SettingsScreen(navController = rememberNavController(), viewModelFactory)
    }
}




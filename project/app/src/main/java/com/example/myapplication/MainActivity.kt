package com.example.myapplication

import android.content.Context
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.data.settings.SettingsSerializer
import com.example.myapplication.model.surfareas.SurfArea
import com.example.myapplication.ui.home.HomeScreen
import com.example.myapplication.ui.surfarea.SurfAreaScreen
import com.example.myapplication.ui.theme.MyApplicationTheme
val Context.settingsStore: DataStore<Settings> by dataStore (
    fileName = "settings",
    serializer = SettingsSerializer()
)
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MyApplicationTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    SmackLipNavigation()
                }
            }
        }
    }
}

@Composable
fun SmackLipNavigation(){
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = "HomeScreen",

        ){
        composable("HomeScreen"){
            HomeScreen(){
                navController.navigate("SurfAreaScreen/$it")
            }
        }
        composable("SurfAreaScreen/{surfArea}") { backStackEntry ->
            val surfArea = backStackEntry.arguments?.getString("surfArea") ?: ""
            SurfAreaScreen(surfAreaName = surfArea){}
        }
    }
}
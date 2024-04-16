package com.example.myapplication.ui.home

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.myapplication.R
import com.example.myapplication.model.metalerts.Features
import com.example.myapplication.model.metalerts.Properties
import com.example.myapplication.model.surfareas.SurfArea
import com.example.myapplication.ui.theme.MyApplicationTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(homeScreenViewModel : HomeScreenViewModel = viewModel()) {
    val homeScreenUiState: HomeScreenUiState by homeScreenViewModel.homeScreenUiState.collectAsState()
    val favoriteSurfAreas by homeScreenViewModel.favoriteSurfAreas.collectAsState()

    val isSearchActive = remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            Column {
                SearchBar(
                    onQueryChange = {},
                    isSearchActive = isSearchActive.value,
                    onActiveChanged = { isActive ->
                        isSearchActive.value = isActive
                    },
                    surfAreas = SurfArea.entries.toList()
                )
            }
        }
    ) { innerPadding ->
        Column(
            modifier = Modifier
                .padding(innerPadding)
        ) {
            FavoritesList(
                favorites = favoriteSurfAreas,
                windSpeedMap = homeScreenUiState.windSpeed,
                windGustMap = homeScreenUiState.windGust,
                waveHeightMap = homeScreenUiState.waveHeight,
                alerts = homeScreenUiState.allRelevantAlerts
            )
            LazyColumn(
                modifier = Modifier
                    .padding(innerPadding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                items(SurfArea.entries) { location ->
                    SurfAreaCard(
                        location,
                        windSpeedMap = homeScreenUiState.windSpeed,
                        windGustMap = homeScreenUiState.windGust,
                        waveHeightMap = homeScreenUiState.waveHeight,
                        alerts = homeScreenUiState.allRelevantAlerts.filter { alert ->
                            alert.any {
                                it.properties?.area?.contains(location.locationName) ?: false }
                        },
                        homeScreenViewModel = homeScreenViewModel
                    )
                }
            }
        }
    }
}

@Composable
fun SearchBar(
    onQueryChange: (String) -> Unit,
    isSearchActive: Boolean,
    onActiveChanged: (Boolean) -> Unit,
    modifier: Modifier = Modifier,
    onSearch: ((String) -> Unit)? = null,
    surfAreas: List<SurfArea>
) {
    var searchQuery by remember { mutableStateOf("") }

    val activeChanged: (Boolean) -> Unit = { active ->
        if (!active) {
            searchQuery = ""
            onQueryChange("")
        }
        onActiveChanged(active)
    }

    TextField(
        value = searchQuery,
        onValueChange = { query ->
            searchQuery = query
            onQueryChange(query)
            activeChanged(true)
        },
        modifier = modifier
            .padding(start = 12.dp, top = 2.dp, end = 12.dp, bottom = 12.dp)
            .fillMaxWidth(),
        placeholder = { Text("Søk etter surfeområde") },
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Search,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        trailingIcon = {
            if(isSearchActive) {
                IconButton(
                    onClick = {
                        searchQuery = ""
                        onQueryChange("")
                        onActiveChanged(false)
                    }
                ) {
                    Icon(
                        imageVector = Icons.Default.Clear,
                        contentDescription = "Clear searchbar"
                    )
                }
            }
        },
        singleLine = true,
        keyboardOptions = KeyboardOptions.Default.copy(
            imeAction = ImeAction.Search
        ),
        keyboardActions = KeyboardActions(
            onSearch = {
                onSearch?.invoke(searchQuery)
                activeChanged(false)
            }
        )
    )
    if (searchQuery.isNotEmpty()) {
        val filteredSurfAreas = surfAreas.filter { it.locationName.contains(searchQuery, ignoreCase = true) }
        if (filteredSurfAreas.isNotEmpty()) {
            LazyColumn {
                items(filteredSurfAreas) {surfArea ->
                    Text(
                        text = surfArea.locationName,
                        modifier = Modifier.clickable { onSearch?.invoke(surfArea.locationName) }
                    )
                }
            }
        } else {
            Text("Ingen samsvarende surfeområder funnet")
        }
    }
}

/* TODO:
implement windspeedmap, windgustmap, waveheightmap and alerts correctly,
to receive accurate values in favorite surfareacards
 */
@Composable
fun FavoritesList(
    favorites: List<SurfArea>,
    windSpeedMap: Map<SurfArea, List<Pair<List<Int>, Double>>>,
    windGustMap: Map<SurfArea, List<Pair<List<Int>, Double>>>,
    waveHeightMap: Map<SurfArea, List<Pair<List<Int>, Double>>>,
    alerts: List<List<Features>>?
) {
    Column {
        Text(
            text = "Favoritter",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp),
            color = Color.DarkGray
        )
    }
    if (favorites.isNotEmpty()) {
        LazyRow {
            items(favorites) { surfArea ->
                Card(
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 8.dp)
                        .size(width = 150.dp, height = 250.dp)
                        .clip(RoundedCornerShape(10.dp))
                ) {
                    SurfAreaCard(
                        surfArea = surfArea,
                        windSpeedMap = emptyMap(),
                        windGustMap = emptyMap(),
                        waveHeightMap = emptyMap(),
                        alerts = emptyList(),
                        homeScreenViewModel = HomeScreenViewModel(),
                        showFavoriteButton = false
                    )
                }
            }
        }
    } else {
        EmptyFavoriteCard()
    }
}

@Composable
fun EmptyFavoriteCard() {
    Card(
        modifier = Modifier
            .padding(horizontal = 16.dp, vertical = 8.dp)
            .size(width = 150.dp, height = 250.dp)
            .clip(RoundedCornerShape(10.dp))
    ) {
        Box(
            modifier = Modifier.fillMaxSize(),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Ingen favoritter lagt til",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                color = Color.Gray,
                modifier = Modifier.padding(16.dp)
            )
        }
    }
}

@Composable
fun SurfAreaCard(
    surfArea: SurfArea,
    windSpeedMap: Map<SurfArea, List<Pair<List<Int>, Double>>>,
    windGustMap: Map<SurfArea, List<Pair<List<Int>, Double>>>,
    waveHeightMap: Map<SurfArea,List<Pair<List<Int>, Double>>>,
    alerts: List<List<Features>>?,
    homeScreenViewModel: HomeScreenViewModel,
    showFavoriteButton: Boolean = true
) {
    val windSpeed = windSpeedMap[surfArea] ?: listOf()
    val windGust = windGustMap[surfArea] ?: listOf()
    val waveHeight = waveHeightMap[surfArea] ?: listOf()

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp)
    ) {
        Row(
            //contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize()
        ) {
            if (showFavoriteButton) {
                Column(
                    modifier = Modifier
                        .padding(2.dp)
                        .align(Alignment.Top),
                    horizontalAlignment = Alignment.End,
                ) {
                    IconButton(
                        onClick = { homeScreenViewModel.updateFavorites(surfArea) },
                        modifier = Modifier.align(Alignment.End)
                    ) {
                        Icon(
                            painter = painterResource(id = homeScreenViewModel.updateFavoritesIcon(surfArea)),
                            contentDescription = "Toggle favorite",
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
            }
            Column(
                modifier = Modifier
                    .fillMaxHeight()
                    .padding(16.dp),
                horizontalAlignment = Alignment.Start,
            ) {
                Row {
                    Text(
                        text = surfArea.locationName,
                        fontWeight = FontWeight.Bold
                    )
                }
                Row {
                    Text(
                        text = "Wind: ${if (windSpeed.isNotEmpty()) windSpeed[0].second else ""}" +
                                if(windGust.isNotEmpty() && windSpeed.isNotEmpty() && windGust[0].second != windSpeed[0].second) "(${windGust[0].second})" else ""
                    )
                }

                Row {
                    Text(
                        text = "Wave height: ${if (waveHeight.isNotEmpty()) waveHeight[0].second else ""}"
                    )
                }

                Row {
                    Text(
                        // only shows description of first alert. There may be several.
                        text = "Alert:  ${if (alerts?.isNotEmpty() == true) alerts[0][0].properties?.description else ""}"
                    )
                }
                Row {
                    if (alerts?.isNotEmpty() == true) {
                        val icon =
                            homeScreenViewModel.getIconBasedOnAwarenessLevel(alerts[0][0].properties?.awarenessLevel.toString())
                        Image(
                            painter = painterResource(id = icon),
                            contentDescription = "Awareness Level Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    } else {
                        Image(
                            painter = painterResource(id = R.drawable.icon_awareness_default),
                            contentDescription = "Awareness Level Icon",
                            modifier = Modifier.size(24.dp)
                        )
                    }
                }
                Column(
                    horizontalAlignment = Alignment.End
                ) {
                    if (surfArea.image != 0) {
                        Image(
                            painter = painterResource(id = surfArea.image),
                            contentDescription = null

                        )
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewSurfAreaCard() {
    val windSpeedMap: Map<SurfArea,List<Pair<List<Int>, Double>>> = mapOf(
        SurfArea.HODDEVIK to listOf(Pair(listOf(2, 4, 6, 8), 1.0))
    )
    val windGustMap: Map<SurfArea,List<Pair<List<Int>, Double>>> = mapOf(
        SurfArea.HODDEVIK to listOf(Pair(listOf(3, 5, 8, 32), 3.0))
    )
    val waveHeightMap: Map<SurfArea,List<Pair<List<Int>, Double>>> = mapOf(
        SurfArea.HODDEVIK to listOf(Pair(listOf(1, 2, 3, 4), 5.0))
    )
    val viewModel = HomeScreenViewModel()
    MyApplicationTheme {
        SurfAreaCard(
            SurfArea.HODDEVIK,
            windSpeedMap,
            windGustMap,
            waveHeightMap,
            listOf(listOf((Features(properties = Properties(description = "Det ræinar"))))),
            viewModel,
            true
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewHomeScreen() {
    MyApplicationTheme {
        HomeScreen()
    }
}
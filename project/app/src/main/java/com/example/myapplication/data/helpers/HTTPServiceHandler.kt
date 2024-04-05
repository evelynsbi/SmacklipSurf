package com.example.myapplication.data.helpers

object HTTPServiceHandler {
    //Global API authenticator
    const val API_KEY = "f25d12be-cbd6-4adf-9aed-c41d84494cdb"
    const val API_HEADER = "X-Gravitee-API-Key"

    //LocationForecast
    const val LOCATION_FORECAST_URL = "https://gw-uio.intark.uh-it.no/in2000/weatherapi/locationforecast/2.0/complete"

    //MetAlerts
    const val METALERTS_URL = "https://gw-uio.intark.uh-it.no/in2000/weatherapi/weatherapi/metalerts/2.0/current.json"

    //Oceanforecast
    const val OCEAN_FORECAST_URL = "https://gw-uio.intark.uh-it.no/in2000/weatherapi/oceanforecast/2.0/complete"


    //WaveForecast
    const val WAVE_FORECAST_BASE = "https://www.barentswatch.no/bwapi"
    const val WAVE_FORECAST_POINT_FORECAST = "https://www.barentswatch.no/bwapi/v1/geodata/waveforecast/pointforecast"
}
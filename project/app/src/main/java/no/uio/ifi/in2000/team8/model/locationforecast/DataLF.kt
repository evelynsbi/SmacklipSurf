package no.uio.ifi.in2000.team8.model.locationforecast

data class DataLF(
    val instant: InstantLF,
    val next_12_hours: Next12Hours,
    val next_1_hours: Next1Hours?,
    val next_6_hours: Next6Hours
)

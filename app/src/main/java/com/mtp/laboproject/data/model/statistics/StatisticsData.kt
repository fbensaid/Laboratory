package com.mtp.laboproject.data.model.statistics

data class StatisticsData(
    val avgCo2: String,
    val avgHumidity: String,
    val avgTemperature: String,
    val openDoors: Int,
    val openWindows: Int,
    val totDoors: Int,
    val totWindows: Int
)
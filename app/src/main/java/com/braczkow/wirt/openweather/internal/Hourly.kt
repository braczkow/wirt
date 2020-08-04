package com.braczkow.wirt.openweather.internal

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Hourly(
    val clouds: Int,
    val dew_point: Double,
    val dt: Long,
    val feels_like: Double,
    val humidity: Int,
    val pop: Double,
    val pressure: Int,
    val rain: Rain?,
    val snow: Snow?,
    val temp: Double,
    val visibility: Int,
    val weather: List<Weather>,
    val wind_deg: Int,
    val wind_speed: Double
)

@JsonClass(generateAdapter = true)
data class Rain(
    val `1h`: Double
)

@JsonClass(generateAdapter = true)
data class Snow(
    val `1h`: Double
)


package com.braczkow.wirt.openweather.internal

import com.braczkow.wirt.openweather.internal.Current
import com.braczkow.wirt.openweather.internal.Daily
import com.braczkow.wirt.openweather.internal.Hourly
import com.braczkow.wirt.openweather.internal.Minutely
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class OpenWeatherOneCallResponse(
    val current: Current,
    val daily: List<Daily>?,
    val hourly: List<Hourly>?,
    val lat: Double,
    val lon: Double,
    val minutely: List<Minutely>?,
    val timezone: String,
    val timezone_offset: Int
)
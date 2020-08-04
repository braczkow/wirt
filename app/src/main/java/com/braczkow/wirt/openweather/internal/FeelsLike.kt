package com.braczkow.wirt.openweather.internal

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class FeelsLike(
    val day: Double,
    val eve: Double,
    val morn: Double,
    val night: Double
)
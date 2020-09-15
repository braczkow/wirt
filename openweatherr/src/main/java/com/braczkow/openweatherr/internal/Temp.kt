package com.braczkow.openweatherr.internal

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Temp(
    val day: Double,
    val eve: Double,
    val max: Double,
    val min: Double,
    val morn: Double,
    val night: Double
)
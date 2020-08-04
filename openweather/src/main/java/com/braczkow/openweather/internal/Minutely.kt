package com.braczkow.wirt.openweather.internal

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class Minutely(
    val dt: Int,
    val precipitation: Int
)
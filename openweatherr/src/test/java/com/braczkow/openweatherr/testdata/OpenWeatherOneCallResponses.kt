package com.braczkow.openweatherr.testdata

import com.braczkow.openweatherr.internal.*


object OpenWeatherOneCallResponses {

    val NOW = 1596690000L

    val withoutCurrentRainNullHourly = OpenWeatherOneCallResponse(
        current = Currents.withoutRain,
        daily = null,
        hourly = null,
        lat = 52.0,
        lon = 14.0,
        minutely = null,
        timezone = "",
        timezone_offset = 0
    )

    val currentRainNullHourly = OpenWeatherOneCallResponse(
        current = Currents.withRain,
        daily = null,
        hourly = null,
        lat = 52.0,
        lon = 14.0,
        minutely = null,
        timezone = "",
        timezone_offset = 0
    )

    val rainInAnHour = OpenWeatherOneCallResponse(
        current = Currents.withoutRain,
        daily = null,
        hourly = listOf(
            Hourly(
                clouds = 100,
                dew_point = 273.0,
                dt = NOW + 1 * 60 * 60,
                feels_like = 299.0,
                humidity = 90,
                pop = 100.0,
                pressure = 999,
                rain = Rain(100.0),
                snow = null,
                temp = 299.0,
                visibility = 200,
                weather = listOf(
                    Weathers.moderateRain
                ),
                wind_deg = 90,
                wind_speed = 10.0
            )
        ),
        lat = 52.0,
        lon = 14.0,
        minutely = null,
        timezone = "",
        timezone_offset = 0
    )

    val rainIn40Hours = OpenWeatherOneCallResponse(
        current = Currents.withoutRain,
        daily = null,
        hourly = listOf(
            Hourly(
                clouds = 100,
                dew_point = 273.0,
                dt = NOW + 40 * 60 * 60,
                feels_like = 299.0,
                humidity = 90,
                pop = 100.0,
                pressure = 999,
                rain = Rain(100.0),
                snow = null,
                temp = 299.0,
                visibility = 200,
                weather = listOf(
                    Weathers.moderateRain
                ),
                wind_deg = 90,
                wind_speed = 10.0
            )
        ),
        lat = 52.0,
        lon = 14.0,
        minutely = null,
        timezone = "",
        timezone_offset = 0
    )
}

object Currents {
    val withoutRain = Current(
        0,
        273.0,
        1,
        303.0,
        60,
        1024,
        1596530647,
        1596573846,
        301.0,
        0.0,
        1000,
        listOf(Weathers.clearSky),
        90,
        12.0
    )

    val withRain = Current(
        100,
        273.0,
        1,
        303.0,
        60,
        1024,
        1596530647,
        1596573846,
        301.0,
        0.0,
        1000,
        listOf(Weathers.moderateRain),
        90,
        12.0
    )
}

object Weathers {
    val moderateRain = Weather(
        "moderate rain",
        "",
        501,
        "Rain"
    )

    val clearSky = Weather(
        "clear sky",
        "",
        800,
        "Clear"
    )
}
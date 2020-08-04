package com.braczkow.wirt.openweather

import com.braczkow.wirt.Timee
import com.braczkow.wirt.openweather.internal.OpenWeatherApi
import com.braczkow.wirt.openweather.internal.OpenWeatherOneCallResponse
import timber.log.Timber
import java.time.LocalDateTime
import java.time.ZoneOffset

class WillItRainUseCase(
    private val api: OpenWeatherApi
) {

    sealed class LocationDescriptor {
        data class LatLon(
            val lat: String,
            val lon: String
        ) : LocationDescriptor()

        data class CityName(
            val name: String
        ) : LocationDescriptor()

        data class CityId(
            val id: String
        ) : LocationDescriptor()
    }

    sealed class TimeRangeDescriptor {
        object Today : TimeRangeDescriptor()
        data class Hours(
            val count: Int
        ) : TimeRangeDescriptor()
    }

    sealed class Result {
        object Error: Result()
        object NoRain : Result()
        object WillRain: Result()
        object IsRaining: Result()
    }


    suspend fun execute(
        locationDescriptor: LocationDescriptor,
        timeRangeDescriptor: TimeRangeDescriptor
    ): Result {
        if (locationDescriptor !is LocationDescriptor.LatLon) {
            Timber.d("Unsupported")
            return Result.Error
        }

        try {
            val resp = api.getOneCallByLatLon(
                locationDescriptor.lat,
                locationDescriptor.lon,
                "daily"
            )

            Timber.d("Got response: $resp")

            return when(timeRangeDescriptor) {
                is TimeRangeDescriptor.Today -> processToday(resp)
                is TimeRangeDescriptor.Hours -> processHours(resp, timeRangeDescriptor)
                else -> Result.Error
            }


        } catch (e: Exception) {
            Timber.d("Failed to getOneCall: $e")
            return Result.Error
        }
    }

    fun processHours(
        resp: OpenWeatherOneCallResponse,
        timeRangeDescriptor: TimeRangeDescriptor.Hours
        ): Result {
        if (isRaining(resp)) return Result.IsRaining

        var hasRain = false

        val maxHours = Timee.localDateTime().plusHours(timeRangeDescriptor.count.toLong())

        resp.hourly?.forEach { hourly ->
            val hourlyDateTime = LocalDateTime.ofEpochSecond(hourly.dt, 0, ZoneOffset.UTC)
            if (maxHours.isAfter(hourlyDateTime) && hourly.rain != null && hourly.rain.`1h` > 0.0) {
                hasRain = true
            }
        }

        return if (hasRain) Result.WillRain else Result.NoRain
    }

    fun processToday(
        resp: OpenWeatherOneCallResponse
    ): Result {
        if (isRaining(resp)) return Result.IsRaining

        var hasRain = false

        val todayEndOfDay = Timee.localDate().plusDays(1).atStartOfDay()

        resp.hourly?.forEach { hourly ->
            val hourlyDateTime = LocalDateTime.ofEpochSecond(hourly.dt, 0, ZoneOffset.UTC)
            if (todayEndOfDay.isAfter(hourlyDateTime) && hourly.rain != null && hourly.rain.`1h` > 0.0) {
                hasRain = true
            }
        }

        return if (hasRain) Result.WillRain else Result.NoRain
    }

    private fun isRaining(resp: OpenWeatherOneCallResponse): Boolean {
        val isRaining = resp.current.weather.any {
            (it.id in 500..599)
        }

        if (isRaining) {
            return true
        }
        return false
    }
}
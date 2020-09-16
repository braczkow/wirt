package com.braczkow.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.braczkow.lib.Logg
import com.google.android.gms.location.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.filter

interface LocationApi {
    interface HighAccuracyRequest {
        fun start()
        fun stop()
    }

    data class Location(
        val lat: Double,
        val long: Double,
        val accuracy: Float
    )

    fun makeLocationRequest(): HighAccuracyRequest



    val locations: Flow<Location>

    companion object {
        val NONE_LOCATION : Location = Location(0.0, 0.0, 0f)
    }
}

class LocationApiImpl(
    private val context: Context
) : LocationApi {

    private val client: FusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(context)
    @OptIn(ExperimentalCoroutinesApi::class)
    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(locationResult: LocationResult?) {
            if (locationResult != null) {
                val ll = locationResult.lastLocation
                (locations as MutableStateFlow).value = LocationApi.Location(ll.latitude, ll.longitude, ll.accuracy)
            }
        }
    }

    private val requests = mutableSetOf<LocationRequest>()

    @OptIn(ExperimentalCoroutinesApi::class)
    override val locations: Flow<LocationApi.Location> = MutableStateFlow<LocationApi.Location>(
        LocationApi.NONE_LOCATION
    )

    override fun makeLocationRequest(): LocationApi.HighAccuracyRequest {

        val locationRequest = LocationRequest
            .create()
            .setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY)

        return object : LocationApi.HighAccuracyRequest {
            override fun start() {
                if (ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_FINE_LOCATION
                    )
                    != PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(
                        context,
                        Manifest.permission.ACCESS_COARSE_LOCATION
                    ) != PackageManager.PERMISSION_GRANTED
                ) {
                    Logg.d("No location permission, skip locationRequest!")
                    return
                }

                requests.add(locationRequest)
                client.requestLocationUpdates(locationRequest, locationCallback, Looper.getMainLooper())
            }

            override fun stop() {
                requests.remove(locationRequest)

                if (requests.isEmpty()) {
                    client.removeLocationUpdates(locationCallback)
                }

            }
        }
    }
}
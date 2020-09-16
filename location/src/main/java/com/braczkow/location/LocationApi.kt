package com.braczkow.location

import android.Manifest
import android.content.Context
import android.content.pm.PackageManager
import android.os.Looper
import androidx.core.content.ContextCompat
import com.braczkow.lib.Logg
import com.google.android.gms.location.*

interface LocationApi {
    interface HighAccuracyRequest {
        fun start()
        fun stop()
    }

    data class Location(
        val lat: Float,
        val long: Float,
        val accuracy: Float
    )

    fun makeLocationRequest(): HighAccuracyRequest
}

class LocationApiImpl(
    private val context: Context
) : LocationApi {

    private val client: FusedLocationProviderClient
    private val locationCallback: LocationCallback = object : LocationCallback() {
        override fun onLocationResult(p0: LocationResult?) {

        }
    }

    private val requests = mutableSetOf<LocationRequest>()

    init {
        client = LocationServices.getFusedLocationProviderClient(context)
    }

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
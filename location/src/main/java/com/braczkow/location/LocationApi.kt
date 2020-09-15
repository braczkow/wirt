package com.braczkow.location

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices

interface LocationApi {
    class LocationRequest


}

class LocationApiImpl(
    private val context: Context
) : LocationApi {

    private val client: FusedLocationProviderClient

    init {
        client = LocationServices.getFusedLocationProviderClient(context)
    }
}
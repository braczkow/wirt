package com.braczkow.wirt.ui.location

import android.Manifest
import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.viewModelScope
import com.braczkow.location.LocationApi
import com.braczkow.openweatherr.WillItRainUseCase
import com.braczkow.wirt.ui.common.BaseViewModel
import com.braczkow.wirt.ui.common.PermissionResult
import com.braczkow.wirt.utils.isLocationPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.launch
import timber.log.Timber

interface PermissionRequest {
    val permissions: List<String>
}

sealed class LocationFragmentDirections {
    object MoveToSettings : LocationFragmentDirections()
    data class ProceedWithPermission(
        val permission: String,
        override val permissions: List<String> = listOf(permission)
    ) : LocationFragmentDirections(), PermissionRequest

}


@OptIn(ExperimentalCoroutinesApi::class)
class LocationFragmentViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val locationApi: LocationApi
) : BaseViewModel<LocationFragmentDirections>() {

    private val locationRequest = locationApi.makeLocationRequest()
    init {
        viewModelScope
            .launch {
                locationApi
                    .locations
                    .collect {
                        if (it != LocationApi.NONE_LOCATION) {
                            locationRequest.stop()
                            navigate(LocationFragmentDirections.MoveToSettings)
                        }
                    }
            }
    }

    fun onUseLocationButtonClicked() {
        navigate(LocationFragmentDirections.ProceedWithPermission(Manifest.permission.ACCESS_FINE_LOCATION))
    }

    fun onLocationNameChanged(s: String) {
        Timber.d("onLocationNameChanged")
    }

    override fun withPermissionsResult(permissionResults: List<PermissionResult>) {
        permissionResults.find {
            it.permission == Manifest.permission.ACCESS_FINE_LOCATION ||
                    it.permission == Manifest.permission.ACCESS_COARSE_LOCATION
        }?.let {
            if (it is PermissionResult.Granted) {
                locationRequest.start()
            }
        }
    }

    override fun onCleared() {
        super.onCleared()
        locationRequest.stop()
    }

    val suggestionsFlow: Flow<List<String>> =
        MutableStateFlow(
            listOf(
                "initial one",
                "initial two"
            )
        )

}
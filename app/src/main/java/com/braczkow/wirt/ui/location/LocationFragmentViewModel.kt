package com.braczkow.wirt.ui.location

import android.Manifest
import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import com.braczkow.openweatherr.WillItRainUseCase
import com.braczkow.wirt.ui.common.BaseViewModel
import com.braczkow.wirt.ui.common.PermissionResult
import com.braczkow.wirt.utils.isLocationPermission
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber

sealed class LocationFragmentDirections {
    object MoveToSettings : LocationFragmentDirections()
    data class ProceedWithPermission(val permission: String)
}



@OptIn(ExperimentalCoroutinesApi::class)
class LocationFragmentViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val willItRainUseCase: WillItRainUseCase
) : BaseViewModel<LocationFragmentDirections>() {

    override fun withPermissionResult(permissionResult: PermissionResult) {
        super.withPermissionResult(permissionResult)

        if (isLocationPermission(permissionResult.permission) && permissionResult is PermissionResult.Granted) {
//            determineLocation()
        }
    }

    fun onUseLocationButtonClicked() {

    }

    fun onLocationNameChanged(s: String) {
        Timber.d("onLocationNameChanged")
    }

    val suggestionsFlow: Flow<List<String>> =
        MutableStateFlow(
            listOf(
                "initial one",
                "initial two"
            )
        )

}
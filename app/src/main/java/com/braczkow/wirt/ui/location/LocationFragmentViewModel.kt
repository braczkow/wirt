package com.braczkow.wirt.ui.location

import android.content.Context
import androidx.hilt.lifecycle.ViewModelInject
import com.braczkow.wirt.openweather.WillItRainUseCase
import com.braczkow.wirt.ui.common.BaseViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import timber.log.Timber

sealed class LocationFragmentDirections {
    object MoveToSettings : LocationFragmentDirections()
    object Debug: LocationFragmentDirections()
}

@OptIn(ExperimentalCoroutinesApi::class)
class LocationFragmentViewModel @ViewModelInject constructor(
    @ApplicationContext private val context: Context,
    private val willItRainUseCase: WillItRainUseCase
) : BaseViewModel<LocationFragmentDirections>() {

    fun onUseLocationButtonClicked() {
        navigate(LocationFragmentDirections.MoveToSettings)
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
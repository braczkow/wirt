package com.braczkow.wirt.ui.common

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow

abstract class BaseViewModel<T> : ViewModel() {
    private val navigationHost = NavigationHost<T>()

    protected fun navigate(direction: T) {
        with(navigationHost) {
            viewModelScope.navigate(direction)
        }
    }

    val directions: Flow<T> = navigationHost.directions

    open fun withPermissionsResult(permissionResults: List<PermissionResult>) {

    }
}
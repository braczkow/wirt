package com.braczkow.wirt.ui.common

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

class NavigationHost<T>: Navigator<T>,
    Navigation<T> {
    private val routesChannel =
        Channel<T>(Channel.UNLIMITED)

    override val directions: Flow<T>
        get() = routesChannel.receiveAsFlow()

    override fun CoroutineScope.navigate(direction: T) {
        this.launch {
            routesChannel.send(direction)
        }
    }
}
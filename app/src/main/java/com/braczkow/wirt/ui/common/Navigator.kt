package com.braczkow.wirt.ui.common

import kotlinx.coroutines.CoroutineScope

interface Navigator<T> {
    fun CoroutineScope.navigate(direction: T)
}
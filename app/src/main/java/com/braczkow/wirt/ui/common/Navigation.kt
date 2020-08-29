package com.braczkow.wirt.ui.common

import kotlinx.coroutines.flow.Flow

interface Navigation<T> {
    val directions: Flow<T>
}
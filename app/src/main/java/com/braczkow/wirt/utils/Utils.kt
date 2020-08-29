package com.braczkow.wirt.utils

import androidx.fragment.app.Fragment
import androidx.lifecycle.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job

fun Fragment.startStopScope(): CoroutineScope {
    return this
        .viewLifecycleOwner
        .lifecycle
        .startStopScope()
}

fun Lifecycle.startStopScope(): CoroutineScope {
    val job = Job()
    this.addObserver(object : LifecycleObserver {
        @OnLifecycleEvent(Lifecycle.Event.ON_STOP)
        fun onStop() {
            job.cancel()
        }
    })

    return CoroutineScope(Dispatchers.Main + job)
}

fun <T> LiveData<T>.observe(owner: LifecycleOwner, l: (item: T)->Unit) {
    this.observe(owner, object : Observer<T>{
        override fun onChanged(t: T) {
            l(t)
        }
    })
}
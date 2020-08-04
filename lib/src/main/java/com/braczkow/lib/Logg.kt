package com.braczkow.lib

object Logg {
    private var printer: (String) -> Unit = {
    }

    fun init(p: (String) -> Unit) {
        printer = p
    }

    fun d(message: String) {
        printer(message)
    }
}
package com.braczkow.lib

import java.time.*


object Timee {
    var clock = Clock.systemDefaultZone()

    fun localTime() = LocalTime.now(clock)
    fun localDate() = LocalDate.now(clock)
    fun localDateTime() = LocalDateTime.now(clock)

    fun fix(now: Long) {
        clock = Clock.fixed(Instant.ofEpochSecond(now), ZoneId.systemDefault())
    }

    fun unfix() {
        clock = Clock.systemDefaultZone()
    }
}
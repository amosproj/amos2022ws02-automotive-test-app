package com.amos.infotaimos.model

import java.time.Duration
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class TimerItem(
    var actionId: String="",
    var start: LocalDateTime,
    var end: LocalDateTime?,
    var description: String="") {

    val time: String
        get() = "${ timeFormatter.format(start) } ${end?.let { " - ${timeFormatter.format(end)}" } ?: ""}"
    private val duration: String
        get() = end?.let { Duration.between(start, end).let { String.format("%02d:%02d:%02d", it.toHours(), it.toMinutesPart(), it.toSecondsPart()) } } ?: "unfinished"

    val actionIdAndDuration: String
        get() = "$actionId - $duration"

    companion object {
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    }
}
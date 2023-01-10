package com.amos.infotaimos.model
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class TestDriveItem(
    var actionId: String="",
    var startDate: LocalDate,
    var startTime: LocalDateTime) {

    val time: String
        get() = timeFormatter.format(startTime)

    companion object {
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm")
    }
}
package com.amos.infotaimos.model
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

data class MediaItem(
    var actionId: String="",
    var description: String="",
    var eventTime: LocalDateTime
    ) {

    val time: String
        get() = timeFormatter.format(eventTime)

    companion object {
        val timeFormatter: DateTimeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
    }
}

package com.amos.infotaimos.model

import java.time.LocalDateTime

/**
 * Enum for types of timer events
 */
enum class TimerEventType{
    START,
    END
}

/**
 * Data class for timer events received via intents
 * @property type Type indicating start/end events
 * @property actionID String with identifier of timer action
 * @property date Date of timer event
 * @property desc String with optional description
 */
data class TimerEvent(
    val type: TimerEventType,
    val actionID: String,
    val date: LocalDateTime,
    val desc: String
)
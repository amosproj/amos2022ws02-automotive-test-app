package com.amos.infotaimos.model

import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime

internal class TimerItemTest {

    @Test
    fun testTimerItem() {
        val start = LocalDateTime.now()
        val end = LocalDateTime.now().plusHours(1).plusMinutes(30).plusSeconds(27)
        val timerItem = TimerItem("1", start, end, "description")
        assertEquals("${TimerItem.timeFormatter.format(start)} - ${TimerItem.timeFormatter.format(end)}", timerItem.time)
        assertEquals("1 - 01:30:27", timerItem.actionIdAndDuration)
        assertEquals("description", timerItem.description)
    }
}

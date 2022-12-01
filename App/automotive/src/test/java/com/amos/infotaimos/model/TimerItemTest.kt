package com.amos.infotaimos.model

import org.junit.Assert.assertEquals
import org.junit.Test

internal class TimerItemTest {

    @Test
    fun testTimerItem() {
        val timerItem = TimerItem("1", "12:00:00", "13:30:27", "description")
        assertEquals("12:00:00 - 13:30:27", timerItem.time)
        assertEquals("1 - 01:30:27", timerItem.actionIdAndDuration)
        assertEquals("description", timerItem.description)
    }
}

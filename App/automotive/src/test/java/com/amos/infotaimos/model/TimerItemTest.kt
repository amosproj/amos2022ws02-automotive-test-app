package com.amos.infotaimos.model

import org.junit.Assert.assertEquals
import org.junit.Test


internal class TimerItemTest {

    @Test
    fun testTime() {
        val timerItem = TimerItem("1", "12:00:00", "13:30:27", "description")
        assertEquals("12:00:00 - 13:30:27", timerItem.time)
        assertEquals("01:30:27", timerItem.duration)
        assertEquals("description", timerItem.description)
        assertEquals("1", timerItem.actionId)
    }
}
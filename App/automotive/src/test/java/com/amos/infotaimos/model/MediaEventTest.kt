package com.amos.infotaimos.model
import org.junit.Assert.assertEquals
import org.junit.Test
import java.time.LocalDateTime


class MediaEventTest {

    @Test
    fun testTimerItem() {
        val actionID = "0123456"
        val description = "Play button"
        val eventTime = LocalDateTime.now()
        val mediaItem = MediaItem(actionID, description, eventTime)

        assertEquals(actionID, mediaItem.actionId)
        assertEquals(description, mediaItem.description)
        assertEquals(eventTime, mediaItem.eventTime)
    }
}

package com.amos.infotaimos.model

import org.junit.Assert
import org.junit.Test
import java.time.LocalDate
import java.time.LocalDateTime

internal class TestDriveItemTest {
    @Test
    fun testTestDriveItem() {
        val startDate = LocalDate.now()
        val startTime = LocalDateTime.now()
        val testDriveItem = TestDriveItem("12345678", startDate, startTime)
        Assert.assertEquals(TestDriveItem.timeFormatter.format(startTime), testDriveItem.time)
        Assert.assertEquals(LocalDate.now().toString(), testDriveItem.startDate.toString())
        Assert.assertEquals("12345678", testDriveItem.actionId)
    }
}
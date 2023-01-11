package com.amos.infotaimos

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.amos.infotaimos.view.TestDrivePage
import org.junit.Test

class TestDriveTest {
    @Test
    fun testLayoutCompleteness() {
        launchFragmentInContainer<TestDrivePage>()
        Espresso.onView(ViewMatchers.withId(R.id.test_drive_start_tile))
            .perform(click())
    }

    @Test
    fun testRecordingTapText(){
        launchFragmentInContainer<TestDrivePage>()
        Espresso.onView(ViewMatchers.withId(R.id.tile_test_drive_start_tap_text))
            .check(matches(withText("tap to start")))
        Espresso.onView(ViewMatchers.withId(R.id.test_drive_start_tile))
            .perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.tile_test_drive_start_tap_text))
            .check(matches(withText("tap to stop")))
        Espresso.onView(ViewMatchers.withId(R.id.test_drive_start_tile))
            .perform(click())
        Espresso.onView(ViewMatchers.withId(R.id.tile_test_drive_start_tap_text))
            .check(matches(withText("tap to start")))
    }
}
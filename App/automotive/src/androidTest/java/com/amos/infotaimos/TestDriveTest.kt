package com.amos.infotaimos

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.*
import com.amos.infotaimos.view.TestDrivePage

import org.junit.Test

class TestDriveTest {
    @Test
    fun testLayoutCompleteness() {
        launchFragmentInContainer<TestDrivePage>()
        Espresso.onView(withId(R.id.test_drive_start_tile))
            .perform(click())
        Espresso.onView(withId(R.id.test_drive_recycler_view))
            .check(matches(isDisplayed()))
    }

    @Test
    fun testRecordingTapText() {
        launchFragmentInContainer<TestDrivePage>()
        Espresso.onView(withId(R.id.tile_test_drive_start_tap_text))
            .check(matches(withText("tap to start")))
        Espresso.onView(withId(R.id.test_drive_start_tile))
            .perform(click())
        Espresso.onView(withId(R.id.tile_test_drive_start_tap_text))
            .check(matches(withText("tap to stop")))
        Espresso.onView(withId(R.id.test_drive_start_tile))
            .perform(click())
        Espresso.onView(withId(R.id.tile_test_drive_start_tap_text))
            .check(matches(withText("tap to start")))
    }

    @Test
    fun testDeleteTestDrive(){
        launchFragmentInContainer<TestDrivePage>()
        Espresso.onView(withId(R.id.test_drive_start_tile))
            .perform(click())
        Espresso.onView(withId(R.id.test_drive_start_tile))
            .perform(click())
        Espresso.onView(withId(R.id.test_drive_recycler_view)).check(matches(hasDescendant(isDisplayed())))
        Espresso.onView(withId(R.id.test_drive_recycler_view)).perform(RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(0, swipeRight()))

    }
}
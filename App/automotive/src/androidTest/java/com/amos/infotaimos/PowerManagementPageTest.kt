package com.amos.infotaimos

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import com.amos.infotaimos.view.PowerManagementPage

import org.junit.Test

class PowerManagementPageTest {

    @Test
    fun testLayoutCompleteness() {
        launchFragmentInContainer<PowerManagementPage>()
        onView(ViewMatchers.withId(R.id.fragment_power_button_display_off)).perform(click())
        onView(ViewMatchers.withId(R.id.fragment_power_button_stand_by)).perform(click())
    }
}
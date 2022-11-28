package com.amos.infotaimos

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import com.amos.infotaimos.view.PowerManagementPage
import com.amos.infotaimos.view.WheelPage

import org.junit.Test

class PowerManagementPageTest {

    @Test
    fun testLayoutCompleteness() {
        launchFragmentInContainer<PowerManagementPage>()
        onView(ViewMatchers.withId(R.id.fragment_power_button_display_off)).perform(click())
        onView(ViewMatchers.withId(R.id.fragment_power_button_stand_by)).perform(click())
        onView(ViewMatchers.withId(R.id.fragment_power_button_delay)).check(matches(isDisplayed()))
        onView(ViewMatchers.withId(R.id.delay_spinner_power_management_page)).check(matches(isDisplayed())).perform(click())
    }
}
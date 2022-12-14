package com.amos.infotaimos

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.amos.infotaimos.view.VehiclePropertiesPage
import org.junit.Test

class VehiclePropertiesPageTest {

    @Test
    fun testLayoutCompleteness() {
        launchFragmentInContainer<VehiclePropertiesPage>()
        onView(ViewMatchers.withId(R.id.tile_vehicle_theme_status_icon)).perform(click())
        onView(ViewMatchers.withId(R.id.tile_vehicle_range_icon)).perform(click())
        onView(ViewMatchers.withId(R.id.battery_low_message_button)).perform(click())
        onView(ViewMatchers.withId(R.id.battery_level_edittext)).perform(typeText("Test"))
        onView(ViewMatchers.withId(R.id.delay_spinner_vehicle_properties_page)).check(
            ViewAssertions.matches(
                ViewMatchers.isDisplayed()
            )
        ).perform(click())
    }
}

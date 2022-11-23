package com.amos.infotaimos

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.matcher.ViewMatchers
import com.amos.infotaimos.view.VehiclePropertiesPage
import org.junit.Test

class VehiclePropertiesPageTest {

    @Test
    fun testLayoutCompleteness() {
        launchFragmentInContainer<VehiclePropertiesPage>()
        onView(ViewMatchers.withId(R.id.tile_vehicle_theme_status_icon)).perform(click())
        onView(ViewMatchers.withId(R.id.tile_vehicle_range_icon)).perform(click())
    }
}
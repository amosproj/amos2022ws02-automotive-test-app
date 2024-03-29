package com.amos.infotaimos

import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.amos.infotaimos.model.VinService
import com.amos.infotaimos.view.VehiclePropertiesPage
import org.junit.Assert.assertEquals
import org.junit.Test

class VehiclePropertiesPageTest {

    @Test
    fun testLayoutCompleteness() {
        launchFragmentInContainer<VehiclePropertiesPage>()
        onView(ViewMatchers.withId(R.id.tile_vin_icon)).perform(scrollTo())
        onView(ViewMatchers.withId(R.id.tile_vin_icon)).perform(click())
        onView(ViewMatchers.withId(R.id.battery_low_message_button)).perform(scrollTo())
        onView(ViewMatchers.withId(R.id.battery_low_message_button)).perform(click())
        onView(ViewMatchers.withId(R.id.battery_level_edittext)).perform(typeText("Test"))
        onView(ViewMatchers.withId(R.id.delay_spinner_vehicle_properties_page)).check(
            matches(
                ViewMatchers.isDisplayed()
            )
        ).perform(click())
    }

    @Test
    fun testVinEditable(){
        launchFragmentInContainer<VehiclePropertiesPage>()
        onView(ViewMatchers.withId(R.id.tile_vin_editable_text)).perform(ViewActions.scrollTo())
        onView(ViewMatchers.withId(R.id.tile_vin_editable_text)).perform(typeText("Test"))
        onView(ViewMatchers.withId(R.id.tile_vin_edit_button)).perform(click())
        onView(ViewMatchers.withId(R.id.tile_vin_editable_text)).check(matches(withText("")))
        onView(ViewMatchers.withId(R.id.tile_vin_text)).check(matches(withText("Test")))
    }

    @Test
    fun saveAndLoadVin(){
        val vin = "12345-Test-54321"
        val context : Context = ApplicationProvider.getApplicationContext()

        VinService.saveData(context, vin)

        assertEquals(vin, VinService.loadData(context))
    }
}

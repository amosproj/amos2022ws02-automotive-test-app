package com.amos.infotaimos

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LandingPageTest {
    @Test
    fun testNavigationToNavigationScreen() {
        val navController = prepareScreen()
        scrollAndClickOn(R.id.cardViewNavigation)
        assertEquals(navController.currentDestination?.id, R.id.navigationPage)
    }

    @Test
    fun testNavigationToSteeringWheelScreen() {
        val navController = prepareScreen()
        scrollAndClickOn(R.id.cardViewWheel)
        assertEquals(navController.currentDestination?.id, R.id.wheelPage)
    }

    @Test
    fun testNavigationToPowerManagementScreen() {
        val navController = prepareScreen()
        scrollAndClickOn(R.id.cardViewPowerManagement)
        assertEquals(navController.currentDestination?.id, R.id.powerManagementPage)
    }

    @Test
    fun testNavigationToVehiclePropertiesScreen() {
        val navController = prepareScreen()
        scrollAndClickOn(R.id.cardViewVehicleProperties)
        assertEquals(navController.currentDestination?.id, R.id.vehiclePropertiesPage)
    }

    private fun scrollAndClickOn(id: Int) {
        onView(ViewMatchers.withId(id)).perform(ViewActions.scrollTo())
        onView(ViewMatchers.withId(id)).perform(ViewActions.click())
    }

    private fun prepareScreen(): TestNavHostController {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val landingScenario = launchFragmentInContainer<LandingPage>()

        landingScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        return navController
    }
}

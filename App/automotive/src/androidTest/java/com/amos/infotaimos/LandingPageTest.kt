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
        onView(ViewMatchers.withId(R.id.cardViewNavigation)).perform(ViewActions.scrollTo())
        onView(ViewMatchers.withId(R.id.cardViewNavigation)).perform(ViewActions.click())
        assertEquals(navController.currentDestination?.id, R.id.navigationPage)
    }

    @Test
    fun testNavigationToSteeringWheelScreen() {
        val navController = prepareScreen()
        onView(ViewMatchers.withId(R.id.cardViewWheel)).perform(ViewActions.scrollTo())
        onView(ViewMatchers.withId(R.id.cardViewWheel)).perform(ViewActions.click())
        assertEquals(navController.currentDestination?.id, R.id.wheelPage)
    }

    @Test
    fun testNavigationToPowerManagementScreen() {
        val navController = prepareScreen()
        onView(ViewMatchers.withId(R.id.cardViewPowerManagement)).perform(ViewActions.scrollTo())
        onView(ViewMatchers.withId(R.id.cardViewPowerManagement)).perform(ViewActions.click())

        assertEquals(navController.currentDestination?.id, R.id.powerManagementPage)
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

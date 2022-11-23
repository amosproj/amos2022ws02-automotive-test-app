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
<<<<<<< HEAD

    // https://developer.android.com/guide/navigation/navigation-testing
    @Test
    fun testNavigationToNavigationPage() {
        // Create a TestNavHostController
=======
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
>>>>>>> development
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        // Create a graphical FragmentScenario for the TitleScreen
        val landingPageScenario = launchFragmentInContainer<LandingPage>()

        landingPageScenario.onFragment { fragment ->
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.nav_graph)

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController)
        }
<<<<<<< HEAD

        // Verify that performing a click changes the NavController’s state
        onView(ViewMatchers.withId(R.id.cardViewNavigation)).perform(ViewActions.click())
        assertEquals(navController.currentDestination?.id, R.id.navigationPage)
=======
        return navController
>>>>>>> development
    }

    @Test
    fun testNavigationToNavigationPageIcon() {
        // Create a TestNavHostController
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        // Create a graphical FragmentScenario for the TitleScreen
        val landingPageScenario = launchFragmentInContainer<LandingPage>()

        landingPageScenario.onFragment { fragment ->
            // Set the graph on the TestNavHostController
            navController.setGraph(R.navigation.nav_graph)

            // Make the NavController available via the findNavController() APIs
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        // Verify that performing a click changes the NavController’s state
        onView(ViewMatchers.withId(R.id.)).perform(ViewActions.click())
        assertEquals(navController.currentDestination?.id, R.id.navigationPage)
    }

    @Test
    fun testNavigationToWheelPage() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        val landingPageScenario = launchFragmentInContainer<LandingPage>()

        landingPageScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(ViewMatchers.withId(R.id.cardViewWheel)).perform(ViewActions.click())
        assertEquals(navController.currentDestination?.id, R.id.wheelPage)
    }

    @Test
    fun testNavigationToMediaPage() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        val landingPageScenario = launchFragmentInContainer<LandingPage>()

        landingPageScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(ViewMatchers.withId(R.id.cardViewMedia)).perform(ViewActions.click())
        assertEquals(navController.currentDestination?.id, R.id.mediaPage)
    }

    @Test
    fun testNavigationToVehiclePropertiesPage() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        val landingPageScenario = launchFragmentInContainer<LandingPage>()

        landingPageScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(ViewMatchers.withId(R.id.cardViewVehicleProperties)).perform(ViewActions.click())
        assertEquals(navController.currentDestination?.id, R.id.vehicleProperties)
    }

    @Test
    fun testNavigationToPowerManagement() {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext())

        val landingPageScenario = launchFragmentInContainer<LandingPage>()

        landingPageScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)
            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(ViewMatchers.withId(R.id.cardViewPowerManagement)).perform(ViewActions.click())
        assertEquals(navController.currentDestination?.id, R.id.powerManagement)
    }
}

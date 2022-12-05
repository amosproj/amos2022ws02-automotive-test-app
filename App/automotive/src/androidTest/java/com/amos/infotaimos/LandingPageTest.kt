package com.amos.infotaimos

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.amos.infotaimos.view.LandingPage
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class LandingPageTest {

    /**
     * Test navigation to the navigation page via the nav_graph
     */
    @Test
    fun testNavigationToNavigationScreen() {
        val navController = prepareScreen()
        scrollAndClickOn(R.id.cardViewNavigation)
        assertEquals(navController.currentDestination?.id, R.id.navigationPage)
    }

    /**
     * Test navigation to the steering wheel page via the nav_graph
     */
    @Test
    fun testNavigationToSteeringWheelScreen() {
        val navController = prepareScreen()
        scrollAndClickOn(R.id.cardViewWheel)
        assertEquals(navController.currentDestination?.id, R.id.wheelPage)
    }

    /**
     * Test navigation to the power management page via the nav_graph
     */
    @Test
    fun testNavigationToPowerManagementScreen() {
        val navController = prepareScreen()
        scrollAndClickOn(R.id.cardViewPowerManagement)
        assertEquals(navController.currentDestination?.id, R.id.powerManagementPage)
    }

    /**
     * Test navigation to the vehicle properties page via the nav_graph
     */
    @Test
    fun testNavigationToVehiclePropertiesScreen() {
        val navController = prepareScreen()
        scrollAndClickOn(R.id.cardViewVehicleProperties)
        assertEquals(navController.currentDestination?.id, R.id.vehiclePropertiesPage)
    }

    /**
     * Test navigation to the media page via the nav_graph
     */
    @Test
    fun testNavigationToMediaScreen() {
        val navController = prepareScreen()
        scrollAndClickOn(R.id.cardViewMedia)
        assertEquals(navController.currentDestination?.id, R.id.mediaPage)
    }

    /**
     * Test navigation to the media page via the nav_graph
     */
    @Test
    fun testNavigationToAppSettings() {
        val navController = prepareScreen()
        scrollAndClickOn(R.id.cardViewAppSettings)
        assertEquals(navController.currentDestination?.id, R.id.appSettings)
    }

    /**
     * Scrolls and clicks on the element specified by [id]
     * @param id ID of the element to be clicked
     */
    private fun scrollAndClickOn(id: Int) {
        onView(ViewMatchers.withId(id)).perform(ViewActions.scrollTo())
        onView(ViewMatchers.withId(id)).perform(ViewActions.click())
    }

    /**
     * Construct the navigation controller to be tested.
     * @return navController that controls the context switching as defined in the nav_graph
     */
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

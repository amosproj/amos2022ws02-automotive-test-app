package com.amos.infotaimos

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.amos.infotaimos.model.LandingPageTileType
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
        scrollAndClickOn(LandingPageTileType.NAVIGATION.getID())
        assertEquals(navController.currentDestination?.id, R.id.navigationPage)
    }

    /**
     * Test navigation to the steering wheel page via the nav_graph
     */
    @Test
    fun testNavigationToSteeringWheelScreen() {
        val navController = prepareScreen()
        scrollAndClickOn(LandingPageTileType.STEERING_WHEEL.getID())
        assertEquals(navController.currentDestination?.id, R.id.wheelPage)
    }

    /**
     * Test navigation to the power management page via the nav_graph
     */
    @Test
    fun testNavigationToPowerManagementScreen() {
        val navController = prepareScreen()
        scrollAndClickOn(LandingPageTileType.POWER_MANAGEMENT.getID())
        assertEquals(navController.currentDestination?.id, R.id.powerManagementPage)
    }

    /**
     * Test navigation to the vehicle properties page via the nav_graph
     */
    @Test
    fun testNavigationToVehiclePropertiesScreen() {
        val navController = prepareScreen()
        scrollAndClickOn(LandingPageTileType.VEHICLE_PROPERTIES.getID())
        assertEquals(navController.currentDestination?.id, R.id.vehiclePropertiesPage)
    }

    /**
     * Test navigation to the media page via the nav_graph
     */
    @Test
    fun testNavigationToMediaScreen() {
        val navController = prepareScreen()
        scrollAndClickOn(LandingPageTileType.MEDIA_PLAY.getID())
        assertEquals(navController.currentDestination?.id, R.id.mediaPage)
    }

    /**
     * Test navigation to the app setting page via the nav_graph
     */
    @Test
    fun testNavigationToAppSettings() {
        val navController = prepareScreen()
        scrollAndClickOn(LandingPageTileType.APP_SETTINGS.getID())
        assertEquals(navController.currentDestination?.id, R.id.appSettings)
    }

    /**
     * Test navigation to the timer page via the nav_graph
     */
    @Test
    fun testNavigationToTimerPage() {
        val navController = prepareScreen()
        scrollAndClickOn(LandingPageTileType.TIMER.getID())
        assertEquals(navController.currentDestination?.id, R.id.timerPage)
    }

    /**
     * Test navigation to the test drive page via the nav_graph
     */
    @Test
    fun testNavigationToTestDrivePage() {
        val navController = prepareScreen()
        scrollAndClickOn(LandingPageTileType.TEST_DRIVE.getID())
        assertEquals(navController.currentDestination?.id, R.id.testDrivePage)
    }

    /**
     * Scrolls and clicks on the element specified by [id]
     * @param id ID of the element to be clicked
     */
    private fun scrollAndClickOn(id: Int) {
        onView(ViewMatchers.withId(R.id.fragment_landing_page_tile_recycler))
            .perform(RecyclerViewActions.scrollTo<RecyclerView.ViewHolder>(ViewMatchers.withId(id)))
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

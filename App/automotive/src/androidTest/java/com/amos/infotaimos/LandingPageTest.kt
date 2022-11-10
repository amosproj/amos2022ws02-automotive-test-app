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
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val landingScenario = launchFragmentInContainer<LandingPage>()

        landingScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }

        onView(ViewMatchers.withId(R.id.Navigation)).perform(ViewActions.click())
        assertEquals(navController.currentDestination?.id, R.id.navigationPage)
    }
}

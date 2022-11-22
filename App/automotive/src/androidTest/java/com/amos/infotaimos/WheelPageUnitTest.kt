package com.amos.infotaimos

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.navigation.Navigation
import androidx.navigation.testing.TestNavHostController
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.amos.infotaimos.view.WheelPage
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class WheelPageUnitTest {

    @Test
    fun testVoiceButtonClickable() {
        prepareScreen()
        clickable(R.id.wheel_button_voice_control)
    }

    @Test
    fun testPhoneButtonClickable() {
        prepareScreen()
        clickable(R.id.wheel_button_phone)
    }

    @Test
    fun testPlayPauseButtonClickable() {
        prepareScreen()
        clickable(R.id.wheel_button_play_pause)
    }

    @Test
    fun testSeekForwardButtonClickable() {
        prepareScreen()
        clickable(R.id.wheel_button_seek_forward)
    }

    @Test
    fun testSkipForwardButtonClickable() {
        prepareScreen()
        clickable(R.id.wheel_button_skip_forward)
    }
    
    private fun clickable(id: Int) {
        onView(ViewMatchers.withId(id)).perform(ViewActions.click()).check(matches(ViewMatchers.isClickable()))
    }

    private fun prepareScreen(): TestNavHostController {
        val navController = TestNavHostController(
            ApplicationProvider.getApplicationContext()
        )

        val landingScenario = launchFragmentInContainer<WheelPage>()

        landingScenario.onFragment { fragment ->
            navController.setGraph(R.navigation.nav_graph)

            Navigation.setViewNavController(fragment.requireView(), navController)
        }
        return navController
    }
}
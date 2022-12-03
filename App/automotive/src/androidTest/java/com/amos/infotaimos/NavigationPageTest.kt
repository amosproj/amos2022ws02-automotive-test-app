package com.amos.infotaimos

import android.car.Car
import android.car.CarAppFocusManager
import android.content.Context
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.core.app.ApplicationProvider
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isClickable
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.amos.infotaimos.model.CarInstanceManager
import com.amos.infotaimos.model.NavigationService
import com.amos.infotaimos.view.NavigationPage
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.Matchers.`is`
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NavigationPageTest {

    @Test
    fun testStartNavigationClickable() {
        launchFragmentInContainer<NavigationPage>()
        onView(withId(R.id.start_navigation_button)).perform(click()).check(matches(isClickable()))
    }
    @Test
    fun testStopNavigationClickable() {
        launchFragmentInContainer<NavigationPage>()
        onView(withId(R.id.stop_navigation_button)).perform(click()).check(matches(isClickable()))
    }

    @Test
    fun startStopNavigation() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val car = CarInstanceManager.getCarInstance(context)
        val carAppFocusManager = car.getCarManager(Car.APP_FOCUS_SERVICE) as CarAppFocusManager
        val navCallback = NavigationService.navCallback

        launchFragmentInContainer<NavigationPage>()

        // test start with delay 0s
        onView(withId(R.id.start_navigation_button)).perform(click())
        assertTrue(
            carAppFocusManager.isOwningFocus(
                navCallback,
                CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION
            )
        )

        // test stop with delay 0s
        onView(withId(R.id.stop_navigation_button)).perform(click())
        assertFalse(
            carAppFocusManager.isOwningFocus(
                navCallback,
                CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION
            )
        )
    }

    @Test
    fun startStopNavigation30sDelay() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val car = CarInstanceManager.getCarInstance(context)
        val carAppFocusManager = car.getCarManager(Car.APP_FOCUS_SERVICE) as CarAppFocusManager
        val navCallback = NavigationService.navCallback

        launchFragmentInContainer<NavigationPage>()

        // test start with delay 30s
        onView(withId(R.id.delay_spinner)).perform(click())
        onData(
            allOf(
                `is`(instanceOf(String::class.java)),
                `is`("30s")
            )
        ).perform(click())
        onView(withId(R.id.start_navigation_button)).perform(click())

        assertFalse(
            carAppFocusManager.isOwningFocus(
                navCallback,
                CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION
            )
        )
        Thread.sleep(15000)
        assertFalse(
            carAppFocusManager.isOwningFocus(
                navCallback,
                CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION
            )
        )
        Thread.sleep(15000)
        assertTrue(
            carAppFocusManager.isOwningFocus(
                navCallback,
                CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION
            )
        )

        // test stop with delay 30s
        onView(withId(R.id.stop_navigation_button)).perform(click())
        assertTrue(
            carAppFocusManager.isOwningFocus(
                navCallback,
                CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION
            )
        )
        Thread.sleep(15000)
        assertTrue(
            carAppFocusManager.isOwningFocus(
                navCallback,
                CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION
            )
        )
        Thread.sleep(15000)
        assertFalse(
            carAppFocusManager.isOwningFocus(
                navCallback,
                CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION
            )
        )
    }

    @Test
    fun startStopNavigation1minDelay() {
        val car = CarInstanceManager.getCarInstance(ApplicationProvider.getApplicationContext())
        val carAppFocusManager = car.getCarManager(Car.APP_FOCUS_SERVICE) as CarAppFocusManager
        val navCallback = NavigationService.navCallback

        launchFragmentInContainer<NavigationPage>()

        // test start with delay 1min
        onView(withId(R.id.delay_spinner)).perform(click())
        onData(
            allOf(
                `is`(instanceOf(String::class.java)),
                `is`("1min")
            )
        ).perform(click())
        onView(withId(R.id.start_navigation_button)).perform(click())
        assertFalse(
            carAppFocusManager.isOwningFocus(
                navCallback,
                CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION
            )
        )
        Thread.sleep(30000)

        assertFalse(
            carAppFocusManager.isOwningFocus(
                navCallback,
                CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION
            )
        )
        Thread.sleep(30000)

        assertTrue(
            carAppFocusManager.isOwningFocus(
                navCallback,
                CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION
            )
        )

        // test stop with delay 1min
        onView(withId(R.id.stop_navigation_button)).perform(click())
        assertTrue(
            carAppFocusManager.isOwningFocus(
                navCallback,
                CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION
            )
        )
        Thread.sleep(30000)
        assertTrue(
            carAppFocusManager.isOwningFocus(
                navCallback,
                CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION
            )
        )
        Thread.sleep(30000)
        assertFalse(
            carAppFocusManager.isOwningFocus(
                navCallback,
                CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION
            )
        )
    }
}

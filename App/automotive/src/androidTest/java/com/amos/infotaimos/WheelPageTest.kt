package com.amos.infotaimos

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.amos.infotaimos.view.WheelPage

import org.junit.Test

class WheelPageTest {

    @Test
    fun testLayoutFunctionality() {
        launchFragmentInContainer<WheelPage>()
        onView(withId(R.id.toggleButton)).check(matches(withText(R.string.description)))
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
        onView(withId(R.id.wheel_button_phone)).perform(click()).check(matches(isDisplayed()))
        onView(withId(R.id.wheel_button_skip_forward)).perform(click()).check(matches(isDisplayed()))
        onView(withId(R.id.wheel_button_voice_control)).perform(click()).check(matches(isDisplayed()))
        onView(withId(R.id.wheel_button_play_pause)).perform(click()).check(matches(isDisplayed()))
    }

    @Test
    fun testLayoutDescription(){
        launchFragmentInContainer<WheelPage>()
        onView(withId(R.id.toggleButton)).check(matches(withText(R.string.description)))
        onView(withId(R.id.toggleButton)).perform(click()).check(matches(withText(R.string.functionality)))
        onView(withId(R.id.imageView)).check(matches(isDisplayed()))
        onView(withId(R.id.wheel_button_phone)).perform(click()).check(matches(isDisplayed()))
        onView(withId(R.id.wheel_button_skip_forward)).perform(click()).check(matches(isDisplayed()))
        onView(withId(R.id.wheel_button_voice_control)).perform(click()).check(matches(isDisplayed()))
        onView(withId(R.id.wheel_button_play_pause)).perform(click()).check(matches(isDisplayed()))
        onView(withId(R.id.toggleButton)).perform(click()).check(matches(withText(R.string.description)))
    }
}
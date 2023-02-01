package com.amos.infotaimos

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import com.amos.infotaimos.view.SpeechAssistantPage
import org.hamcrest.Matchers
import org.hamcrest.Matchers.not
import org.junit.Test



class SpeechAssistantTest {

    /**
     * Test all gui components of the speech assistant page
     */
    @Test
    fun testLayoutCompleteness() {
        launchFragmentInContainer<SpeechAssistantPage>()
        onView(withId(R.id.fragment_speech_announcement_button)).perform(click())
        onView(withId(R.id.fragment_ptt_button)).perform(click())
        onView(withId(R.id.fragment_ttt_button)).perform(click())
        onView(withId(R.id.delay_spinner_speech_assistant_page)).check(matches(isDisplayed())).perform(click())
    }

    @Test
    fun testPTT30sDelay(){
        launchFragmentInContainer<SpeechAssistantPage>()
        onView(withId(R.id.delay_spinner_speech_assistant_page)).perform(click())
        Espresso.onData(
            Matchers.allOf(
                Matchers.`is`(Matchers.instanceOf(String::class.java)),
                Matchers.`is`("30s")
            )
        ).perform(click())
        onView(withId(R.id.fragment_ptt_button)).perform(click())
        onView(withId(R.id.fragment_speech_announcement_textView)).check(matches(not(isDisplayed())))
        Thread.sleep(15000)
        onView(withId(R.id.fragment_speech_announcement_textView)).check(matches(not(isDisplayed())))
        Thread.sleep(15000)
        onView(withId(R.id.fragment_speech_announcement_textView)).check(matches(isDisplayed()))
    }

    @Test
    fun testTTT1minDelay(){
        launchFragmentInContainer<SpeechAssistantPage>()
        onView(withId(R.id.delay_spinner_speech_assistant_page)).perform(click())
        Espresso.onData(
            Matchers.allOf(
                Matchers.`is`(Matchers.instanceOf(String::class.java)),
                Matchers.`is`("1min")
            )
        ).perform(click())
        onView(withId(R.id.fragment_ttt_button)).perform(click())
        onView(withId(R.id.fragment_speech_announcement_textView)).check(matches(not(isDisplayed())))
        Thread.sleep(30000)
        onView(withId(R.id.fragment_speech_announcement_textView)).check(matches(not(isDisplayed())))
        Thread.sleep(30000)
        onView(withId(R.id.fragment_speech_announcement_textView)).check(matches(isDisplayed()))
    }
}
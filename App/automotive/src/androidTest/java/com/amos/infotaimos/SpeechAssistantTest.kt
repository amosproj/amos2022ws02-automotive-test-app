package com.amos.infotaimos

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.amos.infotaimos.view.SpeechAssistantPage
import org.junit.Test

class SpeechAssistantTest {
    @Test
    fun testLayoutCompleteness() {
        launchFragmentInContainer<SpeechAssistantPage>()
        onView(ViewMatchers.withId(R.id.fragment_speech_announcement_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.fragment_ptt_button)).perform(ViewActions.click())
        onView(ViewMatchers.withId(R.id.fragment_ttt_button)).perform(ViewActions.click())
    }
}
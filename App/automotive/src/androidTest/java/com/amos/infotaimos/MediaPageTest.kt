package com.amos.infotaimos

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.amos.infotaimos.view.MediaPage
import com.amos.infotaimos.view.TestDrivePage
import org.junit.Test

class MediaPageTest {

    @Test
    fun testLayoutCompleteness() {
        launchFragmentInContainer<MediaPage>()
        Espresso.onView(ViewMatchers.withId(R.id.toggleMediaBrowseServiceButton))
            .perform(ViewActions.click())
    }

    @Test
    fun testToggleButtonTexts() {
        launchFragmentInContainer<MediaPage>()

        Espresso.onView(ViewMatchers.withId(R.id.mediabrowser_action_text))
            .check(ViewAssertions.matches(ViewMatchers.withText("PRESS TO START MEDIA BROWSER SERVICE")))
        Espresso.onView(ViewMatchers.withId(R.id.mediabrowser_status_text))
            .check(ViewAssertions.matches(ViewMatchers.withText("Media Browser deactivated")))

    }

}
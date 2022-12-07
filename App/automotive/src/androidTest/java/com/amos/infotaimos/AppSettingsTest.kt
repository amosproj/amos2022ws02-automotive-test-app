package com.amos.infotaimos

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.matcher.ViewMatchers
import com.amos.infotaimos.view.AppSettings
import org.junit.Test

class AppSettingsTest {

    @Test
    fun testLayoutCompleteness() {
        launchFragmentInContainer<AppSettings>()
        Espresso.onView(ViewMatchers.withId(R.id.app_theme_mode_tile))
            .perform(ViewActions.click())
    }
}
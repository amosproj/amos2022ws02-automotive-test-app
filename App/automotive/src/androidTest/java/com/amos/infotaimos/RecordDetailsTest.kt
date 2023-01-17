package com.amos.infotaimos

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import com.amos.infotaimos.view.RecordDetailsPage
import org.junit.Test

class RecordDetailsTest {
    @Test
    fun testLayoutCompleteness() {
        launchFragmentInContainer<RecordDetailsPage>()
        Espresso.onView(ViewMatchers.withId(R.id.detail_records_recycler_view))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}
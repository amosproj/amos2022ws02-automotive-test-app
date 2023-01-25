package com.amos.infotaimos

import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.swipeLeft
import androidx.test.espresso.action.ViewActions.swipeRight
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Test
import com.amos.infotaimos.view.ButtonSequencePage
import com.amos.infotaimos.view.WheelPage
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
internal class ButtonSequencePageTest {
    @Test
    fun testDeleteOnSwipe() {
        launchFragmentInContainer<WheelPage>()
        onView(withId(R.id.recordSequenceButton)).perform(click())
        onView(withId(R.id.wheel_button_play_pause)).perform(click())
        onView(withId(R.id.wheel_button_skip_forward)).perform(click())
        onView(withId(R.id.recordSequenceButton)).perform(click())
        onView(withId(R.id.recordSequenceButton)).perform(click())
        onView(withId(R.id.wheel_button_play_pause)).perform(click())
        onView(withId(R.id.recordSequenceButton)).perform(click())

        launchFragmentInContainer<ButtonSequencePage>()
        var count = 0
        onView(withId(R.id.sequenceList)).check { view, _ ->
            count = (view as RecyclerView).childCount
        }
        onView(withId(R.id.sequenceList)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                swipeLeft()
            )
        )
        onView(withId(R.id.sequenceList)).check { view, _ ->
            assert((view as RecyclerView).childCount == count - 1)
        }
        onView(withId(R.id.sequenceList)).perform(
            RecyclerViewActions.actionOnItemAtPosition<RecyclerView.ViewHolder>(
                0,
                swipeRight()
            )
        )
        onView(withId(R.id.sequenceList)).check { view, _ ->
            assert((view as RecyclerView).childCount == count - 2)
        }
    }
}
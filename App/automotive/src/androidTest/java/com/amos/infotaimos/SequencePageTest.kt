package com.amos.infotaimos

import android.content.Intent
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Observer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.platform.app.InstrumentationRegistry
import com.amos.infotaimos.model.ButtonSequence
import com.amos.infotaimos.model.ButtonSequenceRecyclerViewAdapter
import com.amos.infotaimos.model.MediaService
import com.amos.infotaimos.view.ButtonSequencePage
import com.amos.infotaimos.view.WheelPage
import junit.framework.Assert.assertTrue
import org.junit.Test
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

class SequencePageTest {

    @Test
    fun testLayoutComplete() {
        launchFragmentInContainer<ButtonSequencePage>()
        onView(withId(R.id.sequenceList)).check(matches(isDisplayed()))
    }

    @Test
    fun testFirstSequence() {
        launchFragmentInContainer<WheelPage>()
        onView(withId(R.id.recordSequenceButton)).perform(click())
        onView(withId(R.id.wheel_button_play_pause)).perform(click()).check(matches(isDisplayed()))
        onView(withId(R.id.wheel_button_skip_forward)).perform(click())
            .check(matches(isDisplayed()))
        onView(withId(R.id.wheel_button_voice_control)).perform(click())
            .check(matches(isDisplayed()))
        onView(withId(R.id.recordSequenceButton)).perform(click())
        launchFragmentInContainer<ButtonSequencePage>()
        checkSequenceExecuted(0, ButtonSequencePage.sequences.first())
    }

    /**
     * Starts a media service and checks whether media service receives the expected sequence
     * @param position Position of sequence inside sequence recyclerview
     * @param expectedKeyEventSequence ButtonSequence with expected sequence
     * @param timeout Long with timeout duration (default 2)
     * @param timeoutUnit TimeUnit of timeout (default TimeUnit.SECONDS)
     */
    private fun checkSequenceExecuted(position: Int, expectedKeyEventSequence: ButtonSequence, timeout: Long = 2, timeoutUnit: TimeUnit = TimeUnit.SECONDS) {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        context.startService(Intent(context, MediaService::class.java))

        val future: CompletableFuture<Int> = CompletableFuture()
        val recordedSequence= mutableListOf<Int>()
        val keyObserver: Observer<Int?> = Observer<Int?> {
            it?.let {
                recordedSequence.add(it)
                if (recordedSequence.containsAll(expectedKeyEventSequence.sequence) ) {
                    future.complete(it)
                }
            }
        }
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            MediaService.LAST_MEDIA_KEY.observeForever(keyObserver)
        }
        onView(withId(R.id.sequenceList)).perform(
            RecyclerViewActions
                .actionOnItemAtPosition<ButtonSequenceRecyclerViewAdapter.ButtonSequenceViewHolder>(
                    position,
                    click()
                )
        )
        val receivedEvent = future.get(timeout, timeoutUnit)
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            MediaService.LAST_MEDIA_KEY.removeObserver(keyObserver)
        }
        assertTrue(receivedEvent != null)
    }
}

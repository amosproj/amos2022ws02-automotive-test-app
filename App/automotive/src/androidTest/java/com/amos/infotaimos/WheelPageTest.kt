package com.amos.infotaimos

import android.content.Intent
import android.view.KeyEvent
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Observer
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.longClick
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import com.amos.infotaimos.view.WheelPage
import androidx.test.platform.app.InstrumentationRegistry
import com.amos.infotaimos.model.MediaService
import junit.framework.Assert.assertTrue

import org.junit.Test
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

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
    fun testLayoutDescription() {
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

    @Test
    fun testSkipForward() {
        launchFragmentInContainer<WheelPage>()
        checkMediaKeyPressed(R.id.wheel_button_skip_forward, KeyEvent.KEYCODE_MEDIA_NEXT)
    }

    @Test
    fun testPlayPause() {
        launchFragmentInContainer<WheelPage>()
        checkMediaKeyPressed(R.id.wheel_button_play_pause, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE)
        checkMediaKeyPressed(R.id.wheel_button_play_pause, KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE)
    }

    @Test
    fun testSeekForward() {
        launchFragmentInContainer<WheelPage>()
        checkMediaKeyPressed(R.id.wheel_button_skip_forward, KeyEvent.KEYCODE_MEDIA_FAST_FORWARD)
    }

    /**
     * Starts a media service and plays a dummy track, media service will return first received key event via LiveData and CompletableFuture objects
     * @param buttonID ID of button inside view
     * @param expectedKeyEventCode Keycode that should be sent after button press
     * @param timeout Long with timeout duration (default 2)
     * @param timeoutUnit TimeUnit of timeout (default TimeUnit.SECONDS)
     */
    private fun checkMediaKeyPressed(buttonID: Int, expectedKeyEventCode: Int, timeout: Long = 2, timeoutUnit: TimeUnit = TimeUnit.SECONDS) {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        context.startService(Intent(context, MediaService::class.java))

        val future: CompletableFuture<Int> = CompletableFuture()
        val keyObserver:Observer<Int?> = Observer<Int?> {
            it?.let {
                future.complete(it)
            }
        }
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            MediaService.LAST_MEDIA_KEY.observeForever(keyObserver)
        }
        if (expectedKeyEventCode == KeyEvent.KEYCODE_MEDIA_FAST_FORWARD) {
            onView(withId(buttonID)).perform(longClick())
        } else {
            onView(withId(buttonID)).perform(click())
        }
        val keyEventCode = future.get(timeout, timeoutUnit)
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            MediaService.LAST_MEDIA_KEY.removeObserver(keyObserver)
        }
        assertTrue(keyEventCode == expectedKeyEventCode)
    }
}
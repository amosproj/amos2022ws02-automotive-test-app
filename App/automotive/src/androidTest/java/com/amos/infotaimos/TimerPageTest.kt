package com.amos.infotaimos

import android.content.Intent
import android.util.Log
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Observer
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.platform.app.InstrumentationRegistry
import com.amos.infotaimos.model.TimerEvent
import com.amos.infotaimos.model.TimerEventType
import com.amos.infotaimos.model.TimerService
import com.amos.infotaimos.view.TimerPage
import junit.framework.Assert.assertTrue
import org.junit.Test
import java.time.LocalDateTime
import java.util.concurrent.CompletableFuture
import java.util.concurrent.TimeUnit

class TimerPageTest {

    @Test
    fun testLayoutCompleteness() {
        launchFragmentInContainer<TimerPage>()
        Espresso.onView(ViewMatchers.withId(R.id.recycler_view))
            .check(ViewAssertions.matches(isDisplayed()))
    }

    @Test
    fun testEventHandling() {
        launchFragmentInContainer<TimerPage>()
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        context.startService(Intent(context, TimerService::class.java))
        expectEvents(
            listOf(
                TimerEvent(TimerEventType.START,"123", LocalDateTime.now(), "Test1"),
                TimerEvent(TimerEventType.START,"12345", LocalDateTime.now(), "Test1"),
                TimerEvent(TimerEventType.END,"123", LocalDateTime.now(), "")
            )
        )
    }

    /**
     * Function for broadcasting a list of events via instrumentation context
     * @param events List of events for broadcasting
     */
    private fun broadcastEvents(events: List<TimerEvent>) {
        val context = InstrumentationRegistry.getInstrumentation().targetContext
        events.forEach {
            val intent = Intent( if(it.type == TimerEventType.START) TimerService.INTENT_TIMER_START_ACTION_KEY else TimerService.INTENT_TIMER_END_ACTION_KEY)
            intent.putExtra(TimerService.INTENT_TIMER_ACTION_ID_KEY, it.actionID)
            if (it.type == TimerEventType.START) {
                intent.putExtra(TimerService.INTENT_TIMER_DESC_KEY, it.desc)
            }
            context.sendBroadcast(intent)
        }
    }

    /**
     * Function for testing whether a list of expected events is received by timer service
     * @param expectedEvents List of expected TimerEvents
     * @param timeout Long with timeout duration (default 2)
     * @param timeoutUnit TimeUnit of timeout (default TimeUnit.SECONDS)
     */
    private fun expectEvents(expectedEvents: List<TimerEvent>,timeout: Long = 2, timeoutUnit: TimeUnit = TimeUnit.SECONDS) {
        val future: CompletableFuture<List<TimerEvent>> = CompletableFuture()
        val keyObserver: Observer<List<TimerEvent>> = Observer<List<TimerEvent>> {
            if (it.size == expectedEvents.size){
                future.complete(it)
            }

        }
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            TimerService.rawEvents.observeForever(keyObserver)
        }
        broadcastEvents(expectedEvents)
        val receivedEvents = future.get(timeout, timeoutUnit)
        InstrumentationRegistry.getInstrumentation().runOnMainSync {
            TimerService.rawEvents.removeObserver(keyObserver)
        }
        assertTrue(
            expectedEvents.all { expectedEvent ->
                receivedEvents.firstOrNull { receivedEvent ->
                    receivedEvent.actionID == expectedEvent.actionID && receivedEvent.type == expectedEvent.type
                }  != null
            }
        )
    }
}

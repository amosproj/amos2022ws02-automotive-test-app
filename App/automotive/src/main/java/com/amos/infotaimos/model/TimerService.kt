package com.amos.infotaimos.model

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.time.LocalDateTime

/**
 * Service class for registering and receiving events from broadcast receiver
 * @property receiver Broadcast receiver for receiving event actions specified in android manifest
 */
class TimerService : Service() {
    private var receiver = object: BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val date = LocalDateTime.now()
            intent?.let {
                intent.extras?.getString(INTENT_TIMER_ACTION_ID_KEY)?.let { actionID ->
                    when (intent.action) {
                        INTENT_TIMER_START_ACTION_KEY -> {
                            addEvent(
                                TimerEvent(
                                    TimerEventType.START,
                                    actionID,
                                    date,
                                    intent.extras?.getString(INTENT_TIMER_DESC_KEY) ?: ""
                                )
                            )
                        }
                        INTENT_TIMER_END_ACTION_KEY -> {
                            addEvent(
                                TimerEvent(
                                    TimerEventType.END,
                                    actionID,
                                    date,
                                    ""
                                )
                            )
                        }
                        else -> {
                            Log.d(TAG,"Dismissing unknown timer event action ${intent.action}")
                        }
                    }
                } ?: run {
                    Log.d(TAG,"ActionID missing from timer event....dismissed event")
                }
            }
        }
    }

    @Override
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        val intentFilter = IntentFilter()
        intentFilter.addAction(INTENT_TIMER_START_ACTION_KEY)
        intentFilter.addAction(INTENT_TIMER_END_ACTION_KEY)
        registerReceiver(receiver, intentFilter)
        Log.d(TAG,"Timer event receiver registered")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    /**
     * Function for adding new event to event list and live data, will always run on main thread
     * @param event TimerEvent object with event data
     */
    private fun addEvent(event: TimerEvent) {
        MainScope().launch {
            rawEventsList.add(event)
            rawEvents.value = rawEventsList
        }
    }

    companion object {
        private const val TAG = "TIMER_SERVICE"
        const val INTENT_TIMER_START_ACTION_KEY = "com.amos.infotaimos.timer.start"
        const val INTENT_TIMER_END_ACTION_KEY = "com.amos.infotaimos.timer.end"
        const val INTENT_TIMER_ACTION_ID_KEY = "com.amos.infotaimos.timer.action_id"
        const val INTENT_TIMER_DESC_KEY = "com.amos.infotaimos.timer.desc"
        private val rawEventsList = mutableListOf<TimerEvent>()
        val rawEvents = MutableLiveData(rawEventsList)
    }
}

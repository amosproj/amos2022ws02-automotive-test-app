package com.amos.infotaimos.model

import android.app.Service
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import kotlinx.coroutines.MainScope
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

/**
 * Service class for registering and receiving events from broadcast receiver
 * @property receiver Broadcast receiver for receiving event actions specified in android manifest
 */
class TimerService : Service() {
    private var receiver = object:BroadcastReceiver() {
        override fun onReceive(context: Context?, intent: Intent?) {
            val date = LocalDateTime.now()
            intent?.let {
                intent.extras?.getString(INTENT_TIMER_ACTION_ID_KEY)?.let { actionID ->
                    when (intent.action) {
                        INTENT_TIMER_START_ACTION_KEY -> {
                            MainScope().let {
                                rawEvents.value?.add(
                                    TimerEvent(
                                        TimerEventType.START,
                                        actionID,
                                        date,
                                        intent.extras?.getString(INTENT_TIMER_DESC_KEY) ?: ""
                                    )
                                )
                            }
                        }
                        INTENT_TIMER_END_ACTION_KEY -> {
                            MainScope().let {
                                val startEvent = rawEvents.value?.firstOrNull { it.actionID === actionID}
                                startEvent?.let {
                                    rawEvents.value?.remove(it)
                                    events.value?.add(TimerItem(
                                        startEvent.actionID,
                                        timeFormatter.format(startEvent.date),
                                        timeFormatter.format(date),
                                        startEvent.desc
                                    ))
                                } ?: run {
                                    rawEvents.value?.add(
                                        TimerEvent(
                                            TimerEventType.END,
                                            actionID,
                                            date,
                                            ""
                                        )
                                    )
                                    Log.d(TAG,"Received end timer event but not start event at $date")
                                }
                            }
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
        registerReceiver(receiver, IntentFilter(INTENT_TIMER_START_ACTION_KEY))
        registerReceiver(receiver, IntentFilter(INTENT_TIMER_END_ACTION_KEY))
        Log.d(TAG,"Timer event receiver registered")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        return START_STICKY
    }

    companion object {
        val timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss")
        private const val TAG = "TIMER_SERVICE"
        const val INTENT_TIMER_START_ACTION_KEY = "com.amos.infotaimos.timer.start"
        const val INTENT_TIMER_END_ACTION_KEY = "com.amos.infotaimos.timer.end"
        const val INTENT_TIMER_ACTION_ID_KEY = "com.amos.infotaimos.timer.action_id"
        const val INTENT_TIMER_DESC_KEY = "com.amos.infotaimos.timer.desc"
        private val rawEvents = MutableLiveData(mutableListOf<TimerEvent>())
        val events = MutableLiveData(mutableListOf<TimerItem>())
    }
}

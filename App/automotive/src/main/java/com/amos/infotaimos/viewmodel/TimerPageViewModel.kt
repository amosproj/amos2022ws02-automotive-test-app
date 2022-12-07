package com.amos.infotaimos.viewmodel

import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.amos.infotaimos.model.TimerEvent
import com.amos.infotaimos.model.TimerEventType
import com.amos.infotaimos.model.TimerItem
import com.amos.infotaimos.model.TimerService

class TimerPageViewModel : ViewModel() {

    val events = Transformations.map(TimerService.rawEvents) { events ->
        val itemList = mutableListOf<TimerItem>()
        for (eventIndex in 0 until events.size) {
            val event = events[eventIndex]
            if (event.type == TimerEventType.END) {
                continue
            }
            var endEvent: TimerEvent? = null
            for (endEventIndex in (eventIndex + 1) until events.size) {
                val checkEvent = events[endEventIndex]
                if (checkEvent.actionID == event.actionID) {
                    if (checkEvent.type == TimerEventType.END) {
                        endEvent = checkEvent
                    }
                    break //if its a start event with same actionID before first end event well skip
                }
            }
            itemList.add(
                TimerItem(
                    event.actionID,
                    event.date,
                    endEvent?.date,
                    event.desc
                )
            )
        }
        return@map itemList
    }
}

package com.amos.infotaimos.viewmodel

import android.content.Context
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.amos.infotaimos.model.*

class RecordDetailsViewModel : ViewModel() {

    val events = Transformations.map(RecordingService.recordList) { events ->
        val itemList = mutableListOf<DetailRecordsItem>()
        for (eventIndex in 0 until events.size) {
            val event = events[eventIndex]
            itemList.add(
                event
                )
        }
        return@map itemList
    }

    fun initialise(context: Context, id: String){
        RecordingService.load(context, id)
    }
}
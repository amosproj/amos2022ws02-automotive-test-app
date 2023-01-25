package com.amos.infotaimos.viewmodel

import android.content.Context
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.amos.infotaimos.model.*

class RecordDetailsViewModel : ViewModel() {

    val events = Transformations.map(RecordingService.recordDetailsList) { events ->
        val itemList = mutableListOf<RecordDetailsItem>()
        for (event in events) {
            itemList.add(event)
        }
        return@map itemList
    }

    fun initialise(context: Context, id: String){
        RecordingService.loadRecordDetail(context, id)
    }

    fun exportTestDrive(context: Context) {
        RecordingService.exportTestDrive(context, events.value!!)
    }
}
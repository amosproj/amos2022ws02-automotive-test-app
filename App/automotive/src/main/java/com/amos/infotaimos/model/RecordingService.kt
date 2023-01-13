package com.amos.infotaimos.model

import android.content.Context
import java.time.LocalDateTime

object RecordingService {
    fun saveData(context: Context, id: String, eventName: String, vehiclePropertyID: Int, value: String, time: LocalDateTime) {
        val line = id.plus(";").plus(eventName).plus(";").plus(vehiclePropertyID).plus(";").plus(value).plus(";").plus(time).plus(";\n");
        val file = ("Recording").plus(id).plus(".txt")
        context.openFileOutput(file, Context.MODE_PRIVATE).use {
            it?.write(line.toByteArray())
        }
    }

    fun save (context: Context, timeStamp: String){
        val line = ("Recording").plus(timeStamp).plus(".txt")
        context.openFileOutput("PastRecords.txt", Context.MODE_PRIVATE).use {
            it?.write(line.toByteArray())
        }
    }
}

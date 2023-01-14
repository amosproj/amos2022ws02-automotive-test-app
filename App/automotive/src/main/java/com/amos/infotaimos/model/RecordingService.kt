package com.amos.infotaimos.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import java.io.FileNotFoundException
import java.time.LocalDateTime

object RecordingService {
    var recordList: MutableLiveData<MutableList<DetailRecordsItem>> = MutableLiveData()
    private const val TAG = "SPEECH_SERVICE"

    fun saveData(context: Context, id: String, eventName: String, vehiclePropertyID: Int, value: String, time: LocalDateTime) {
        val line = id.plus(";").plus(eventName).plus(";").plus(vehiclePropertyID).plus(";").plus(value).plus(";").plus(time).plus(";\n");
        val file = ("Recording").plus(id).plus(".txt")
        Log.d(TAG, "save $line")
        context.openFileOutput(file, Context.MODE_APPEND).use {
            it?.write(line.toByteArray())
        }
    }


    fun load(context: Context, id: String) {
        try{
            val file = ("Recording").plus(id).plus(".txt")
            recordList.value = ArrayList()
            Log.d(TAG, "load $id")

            val lineList = context.openFileInput(file).bufferedReader().readLines()
            lineList.forEach{
                    var array = it.split(";")
                    recordList.value?.add(DetailRecordsItem(array[0], array[1], array[2], array[3], array[4] ))
                }
        }
        catch(e : FileNotFoundException){

        }
    }
}

package com.amos.infotaimos.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import java.io.FileNotFoundException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.absoluteValue

object RecordingService {
    var recordList: MutableLiveData<MutableList<DetailRecordsItem>> = MutableLiveData()
    var previousRecordList: MutableLiveData<MutableList<TestDriveItem>> = MutableLiveData()
    private const val TAG = "SPEECH_SERVICE"
    private const val PREVIOUS_RECORD_FILE = "PreviousRecordList.txt"

    fun saveData(context: Context, id: String, eventName: String, vehiclePropertyID: Int, value: String, time: LocalDateTime) {
        val line = id.plus(";").plus(eventName).plus(";").plus(vehiclePropertyID).plus(";").plus(value).plus(";").plus(DateTimeFormatter.ofPattern("HH:mm:ss").format(time)).plus(";\n");
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

    fun saveRecord(context: Context, id: LocalDateTime){
        Log.d(TAG, "save $id")
        context.openFileOutput("$PREVIOUS_RECORD_FILE.txt", Context.MODE_APPEND).use {
            it?.write(("$id\n").toByteArray())
        }
        val item =
            TestDriveItem(id.hashCode().absoluteValue.toString(), id.toLocalDate(), id)
        previousRecordList.value?.add(item)
        previousRecordList.value = previousRecordList.value
    }
    fun loadPreviousRecords(context: Context) {
        try {
            previousRecordList.value = ArrayList()
            Log.d(TAG, "load previous records")
            val lineList = context.openFileInput("$PREVIOUS_RECORD_FILE.txt").bufferedReader().readLines()
            lineList.forEach {
                val timeStamp : LocalDateTime = LocalDateTime.parse(it, DateTimeFormatter.ISO_DATE_TIME)
                previousRecordList.value?.add(
                    TestDriveItem(
                        timeStamp.hashCode().absoluteValue.toString(), timeStamp.toLocalDate(), timeStamp)
                )
            }
        }
        catch(_: FileNotFoundException){}
    }
}

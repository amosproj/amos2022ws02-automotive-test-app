package com.amos.infotaimos.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import java.io.FileNotFoundException
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.absoluteValue

object RecordingService {
    var recordDetailsList: MutableLiveData<MutableList<RecordDetailsItem>> = MutableLiveData()
    var testDriveList: MutableLiveData<MutableList<TestDriveItem>> = MutableLiveData()
    private const val TAG = "RECORD_SERVICE"
    private const val PREVIOUS_RECORD_FILE = "PreviousRecordList.txt"

    fun saveRecordDetail(context: Context, id: String, eventName: String, vehiclePropertyID: Int, value: String, time: LocalDateTime) {
        val line = id.hashCode().absoluteValue.toString().plus(";").plus(eventName).plus(";").plus(vehiclePropertyID).plus(";").plus(value).plus(";").plus(DateTimeFormatter.ofPattern("HH:mm:ss").format(time)).plus(";\n");
        val file = ("Recording").plus(id).plus(".txt")
        Log.d(TAG, "save $line")
        context.openFileOutput(file, Context.MODE_APPEND).use {
            it?.write(line.toByteArray())
        }
    }

    fun loadRecordDetail(context: Context, id: String) {
        try{
            val file = ("Recording").plus(id).plus(".txt")
            recordDetailsList.value = ArrayList()
            Log.d(TAG, "load $id")

            val lineList = context.openFileInput(file).bufferedReader().readLines()
            lineList.forEach{
                    var array = it.split(";")
                    recordDetailsList.value?.add(RecordDetailsItem(array[0], array[1], array[2], array[3], array[4]))
                }
        }
        catch(e : FileNotFoundException){

        }
    }

    fun saveTestDrive(context: Context, id: LocalDateTime){
        Log.d(TAG, "save $id")
        context.openFileOutput(PREVIOUS_RECORD_FILE, Context.MODE_APPEND).use {
            it?.write(("$id\n").toByteArray())
        }
        testDriveList.value?.add(0, createTestDriveItem(id))
        testDriveList.value = testDriveList.value
    }
    fun loadTestDrive(context: Context) {
        try {
            testDriveList.value = ArrayList()
            Log.d(TAG, "load previous records")
            val lineList = context.openFileInput(PREVIOUS_RECORD_FILE).bufferedReader().readLines()
            lineList.forEach {
                val id : LocalDateTime = LocalDateTime.parse(it, DateTimeFormatter.ISO_DATE_TIME)
                testDriveList.value?.add(0,
                    createTestDriveItem(id)
                )
            }
        }
        catch(_: FileNotFoundException){}
    }

    private fun createTestDriveItem(id: LocalDateTime) : TestDriveItem {
        return TestDriveItem(
            id.hashCode().absoluteValue.toString(), id.toLocalDate(), id)
    }
}

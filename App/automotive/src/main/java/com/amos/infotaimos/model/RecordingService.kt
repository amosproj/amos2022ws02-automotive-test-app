package com.amos.infotaimos.model

import android.content.Context
import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import kotlin.math.absoluteValue

object RecordingService {
    var recordDetailsList: MutableLiveData<MutableList<RecordDetailsItem>> = MutableLiveData()
    var testDriveList: MutableLiveData<MutableList<String>> = MutableLiveData()
    private const val TAG = "RECORD_SERVICE"
    private const val PREVIOUS_RECORD_FILE = "PreviousRecordList.txt"

    fun saveRecordDetail(context: Context, id: String, eventName: String, vehiclePropertyID: Int, value: String, time: LocalDateTime) {
        loadRecordDetail(context, id)
        recordDetailsList.value?.add(RecordDetailsItem(id.hashCode().absoluteValue.toString(), eventName, vehiclePropertyID.toString(), value, time.toString()))
        val file = ("Recording").plus(id).plus(".txt")
        val json = getMoshiAdapterRecordDetails().toJson(recordDetailsList.value)
        context.openFileOutput(file, Context.MODE_PRIVATE).use {
            it?.write(json.toByteArray())
        }
    }

    fun loadRecordDetail(context: Context, id: String) {
        val file = ("Recording").plus(id).plus(".txt")
        checkFileExistence(context, file)
        recordDetailsList.value = ArrayList()
        Log.d(TAG, "load $id")

        val json = context.openFileInput (file).bufferedReader().readLine()
        val details = getMoshiAdapterRecordDetails().fromJson(json)
        recordDetailsList.value = details?.toMutableList() ?: mutableListOf()
    }

    fun saveTestDrive(context: Context, id: LocalDateTime, position: Int){
        loadTestDrive(context)
        Log.d(TAG, "save $id")
        testDriveList.value?.add(position, id.toString())
        val json = getMoshiAdapter().toJson(testDriveList.value)
        context.openFileOutput(PREVIOUS_RECORD_FILE, Context.MODE_PRIVATE).use {
            it?.write(json.toByteArray())
        }
        testDriveList.value = testDriveList.value
    }
    fun loadTestDrive(context: Context) {
            checkFileExistence(context, PREVIOUS_RECORD_FILE)
            testDriveList.value = ArrayList()
            Log.d(TAG, "load previous records")
            val json = context.openFileInput(PREVIOUS_RECORD_FILE).bufferedReader().readLine()
            val records = getMoshiAdapter().fromJson(json)
            testDriveList.value = records?.toMutableList() ?: mutableListOf()
    }

    fun deleteTestDrive(context: Context, id: String){
        testDriveList.value?.remove(id)
        val json = getMoshiAdapter().toJson(testDriveList.value)
        context.openFileOutput(PREVIOUS_RECORD_FILE, Context.MODE_PRIVATE).use {
            it?.write(json.toByteArray())
        }
        val file = ("Recording").plus(id).plus(".txt")
        context.deleteFile(file)
        testDriveList.value = testDriveList.value
    }

    fun createTestDriveItem(id: String) : TestDriveItem {
        val time : LocalDateTime = LocalDateTime.parse(id, DateTimeFormatter.ISO_DATE_TIME)
        return TestDriveItem(
            id.hashCode().absoluteValue.toString(), time.toLocalDate(), time)
    }

    private fun getMoshiAdapter() = Moshi.Builder().build().adapter<List<String>>(
        Types.newParameterizedType(
            List::class.java,
            String::class.java
        )
    )

    private fun getMoshiAdapterRecordDetails() = Moshi.Builder().build().adapter<List<RecordDetailsItem>>(
        Types.newParameterizedType(
            List::class.java,
            RecordDetailsItem::class.java
        )
    )

    private fun checkFileExistence(context: Context, file: String) {
        try {
            context.openFileInput(file)
        } catch (e: Exception) {
            context.openFileOutput(file, Context.MODE_PRIVATE).use {
                it?.write("[]".toByteArray())
            }
        }
    }
}

package com.amos.infotaimos.viewmodel

import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amos.infotaimos.R
import com.amos.infotaimos.model.NotificationManager
import com.amos.infotaimos.model.VinService
import java.util.*
import kotlin.concurrent.schedule

class VehiclePropertiesPageViewModel : ViewModel() {

    val _vin: MutableLiveData<String> = MutableLiveData("")
    val vin: LiveData<String>
        get() = _vin

    fun loadData(context: Context) {
        _vin.value = VinService.loadData(context)
    }

    fun saveData(context: Context, s: String) {
        VinService.saveData(context, s)
    }

    fun sendNotification(context: Context, percentage: String, delay: Long) {
        Log.d("VM", "$percentage $delay")
        Timer().schedule(delay) {
            NotificationManager.createNotification(context, context.getString(R.string.battery_notification,percentage))
        }
    }
}

package com.amos.infotaimos.viewmodel

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amos.infotaimos.model.VinService

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
}

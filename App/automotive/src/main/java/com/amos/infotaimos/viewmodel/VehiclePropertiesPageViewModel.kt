package com.amos.infotaimos.viewmodel

import android.car.Car
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.content.Context
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.amos.infotaimos.model.CarInstanceManager

class VehiclePropertiesPageViewModel : ViewModel() {
    private val _vin: MutableLiveData<String> = MutableLiveData("712587-3567883-5398753")
    val vin: LiveData<String>
        get() = _vin

    fun registerVinCallback(context: Context) {
        Log.d(TAG, "Registering VIN callback")
        val car = CarInstanceManager.getCarInstance(context)
        val carPropertyManager = car.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
        carPropertyManager.registerCallback(
            carPropertyListener,
            VehiclePropertyIds.INFO_VIN,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
    }

    private var carPropertyListener = object : CarPropertyManager.CarPropertyEventCallback {
        override fun onChangeEvent(value: CarPropertyValue<Any>) {
            Log.d(TAG, "Received on changed car property event")
            _vin.value = value.value.toString()
            Log.d(TAG, "VIN: ${_vin.value}")
        }

        override fun onErrorEvent(propId: Int, zone: Int) {
            Log.w(TAG, "Received error car property event, propId=$propId")
        }
    }

    companion object {
        const val TAG = "VehiclePropertiesPage"
    }
}

package com.amos.infotaimos.viewmodel

import android.app.Activity
import android.content.Context
import android.view.View
import androidx.lifecycle.ViewModel
import com.amos.infotaimos.model.CarInstanceManager

import android.car.Car
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.content.pm.PackageManager

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat


import kotlin.math.roundToInt


class VehiclePropertiesPageViewModel : ViewModel() {
    private var batteryPermission: Boolean = false
    private lateinit var propertyManager: CarPropertyManager

    fun checkBatteryPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(context, Car.PERMISSION_ENERGY) == PackageManager.PERMISSION_GRANTED
    }

    fun getBatteryPermission(context: Context, activity: Activity): Boolean {
        batteryPermission = checkBatteryPermission(context)
        if (!batteryPermission) {
            ActivityCompat.requestPermissions(activity, arrayOf(Car.PERMISSION_ENERGY), 0)
            batteryPermission = checkBatteryPermission(context)
        }
        return batteryPermission
    }

    fun setPropertyManager(context: Context) {
        val car: Car = CarInstanceManager.getCarInstance(context)
        propertyManager =  car.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
    }

    fun getBatteryWH(): Float {
        return propertyManager.getFloatProperty(VehiclePropertyIds.EV_BATTERY_LEVEL, 0)
    }

    fun getCapacityWH(): Float {
        return propertyManager.getFloatProperty(VehiclePropertyIds.INFO_EV_BATTERY_CAPACITY, 0)
    }

    fun getBatteryLevel(): Int {
        val batteryLevel = getBatteryWH() / getCapacityWH()
        return (batteryLevel * 100).roundToInt()
    }

    // TODO
    fun registerBatteryCallback(context: Context, view: View) {
        propertyManager.registerCallback(object: CarPropertyManager.CarPropertyEventCallback {
                private val capacity: Float = getCapacityWH()
                override fun onChangeEvent(p0: CarPropertyValue<*>?) {
                    val battery = p0?.value
                    // set text to new value
                }

                override fun onErrorEvent(p0: Int, p1: Int) {
                    // set text to error msg
                }
            },
            VehiclePropertyIds.EV_BATTERY_LEVEL,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
    }
}

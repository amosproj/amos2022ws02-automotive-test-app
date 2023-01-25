package com.amos.infotaimos.viewmodel

import android.car.Car
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.car.hardware.property.CarPropertyManager.CarPropertyEventCallback
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.amos.infotaimos.model.CarInstanceManager
import com.amos.infotaimos.model.RecordingService
import com.amos.infotaimos.model.TestDriveItem
import java.time.LocalDateTime

class TestDrivePageViewModel : ViewModel() {
    private lateinit var propertyManager: CarPropertyManager
    val _tapText: MutableLiveData<String> = MutableLiveData("")
    val tapText: LiveData<String>
        get() = _tapText
    val events = Transformations.map(RecordingService.testDriveList) { events ->
        val itemList = mutableListOf<TestDriveItem>()
        for (event in events){
            itemList.add(RecordingService.createTestDriveItem(event))
        }
        return@map itemList
    }
    lateinit var batteryLevelEvent: CarPropertyEventCallback
    lateinit var fuelLevelEvent: CarPropertyEventCallback
    lateinit var fuelLevelLowEvent: CarPropertyEventCallback
    lateinit var gearSelectionEvent: CarPropertyEventCallback


    fun loadTestDrive(context: Context){
        RecordingService.loadTestDrive(context)
    }
    fun saveTestDrive(context: Context, timeStamp: LocalDateTime) {
        RecordingService.saveTestDrive(context, timeStamp, 0)
    }

    fun setPropertyManager(context: Context) {
        val car: Car = CarInstanceManager.getCarInstance(context)
        propertyManager = car.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
    }

    fun checkPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Car.PERMISSION_ENERGY
        ) == PackageManager.PERMISSION_GRANTED
                && ContextCompat.checkSelfPermission(
            context,
            Car.PERMISSION_POWERTRAIN
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun record(context: Context, id: String) {
        fuelLevelEvent = object : CarPropertyEventCallback {
            override fun onChangeEvent(p0: CarPropertyValue<*>?) {
                val time = LocalDateTime.now()
                val value = propertyManager.getFloatProperty(VehiclePropertyIds.FUEL_LEVEL, 0)
                RecordingService.saveRecordDetail(
                    context,
                    id,
                    "FUEL_LEVEL",
                    VehiclePropertyIds.FUEL_LEVEL,
                    value.toString(),
                    time
                )
            }

            override fun onErrorEvent(p0: Int, p1: Int) {
                //Called only when an error is detected when setting a property.
            }
        }
        batteryLevelEvent = object : CarPropertyEventCallback {
            override fun onChangeEvent(p0: CarPropertyValue<*>?) {
                val time = LocalDateTime.now()
                val value =
                    propertyManager.getFloatProperty(VehiclePropertyIds.EV_BATTERY_LEVEL, 0)
                RecordingService.saveRecordDetail(
                    context,
                    id,
                    "EV_BATTERY_LEVEL",
                    VehiclePropertyIds.EV_BATTERY_LEVEL,
                    value.toString(),
                    time
                )
            }

            override fun onErrorEvent(p0: Int, p1: Int) {
                //Called only when an error is detected when setting a property.
            }
        }
        fuelLevelLowEvent = object : CarPropertyEventCallback {
            override fun onChangeEvent(p0: CarPropertyValue<*>?) {
                val time = LocalDateTime.now()
                val value =
                    propertyManager.getBooleanProperty(VehiclePropertyIds.FUEL_LEVEL_LOW, 0)
                RecordingService.saveRecordDetail(
                    context,
                    id,
                    "FUEL_LEVEL_LOW",
                    VehiclePropertyIds.FUEL_LEVEL_LOW,
                    value.toString(),
                    time
                )
            }

            override fun onErrorEvent(p0: Int, p1: Int) {
                //Called only when an error is detected when setting a property.
            }
        }
        gearSelectionEvent = object : CarPropertyEventCallback {
            override fun onChangeEvent(p0: CarPropertyValue<*>?) {
                val time = LocalDateTime.now()
                val value = propertyManager.getIntProperty(VehiclePropertyIds.GEAR_SELECTION, 0)
                RecordingService.saveRecordDetail(
                    context,
                    id,
                    "GEAR_SELECTION",
                    VehiclePropertyIds.GEAR_SELECTION,
                    value.toString(),
                    time
                )
            }

            override fun onErrorEvent(p0: Int, p1: Int) {
                //Called only when an error is detected when setting a property.
            }
        }
        propertyManager.registerCallback(
            batteryLevelEvent,
            VehiclePropertyIds.EV_BATTERY_LEVEL,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
        propertyManager.registerCallback(
            fuelLevelEvent,
            VehiclePropertyIds.FUEL_LEVEL,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
        propertyManager.registerCallback(
            fuelLevelLowEvent, VehiclePropertyIds.FUEL_LEVEL_LOW,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
        propertyManager.registerCallback(
            gearSelectionEvent, VehiclePropertyIds.GEAR_SELECTION,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )

    }

    fun unrecord() {
        propertyManager.unregisterCallback(batteryLevelEvent)
        propertyManager.unregisterCallback(fuelLevelEvent)
        propertyManager.unregisterCallback(fuelLevelLowEvent)
        propertyManager.unregisterCallback(gearSelectionEvent)
    }
}
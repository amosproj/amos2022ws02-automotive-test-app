package com.amos.infotaimos.viewmodel

import android.car.Car
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.content.Context
import android.content.pm.PackageManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
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
    val events = MutableLiveData< MutableList<TestDriveItem>>()

    init{
        events.value = ArrayList()
    }

    fun addNewRecording(timeStamp: LocalDateTime){
        val item = TestDriveItem(timeStamp.hashCode().toString(), timeStamp.toLocalDate(), timeStamp)
        events.value?.add(item)
        events.value = events.value
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
        propertyManager.registerCallback(
            object : CarPropertyManager.CarPropertyEventCallback {
                override fun onChangeEvent(p0: CarPropertyValue<*>?) {
                    val time = LocalDateTime.now()
                    val newValue = propertyManager.getFloatProperty(VehiclePropertyIds.EV_BATTERY_LEVEL, 0)
                    RecordingService.saveData(context, id, "EV_BATTERY_LEVEL", VehiclePropertyIds.EV_BATTERY_LEVEL, newValue.toString(), time)
                }

                override fun onErrorEvent(p0: Int, p1: Int) {
                    //Called only when an error is detected when setting a property.
                }
            },
            VehiclePropertyIds.EV_BATTERY_LEVEL,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
        propertyManager.registerCallback(
            object : CarPropertyManager.CarPropertyEventCallback {
                override fun onChangeEvent(p0: CarPropertyValue<*>?) {
                    val time = LocalDateTime.now()
                    val newValue = propertyManager.getFloatProperty(VehiclePropertyIds.FUEL_LEVEL, 0)
                    RecordingService.saveData(context, id, "FUEL_LEVEL", VehiclePropertyIds.FUEL_LEVEL, newValue.toString(), time)
                }

                override fun onErrorEvent(p0: Int, p1: Int) {
                    //Called only when an error is detected when setting a property.
                }
            },
            VehiclePropertyIds.FUEL_LEVEL,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
        propertyManager.registerCallback(
            object : CarPropertyManager.CarPropertyEventCallback {
                override fun onChangeEvent(p0: CarPropertyValue<*>?) {
                    val newTime = LocalDateTime.now()
                    val newValue = propertyManager.getBooleanProperty(VehiclePropertyIds.FUEL_LEVEL_LOW, 0)
                    RecordingService.saveData(context, id, "FUEL_LEVEL_LOW", VehiclePropertyIds.FUEL_LEVEL_LOW, newValue.toString(), newTime)
                }

                override fun onErrorEvent(p0: Int, p1: Int) {
                    //Called only when an error is detected when setting a property.
                }
            },
            VehiclePropertyIds.FUEL_LEVEL_LOW,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
        propertyManager.registerCallback(
            object : CarPropertyManager.CarPropertyEventCallback {
                override fun onChangeEvent(p0: CarPropertyValue<*>?) {
                    val newTime = LocalDateTime.now()
                    val newValue = propertyManager.getIntProperty(VehiclePropertyIds.GEAR_SELECTION, 0)
                    RecordingService.saveData(context, id, "GEAR_SELECTION", VehiclePropertyIds.GEAR_SELECTION, newValue.toString(), newTime)
                }

                override fun onErrorEvent(p0: Int, p1: Int) {
                    //Called only when an error is detected when setting a property.
                }
            },
            VehiclePropertyIds.GEAR_SELECTION,
            CarPropertyManager.SENSOR_RATE_ONCHANGE
        )
    }
}
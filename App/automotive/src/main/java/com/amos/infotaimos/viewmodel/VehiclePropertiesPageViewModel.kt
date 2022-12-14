package com.amos.infotaimos.viewmodel

import android.app.Activity
import android.content.Context
import androidx.lifecycle.ViewModel
import com.amos.infotaimos.model.CarInstanceManager

import android.car.Car
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.content.pm.PackageManager

import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.amos.infotaimos.databinding.FragmentVehiclePropertiesPageBinding
import kotlin.math.roundToInt

import android.util.Log
import android.view.View
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.amos.infotaimos.R
import com.amos.infotaimos.model.NotificationManager
import com.amos.infotaimos.model.VinService
import java.util.*
import kotlin.concurrent.schedule


class VehiclePropertiesPageViewModel : ViewModel() {
    private var batteryPermission: Boolean = false
    private var registerCallBack: Boolean = false
    private lateinit var propertyManager: CarPropertyManager
    private val globalArea = 0

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
            NotificationManager.createNotification(
                context,
                context.getString(R.string.battery_notification, percentage)
            )
        }
    }

    private fun checkBatteryPermission(context: Context): Boolean {
        return ContextCompat.checkSelfPermission(
            context,
            Car.PERMISSION_ENERGY
        ) == PackageManager.PERMISSION_GRANTED
    }

    fun getBatteryPermission(context: Context, activity: Activity, view: View): Boolean {
        batteryPermission = checkBatteryPermission(context)
        if (!batteryPermission) {
            ActivityCompat.requestPermissions(activity, arrayOf(Car.PERMISSION_ENERGY), 0)
            batteryPermission = checkBatteryPermission(context)
            if (batteryPermission)
                view.invalidate()
        }
        return batteryPermission
    }

    fun setPropertyManager(context: Context) {
        val car: Car = CarInstanceManager.getCarInstance(context)
        propertyManager = car.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
    }

    fun setBatteryWH(value: Float) {
        propertyManager.setFloatProperty(VehiclePropertyIds.EV_BATTERY_LEVEL, globalArea, value)
    }

    fun getBatteryWH(): Float {
        return propertyManager.getFloatProperty(VehiclePropertyIds.EV_BATTERY_LEVEL, globalArea)
    }

    fun getCapacityWH(): Float {
        return propertyManager.getFloatProperty(
            VehiclePropertyIds.INFO_EV_BATTERY_CAPACITY,
            globalArea
        )
    }

    fun getBatteryText(): String {
        val battery = getBatteryWH()
        val capacity = getCapacityWH()
        return "$battery/$capacity Wh"
    }

    fun getBatteryLevel(): String {
        val batteryLevel = getBatteryWH() / getCapacityWH()
        val batteryPercent = (batteryLevel * 100).roundToInt()
        return "$batteryPercent%"
    }

    fun registerBatteryCallback(fragmentBinding: FragmentVehiclePropertiesPageBinding) {
        if (!registerCallBack) {
            registerCallBack =
                propertyManager.registerCallback(
                    object : CarPropertyManager.CarPropertyEventCallback {
                        private val binding: FragmentVehiclePropertiesPageBinding = fragmentBinding
                        override fun onChangeEvent(p0: CarPropertyValue<*>?) {
                            //val battery = p0?.value

                            binding.batteryLevelTile.tileBatteryLevelText.text = getBatteryLevel()
                            binding.batteryProgressBar.progress = getBatteryWH().toInt()
                            binding.batteryProgressText.text = getBatteryText()
                        }

                        override fun onErrorEvent(p0: Int, p1: Int) {
                            binding.batteryLevelTile.tileBatteryLevelText.text = "Error"
                            binding.batteryProgressBar.progress = 0
                            binding.batteryProgressText.text = "Error"
                        }
                    },
                    VehiclePropertyIds.EV_BATTERY_LEVEL,
                    CarPropertyManager.SENSOR_RATE_ONCHANGE
                )
        }
    }
}



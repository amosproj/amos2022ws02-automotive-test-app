package com.amos.infotaimos.view

import android.car.Car
import android.car.VehiclePropertyIds
import android.car.hardware.CarPropertyValue
import android.car.hardware.property.CarPropertyManager
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.viewModels
import com.amos.infotaimos.ViewBindingFragment
import com.amos.infotaimos.databinding.TileVinBinding
import com.amos.infotaimos.viewmodel.VinTileViewModel

class VinTile : ViewBindingFragment<TileVinBinding>() {
    private val viewModel: VinTileViewModel by viewModels()
    private lateinit var vin: String


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val car = Car.createCar(requireContext())
        binding.tileVinText.text = vin
        val carPropertyManager = car.getCarManager(Car.PROPERTY_SERVICE) as CarPropertyManager
        carPropertyManager.registerCallback(
            carPropertyListener,
            VehiclePropertyIds.INFO_VIN,
            CarPropertyManager.SENSOR_RATE_NORMAL
        )
    }

    private var carPropertyListener = object : CarPropertyManager.CarPropertyEventCallback {
        override fun onChangeEvent(value: CarPropertyValue<Any>) {
            Log.d(TAG, "Received on changed car property event")
            vin = value.value.toString()
        }

        override fun onErrorEvent(propId: Int, zone: Int) {
            Log.w(TAG, "Received error car property event, propId=$propId")
        }
    }

    companion object {
        const val TAG = "VIN tile"
    }
}

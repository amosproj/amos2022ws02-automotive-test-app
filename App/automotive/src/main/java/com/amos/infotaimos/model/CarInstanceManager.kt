package com.amos.infotaimos.model

import android.car.Car
import android.content.Context

object CarInstanceManager {
    private var carInstance: Car? = null

    fun getCarInstance(context: Context): Car {
        carInstance?.let {
            return it
        }
        Car.createCar(context.applicationContext).also {
            carInstance = it
            return it
        }
    }
}

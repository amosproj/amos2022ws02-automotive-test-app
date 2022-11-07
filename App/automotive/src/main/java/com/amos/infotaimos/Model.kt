package com.amos.infotaimos

import android.car.Car
import android.car.CarAppFocusManager
import androidx.test.core.app.ApplicationProvider.getApplicationContext

class Model {
    val car = Car.createCar(getApplicationContext())
    val carAppFocusManager = car.getCarManager(Car.APP_FOCUS_SERVICE) as CarAppFocusManager
    fun startNavigation(delay: Int) {
        println(carAppFocusManager.requestAppFocus(CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION, null))
    }
}
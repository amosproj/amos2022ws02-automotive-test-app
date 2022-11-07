package com.amos.infotaimos

import android.car.Car
import android.car.CarAppFocusManager
import android.content.Context
import androidx.test.core.app.ApplicationProvider.getApplicationContext

class Model(context: Context) {
    private var car = Car.createCar(context)
    private var carAppFocusManager = car.getCarManager(Car.APP_FOCUS_SERVICE) as CarAppFocusManager
    fun startNavigation(delay: Int) {
        val navCallback = NavCallback()
        println(carAppFocusManager.requestAppFocus(CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION, navCallback))
    }

    class NavCallback : CarAppFocusManager.OnAppFocusOwnershipCallback{
        override fun onAppFocusOwnershipLost(appType: Int) {
            if(appType == CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION)
                return
            TODO("Not yet implemented")
        }

        override fun onAppFocusOwnershipGranted(appType: Int) {
            if(appType == CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION)
                return
            TODO("Not yet implemented")
        }

    }
}
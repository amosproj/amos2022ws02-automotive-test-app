package com.amos.infotaimos

import android.car.Car
import android.car.CarAppFocusManager
import android.content.Context
import java.util.*
import kotlin.concurrent.schedule

class Model(context: Context) {
    private val car = Car.createCar(context)
    private val carAppFocusManager = car.getCarManager(Car.APP_FOCUS_SERVICE) as CarAppFocusManager
    fun startNavigation(delay: Long) {
        val navCallback = NavCallback()

        Timer().schedule(delay) {
            try {
                carAppFocusManager.requestAppFocus(
                    CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION,
                    navCallback
                )
            } catch (e : SecurityException){
                //If owner cannot be changed
            }
        }

    }

    class NavCallback : CarAppFocusManager.OnAppFocusOwnershipCallback {
        override fun onAppFocusOwnershipLost(appType: Int) {
            if (appType == CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION)
                return

        }

        override fun onAppFocusOwnershipGranted(appType: Int) {
            if (appType == CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION)
                return
        }

    }
}
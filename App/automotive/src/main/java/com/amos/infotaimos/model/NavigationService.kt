package com.amos.infotaimos.model

import android.car.Car
import android.car.CarAppFocusManager
import android.util.Log
import android.view.MenuItem
import androidx.activity.viewModels
import androidx.lifecycle.MutableLiveData
import com.amos.infotaimos.NavigationPageViewModel
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.schedule

object NavigationService {
    private const val TAG = "NAVIGATION_SERVICE"
    private var navCallback = NavCallback()
    private var startTask: TimerTask? = null
    private var stopTask: TimerTask? = null
    val navIndicatorLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()


    fun startNavigation(car: Car, delay: Long) {
        val carAppFocusManager = car.getCarManager(Car.APP_FOCUS_SERVICE) as CarAppFocusManager
        Log.d(TAG, "Requested navigation start in $delay ms")

        startTask?.cancel()
        startTask = Timer().schedule(delay) {
            Log.d(TAG, "Start navigation timer fired")
            try {
                when (
                    carAppFocusManager.requestAppFocus(
                        CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION,
                        navCallback
                    )
                ) {
                    CarAppFocusManager.APP_FOCUS_REQUEST_FAILED -> {
                        Log.d(TAG, "Requesting navigation focus failed")
                        MainScope().launch { navIndicatorLiveData.value = false }
                    }
                    CarAppFocusManager.APP_FOCUS_REQUEST_SUCCEEDED -> {
                        Log.d(TAG, "Successfully requested navigation focus")
                        MainScope().launch { navIndicatorLiveData.value = true }
                    }
                }
            } catch (e: SecurityException) {
                Log.d(TAG, "Couldnt request focus due to $e")
            }

            startTask = null
        }


    }

    fun stopNavigation(car: Car, delay: Long) {
        val carAppFocusManager = car.getCarManager(Car.APP_FOCUS_SERVICE) as CarAppFocusManager
        Log.d(TAG, "Requested navigation stop in $delay ms")
        stopTask?.cancel()
        stopTask = Timer().schedule(delay) {
            Log.d(TAG, "Stop navigation timer fired")
            if (carAppFocusManager.isOwningFocus(
                    navCallback,
                    CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION
                )
            ) {
                carAppFocusManager.abandonAppFocus(
                    navCallback,
                    CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION
                )
                Log.d(TAG, "Navigation focus released")
                MainScope().launch { navIndicatorLiveData.value = false }
            } else {
                Log.d(TAG, "App doesnt own navigation focus anymore")
            }

            stopTask = null
        }

    }

    fun updateNavigationLiveData(car: Car) {
        val carAppFocusManager = car.getCarManager(Car.APP_FOCUS_SERVICE) as CarAppFocusManager
        navIndicatorLiveData.value = carAppFocusManager.isOwningFocus(
            navCallback,
            CarAppFocusManager.APP_FOCUS_TYPE_NAVIGATION
        )
        Log.d(TAG, "Updated Navigation LiveData")
    }

    class NavCallback : CarAppFocusManager.OnAppFocusOwnershipCallback {
        override fun onAppFocusOwnershipLost(appType: Int) {
            Log.d(TAG, "Navigation focus lost")

        }

        override fun onAppFocusOwnershipGranted(appType: Int) {
            Log.d(TAG, "Navigation focus granted")

        }
    }
}

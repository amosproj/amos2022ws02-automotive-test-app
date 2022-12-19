package com.amos.infotaimos.model

import android.car.Car
import android.car.CarAppFocusManager
import android.media.MediaPlayer
import android.os.CountDownTimer
import android.util.Log
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import java.util.Timer
import java.util.TimerTask
import kotlin.concurrent.schedule

object NavigationService {
    private const val TAG = "NAVIGATION_SERVICE"
    val navCallback = NavCallback()
    var startTask: TimerTask? = null
    var stopTask: TimerTask? = null
    private var announcementTask: TimerTask? = null
    val navIndicatorLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>()
    val countdown: MutableLiveData<Long> = MutableLiveData<Long>(0)
    var delayCounter: DelayCounter? = null

    fun startNavigation(car: Car, delay: Long) {
        val carAppFocusManager = car.getCarManager(Car.APP_FOCUS_SERVICE) as CarAppFocusManager
        Log.d(TAG, "Requested navigation start in $delay ms")

        startTask?.cancel()
        delayCounter = DelayCounter(delay)
        delayCounter?.start()
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
            delayCounter?.cancel()
            delayCounter = null
            startTask = null
        }
    }

    fun stopNavigation(car: Car, delay: Long) {
        val carAppFocusManager = car.getCarManager(Car.APP_FOCUS_SERVICE) as CarAppFocusManager
        Log.d(TAG, "Requested navigation stop in $delay ms")
        stopTask?.cancel()
        delayCounter = DelayCounter(delay)
        delayCounter?.start()
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
            delayCounter?.cancel()
            delayCounter = null
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

    fun speechAnnouncement(mediaPlayer: MediaPlayer, delay: Long) {

        Log.d(TAG, "Requested navigation announcement in $delay ms")

        // announcementTask?.cancel()
        if (announcementTask == null) {
            announcementTask = Timer().schedule(delay) {
                Log.d(TAG, "Navigation announcement timer fired")
                mediaPlayer.start()

                Thread.sleep(3000)
                mediaPlayer.release()
                Log.d(TAG, "mediaPlayer released")

                announcementTask = null
            }
        } else {
            Log.d(TAG, "Navigation announcement already scheduled, new announcement will be discarded")
        }
    }

    class NavCallback : CarAppFocusManager.OnAppFocusOwnershipCallback {
        override fun onAppFocusOwnershipLost(appType: Int) {
            Log.d(TAG, "Navigation focus lost")
        }

        override fun onAppFocusOwnershipGranted(appType: Int) {
            Log.d(TAG, "Navigation focus granted")
        }
    }

    class DelayCounter(val delay: Long) : CountDownTimer(delay, 500) {
        override fun onFinish() {
            Log.d(TAG, "Navigation announcement timer finished")
        }

        override fun onTick(millisUntilFinished: Long) {
            countdown.postValue(millisUntilFinished / 1000)
        }
    }
}

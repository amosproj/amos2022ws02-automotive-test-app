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
    private lateinit var carAppFocusManager: CarAppFocusManager
    val navCallback = NavCallback()
    private var startTask: CountDownTimer? = null
    val startTaskActive: Boolean get() = startTask != null
    val stopTaskActive: Boolean get() = stopTask != null
    private var stopTask: CountDownTimer? = null
    private var announcementTask: TimerTask? = null
    val navIndicatorLiveData: MutableLiveData<Boolean> = MutableLiveData<Boolean>(false)
    val countdown: MutableLiveData<Long> = MutableLiveData<Long>(0)

    fun registerCarAppFocusManager(car: Car) {
        if (!::carAppFocusManager.isInitialized) {
            this.carAppFocusManager = car.getCarManager(Car.APP_FOCUS_SERVICE) as CarAppFocusManager
        }
    }

    private fun startNavigation(delay: Long) {
        Log.d(TAG, "Requested navigation start in $delay ms")
        startTask?.cancel()
        startTask = StartTask(carAppFocusManager, delay)
        startTask?.start()
    }

    private fun stopNavigation(delay: Long) {
        Log.d(TAG, "Requested navigation stop in $delay ms")
        stopTask?.cancel()
        stopTask = StopTask(carAppFocusManager, delay)
        stopTask?.start()
    }

    fun performNavigationAction(delay: Long) {
        if (startTask == null && stopTask == null) {
            if (navIndicatorLiveData.value == true) {
                stopNavigation(delay)
            } else {
                startNavigation(delay)
            }
        } else {
            if (startTaskActive) {
                startTask?.cancel()
                startTask = null
            } else {
                stopTask?.cancel()
                stopTask = null
            }
            navIndicatorLiveData.postValue(navIndicatorLiveData.value)
        }
    }

    fun updateNavigationLiveData() {
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
            Log.d(
                TAG,
                "Navigation announcement already scheduled, new announcement will be discarded"
            )
        }
    }

    class NavCallback : CarAppFocusManager.OnAppFocusOwnershipCallback {
        override fun onAppFocusOwnershipLost(appType: Int) {
            Log.d(TAG, "Navigation focus lost")
            updateNavigationLiveData()

        }

        override fun onAppFocusOwnershipGranted(appType: Int) {
            Log.d(TAG, "Navigation focus granted")
            updateNavigationLiveData()
        }
    }

    class StartTask(private val carAppFocusManager: CarAppFocusManager, val delay: Long) :
        CountDownTimer(delay, 500) {
        override fun onTick(millisUntilFinished: Long) {
            Log.d(TAG, "Start navigation timer ticked")
            countdown.postValue(millisUntilFinished / 1000)
        }

        override fun onFinish() {
            Log.d(TAG, "Start navigation timer finished")
            countdown.postValue(0)
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

    class StopTask(private val carAppFocusManager: CarAppFocusManager, val delay: Long) :
        CountDownTimer(delay, 500) {
        override fun onTick(millisUntilFinished: Long) {
            Log.d(TAG, "Stop navigation timer ticked")
            countdown.postValue(millisUntilFinished / 1000)
        }

        override fun onFinish() {
            Log.d(TAG, "Stop navigation timer finished")
            countdown.postValue(0)
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


}

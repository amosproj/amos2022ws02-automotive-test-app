package com.amos.infotaimos

import android.content.Context
import androidx.lifecycle.ViewModel
import com.amos.infotaimos.model.CarInstanceManager
import com.amos.infotaimos.model.NavigationService
import com.amos.infotaimos.model.NotificationManager
import com.amos.infotaimos.model.SpeechService

class MainActivityViewModel(context: Context) : ViewModel() {

    val navIndicatorLiveData = NavigationService.navIndicatorLiveData

    fun updateNavigationLiveData(context: Context) {
        NavigationService.updateNavigationLiveData(CarInstanceManager.getCarInstance(context))
    }

    fun setupNotificationChannel(context: Context) {
        NotificationManager.createNotificationChannel(context)
    }

    fun setupSpeechService(permissionGranted :Boolean){
        SpeechService.setupSpeechService(permissionGranted)
    }
}

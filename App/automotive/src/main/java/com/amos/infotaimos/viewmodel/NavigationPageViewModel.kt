package com.amos.infotaimos.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.amos.infotaimos.model.CarInstanceManager
import com.amos.infotaimos.model.NavigationService

class NavigationPageViewModel : ViewModel() {

    fun startNavigation(context: Context, delay: Long) {
        NavigationService.startNavigation(CarInstanceManager.getCarInstance(context), delay)
    }

    fun stopNavigation(context: Context, delay: Long) {
        NavigationService.stopNavigation(CarInstanceManager.getCarInstance(context), delay)
    }
}

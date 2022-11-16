package com.amos.infotaimos

import android.content.Context
import androidx.lifecycle.ViewModel
import com.amos.infotaimos.model.CarInstanceManager
import com.amos.infotaimos.model.NavigationService

class MainActivityViewModel(context: Context) : ViewModel() {

    val navIndicatorLiveData = NavigationService.navIndicatorLiveData

    fun updateNavigationLiveData(context: Context) {
        NavigationService.updateNavigationLiveData(CarInstanceManager.getCarInstance(context))
    }
}

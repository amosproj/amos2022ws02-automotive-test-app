package com.amos.infotaimos

import android.annotation.SuppressLint
import android.content.Context
import android.view.MenuItem
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

    fun setNavMenuItems(navActiveMenuItem: MenuItem, navNotActiveMenuItem: MenuItem) {
        NavigationService.setMenuItems(navActiveMenuItem, navNotActiveMenuItem)
    }

}

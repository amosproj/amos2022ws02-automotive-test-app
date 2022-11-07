package com.amos.infotaimos

import androidx.lifecycle.ViewModel

class NavigationPageViewModel : ViewModel() {
    val model = Model()
    fun startNavigation(delay: Int) {
        model.startNavigation(delay)
    }
}
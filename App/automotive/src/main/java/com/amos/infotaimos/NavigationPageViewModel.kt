package com.amos.infotaimos

import android.content.Context
import androidx.lifecycle.ViewModel

class NavigationPageViewModel(context: Context) : ViewModel() {
    private var model = Model(context)
    fun startNavigation(delay: Int) {
        model.startNavigation(delay)
    }
}
package com.amos.infotaimos.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.amos.infotaimos.model.KeyEventControl

class WheelPageViewModel : ViewModel() {
    fun handleButtonPress(context: Context, action: Int) {
        KeyEventControl.handleButtonPress(context,action)
    }
}

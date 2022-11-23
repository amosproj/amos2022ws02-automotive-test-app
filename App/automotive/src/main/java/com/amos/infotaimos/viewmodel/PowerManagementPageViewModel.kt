package com.amos.infotaimos.viewmodel

import android.app.admin.DevicePolicyManager
import android.content.Context
import android.os.PowerManager
import androidx.lifecycle.ViewModel

class PowerManagementPageViewModel : ViewModel() {
    fun turnoffDisplay(context: Context) {
        val manager = context.getSystemService(Context.DEVICE_POLICY_SERVICE) as DevicePolicyManager
        manager.lockNow()
    }
}

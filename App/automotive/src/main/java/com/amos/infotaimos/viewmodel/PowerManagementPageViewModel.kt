package com.amos.infotaimos.viewmodel

import android.content.Context
import android.media.AudioManager
import androidx.lifecycle.ViewModel
import com.amos.infotaimos.model.PowerManagementService

class PowerManagementPageViewModel : ViewModel() {

    fun muteSystem(context: Context, delay: Long) {
        PowerManagementService.muteSystem(
            context.getSystemService(Context.AUDIO_SERVICE) as AudioManager,
            delay
        )
    }

    fun unmuteSystem(context: Context, delay: Long) {
        PowerManagementService.unmuteSystem(
            context.getSystemService(Context.AUDIO_SERVICE) as AudioManager,
            delay
        )
    }

    fun toggleMuteSystem(context: Context, delay: Long) {
        PowerManagementService.toggleMuteSystem(
            context.getSystemService(Context.AUDIO_SERVICE) as AudioManager,
            delay
        )
    }
}

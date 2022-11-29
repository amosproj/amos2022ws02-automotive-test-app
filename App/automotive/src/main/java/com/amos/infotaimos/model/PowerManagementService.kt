package com.amos.infotaimos.model

import android.media.AudioManager
import android.util.Log
import java.util.*
import kotlin.concurrent.schedule

object PowerManagementService {
    private const val TAG = "POWERMANAGEMENT_SERVICE"
    private var toggleTask: TimerTask? = null

    fun muteSystem(audioManager: AudioManager, delay: Long) {
        Log.d(TAG, "Requested system mute in $delay ms")

        toggleTask?.cancel()
        toggleTask = Timer().schedule(delay) {
            Log.d(TAG, "mute system timer fired")

            if (audioManager.isVolumeFixed) {
                Log.w(TAG, "audio volume is fixed and can not be muted")
            } else {
                Log.d(TAG, "requesting system mute")
                audioManager.adjustVolume(AudioManager.ADJUST_MUTE, 0)
            }
            toggleTask = null
        }

    }

    fun unmuteSystem(audioManager: AudioManager, delay: Long) {
        Log.d(TAG, "Requested system unmute in $delay ms")

        toggleTask?.cancel()
        toggleTask = Timer().schedule(delay) {
            Log.d(TAG, "unmute system timer fired")

            if (audioManager.isVolumeFixed) {
                Log.w(TAG, "audio volume is fixed and can not be unmuted")
            } else {
                Log.d(TAG, "requesting system unmute")
                audioManager.adjustVolume(AudioManager.ADJUST_MUTE, 1)
            }
            toggleTask = null
        }

    }

    fun toggleMuteSystem(audioManager: AudioManager, delay: Long) {
        Log.d(TAG, "Requested system (un)mute in $delay ms")

        toggleTask?.cancel()
        toggleTask = Timer().schedule(delay) {
            Log.d(TAG, "(un)mute system timer fired")

            if (audioManager.isVolumeFixed) {
                Log.w(TAG, "audio volume is fixed and can not be (un)muted")
            } else {
                Log.d(TAG, "requesting system (un)mute")
                audioManager.adjustVolume(AudioManager.ADJUST_TOGGLE_MUTE, 1)
            }
            toggleTask = null
        }

    }


}
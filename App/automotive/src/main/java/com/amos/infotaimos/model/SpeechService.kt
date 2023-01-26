package com.amos.infotaimos.model

import android.content.Context
import android.service.voice.VoiceInteractionSession
import android.util.Log
import androidx.core.os.bundleOf

object SpeechService {
    private const val TAG = "SPEECH_SERVICE"
    private var recordAudioPermissionGranted = false

    private var voiceInteractionSession: VoiceInteractionSession? = null

    fun startPTT(context: Context, delay: Long) {
        triggerVoiceActivity(context, delay)
    }

    fun startTTT(context: Context, delay: Long) {
        triggerVoiceActivity(context, delay)
    }


    private fun triggerVoiceActivity(context: Context, delay: Long) {
        if (voiceInteractionSession == null) {
            voiceInteractionSession =
                AmosVoiceInteractionSessionService(context).onNewSession(null)
        }

        if (recordAudioPermissionGranted) {
            val delayBundle = bundleOf("delay" to (delay) )
            voiceInteractionSession!!.onShow(
                delayBundle, VoiceInteractionSession.SHOW_WITH_ASSIST,
            )

        } else
            Log.w(TAG, "Record Audio permission not granted")
    }

    fun setupSpeechService(permissionGranted: Boolean) {
        Log.d(TAG, "permission: $permissionGranted")
        recordAudioPermissionGranted = permissionGranted
    }
}


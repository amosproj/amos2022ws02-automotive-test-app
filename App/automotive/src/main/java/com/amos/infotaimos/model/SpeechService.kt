package com.amos.infotaimos.model

import android.content.Context
import android.service.voice.VoiceInteractionSession
import android.util.Log
import java.util.*

object SpeechService {
    private const val TAG = "SPEECH_SERVICE"
    private var speechTask: TimerTask? = null
    private var recordAudioPermissionGranted = false
    private var voiceInteractionSession: VoiceInteractionSession? = null

    fun startPTT(context: Context) {
        triggerVoiceActivity(context)
    }

    fun startTTT(context: Context) {
        triggerVoiceActivity(context)
    }


    private fun triggerVoiceActivity(context: Context) {
        Log.d(TAG, "Start Voice Activity")

        if (voiceInteractionSession == null) {
            voiceInteractionSession =
                AmosVoiceInteractionSessionService(context).onNewSession(null)
        }

        if (recordAudioPermissionGranted) {
            voiceInteractionSession!!.onShow(
                null, VoiceInteractionSession.SHOW_WITH_ASSIST
            )

        } else
            Log.w(TAG, "Record Audio permission not granted")

    }

    fun setupSpeechService(permissionGranted: Boolean) {
        Log.d(TAG, "permission: $permissionGranted")
        recordAudioPermissionGranted = permissionGranted
    }
}

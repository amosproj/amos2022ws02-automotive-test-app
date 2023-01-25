package com.amos.infotaimos.model

import android.content.Context
import android.service.voice.VoiceInteractionSession
import android.util.Log

object SpeechService {
    private const val TAG = "SPEECH_SERVICE"
    private var usedTTT: Boolean = false
    private var usedPTT: Boolean = false
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

    fun getUsedPTT() : Boolean{
        return usedPTT;
    }

    fun setUsedPTT(newUsed: Boolean){
        usedPTT = newUsed;
    }
    fun getUsedTTT() : Boolean{
        return usedTTT;
    }

    fun setUsedTTT(newUsed: Boolean){
        usedTTT = newUsed;
    }
}


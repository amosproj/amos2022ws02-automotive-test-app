package com.amos.infotaimos.model

import android.content.Context
import android.content.Intent
import android.service.voice.VoiceInteractionService
import android.service.voice.VoiceInteractionSession

object SpeechService {
    private const val TAG = "SPEECH_SERVICE"


    fun startPTT(context: Context){
        //voiceInteractionSession.onCreate()
        //voiceInteractionSession.startVoiceActivity(Intent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        AmosVoiceInteractionSessionService(context).onNewSession(null).startVoiceActivity(null)
    }
}
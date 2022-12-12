package com.amos.infotaimos.viewmodel

import android.content.Context
import android.service.voice.VoiceInteractionSession
import androidx.lifecycle.ViewModel
import com.amos.infotaimos.model.AmosVoiceInteractionSessionService
import com.amos.infotaimos.model.SpeechService

class SpeechAssistantViewModel : ViewModel() {


    fun startPTT(context: Context) {
        val amosVoiceInteractionSessionService = AmosVoiceInteractionSessionService(context)
        SpeechService.startPTT(amosVoiceInteractionSessionService)
    }
}
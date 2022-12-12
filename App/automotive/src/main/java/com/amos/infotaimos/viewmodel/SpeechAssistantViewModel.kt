package com.amos.infotaimos.viewmodel

import android.content.Context
import android.service.voice.VoiceInteractionSession
import androidx.lifecycle.ViewModel
import com.amos.infotaimos.model.SpeechService

class SpeechAssistantViewModel : ViewModel() {
    fun startPTT (context: Context){
        SpeechService.startPTT(context)
    }
}
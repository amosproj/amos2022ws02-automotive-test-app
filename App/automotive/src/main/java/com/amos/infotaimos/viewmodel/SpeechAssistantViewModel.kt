package com.amos.infotaimos.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.amos.infotaimos.model.SpeechService

class SpeechAssistantViewModel : ViewModel() {

    fun startPTT(context: Context, delay: Long) {
        SpeechService.startPTT(context, delay)
    }

    fun startTTT(context: Context, delay: Long){
        SpeechService.startTTT(context, delay)
    }
}
































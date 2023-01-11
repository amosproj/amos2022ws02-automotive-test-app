package com.amos.infotaimos.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.amos.infotaimos.model.SpeechService

class SpeechAssistantViewModel : ViewModel() {


    fun startPTT(context: Context) {
        SpeechService.startPTT(context)
    }

    fun startTTT(context: Context){
        SpeechService.startTTT(context)
    }

}
































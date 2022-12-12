package com.amos.infotaimos.model

import android.content.Context
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.service.voice.VoiceInteractionSessionService

class AmosVoiceInteractionSessionService(context: Context): VoiceInteractionSessionService() {
    val cont = context
    override fun onNewSession(args: Bundle?): VoiceInteractionSession {
        return AmosVoiceInteractionSession(cont)
    }

}
package com.amos.infotaimos.model

import android.content.Context
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.service.voice.VoiceInteractionSessionService

class AmosVoiceInteractionSessionService(val context: Context): VoiceInteractionSessionService() {

    override fun onNewSession(args: Bundle?): VoiceInteractionSession {
        return AmosVoiceInteractionSession(context)

    }
}

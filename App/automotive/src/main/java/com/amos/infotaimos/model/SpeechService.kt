package com.amos.infotaimos.model


object SpeechService {
    private const val TAG = "SPEECH_SERVICE"


    fun startPTT(voiceSessionService: AmosVoiceInteractionSessionService) {
        //voiceInteractionSession.onCreate()
        //voiceInteractionSession.startVoiceActivity(Intent().addFlags(Intent.FLAG_ACTIVITY_NEW_TASK))
        voiceSessionService.onNewSession(null).startVoiceActivity(null)
    }
}
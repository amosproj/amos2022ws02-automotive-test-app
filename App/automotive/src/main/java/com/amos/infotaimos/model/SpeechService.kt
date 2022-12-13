package com.amos.infotaimos.model

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.service.voice.VoiceInteractionService
import android.service.voice.VoiceInteractionSession
import android.util.Log
import com.amos.infotaimos.R
import java.util.*
import kotlin.concurrent.schedule

object SpeechService {
    private const val TAG = "SPEECH_SERVICE"
    private var speechTask: TimerTask? = null

    fun startPTT(audioManager: AudioManager, mediaPlayer: MediaPlayer) {
        Log.d(TAG, "Start Voice Activity")

        val audioFocusRequest =
            AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE).build()

        if (speechTask == null) {
            speechTask = Timer().schedule(0) {
                val stat = audioManager.requestAudioFocus(audioFocusRequest)
                if (stat == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    Thread.sleep(5000)
                    audioManager.abandonAudioFocusRequest(audioFocusRequest)
                }
                mediaPlayer.start()
                Thread.sleep(1500)
                mediaPlayer.release()
                speechTask = null
            }
        }
    }
}
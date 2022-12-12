package com.amos.infotaimos.model

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.util.Log
import com.amos.infotaimos.R
import java.util.*
import kotlin.concurrent.schedule

class AmosVoiceInteractionSession(context: Context) : VoiceInteractionSession(context) {

    private val TAG = "VOICEINTERACTIONSESSION"
    private var speechTask: TimerTask? = null


    override fun onShow(args: Bundle?, showFlags: Int) {
        super.onShow(args, showFlags)
    }

    override fun startVoiceActivity(intent: Intent?) {
        Log.d(TAG, "Start Voice Activity")
        //super.startVoiceActivity(intent)
        val car = CarInstanceManager.getCarInstance(context)
        //val audioManager = car.getCarManager(Car.AUDIO_SERVICE) as AudioManager
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager

        val audioFocusRequest =
            AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE).build()

        //Log.d(TAG, (speechTask).toString())
        if (speechTask == null) {
            //Log.d(TAG, (speechTask).toString())
            speechTask = Timer().schedule(0) {
                //Log.d(TAG, (speechTask).toString())
                val stat = audioManager.requestAudioFocus(audioFocusRequest)
                if (stat == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    Thread.sleep(5000)
                    audioManager.abandonAudioFocusRequest(audioFocusRequest)
                    val beepUri = Uri.Builder()
                        .scheme(ContentResolver.SCHEME_ANDROID_RESOURCE)
                        .authority(context.resources.getResourcePackageName(R.raw.beep))
                        .appendPath(context.resources.getResourceTypeName(R.raw.beep))
                        .appendPath(context.resources.getResourceEntryName(R.raw.beep))
                        .build()

                    val mediaPlayer = MediaPlayer().apply {
                        setAudioAttributes(
                            AudioAttributes.Builder()
                                .setUsage(AudioAttributes.USAGE_ASSISTANT)
                                .setContentType(AudioAttributes.CONTENT_TYPE_SPEECH)
                                .build()
                        )
                        setDataSource(context, beepUri)
                        prepare()
                    }
                    mediaPlayer.start()
                    Thread.sleep(1500)
                    mediaPlayer.release()
                }

                speechTask = null
            }
        }

    }
}
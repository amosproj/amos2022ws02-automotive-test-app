package com.amos.infotaimos.model

import android.content.ContentResolver
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioFocusRequest
import android.media.AudioManager
import android.media.MediaPlayer
import android.media.MediaRecorder
import android.net.Uri
import android.os.Bundle
import android.service.voice.VoiceInteractionSession
import android.util.Log
import com.amos.infotaimos.R
import java.util.*
import kotlin.concurrent.schedule

class AmosVoiceInteractionSession(context: Context) : VoiceInteractionSession(context) {

    private val TAG = "SPEECH_SERVICE"
    private var speechTask: TimerTask? = null

    override fun onShow(args: Bundle?, showFlags: Int) {
        super.onShow(args, showFlags)
        val audioManager = context.getSystemService(Context.AUDIO_SERVICE) as AudioManager
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
        val mediaRecorder = MediaRecorder()
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.VOICE_RECOGNITION)
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP)
        mediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AMR_NB)
        mediaRecorder.setOutputFile("/dev/null")
        mediaRecorder.prepare()


        val audioFocusRequest =
            AudioFocusRequest.Builder(AudioManager.AUDIOFOCUS_GAIN_TRANSIENT_EXCLUSIVE).build()

        if (speechTask == null) {
            Log.d(TAG, "Start Voice Activity")

            val delay = args!!.getLong("delay")
            speechTask = Timer().schedule(delay) {
                val stat = audioManager.requestAudioFocus(audioFocusRequest)
                if (stat == AudioManager.AUDIOFOCUS_REQUEST_GRANTED) {
                    Log.d(TAG, "Start Recording")

                    mediaRecorder.start()
                    Thread.sleep(5000)
                    mediaRecorder.stop()

                    audioManager.abandonAudioFocusRequest(audioFocusRequest)
                }
                mediaPlayer.start()
                Thread.sleep(1500)

                mediaRecorder.release()
                mediaPlayer.release()

                speechTask = null
            }
        }
        else {
            Log.d(TAG, "Speech Assistant is already in use")
        }

    }


}
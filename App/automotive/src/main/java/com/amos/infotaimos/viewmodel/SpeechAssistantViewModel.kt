package com.amos.infotaimos.viewmodel

import android.content.ContentResolver
import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.MediaPlayer
import android.net.Uri
import androidx.lifecycle.ViewModel
import com.amos.infotaimos.R
import com.amos.infotaimos.model.SpeechService

class SpeechAssistantViewModel : ViewModel() {


    fun startPTT(context: Context) {
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
        SpeechService.startPTT(audioManager, mediaPlayer)
    }
}
package com.amos.infotaimos.model

import android.app.Service
import android.content.Intent
import android.media.AudioFormat
import android.media.AudioManager
import android.media.AudioTrack
import android.media.session.MediaSession
import android.os.Build
import android.os.IBinder
import android.view.KeyEvent
import androidx.lifecycle.MutableLiveData

/**
 * Service class currently only used for tests as we dont handle media events inside app for now
 */
class MediaService: Service() {
    private lateinit var mediaSession: MediaSession

    @Override
    override fun onBind(intent: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        mediaSession = MediaSession(this, "InfotAiMOS_Media_Service")
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        mediaSession.setCallback(object : MediaSession.Callback() {
            override fun onMediaButtonEvent(mediaButtonIntent: Intent): Boolean {
                val keyEvent : KeyEvent? = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    mediaButtonIntent.getParcelableExtra(Intent.EXTRA_KEY_EVENT, KeyEvent::class.java)
                } else {
                    mediaButtonIntent.getParcelableExtra(Intent.EXTRA_KEY_EVENT)
                }
                keyEvent?.let {
                    LAST_MEDIA_KEY.value = it.keyCode
                    return true
                }
                return false
            }
        })
        mediaSession.isActive = true
        //deprecated but its just a dummy anyways
        AudioTrack(
            AudioManager.STREAM_MUSIC,
            48000,
            AudioFormat.CHANNEL_OUT_STEREO,
            AudioFormat.ENCODING_PCM_16BIT,
            AudioTrack.getMinBufferSize(
                48000,
                AudioFormat.CHANNEL_OUT_STEREO,
                AudioFormat.ENCODING_PCM_16BIT
            ),
            AudioTrack.MODE_STREAM
        ).play()


        return START_STICKY;
    }

    companion object {
        var LAST_MEDIA_KEY = MutableLiveData<Int?>(null)
    }
}
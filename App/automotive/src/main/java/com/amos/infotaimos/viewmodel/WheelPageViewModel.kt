package com.amos.infotaimos.viewmodel

import android.app.Instrumentation
import android.content.Context
import android.media.AudioManager
import android.view.KeyEvent
import androidx.lifecycle.ViewModel

class WheelPageViewModel : ViewModel() {
    fun handleButtonPress(context: Context, action: Int) {
        when (action) {
            KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, KeyEvent.KEYCODE_MEDIA_NEXT, KeyEvent.KEYCODE_MEDIA_FAST_FORWARD -> {
                executeMediaButtonPress(
                    (context.getSystemService(Context.AUDIO_SERVICE) as AudioManager),
                    action
                )
            }
            KeyEvent.KEYCODE_VOICE_ASSIST -> {
                executeVoiceControlButtonPress(action)
            }
            else -> {
                TODO("Implement other actions")
            }
        }
    }

    private fun executeMediaButtonPress(audioManager: AudioManager, keyEvent: Int) {
        audioManager.dispatchMediaKeyEvent(KeyEvent(KeyEvent.ACTION_DOWN, keyEvent))
        audioManager.dispatchMediaKeyEvent(KeyEvent(KeyEvent.ACTION_UP, keyEvent))
    }

    private fun executeVoiceControlButtonPress(keyEvent: Int) {
        Thread(
            kotlinx.coroutines.Runnable {
                val inst = Instrumentation()
                inst.sendKeyDownUpSync(keyEvent)
            }
        ).start()
    }
}

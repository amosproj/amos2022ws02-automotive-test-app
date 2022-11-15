package com.amos.infotaimos.viewmodel

import android.content.Context
import android.media.AudioManager
import android.view.KeyEvent
import androidx.lifecycle.ViewModel

class WheelPageViewModel : ViewModel() {
    fun handleButtonPress(context: Context, action: Int) {
        when (action) {
            KeyEvent.KEYCODE_MEDIA_PLAY_PAUSE, KeyEvent.KEYCODE_MEDIA_NEXT -> {
                executeMediaButtonPress(
                    (context.getSystemService(Context.AUDIO_SERVICE) as AudioManager),
                    action
                )
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
}

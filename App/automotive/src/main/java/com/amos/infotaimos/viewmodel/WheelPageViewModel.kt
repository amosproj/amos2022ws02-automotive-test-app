package com.amos.infotaimos.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import com.amos.infotaimos.model.ButtonSequence
import com.amos.infotaimos.model.ButtonSequenceStoreService
import com.amos.infotaimos.model.KeyEventControl
import java.util.UUID

class WheelPageViewModel : ViewModel() {

    private val currentSequence = mutableListOf<Int>()

    fun startRecording() {
        currentSequence.clear()
    }

    fun stopRecording(context: Context) {
        if(currentSequence.isNotEmpty()) {
            val sequence = ButtonSequence(UUID.randomUUID().toString(), currentSequence)
            ButtonSequenceStoreService.saveButtonSequence(context, sequence)
        }
    }

    fun handleButtonPress(context: Context, action: Int) {
        currentSequence.add(action)
        KeyEventControl.handleButtonPress(context, action)
    }
}

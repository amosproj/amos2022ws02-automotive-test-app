package com.amos.infotaimos.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.amos.infotaimos.model.ButtonSequence
import com.amos.infotaimos.model.KeyEventControl
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ButtonSequenceViewModel : ViewModel() {
    fun playSequence(context: Context, sequence: ButtonSequence) {
        viewModelScope.launch(Dispatchers.IO) {
            sequence.sequence.forEach { keyCode ->
                KeyEventControl.handleButtonPress(context, keyCode)
            }
        }
    }
}